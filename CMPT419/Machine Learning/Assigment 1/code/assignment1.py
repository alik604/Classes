#!/usr/bin/env python

"""Basic code for assignment 1."""

"""
References: 
  I've never taken a class on python, so many things might be bad practice 
  http://www.cs.toronto.edu/~mbrubake/teaching/C11/Handouts/NonlinearRegression.pdf
  http://www.utstat.utoronto.ca/~radford/sta414.S11/week1b.pdf
  """

import numpy as np
from numpy.linalg import *

import pandas as pd

from scipy import nanmean


def load_unicef_data():
    """Loads Unicef data from CSV file.

    Retrieves a matrix of all rows and columns from Unicef child mortality
    dataset.

    Args:
      none

    Returns:
      Country names, feature names, and matrix of values as a tuple (countries, features, values).

      countries: vector of N country names
      features: vector of F feature names
      values: matrix N-by-F
      """
    fname = 'SOWC_combined_simple.csv'

    # Uses pandas to help with string-NaN-numeric data.
    data = pd.read_csv(fname, na_values='_', encoding='latin1')
    # Strip countries title from feature names.
    features = data.axes[1][1:]
    # Separate country names from feature values.
    countries = data.values[:, 0]
    values = data.values[:, 1:]
    # Convert to numpy matrix for real.
    values = np.asmatrix(values, dtype='float64')

    # Modify NaN values (missing values).
    mean_vals = nanmean(values, axis=0)  ## Compute the arithmetic mean along the specified axis, ignoring NaNs.
    inds = np.where(np.isnan(values))  ## where there are NaNs 
    values[inds] = np.take(mean_vals, inds[1])  ##
    return (countries, features, values)


def normalize_data(x):
    """Normalize each column of x to have mean 0 and variance 1.
    Note that a better way to normalize the data is to whiten the data (decorrelate dimensions).  This can be done using PCA.

    Args:
      input matrix of data to be normalized

    Returns:
      normalized version of input matrix with each column with 0 mean and unit variance

    """

    mvec = x.mean(0)
    stdvec = x.std(axis=0)

    return (x - mvec) / stdvec


def linear_regression(x, t, basis, reg_lambda, degree, mu=[], s=0, include_bias=True):  # mu = [] to handle empty 
    """Perform linear regression on a training set with specified regularizer lambda and basis

    Args:
      x is training inputs
      t is training targets
      reg_lambda is lambda to use for regularization tradeoff hyperparameter
      basis is string, name of basis to use
      degree is degree of polynomial to use (only for polynomial basis)
      mu,s are parameters of Gaussian basis
      include_bias is weather or not to include the bias term 

    Returns:
      pred is predictions 
      w vector of learned coefficients
      RMSE on training set
      """

    # Construct the design matrix.
    # Pass the required parameters to this function

    phi = design_matrix(x, basis, degree, mu, s, include_bias)
    # print(phi.shape)
    # print(t.shape)
    # 100,3 means 3 columns 

    # Learning Coefficients
    if reg_lambda > 0:
        # regularized regression

        ## w^ = (phi^T phi)^-1 phi^T t
        ## PRML 3.28 below; pdf pg 145  
        ## phi.shape[1] is the only nubmer that will works.... phi^T * phi is a (100,100), so I[phi.shape[1]]*lambda match 
        w = pinv((reg_lambda * np.identity(phi.shape[1])) + (phi.transpose() * phi))
        w = w * phi.transpose() * t

    else:
        # no regularization
        w = pinv(phi) * t  # Pinv does the needed work implicitly

    pred = phi * w

    # Measure root mean squared error on training data.
    MSE = np.sum(np.square(pred - t)) / len(t)
    RMSE = np.sqrt(MSE)

    return (pred, w, RMSE)


def evaluate_regression(x, t, w, basis, degree, mu=0, s=0, include_bias=True):  # include w 
    """Evaluate linear regression on a dataset.

      Args:
        x is training inputs
        t is training targets
        w is coefficients/weigths 
        basis is string, name of basis to use
        degree is degree of polynomial to use (only for polynomial basis)
        mu,s are parameters of Gaussian basis

      Returns:
        pred values of regression on inputs
        RMSE on training set if t is not None
    """
    phi = design_matrix(x, basis, degree, mu, s, include_bias)
    pred = phi * w

    # Measure root mean squared error on training data.
    MSE = np.sum(np.square(pred - t)) / len(t)
    RMSE = np.sqrt(MSE)

    return (pred, RMSE)


def design_matrix(x, basis, polynomial_degree=0, mu=0, s=0, include_bias=True):
    """ Compute a design matrix Phi from given input datapoints and basis.

    Args:
      x is training inputs
      basis is string, name of basis to use
      polynomial_degree  is degree of polynomial to use (only for polynomial basis)
      mu,s are parameters of Gaussian basis
      include_bias is weather or not to include the bias term 


    Returns:
      phi design matrix


    recall that phij are basis functions, that we have selected to allow for a non-linear function of x. # http://www.utstat.utoronto.ca/~radford/sta414.S11/week1b.pdf
    example: gaussian basis func = phi j (x) = exp(-(x - mu )^2/2s^2)


    """
    # if no bias, remove this? 
    phi = np.ones((len(x), 1))  # init with column vector of 1s 

    if basis == 'polynomial':
        if polynomial_degree == 1 and include_bias == False:
            print('Error... exception!') 
            return phi

        for i in range(1, polynomial_degree + 1):
            tmp = np.power(x, i)
            phi = np.concatenate((phi, tmp), axis=1)  # as column 

    elif basis == 'sigmoid':
        for i in mu:
            a = (x - i) / s  # PRML eq 3.5
            tmp = 1 / (1 + np.exp(-a))  # PRML eq 3.6
            phi = np.concatenate((phi, tmp), axis=1)  # as column 

    else:
        assert (False), 'Unknown basis %s' % basis

    return phi if include_bias == True else phi[:, 1:]
