import getopt
import pickle
import sys
import time

import matplotlib.pyplot as plt
import numpy as np


def load_data():
    train_data = pickle.load(open('dataset/train.pkl', 'rb'))
    valid_data = pickle.load(open('dataset/valid.pkl', 'rb'))

    return train_data, valid_data


# You can pass a single row of data to plot the 28*28 image
# Please keep in mind that you can slow down your code dramatically by using this function in training loop.
def plot_data_row(row):
    x = row[:-1]
    t = row[-1]
    x = [i * 255 for i in x]
    x = np.array(x, dtype='uint8')
    x = x.reshape((28, 28))
    plt.title('Label is {label}'.format(label=t))
    plt.imshow(x, cmap='gray')
    plt.show()


# Init network given the node count (including input data feature count)
def init_network(layers=[784, 10, 1]):
    W = []
    B = []
    for i in range(len(layers) - 1):
        W.append(np.random.randn(layers[i + 1], layers[i]))
        B.append(np.random.randn(layers[i + 1], 1))
    return W, B


# Use a vectorized form of sigmoid function.
# Many numpy return vector outputs for vector inputs:
# e.g. np.exp(x)
def activation(x):
    return np.exp(x) / (1 + np.exp(x))


# This function should return the derivate value of sigmoid function at point x
# You need to implement a vectorized version of this method again.
def derivative_activation(x):
    sig = lambda y: np.exp(y) / (1 + np.exp(y))
    return sig(x) * (1 - sig(x))


# In this function you need to do feed-forward phase of the network.
# each x is a sample in the dataset. W and B are list of weights and biases
# for different layers of network.
# You need to return list of z and a values for each layer
# a = activation_function(z)
def compute_activations(x, W, B):
    a = np.copy(x)
    z_s = [None]
    a = a.reshape((len(a), 1))
    a_s = [a]
    for i in range(0, len(W)):
        (n1, n2) = W[i].shape
        z_s.append(np.zeros((n1, 1)))
        a_s.append(np.zeros((n1, 1)))
    # TODO: YOUR CODE STARTS HERE
    # calculate z and a for each layer and append them to z_s and a_s
    # TODO: YOUR CODE ENDS HERE
    return (z_s, a_s)


# Using y, the true label for the data point and z and a values,
# you must implement a backpropagation algorithm to calculate db and dw for different layers.
# db: list of derivatives W.R.T. bias for each layer
# dw: list of derivatives W.R.T. weights for each layer
def backpropagation(x, t, W, B):
    (z_s, a_s) = compute_activations(x, W, B)
    d_b = [np.zeros(b.shape) for b in B]
    d_w = [np.zeros(w.shape) for w in W]

    # TODO: YOUR CODE STARTS HERE
    # Calculate d_w and d_b for all weights and biases and return them in the same structure as W and B
    # TODO: YOUR CODE ENDS HERE
    return d_w, d_b, z_s, a_s


# In this function we use derivatives and learning rate to update existing weights and biases
def update_weights(W, B, dw, db, lr):
    W = [w + lr * dweight for w, dweight in zip(W, dw)]
    B = [w + lr * dbias for w, dbias in zip(B, db)]
    return W, B


# Calculate loss and accuracy of network
def calc_error(X, W, B):
    err = 0
    n_true = 0.0
    for row in X:
        x = row[:-1]
        t = row[-1]
        z, a = compute_activations(x, W, B)
        if np.linalg.norm(a[-1].round() - t) == 0:
            n_true += 1
        err += np.linalg.norm(a[-1] - t)
    return err / len(X), n_true / len(X)


# Test the model and print the loss and accuracy (used for grading your submission)
def test(model_path):
    # Load Test Data
    test_date = pickle.load(open('dataset/test.pkl', 'rb'))
    # Load Model
    (W, B) = pickle.load(open(model_path, 'rb'))
    # Evaluate
    test_loss, test_acc = calc_error(test_date, W, B)
    # Print Accuracy
    print("Test Error: {0:4.4f}\tTest Acc: {1:4.4f}".format(test_loss, test_acc))
    return test_loss, test_acc


def print_status(print_sample_values, z_s, a_s, d_w, d_b, W, B):
    if print_sample_values and z_s is not None and a_s is not None:
        print('-' * 10 + 'Sample values for debugging:' + '-' * 10)
        print('\nz_s[1][0:4]=')
        print(z_s[1][0:4].flatten())
        print('\na_s[-1][0:4]=')
        print(a_s[-1][0:4].flatten())

    if print_sample_values and d_w is not None and d_b is not None:
        print('\nd_w[-1][0][0:4]=')
        print(d_w[-1][0][0:4].flatten())
        print('\nd_b[0][0:4]=')
        print(d_b[0][0:4].flatten())

    if print_sample_values and W is not None and B is not None:
        print('\nW[0][0][0:4]')
        print(W[0][0][0:4].flatten())
        print('\nB[0][0:4]')
        print(B[0][0:4].flatten())
        print('-' * 50)


def stochastic_gradient_descent(max_epoch, lr):
    timestr = time.strftime("%Y%m%d-%H%M%S")
    X_train, X_valid = load_data()
    W, B = init_network()
    train_loss, train_acc = calc_error(X_train, W, B)
    valid_loss, valid_acc = calc_error(X_valid, W, B)
    print(
        "Evaluate using randomly initialized weights:\nTrain Loss: {2:4.4f}\tValid Loss: {3:4.4f}\tTrain Acc: {4:4.4f}\tValid Acc: {5:4.4f}\n\n".format(
            0, max_epoch, train_loss,
            valid_loss, train_acc, valid_acc))

    print_sample_values = True
    for e in range(1, max_epoch + 1):
        for row in X_train:
            x = row[:-1]
            t = row[-1]
            # plot_data_row(row)
            # Do Backpropagation
            d_w, d_b, z_s, a_s = backpropagation(x, t, W, B)
            print_status(print_sample_values, z_s, a_s, None, None, None, None)
            print_status(print_sample_values, None, None, d_w, d_b, None, None)

            # Update weights
            W, B = update_weights(W, B, d_w, d_b, lr)
            print_status(print_sample_values, None, None, None, None, W, B)
            print_sample_values = False

        train_loss, train_acc = calc_error(X_train, W, B)
        valid_loss, valid_acc = calc_error(X_valid, W, B)
        print(
            "Epoch #{0:4}/{1}\tTrain Loss: {2:4.4f}\tValid Loss: {3:4.4f}\tTrain Acc: {4:4.4f}\tValid Acc: {5:4.4f}".format(
                e, max_epoch, train_loss,
                valid_loss, train_acc, valid_acc))
    # Save the model
    with open('model-' + timestr + '.pkl', 'wb') as output:
        pickle.dump((W, B), output, pickle.HIGHEST_PROTOCOL)


# You can run the script using below command and pass max epoch and learning rate
# python3 digit_classification.py 10 0.1
if __name__ == '__main__':
    np.random.seed(1234)
    # Set default value for arguments
    mode = 'train'
    lr = 0.1
    max_epoch = 40
    model_path = None
    sample_command = 'python --mode=train --lr=0.1 --max-epoch=10'
    # Parse arguments
    try:
        opts, args = getopt.getopt(sys.argv[1:], "",
                                   ["lr=", "max-epoch=", "mode=", "model="])
    except getopt.GetoptError:
        print(sample_command)
        sys.exit(2)

    for opt, arg in opts:
        if opt == '--mode':
            mode = arg
        elif opt in ("--lr"):
            lr = float(arg)
        elif opt in ("--max-epoch"):
            max_epoch = int(arg)
        elif opt in ("--model"):
            model_path = arg

    # Start algorithm
    if mode == 'train':
        stochastic_gradient_descent(max_epoch, lr)
    else:
        test(model_path)
