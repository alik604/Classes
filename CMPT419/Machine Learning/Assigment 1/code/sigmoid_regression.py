#!/usr/bin/env python

import assignment1 as a1
import numpy as np
import matplotlib.pyplot as plt

(countries, features, values) = a1.load_unicef_data()

targets = values[:, 1]
x = values[:, 10]
features = features[10]
# x = a1.normalize_data(x)

N_TRAIN = 100
x_train = x[0:N_TRAIN, :]
x_test = x[N_TRAIN:, :]
t_train = targets[0:N_TRAIN]
t_test = targets[N_TRAIN:]

MU = [100, 10000]
S = 2000

# print(x_train.shape)
# print(features)
(pred_train, w_train, RMSE_train) = a1.linear_regression(x_train, t_train, basis='sigmoid', reg_lambda=0, degree=-1,
                                                         mu=MU, s=S, include_bias=True)
(pred_test, RMSE_test) = a1.evaluate_regression(x_test, t_test, w_train, basis='sigmoid', degree=-1, mu=MU, s=S,
                                                include_bias=True)
# print('Training error is: ',RMSE_train)
# print('Testing error is: ',RMSE_test)

xx = np.linspace(np.min(x_train), np.max(x_train), num=1000).reshape(1000, 1)
phi = a1.design_matrix(xx, "sigmoid", -1, MU, S, True)
y = phi * w_train
plt.plot(x_train, t_train, 'b+')
plt.plot(x_test, t_test, 'r+')
plt.plot(xx, y, 'y.')
plt.xlabel('Capita')
plt.ylabel('GNI in US$')
plt.title('Visualization of a sigmoid function with bias for ' + features)
plt.legend(['Training data', 'Testing data', 'Linear reg with sigmoid basis'])
plt.show()


print(RMSE_train)
print(RMSE_test)
plt.bar(1, RMSE_train, 0.20, label='training RMSe')
plt.bar(1 + 0.3, RMSE_test, 0.20, label='testing RMSe')
plt.xlabel('Set')
plt.ylabel('RMS')
plt.legend(['Training error', 'Testing error'])
plt.title('The training and testing errors (RMSe)')
plt.tick_params(  ## from https://stackoverflow.com/questions/12998430/remove-xticks-in-a-matplotlib-plot
    axis='x',  # changes apply to the x-axis
    which='both',  # both major and minor ticks are affected
    bottom=False,  # ticks along the bottom edge are off
    top=False,  # ticks along the top edge are off
    labelbottom=False)  # labels along the bottom edge are offplt.xlabel(features + 'feature')
plt.show()
