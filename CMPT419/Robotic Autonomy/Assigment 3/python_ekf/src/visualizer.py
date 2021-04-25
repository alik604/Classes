#!/usr/bin/env python

import rospy
import numpy as np
from nav_msgs.msg import Odometry, Path 
from geometry_msgs.msg import PoseStamped


odom_callback_flag = False 
frame_id = "map" # second_map
list_of_poses = []

def odom_callback(odom):
  ps = PoseStamped()
  ps.header.frame_id = frame_id
  ps.header.stamp = rospy.Time.now()
  ps.pose = odom.pose.pose
  list_of_poses.append(ps)
  
  global odom_callback_flag
  odom_callback_flag = True


if __name__== "__main__":
  rospy.loginfo("Starting visualizer")
  rospy.init_node("visualizer_visualizer")

  # Publish Path data
  path = Path()  
  path.header.frame_id = frame_id

  # Init Publishers
  odom_path_pub = rospy.Publisher("odom_path_visualizer", Path, queue_size=100) # unique name 
  odom_err_pub = rospy.Publisher("odom_err_visualizer", Odometry, queue_size=100)

  odom_pub = rospy.Subscriber('odom_ekf', Odometry, odom_callback, queue_size=100)


  #// Main While
  while not rospy.is_shutdown():

    if (odom_callback_flag):
      path.header.stamp = rospy.Time.now()
      path.poses = list_of_poses
      odom_path_pub.publish(path)
      odom_callback_flag = False
  
    rospy.sleep(0.1) 
  

  
