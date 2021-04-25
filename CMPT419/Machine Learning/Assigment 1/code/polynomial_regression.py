#!/usr/bin/env python

import assignment1 as a1
import numpy as np
import matplotlib.pyplot as plt

(countries, features, values) = a1.load_unicef_data()

targets = values[:, 1]
x = values[:, 7:40]
x = a1.normalize_data(x)

N_TRAIN = 100
x_train = x[0:N_TRAIN, :]
x_test = x[N_TRAIN:, :]
t_train = targets[0:N_TRAIN]
t_test = targets[N_TRAIN:]

# index_oflowest_1990 = np.argmin(values[:,0])
# print(countries[index_oflowest_1990],"had the lowest child mortality rate in 1990, with a rate of:\t\t", values[index_oflowest_1990,0])
# # print("-----------------------------")
# index_oflowest_2011 = np.argmin(values[:,1])
# print(countries[index_oflowest_2011],"had the lowest child mortality rate in 2011, with a rate of: \t", values[index_oflowest_2011,0])


t_est = {}
te_err = {}
n_degree = 6
for i in range(1, n_degree + 1):
    # its not unreasonable to start from one, since we are doing degree 1 to 6 (inclusive); myArry[0] will be empty
    (pred_train, w_train, RMSE_train) = a1.linear_regression(x_train, t_train, basis='polynomial', reg_lambda=0,
                                                             degree=i, mu=0, s=1, include_bias=True)
    t_est[i] = RMSE_train

    (pred_test, RMSE_test) = a1.evaluate_regression(x_test, t_test, w_train, basis='polynomial', degree=i, mu=0, s=1,
                                                    include_bias=True)  # !!pass in w_train
    te_err[i] = RMSE_test

# print(t_est)
# print(te_err)

plt.rcParams.update({'font.size': 15})
plt.plot(t_est.keys(), t_est.values())
plt.plot(te_err.keys(), te_err.values())
plt.legend(['Training error', 'Testing error'])
plt.title('Fit with polynomials, no regularization')
plt.xlabel('Polynomial degree')
plt.ylabel('RMSe')
plt.show()
