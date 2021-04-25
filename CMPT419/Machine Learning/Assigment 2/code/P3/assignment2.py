"""Basic code for assignment 2."""

import numpy as np
import matplotlib.pyplot as plt


def draw_sep(w):
    """ Draw a hyperplane w'x
    w is [w_1 w_2 bias]'
    """
    L = 1000
    SMALL = 0.00001

    # Check for degeneracy, at least one of w[0:2] must be large enough to invert
    w = np.asarray(w)
    if np.absolute(w[1]) > SMALL:
        x1 = np.array([-L, L])
        x2 = (-w[2] - w[0]*x1)/w[1]
    elif np.absolute(w[0]) > SMALL:
        x2 = np.array([-L, L])
        x1 = (-w[2] - w[1]*x2)/w[0]
    else:
        sys.exit('Invalid separator')

    plt.plot(np.squeeze(x1), np.squeeze(x2), 'r-', linewidth=0.85)


def plot_mb(w, w_old):
    """ Plot the current separator in slope-intercept space (m-b)
    """

    w = np.asarray(w)
    m = -w[0]/w[1]
    b = -w[2]/w[1]

    m_old = -w_old[0]/(w_old[1] + np.finfo(float).eps)
    b_old = -w_old[2]/(w_old[1] + np.finfo(float).eps)
    plt.plot([m_old, m], [b_old, b], 'ko-', linewidth=0.85)

