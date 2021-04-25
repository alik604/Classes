from random import random, randint
import numpy as np 
import cvxpy as cp 
# import matplotlib as mpl
import matplotlib.pyplot as plt
from matplotlib import cm
from mpl_toolkits import mplot3d

import warnings
warnings.filterwarnings("ignore") # I live on the edge :)

# howto install https://www.cvxpy.org/install/
# ref  http://yetanothermathprogrammingconsultant.blogspot.com/2019/11/cvxpy-matrix-style-modeling-limits.html

def cvx_problem(x1, x2):
    f = np.sin(np.pi *x1)*np.sin(2*np.pi*x2)
    
    gf = np.array([np.pi * np.cos(np.pi*x1)*np.sin(2*np.pi*x2),
                    2*np.pi*np.sin(np.pi*x1)*np.cos(2*np.pi*x2)])
    
    hf = np.array([[((-np.pi**2)*np.sin(np.pi*x1)*np.sin(2*np.pi*x2)), (2*(np.pi**2)*np.cos(np.pi*x1)*np.cos(2*np.pi*x2))], 
                    [(2*(np.pi**2)*np.cos(np.pi*x1)*np.cos(2*np.pi*x2)), (-4*(np.pi**2)*np.sin(np.pi*x1)*np.sin(2*np.pi*x2))]])
    
    d, v = np.linalg.eig(hf) #convexify; zero-out neg eigens 
    d[d<0] = 0  
    d = np.matrix([[d[0], 0], [0, d[1]]])
    BK = v * d * np.linalg.inv(v)

    #q_approx = f + gf + 0.5*BK*()
#     print(f'v {v}')
#     print(f'vI {vI}')
#     print(f'BK {BK}')

    # https://www.cvxpy.org/examples/basic/quadratic_program.html
    x = cp.Variable(2)
    bounds =        [-x1 - x[0] - 1 <= 0,
                      x1 + x[0] - 1 <= 0,
                     -x2 - x[1] - 1 <= 0,
                      x2 + x[1] - 1 <= 0]
    prob = cp.Problem(cp.Minimize((1/2)*cp.quad_form(x, BK) + gf.T @ x), bounds)
    p1 = prob.solve()
    return x.value


x = np.linspace(-1, 1) 
y = np.linspace(-1, 1)
X, Y = np.meshgrid(x, y)
Z = np.sin(np.pi * X) * np.sin(2*np.pi*Y)


# uncomment for blank plots (same as given in assignment)

# fig = plt.figure()
# plt.xlim(-1,1)
# plt.ylim(-1,1)
# ax1 = fig.add_subplot(111)
# ax1 = plt.axes(projection='3d')
# ax1.plot_surface(X, Y, Z, rstride=1, cstride=1,
#                 cmap='viridis', edgecolor='none')
# ax1.contour( X, Y, Z)
# plt.show()
# ####

# fig2 = plt.figure()
# plt.xlim(-1,1)
# plt.ylim(-1,1)
# ax2 = fig2.add_subplot(111)
# ax2 = plt.axes() # projection='2d'

# ax2.contour( X, Y, Z)

# m = cm.ScalarMappable(cmap=cm.plasma)
# m.set_array(Z)
# cbar = plt.colorbar(m)
# plt.show()



###############
simulations = 15
epochs = 300   # max steps
small = 0.00005 # convergence
step_size = 0.30
bool_switch = True
number_of_iterations = [] 

# mainview
fig = plt.figure()
ax1 = fig.add_subplot(111)
ax1 = plt.axes(projection='3d')
ax1.plot_surface(X, Y, Z, rstride=1, cstride=1,
                cmap='viridis', edgecolor='none', alpha=0.60, linewidth=0, antialiased=True)

#overview
fig2 = plt.figure()
plt.xlim(-1,1)
plt.ylim(-1,1)
ax2 = fig2.add_subplot(111)
ax2 = plt.axes() # 
ax2.contour(X, Y, Z)
m = cm.ScalarMappable(cmap=cm.plasma)
m.set_array(Z)
cbar = plt.colorbar(m)


for j in range(0, simulations):
    # [0 to 1) -> [0 to 2) -> [-1 to 1)
    x1  = random()*2-1
    x2  = random()*2-1
    x1s = [x1]
    x2s = [x2]
    print(f'Initialization points: {x1:.2f} and {x2:.2f}')
    
    for i in range(0, epochs):
        point = cvx_problem(x1, x2)
        x1 += step_size*point[0]
        x2 += step_size*point[1]
        x1s.append(x1)
        x2s.append(x2)
#         print("the computed optimal value is " + str(point[0]) + " " + str(point[1]))
#         print(f'points after step: {x1s[-1]:.2f} and {x1s[-2]:.2f}')

        if i > 3 and np.sqrt((x1s[-1] - x1s[-2])**2 + (x2s[-1] - x2s[-2])**2) < small: # might not be the best way, but it works 
            print(f'Convergence achieved, Terminal points: {x1s[-1]:.2f} and {x1s[-2]:.2f}\n')
            number_of_iterations.append(i)
            break
        if i == 100: print(f'This is the 100th iteration..') 

    # supporting calculations from plot 
    x1s, x2s = np.array(x1s), np.array(x2s)
    Z1 = np.sin(np.pi * x1s) * np.sin(2*np.pi*x2s)
    
    # mainview
    ax1.plot3D(x1s, x2s, Z1,'k', linewidth=1)
    
    # overview
    ax2.plot(x1s, x2s, Z1,'k', linewidth=1)


# ax1.view_init(elev=90, azim=45) # azim=45
ax1.view_init(elev=45, azim=45) # azim=45

plt.show()

print(f'With a step size of {step_size}, it took an average of {sum(number_of_iterations)/len(number_of_iterations):.2f} iterations to achieve convergence. Small = {small}')
# With a step size of 0.15, it took an average of 45.10 iterations to achieve convergence. Small = 1e-05
# With a step size of 0.20, it took an average of 34.81 iterations to achieve convergence. Small = 1e-05
# With a step size of 0.25, it took an average of 27.29 iterations to achieve convergence. Small = 1e-05
# With a step size of 0.30, it took an average of 21.58 iterations to achieve convergence. Small = 1e-05
# With a step size of 0.40, it took an average of 16.74 iterations to achieve convergence. Small = 1e-05
# With a step size of 0.50, it took an average of 13.67 iterations to achieve convergence. Small = 1e-05
# With a step size of 0.50, it took an average of 5.01 iterations to achieve convergence. Small = 0.01
# With a step size of 0.50, it took an average of 7.24 iterations to achieve convergence. Small = 0.001
# With a step size of 0.60, it took an average of 6.31 iterations to achieve convergence. Small = 0.001