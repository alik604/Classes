# Most of my code if from docs
# https://github.com/casadi/casadi/blob/master/docs/examples/python/direct_multiple_shooting.py

import numpy as np
from casadi import *

T = 5 
N = 50
h = T/N

u = SX.sym('u') # control; force 

x = SX.sym('x')
v = SX.sym('v')
theta = SX.sym('theta')
omega = SX.sym('omega')

states = horzcat(x, v, theta, omega)

M = 0.5   # kg    Mass of cart
m = 0.2   # kg    Mass of pendulum
b = 0.1   # M/n/s Ceofficient of friction 
l = 0.3   # m     Half-length of the pendulum 
i = 0.006 # kg    Moment of inertia of the pendulum
g = 9.81  # m/s^2 Gravity

u_max = 0.2 # |u(t)| < 0.2
x_max = 1


mat_1 = vertcat(horzcat(M+m, m*l*np.cos(theta)), horzcat(m*l*np.cos(theta), i+m*l**2)) # [[M+m, m*l*np.cos(theta)], [m*l*np.cos(theta), I+m*l**2]]
mat_2 = vertcat(-b*v + m*l*omega**2*np.sin(theta) + u, -m*g*l*np.sin(theta))

xdot = solve(mat_1, mat_2)
xdot = vertsplit(xdot)

xdot = vertcat(v, xdot[0], omega, xdot[1]) #[v, before, omega, after]

Jt = u**2 # # Objective term

f = Function('f', [states, u], [xdot, Jt])

X0 = SX.sym('X0', 4) # 4x1
U = SX.sym('U')      # 1x1

Xdot, Jk = f(X0, U)
X = X0 + h*Xdot
Q = h*Jk

F = Function('F', [X0, U], [X, Q],['x0','p'],['xf','qf'])

# dae = {'x':states, 'p':u, 'ode':xdot, 'quad':Jt}
# opts = {'tf':T/N}
# F = integrator('F', 'cvodes', dae, opts)

# Start with an empty NLP
w=[]
w0 = []
lbw = []
ubw = []
J = 0
g=[]
lbg = []
ubg = []

# "Lift" initial conditions
Xk = MX.sym('X0', 4)
w += [Xk]
lbw += [0,0,0,0]
ubw += [0,0,0,0]
w0 +=  [0,0,0,0]

# Formulate the NLP
for k in range(N):
    # New NLP variable for the control
    Uk = MX.sym('U_' + str(k)) # 1x1
    w   += [Uk]
    lbw += [-u_max] # lower bound 
    ubw += [u_max]  # upper bound 
    w0  += [0]      # because the docs said so

    # Integrate till the end of the interval
    Fk = F(x0=Xk, p=Uk)
    Xk_end = Fk['xf']
    J=J+Fk['qf']

    # New NLP variable for state at end of interval
    Xk = MX.sym('X_' + str(k+1), 4) # 1x4
    w   += [Xk]
  
    if k == N-1: # final itr
        lbw += [0, 0, np.pi, 0] #state_k
        ubw += [0, 0, np.pi, 0]
    else:
        tmp = np.inf
        lbw += [-x_max, -tmp, -tmp, -tmp]# [-x_max, -np.inf, -np.inf, -np.inf] #state_i
        ubw += [x_max,  tmp, tmp, tmp]# [x_max,  np.inf, np.inf, np.inf]
        
    w0  += [0, 0, 0, 0] # the docs say to have this
       
        
    # Add equality constraint
    g   += [Xk_end-Xk]
    lbg += [0, 0, 0, 0]
    ubg += [0, 0, 0, 0]
    

# Create an NLP solver
prob = {'f': J, 'x': vertcat(*w), 'g': vertcat(*g)}
solver = nlpsol('solver', 'ipopt', prob);

# Solve the NLP
sol = solver(x0=w0, lbx=lbw, ubx=ubw, lbg=lbg, ubg=ubg)
w_opt = sol['x'].full().flatten()

# Plot the solution
x_opt =       w_opt[0::5] 
v_opt =       w_opt[1::5] 
theta_opt =   w_opt[2::5] 
omega =       w_opt[4::5] 
control_opt = w_opt[5::5] 
w_opt.shape
# u_opt.shape

tgrid = np.array([k*T/N for k in np.arange(N+1)])#/2

import matplotlib.pyplot as plt
plt.figure(1, figsize=(12,6))
plt.clf()
plt.ylim(-2, 4)
# plt.axhline(y=np.pi, color='r', linestyle='dotted')

plt.plot(tgrid, x_opt, color='k', linestyle='--')
plt.plot(tgrid, v_opt, color='b', linestyle='--')
plt.plot(tgrid, theta_opt, color='r', linestyle='-')
plt.plot(tgrid, vertcat(DM.nan(1), omega), color='pink', linestyle='-')
plt.plot([5], [np.pi],'ro') # red dot; target theta
plt.ylabel('State')
plt.xlabel('t')
plt.legend(['x_opt','v_opt','theta','omega','target theta'])
plt.grid()
plt.title('State trajectories')
plt.show()

tgrid = [k*T/N for k in range(N+1)]
import matplotlib.pyplot as plt

plt.figure(1, figsize=(12,6))
plt.clf()
plt.ylim(-0.5, 0.5)
plt.xlabel('t')
plt.ylabel('F(t)')
plt.step(tgrid, vertcat(DM.nan(1), control_opt), '-.')
plt.legend(['x_opt','v_opt','theta','omega'])
plt.grid()
plt.title('Control trajectories')
plt.show()