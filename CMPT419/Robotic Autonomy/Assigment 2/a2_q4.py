import numpy as np
# Utility functions to initialize the problem
from Grid.GridProcessing import Grid
from Shapes.ShapesFunctions import *
# Specify the  file that includes dynamic systems
from dynamics.DubinsCar4D import *
from dynamics.DubinsCapture import *
from dynamics.DubinsCar4D2 import *
# Plot options
from plot_options import *
# Solver core
from solver import HJSolver

import math

""" USER INTERFACES
- Define grid

- Generate initial values for grid using shape functions

- Time length for computations

- Initialize plotting option

- Call HJSolver function
"""

# Second Scenario
g = Grid(np.array([-5.0, -5.0,-5.0, -math.pi]), np.array([5.0, 5.0, 5.0, math.pi]), 4,
	np.array([50, 50, 30, 35]), [3])

# Define my object

dMin = [0, 0] 
dMax = [0, 0] 

uMin = [-0.01, -0.05]
uMax = [0.01, 0.05]

## [ignore] 
#uMin = [0.08, 0.08]
#uMax = [0.3, 0.3]

## Part f - set disturbance
dMin = [-0.025,-0.025] 
dMax = [0.025, 0.025]

my_car = DubinsCar4D(uMin=uMin, uMax=uMax, dMin=dMin, dMax=dMax,
	uMode="max", dMode="min")

# Use the grid to initialize initial value function
# Initial_value_f = CylinderShape(g, [3,4], np.zeros(4), 1)
init_val_f  = CylinderShape(g, [2, 3], np.zeros(4), 1)

#print("what is target set shape:" , Initial_value_f.shape[0])
## a = Lower_Half_Space(g,   0, 0.01)  # 10km/s
## b = Upper_Half_Space(g ,  0, 0.08)  # 
## c = Lower_Half_Space(g ,  1, 0.01)  # 10km/s
## d = Upper_Half_Space(g ,  1, 0.08)  # 

## part e - change control
a = Lower_Half_Space(g,   2, 0.08)
b = Upper_Half_Space(g ,  2, 0.3)  

# print(init_val_f)
# print(init_val_f[:, :, 0, :])
# print(init_val_f[:, :, 1, :])

init_val_f = np.subtract(init_val_f, a)
init_val_f = np.subtract(init_val_f, b)
# init_val_f = np.subtract(init_val_f, c)
# init_val_f = np.subtract(init_val_f, d)


print("shape: ",init_val_f.shape)



# Look-back length and time step
lookback_length = 6.0 # was 3.0 
t_step = 0.05 # 0.075 #0.1

small_number = 1e-5
tau = np.arange(start=0, stop=lookback_length + small_number, step=t_step)

po = PlotOptions("3d_plot", [0,1,3], [15])
HJSolver(my_car, g, init_val_f, tau, "minVWithV0", po)

