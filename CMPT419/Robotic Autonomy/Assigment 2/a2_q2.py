import numpy as np
from numpy.linalg import inv, pinv#, transpose
import math
import matplotlib.pyplot  as plt

T= 10
dt = .00001
x_axis = np.arange(0, int(1/dt*T)+1) * dt # 0 to 10, in dt increments

M = np.array([[1, 0, 0, 0],[0, 1, 0, 0],[1, T, T**2, T**3], [0, 1, 2*T, 3*T**2]])
x_traject_gen = np.array([0,1,0,0])
y_traject_gen = np.array([0,0,0,1])
M = M.astype(float)
b_x = pinv(M) @ np.transpose(x_traject_gen) # same as foo.dot(bar)
b_y = pinv(M) @ np.transpose(y_traject_gen)
# b_x
print(inv(M).shape)
print(len(x_traject_gen))
# b_x = np.linalg.lstsq(M, x_traject_gen)[0] # inv(M)
# b_y = np.linalg.lstsq(M, y_traject_gen)[0]


x, y = np.zeros(x_axis.shape[0]), np.zeros(x_axis.shape[0])
for i in range(4):
    x += b_x[i] * x_axis**i
    y += b_y[i] * x_axis**i


x_dot, y_dot = np.zeros(x_axis.shape[0]), np.zeros(x_axis.shape[0])
for i in range(3): # in first itr, this will made x_dot = 0 
    x_dot += (i+1)*b_x[i+1] * x_axis**i
    y_dot += (i+1)*b_y[i+1] * x_axis**i


x_dot_dot, y_dot_dot = np.zeros(x_axis.shape[0]), np.zeros(x_axis.shape[0])

x_dot_dot = x_dot_dot + 2.0*b_x[2] * x_axis**0
y_dot_dot = y_dot_dot + 2.0*b_y[2] * x_axis**0

x_dot_dot = x_dot_dot + 6.0*b_x[3] * x_axis**1
y_dot_dot = y_dot_dot + 6.0*b_y[3] * x_axis**1


theta = np.arctan2(y_dot, x_dot)

# v = x_dot/np.cos(theta)
v = np.divide(x_dot, np.cos(theta)) # most safe
# v = np.sqrt(np.power(x_dot,2), np.power(y_dot,2))


eps_0 = 0#0.1
eps_1 = 0#1.0
numerator = np.multiply(y_dot_dot, x_dot)-np.multiply(x_dot_dot, y_dot)
omega = (numerator/(eps_0+v**2))


numerator = np.multiply(x_dot, x_dot_dot) + np.multiply(y_dot, y_dot_dot)
a = numerator/(eps_1+v)

# tmp = y_dot_dot@ x_dot - x_dot_dot@ y_dot
# omega = np.divide(tmp, np.power(v,2))

# tmp = x_dot@ x_dot_dot + y_dot@ y_dot_dot
# a = np.divide(tmp, v)


plt.figure(figsize=(6,6))
plt.xlim(0, 1.5)
plt.ylim(-1.5, 0)
# plt.plot(x_axis, x, label='x')
# plt.plot(x_axis, y, label='y')
plt.plot(x, y, label='path')
plt.title("path of car");
plt.legend()
# plt.show()


# x_axis= x_axis*10
plt.figure(figsize=(12,6))
plt.xlim(0,10)
plt.ylim(-4,4)
plt.plot(x_axis, x,     color='b',      linestyle='--', label='x',)
plt.plot(x_axis, y,     color='r',      linestyle='--', label='y')
plt.plot(x_axis, theta, color='y',      linestyle='-',  label='theta')
plt.plot(x_axis, v,     color='purple', linestyle='-',  label='v')
plt.title("State trajectory");
plt.legend()
# plt.show()


plt.figure(figsize=(12,6))
plt.xlim(0,1)
plt.ylim(-.80,.40)
plt.plot(x_axis/10, omega, color='b', linestyle='-', label='Omega (delta theta)')
plt.plot(x_axis/10, a,   color='r', linestyle='-', label='a (delta v)')
plt.title("Control trajectory");
plt.legend()
plt.show()