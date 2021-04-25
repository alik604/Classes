#!/usr/bin/env python

import assignment1 as a1
import numpy as np
import matplotlib.pyplot as plt

(countries, features, values) = a1.load_unicef_data()
features = features[7:15]
targets = values[:,1]
x = values[:,7:15] # includes 8-15 

N_TRAIN = 100
x_train = x[0:N_TRAIN,:]
x_test = x[N_TRAIN:,:]
t_train = targets[0:N_TRAIN]
t_test = targets[N_TRAIN:]

names = []
t_est = []
te_err = []
i = 3 

'''
Try features 8-15 (Total population - Low birthweight).
Try with and without a bias term.
Plot training error and test error (in RMS error)
for each of the 8 features. 
This should be as bar charts (e.g. use matplotlib.pyplot.bar()) 
— one for models with bias term, and another for models without bias term.
'''

for i in range(x.shape[1]):
	(pred_train, w_train, RMSE_train) = a1.linear_regression(x_train[:,i], t_train, basis = 'polynomial',reg_lambda=0, degree = 3, mu=0, s=1, include_bias=True)
	t_est.append(RMSE_train)

	(pred_test, RMSE_test) = a1.evaluate_regression(x_test[:,i], t_test, w_train,basis = 'polynomial', degree = 3, mu=0, s=1, include_bias=True) # !!pass in w_train 
	te_err.append(RMSE_test)

	names.append("f" + str(i+x.shape[1]))

# print(names)
# print(t_est)
# print(te_err)



i = np.arange(len(t_est))
plt.rcParams.update({'font.size': 15})
plt.bar(i,t_est,0.20,label = 'training RMSe')
plt.bar(i+0.2,te_err,0.20,label = 'testing RMSe')
plt.ylabel('RMS')
plt.legend(['Training error','Testing error'])
plt.title('Fit with single input feature 3rd degree poly, no reg, with bias')
plt.xticks(i,names) # xticks(​ticks, labels​)
plt.xlabel('Nth feature')
plt.show()


for i in [3,4,5]:

	# print(features[i])
	xx = np.linspace(np.min(x_train[:,i]),np.max(x_train[:,i]),1000).reshape(1000,1)
	xx_test = np.linspace(np.min(x_test[:,i]),np.max(x_test[:,i]),1000).reshape(1000,1)
	(pred_train, w_train, RMSE_train) = a1.linear_regression(x_train[:,i], t_train, basis = 'polynomial',reg_lambda=0, degree = 3, mu=0, s=1, include_bias=True)
	phi = a1.design_matrix(xx,'polynomial',3,0,1,True)
	y = phi*w_train

	plt.plot(x_train[:,i], t_train,'b+')
	plt.plot(x_test[:,i],  t_test,'r+')
	plt.plot(xx, y,'g')
	plt.title('Plot with bias of '+features[i])
	plt.show()





############ no bias #################
names = []
t_est = []
te_err = []

for i in range(x.shape[1]):
	(pred_train, w_train, RMSE_train) = a1.linear_regression(x_train[:,i], t_train, basis = 'polynomial',reg_lambda=0, degree = 3, mu=0, s=1, include_bias=False)
	t_est.append(RMSE_train)

	(pred_test, RMSE_test) = a1.evaluate_regression(x_test[:,i], t_test, w_train,basis = 'polynomial', degree = 3, mu=0, s=1, include_bias=False) # !!pass in w_train 
	te_err.append(RMSE_test)

	names.append("f" + str(i+x.shape[1]))

i = np.arange(len(t_est))
plt.rcParams.update({'font.size': 15})
plt.bar(i,t_est,0.20,label = 'training RMSe')
plt.bar(i+0.2,te_err,0.20,label = 'testing RMSe')
plt.ylabel('RMS')
plt.legend(['Training error','Testing error'])
plt.title('Fit with single input feature 3rd degree poly, no reg, without bias')
plt.xticks(i,names) # xticks(​ticks, labels​)
plt.xlabel('Nth feature')
plt.show()


for i in [3,4,5]:

	# print(features[i])
	xx = np.linspace(np.min(x_train[:,i]),np.max(x_train[:,i]),1000).reshape(1000,1)
	xx_test = np.linspace(np.min(x_test[:,i]),np.max(x_test[:,i]),1000).reshape(1000,1)
	(pred_train, w_train, RMSE_train) = a1.linear_regression(x_train[:,i], t_train, basis = 'polynomial',reg_lambda=0, degree = 3, mu=0, s=1, include_bias=False)
	phi = a1.design_matrix(xx,'polynomial',3,mu= 0,s= 1,include_bias= False)
	y = phi*w_train

	plt.plot(x_train[:,i], t_train,'b+')
	plt.plot(x_test[:,i],  t_test,'r+')
	plt.plot(xx, y,'g')
	plt.title('Plot without bias of '+features[i])
	plt.show()

