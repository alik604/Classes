import numpy as np
from matplotlib import pyplot as plt 
def f(_, x):
	eps = 0.0001
	dx = np.zeros(4) # mapping [r, dr, theta, dtheta]
	dx[0] = x[1]
	dx[1] = x[0]*x[3]**2 - k/x[0]**2 # u_1 is 0 
	dx[2] = x[3]
	dx[3] = -2*x[1]*x[3]/(x[0]) #u_2 is 0 # was x[1]*x[2]
	# print(x," | " ,dx)
	return dx

if __name__ == '__main__':
	print("Start calculating")

	r = 410+6378 	# km
	r *=1000  		# meters
	dr = 0			# meters per secound  	
	theta  = 0		# rad?

	T = 92.68*60 	# secounds 
	dtheta  = 2*np.pi/T # rad per secound; Angular frequency

	G = 6.67*(10**-11)
	M = 5.97*(10**24)
	k = G*M

	x = np.array([r, dr, theta, dtheta])
	print(f'X is      \t{x}')
	print(f'd/dx X is \t{f(None, x)}')


	list_of_dx = [x]
	rk4 = [0, 0, 0, 0] # list of 4 ndarrays 
	dtime = 1
	time = np.arange(0, 60*1000, step=dtime)
	for i in range(len(time)-1): # range(len(time))
		# x is a single step, half of which is 0. We only care about the y axis. 
		rk4[0] = dtime * f(None, list_of_dx[-1])
		rk4[1] = dtime * f(None, list_of_dx[-1] + rk4[0]/2) # my past way: list_of_dx[i] + rk4[0]/2
		rk4[2] = dtime * f(None, list_of_dx[-1] + rk4[1]/2)
		rk4[3] = dtime * f(None, list_of_dx[-1] + rk4[2])
		# print(f'd/dx X is \t{list_of_dx[i]}')
		# print(f'rk4 is    \t{rk4}')
		# print("__________________________")

		next_dx = list_of_dx[-1] + (rk4[0] + rk4[1]*2.0 + rk4[2]*2.0 + rk4[3])/6
		list_of_dx.append(next_dx)



	list_of_d 		= [i[0] for i in list_of_dx] # unpacked 
	list_of_dr 		= [i[1] for i in list_of_dx]
	list_of_theta 	= [i[2] for i in list_of_dx]
	list_of_dtheta	= [i[3] for i in list_of_dx] # unpacked 

	# print(f'r is     \t{list_of_d}')
	# print(f'theta is \t{list_of_theta}')

	x = np.arange(len(list_of_d))/60 # all lists are equals. This is fine
	list_of_d = np.array(list_of_d)/1000
	list_of_theta = np.array(list_of_theta)


	fig = plt.figure(figsize=(16,9))

	plt.subplot(2, 2, 1)

	plt.title("r (km above earth's surface)")
	plt.xlabel("Time (minutes)")
	plt.plot(x, list_of_d-6378)
	# plt.show()

	plt.subplot(2, 2, 2)
	plt.title("m/s")
	plt.xlabel("Time (minutes)")
	plt.plot(x, list_of_dr)
	# plt.show()

	plt.subplot(2, 2, 3)
	plt.title("theta rad")
	plt.xlabel("Time (minutes)")
	plt.plot(x, list_of_theta)
	# plt.show()

	plt.subplot(2, 2, 4)
	plt.title("d/dx theta (rad/s)")
	plt.xlabel("Time (minutes)")
	plt.plot(x, list_of_dtheta)
	plt.show()

	cir = np.linspace(0, 2*np.pi, 1000)
	plt.title("Orbit around Earth")
	plt.ylabel("y position (km) w.r.t. Earth's Center")
	plt.xlabel("x position (km) w.r.t. Earth's Center")
	plt.plot(6378 * np.cos(cir), 6378 * np.sin(cir), label='Earth')
	plt.plot(list_of_d * np.cos(list_of_theta), list_of_d * np.sin(list_of_theta), label='Orbit')

	plt.legend()
	plt.show()
