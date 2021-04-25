#!/usr/bin/env python

import assignment1 as a1
import numpy as np
import matplotlib.pyplot as plt

(countries, features, values) = a1.load_unicef_data()

targets = values[:, 1]
x = values[:, 7:]
x = a1.normalize_data(x)
features = features[7:]

lambs = [0, 0.01, 0.1, 1, 10, 100, 1000, 10000]
testing_err = {}


# https://stackoverflow.com/questions/38850885/k-fold-cross-validation-implementation-python
def CV(lamb):
    offset, test_err = 10, 0
    for i in range(0, 10):
        boundA = i * offset + i
        boundB = i * offset + i + offset

        x_test = x[boundA:boundB]
        t_test = targets[boundA:boundB]

        x_train = np.concatenate((x[0:boundA - 1, :], x[boundB + 1:, :]))
        t_train = np.concatenate((targets[0:boundA - 1], targets[boundB + 1:]))

        (pred_train, w_train, RMSE_train) = a1.linear_regression(x_train, t_train, basis='polynomial', reg_lambda=lamb,
                                                                 degree=2, include_bias=True)
        (pred_test, RMSE_test) = a1.evaluate_regression(x_test, t_test, w_train, basis='polynomial', degree=2,
                                                        include_bias=True)  # !!pass in w_train 

        test_err += RMSE_test  # RMSE_test
    return test_err / 10


# for lamb in lambs:
# 	testing_errors[lamb] = CV(lamb) # use dict 

testing_err = [CV(lamb) for lamb in lambs]  # list, not a dict

# print(testing_err)

plt.semilogx(lambs, testing_err, color='brown')
plt.ylabel('RMSe')
plt.legend(['Validation error'])
plt.title("L2-regularized regression")
plt.xlabel('Features')
plt.show()
