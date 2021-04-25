#!/usr/bin/env python

import numpy as np
import scipy.special as sps
import matplotlib.pyplot as plt
import assignment2 as a2
import random as rand

# Maximum number of iterations.  Continue until this limit, or when error change is below tol.
max_iter = 500
tol = 0.00001

# Step size for gradient descent.
# eta = 0.5
etas = [0.5, 0.3, 0.1, 0.05, 0.01]

# Load data.
data = np.genfromtxt('data.txt')

# Data matrix, with column of ones at end.
X = data[:, 0:3]

# Target values, 0 for class 1, 1 for class 2.
t = data[:, 3]

# For plotting data
class1 = np.where(t == 0)
X1 = X[class1]
class2 = np.where(t == 1)
X2 = X[class2]


def run_SGD(eta):
    # Error values over all iterations.
    e_all = []

    # Initialize w.
    w = [0.1, 0, 0]

    for iter in range(0, max_iter):

        # Compute output using current w on all data X.
        y = sps.expit(np.dot(X, w))

        # e is the error, negative log-likelihood (Eqn 4.90) pg 206 # crossentropy error
        e = -np.mean(np.multiply(t, np.log(y)) + np.multiply((1 - t), np.log(1 - y)))
        e_all.append(e)

        for _ in range(len(t)):  # 200
            r = np.random.randint(len(t))
            # X_single, t_single = X[r], t[r]

            # Gradient of the error, using Eqn 4.91 
            w = w - np.multiply(eta, np.multiply((y[r] - t[r]), X[r])) / len(t)  
                # [row][col]#### diff of pred & y-values, times DATA_MATRIX
                # y = sps.expit(np.dot(X, w))  # DO NOT USE
                # TODO do we update y? 

        print('epoch {0:d}, negative log-likelihood {1:.4f}, w={2}'.format(iter, e, w))
        if iter > 0:
            if np.absolute(e - e_all[iter - 1]) < tol:
                print('breaking...', eta)
                break

        '''
          for i in range(len(t)):
            r = rand.randint(0,len(t)-1)
            X_single, t_single = X[r], t[r]
    
            # Gradient of the error, using Eqn 4.91
            mean = np.multiply((y[r] - t_single), X_single.T)
            grad_e = mean #np.mean(mean, axis=1)
            #### diff of pred & y-values, times DATA_MATRIX
    
            # Update w, *subtracting* a step in the error derivative since we're minimizing
            w_old = w
            w = w - eta*grad_e
            y = sps.expit(np.dot(X, w)) # small redundancy 
            #### 5.43 on pg 240 
        '''
    return e_all


# Plot error over iterations
TRAIN_FIG = 3
plt.figure(TRAIN_FIG, figsize=(8.5, 6))
plt.ylabel('Negative log likelihood')
plt.title('Training logistic regression - SGD')
plt.xlabel('Epoch')

for eta in etas:
    e_all = run_SGD(eta)
    plt.plot(e_all, label=str(eta))

plt.legend()
plt.show()
