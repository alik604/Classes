import numpy as np
import matplotlib.pyplot as plt
import sys

# watched: https://www.youtube.com/watch?v=_0mvWedqW7c

def backward_euler(A,y0,t):
    '''Approximate the solution of y'=ax by backward Euler's method.

    Parameters
    ----------
    f : function
    y0 : numpy array
    t : array
        1D NumPy array of t values where we approximate y values. Time step
        at each iteration is given by t[n+1] - t[n].

    Returns
    -------
    y : 2D NumPy array
        Approximation y[n] of the solution y(t_n) computed by Euler's method.
    '''
    list_y = [np.array([1,1]).T]
    EIG, _ = np.linalg.eig(A)
    for i in np.arange(0,total_time, delta_time):
        next_val = 1/(1 - delta_time*EIG) # np.linalg.pinv(np.eye(2) - delta_time*A)
        next_val *= list_y[-1] # todo 

        list_y.append(next_val)

    return np.stack(list_y)
    

def forward_euler(A, y0, t):
    '''Approximate the solution of y'=ax by forward Euler's method.

    Parameters
    ----------
    f : function
    y0 : numpy array
    t : array
        1D NumPy array of t values where we approximate y values. Time step
        at each iteration is given by t[n+1] - t[n].

    Returns
    -------
    y : 2D NumPy array
        Approximation y[n] of the solution y(t_n) computed by Euler's method.
    '''
    list_y = [np.array([1,1]).T]
    EIG, _ = np.linalg.eig(A)
    for i in np.arange(0, total_time, delta_time):
        next_val = 1 + delta_time*EIG # np.eye(2) + delta_time*A
        next_val *= list_y[-1]
        list_y.append(next_val)

    return np.stack(list_y) # np.ndarray(list_y)



if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Delta time expected. You can use python ode.py 1/300")
        print("Defaulting to 1/500")
        delta_time = 1/500
    else:
        delta_time = eval(sys.argv[1])
    total_time = 2

    # A = np.array([[-100, 0], [1, -1]])
    A = np.array([[0, 1], [-500, -501]])
    y0 = np.array([1, 1])

    print(f'Ax = {np.dot(A, y0)}')
    print('Eigan values:', np.linalg.eig(A)[0])


    b_euler_y = backward_euler(A=A, y0=y0, t=None)
    f_euler_y = forward_euler( A=A, y0=y0, t=None)

    x = np.arange(0,total_time, delta_time)
    # I do not plot the initial y. 


    fig = plt.figure(figsize=(21,9))

    plt.subplot(1, 3, 1)
    plt.title("Forward & Backward Euler h="+ str(delta_time))
    plt.ylabel("X2(t)")
    plt.xlabel("X1(t)")
    plt.plot(b_euler_y[1:,0], b_euler_y[1:,1], "--", label='Backward Euler')
    plt.plot(f_euler_y[1:,0], f_euler_y[1:,1], ":", label='Foreward Euler')
    plt.legend()
    # plt.show()

    plt.subplot(1, 3, 2)
    plt.title("Forward & Backward Euler h="+ str(delta_time))
    plt.ylabel("X1(t)")
    plt.xlabel("time")
    plt.plot(x, b_euler_y[1:,0], "--", label='Backward Euler')
    plt.plot(x, f_euler_y[1:,0], ":", label='Forward Euler')
    plt.legend()
    # plt.show()

    plt.subplot(1, 3, 3)
    plt.title("Forward & Backward Euler h="+ str(delta_time))
    plt.ylabel("X2(t)")
    plt.xlabel("time")
    plt.plot(x, b_euler_y[1:,1], "--", label='backward Euler')
    plt.plot(x, f_euler_y[1:,1], ":", label='Forward Euler')
    plt.legend()
    plt.show()


    # ### Forward Euler ##

    # plt.title("Forward Euler h="+ str(delta_time))
    # plt.ylabel("X2(t)")
    # plt.xlabel("X1(t)")
    # plt.plot(f_euler_y[:,0],  f_euler_y[:,1], "-", label='forward Euler')
    # # plt.legend()
    # plt.show()


    # plt.title("Forward Euler h="+ str(delta_time))
    # plt.ylabel("X1(t)")
    # plt.xlabel("time")
    # plt.plot(x, f_euler_y[1:,0], "-", label='forward Euler')
    # # plt.legend()
    # plt.show()


    # plt.title("Forward Euler h="+ str(delta_time))
    # plt.ylabel("X2(t)")
    # plt.xlabel("time")
    # plt.plot(x, f_euler_y[1:,1], "-", label='forward Euler')
    # # plt.legend()
    # plt.show()


    print ("forward Euler: {}".format(f_euler_y))
    print ("backward Euler: {}".format(b_euler_y))
