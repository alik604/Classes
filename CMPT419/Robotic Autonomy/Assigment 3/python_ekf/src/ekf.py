#!/usr/bin/env python

import rospy
import numpy as np

from tf.transformations import quaternion_from_euler
from geometry_msgs.msg import *
from nav_msgs.msg import Odometry
from nuslam.msg import TurtleMap


def ensure_bound(theta):
    # not sure if this is needed, but better be safe than sorry. what if you hold down 'A' or 'D'...
    theta = theta % (2 * np.pi)
    if theta > np.pi:
        theta -= 2 * np.pi
    elif theta < -np.pi:
        theta += 2 * np.pi
    return theta


class Struct():
    """ For Wheel_Velocities, Pose, Range and Bearing.
    This class is just a public naked Struct...
    which should have been a dict() """

    def __init__(self):
        self.ul = 0
        self.ur = 0
        self.x = 0
        self.y = 0
        self.theta = 0
        self.range = 0
        self.bearing = 0


class EKF():
    def __init__(self, robot_state, map_state, xyt_noise_var, rb_noise_var, mahalanobis_lower=100, mahalanobis_upper=1e5):
        self.state = np.zeros(3 + 2*map_state.shape[0])
        self.robot_state = np.array([0.1, 0.1, -0.1]) # since msr_update is not working
        self.map_state = map_state

        self.msr_noise = np.diagflat(np.array([rb_noise_var.range,rb_noise_var.bearing]).T)
        self.proc_noise = self.ProcessNoise(xyt_noise_var, map_state.shape[0])

        self.counter = 0
        self.range = 0
        self.bearing = 0
        self.polarCords = np.zeros((2, 1))
        self.pose = np.array([0, 0])
        self.mahalanobis_lower = mahalanobis_lower
        self.mahalanobis_upper = mahalanobis_upper

        left_mtx = np.concatenate((np.eye(3*map_state.shape[0]), np.zeros((2*map_state.shape[0], 3))  ))
        right_mtx = np.concatenate(( np.zeros((3, 2*map_state.shape[0])), np.eye(2*map_state.shape[0]) )) 
        self.cov_mtx = np.hstack((left_mtx, right_mtx))

    def ProcessNoise(self, xyt_noise_var, const):
        # we dont really care about the content,
        # only the shape of the output is ever used.
        # which is in `getMultivarNoise` 
        Q = np.diagflat(
            [xyt_noise_var.theta, xyt_noise_var.x, xyt_noise_var.y])
        M = np.hstack((np.concatenate((Q, np.zeros((2*const, 3)))),
                         np.concatenate((np.zeros((3, 2*const)), np.zeros((2*const, 2*const))))))
        return M

    def getNoise(self, noise):
        # tmp = noise[~np.all(noise == 0, axis=1)]
        # tmp = tmp[~np.all(tmp == 0, axis=0)]
        p = np.where(noise != 0)
        noise = noise[min(p[0]): max(p[0]) + 1, min(p[1]): max(p[1]) + 1] # from StackOverflow
        tmp = np.linalg.cholesky(noise)

        L = np.zeros(tmp.shape)
        mtx_dimension = L.shape[1]

        # col vect. `.transpose()` did not work
        mat = np.zeros((mtx_dimension, 1))
        for i in range(mtx_dimension):
            val = np.random.normal(0, 1)
            mat[i] = val
        tmp = L*mat
        # a lot of work for a trivial matter of 3 randn floats
        return tmp[0][0], tmp[1][1], tmp[2][2]

    def predict(self, twist):
        self.robot_state[2] = ensure_bound(self.robot_state[2])
        theta = ensure_bound(self.robot_state[2])
        a, b, c = self.getNoise(self.proc_noise)  # 3 randn
        a *=10
        g = np.zeros((5, 5))
        # print('theta is', theta, 'twist[2] is', twist[2])
        if abs(twist[2]) < 0.001: # string-ish
            # print('here')
            self.robot_state[0] = self.robot_state[0] + (twist[0] * ensure_bound(np.cos(theta))) + b
            self.robot_state[1] = self.robot_state[1] + (twist[0] * ensure_bound(np.sin(theta))) + c
            self.robot_state[2] = theta + a
            g[1, 0] = -twist[0] * ensure_bound(np.sin(theta))
            g[2, 0] =  twist[0] * ensure_bound(np.cos(theta))
        else:
            # twist = np.array(twist) + 1e-3
            self.robot_state[0] = self.robot_state[0] + ((-twist[0] / twist[2]) * ensure_bound(np.sin(theta)) + ( twist[0] / twist[2]) * ensure_bound(np.sin(theta + twist[2]))) + b
            self.robot_state[1] = self.robot_state[1] + (( twist[0] / twist[2]) * ensure_bound(np.cos(theta)) + (-twist[0] / twist[2]) * ensure_bound(np.cos(theta + twist[2]))) + c
            self.robot_state[2] = theta + twist[0] + a
            g[1, 0] = (-twist[0] / twist[2]) * ensure_bound(np.cos(theta)) + (twist[0] / twist[2]) * ensure_bound(np.cos(theta + twist[2]))
            g[2, 0] = (-twist[0] / twist[2]) * ensure_bound(np.sin(theta)) + (twist[0] / twist[2]) * ensure_bound(np.sin(theta + twist[2]))

        G = np.identity(5) + g  # g as matrix
        self.cov_mtx = G * self.cov_mtx * G.T + self.proc_noise
        self.robot_state[2] = ensure_bound(self.robot_state[2])
        # print('twist', twist)
        # print('self.robot_state', self.robot_state)
        self.state[0] = self.robot_state[2]
        self.state[1] = self.robot_state[0]
        self.state[2] = self.robot_state[1]

    def mahalanobis_test(self, z):
            d_k = []
            if self.counter == 0:
                d_k.append(1e12)
            for i in range(self.counter): # it can NEVER be 1 or else idx out of bounds
                H = self.inv_msr_model(i) #done
                # print(H.shape) # 2,5
                # print(self.cov_mtx.shape) # 5,5
                # print(H.transpose().shape) # 5,2
                # print(self.msr_noise)

                psi = H.dot(self.cov_mtx)  # 2,5 * 5,5 = 2,5
                psi = psi.dot(H.transpose()) # 2,5 * 5,2 = 2,2
                psi = psi + self.msr_noise

                self.pose = np.array([self.state[3]-self.state[1], self.state[4]-self.state[2]])
                self.range = np.sqrt((self.state[0]*self.state[0]) + (self.state[1]*self.state[1]))
                
                self.bearing = ensure_bound(np.arctan2(self.state[1], self.state[0]) - self.state[0])
                self.bearing = ensure_bound(self.bearing)

                z_diff = self.polarCords - np.array([self.range, self.bearing])
                z_diff[1] = ensure_bound(z_diff[1])
                d_k.append(z_diff.T * np.linalg.pinv(psi) * z_diff)
            
            return d_k

    def inv_msr_model(self, j):
        x_diff = self.state[3]-self.state[1]
        y_diff = self.state[4]-self.state[2]

        squared_diff = np.sqrt(np.power(x_diff, 2) + np.power(y_diff, 2))
        if squared_diff < 0.1 or squared_diff > 10:
            squared_diff = 1

        lefty = np.zeros((2, 3))
        lefty[0, 1] = 0.0
        lefty[0, 1] = -x_diff/ squared_diff
        lefty[0, 2] = -y_diff/ squared_diff
        lefty[1, 0] = -1.0
        lefty[1, 1] =  y_diff/ squared_diff
        lefty[1, 2] = -x_diff/ squared_diff

        righty = np.zeros((2, 2))
        righty[0, 0] = x_diff / squared_diff
        righty[0, 1] = y_diff / squared_diff
        righty[1, 0] = -y_diff/ squared_diff
        righty[1, 1] = x_diff / squared_diff

        return np.hstack((lefty, righty )) # (2,5)

    def msr_update(self, measurements_):
        for i in range(len(measurements_)):
            self.polarCords[0] = self.range + np.random.normal(0, 1)
            self.polarCords[1] = self.bearing + np.random.normal(0, 1)
            self.polarCords[1] = ensure_bound(self.polarCords[1])

            d_k = self.mahalanobis_test(self.polarCords)
            d_star = np.amin(d_k)
            # im too scared to make this better
            if d_star < self.mahalanobis_lower or d_star > self.mahalanobis_upper:
                j = 0
                if d_star < self.mahalanobis_lower:
                    j = np.where(d_k ==np.amin(d_k))
                elif d_star> self.mahalanobis_upper:
                    j = self.counter 
                    self.counter += 1
                    if self.counter <= 1:
                        self.state[3] = self.pose[0] + self.state[1]
                        self.state[4] = self.pose[1] + self.state[2]
                if self.counter <= 1:
                    self.pose = np.array([self.state[3] - self.state[1], self.state[4] - self.state[2]])
                    self.range = np.sqrt((self.pose[0] * self.pose[0]) + (self.pose[1] * self.pose[1]))
                    self.bearing = ensure_bound(np.arctan2(self.pose[1], self.pose[0]) - self.state[0])
                    self.bearing = ensure_bound(self.bearing)

                    H = self.inv_msr_model(j)
                    K = self.cov_mtx.dot(H.T).dot(np.linalg.pinv(H.dot(self.cov_mtx).dot(H.T) + self.msr_noise)) 

                    z_diff = self.polarCords - np.array([self.range, self.bearing]) 
                    z_diff[1] = ensure_bound(z_diff[1])

                    k_update = K.dot(z_diff)
                    self.state += k_update.flatten()

                    self.state[0] = ensure_bound(self.state[0])
                    self.cov_mtx = (np.identity(5) - K.dot(H)).dot(self.cov_mtx)
                else:
                    self.counter = 1
        self.robot_state[0] = self.state[1]
        self.robot_state[1] = self.state[2]
        self.robot_state[2] = ensure_bound(self.state[0])

# def js_cb(js):
#     # brief /joint_states subscriber callback. Records left and right wheel angles
#     w_vel = driver.dealWithOdometry(js.position.at(0), js.position.at(1))
#     Vb = driver.wheelsToTwist(w_vel)  # Vb is driver.Vb

def landmark_cb(my_map):
    measurements = []
    for i in range(len(my_map.radii)):
        points = Struct()
        points.x = my_map.x_pts[i]
        points.y = my_map.y_pts[i]
        measurements.append(points)
    # ekf.msr_update(measurements) # TODO yeilds bad data. 

def odom_cb(msg):
    x = msg.twist.twist.linear.x
    y = msg.twist.twist.linear.y
    theta = msg.twist.twist.angular.z

    global ekf
    ekf.predict([x, y, theta])
    global callback_flag
    callback_flag = True

callback_flag = False

if __name__ == "__main__":
    rospy.loginfo("Starting ekf_ekf")
    rospy.init_node('ekf_ekf')

    # Init Subscribers
    rospy.Subscriber('odom', Odometry, odom_cb)
    rospy.Subscriber('analysis/landmarks', TurtleMap, landmark_cb)

    # Init Publishers
    odom_pub = rospy.Publisher("odom_ekf", Odometry, queue_size=100)
    # lnd_pub = rospy.Publisher("landmarks_ekf", TurtleMap, queue_size=100)

    frame_id = "second_map"

    # hyper prams
    X_NOISE = 1e-6
    Y_NOISE = 1e-6
    THETA_NOISE = 1e-5
    RANGE_NOISE = 1e-10
    BEARING_NOISE = 1e-10
    state = np.zeros(3)


    map_state = np.array([Point()])  # changing this will cause errors

    cord_noise_var = Struct()
    cord_noise_var.x = X_NOISE
    cord_noise_var.y = Y_NOISE
    cord_noise_var.theta = THETA_NOISE

    polar_noise_var = Struct()
    polar_noise_var.range = RANGE_NOISE
    polar_noise_var.bearing = BEARING_NOISE

    ekf = EKF(np.array(state), map_state, cord_noise_var, polar_noise_var)

    # Init Time
    current_time = rospy.Time.now()

    # Program Loop
    while not rospy.is_shutdown():

        # print("inside program loop")
        current_time = rospy.Time.now()

        if (callback_flag):
            ekf_state = ekf.robot_state
            # print(ekf_state)

            # Update and Publish Odom Msg
            odometry = Odometry()
            odometry.header.stamp = current_time
            odometry.header.frame_id = frame_id
            odometry.child_frame_id = frame_id

            # Update Pose
            odometry.pose.pose.position.x = ekf_state[0] # from slam.cpp 
            odometry.pose.pose.position.y = ekf_state[1]
            odometry.pose.pose.position.z = 0.0 #

            # Quaternion stuff based on CPP code
            ekf_q = quaternion_from_euler(0, 0, ekf_state[2]) # from slam.cpp 
            quat = Quaternion(ekf_q[0], ekf_q[1], ekf_q[2], ekf_q[3])
            # print(quat)
            odometry.pose.pose.orientation = quat

            odometry.twist.twist.linear.x = 0
            odometry.twist.twist.linear.y = 0
            odometry.twist.twist.linear.z = 0

            # Publish the Message
            odom_pub.publish(odometry)
            # print('odom_pub', odom_pub)
            # print('odometry', odometry)
            callback_flag = False

        rospy.sleep(0.1)
