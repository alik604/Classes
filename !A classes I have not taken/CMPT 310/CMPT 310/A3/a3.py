#!/usr/bin/python3

import math
import random
import sys


# Outputs a random integer, according to a multinomial
# distribution specified by probs.
def rand_multinomial(probs):
    # Make sure probs sum to 1
    assert (abs(sum(probs) - 1.0) < 1e-5)
    rand = random.random()
    for index, prob in enumerate(probs):
        if rand < prob:
            return index
        else:
            rand -= prob
    return 0


# Outputs a random key, according to a (key,prob)
# iterator. For a probability dictionary
# d = {"A": 0.9, "C": 0.1}
# call using rand_multinomial_iter(d.items())
def rand_multinomial_iter(iterator):
    rand = random.random()
    for key, prob in iterator:
        if rand < prob:
            return key
        else:
            rand -= prob
    return 0


class HMM():

    def __init__(self):
        self.num_states = 2
        self.prior = [0.5, 0.5]
        self.transition = [[0.999, 0.001], [0.01, 0.99]]
        self.emission = [{"A": 0.291, "T": 0.291, "C": 0.209, "G": 0.209},
                         {"A": 0.169, "T": 0.169, "C": 0.331, "G": 0.331}]

    # Generates a sequence of states and characters from
    # the HMM model.
    # - length: Length of output sequence
    def sample(self, length):
        sequence = []
        states = []
        rand = random.random()
        cur_state = rand_multinomial(self.prior)
        for i in range(length):
            states.append(cur_state)
            char = rand_multinomial_iter(self.emission[cur_state].items())
            sequence.append(char)
            cur_state = rand_multinomial(self.transition[cur_state])
        return sequence, states

    # Generates a emission sequence given a sequence of states
    def generate_sequence(self, states):
        sequence = []
        for state in states:
            char = rand_multinomial_iter(self.emission[state].items())
            sequence.append(char)
        return sequence

    # Computes the (natural) log probability of sequence given a sequence of states.
    def logprob(self, sequence, states):
        ###########################################
        # Start your code
        if (len(sequence) != len(states) or (len(sequence) == 0) or (len(states) == 0)):
            return None
        prob = math.log(self.prior[states[0]])
        prob += math.log(self.emission[states[0]][sequence[0]])
        for i in range(1, len(sequence)):
            seqProb = self.emission[states[i]][sequence[i]]
            stateProb = self.transition[states[i - 1]][states[i]]
            prob += math.log(stateProb)
            prob += math.log(seqProb)
        print("Log prob: " + str(prob));
        return prob
        # End your code
        ###########################################

    # Outputs the most likely sequence of states given an emission sequence
    # - sequence: String with characters [A,C,T,G]
    # return: list of state indices, e.g. [0,0,0,1,1,0,0,...]
    def viterbi(self, sequence):
        ###########################################
        # Start your code
        M = matrix(len(sequence), self.num_states)
        prev = matrix(len(sequence), self.num_states)
        # Initialize first row of M and prev
        for n in range(self.num_states):
            for k in range(self.num_states):
                prob = math.log(self.prior[k]) + math.log(self.transition[k][n]) + math.log(
                    self.emission[k][sequence[0]])
                if ((M[0][n] == None) or (prob > M[0][n])):
                    M[0][n] = prob
                    prev[0][n] = k
        # Fill in rest of M and prev
        for t in range(1, len(sequence)):
            for i in range(self.num_states):
                for j in range(self.num_states):
                    prob = M[t - 1][j] + math.log(self.transition[j][i]) + math.log(self.emission[j][sequence[t]])
                    if (prob > M[t][i]):
                        M[t][i] = prob
                        prev[t][i] = j
        return bestSequence(M, prev)
        # End your code
        ###########################################


def read_sequence(filename):
    with open(filename, "r") as f:
        return f.read().strip()


def write_sequence(filename, sequence):
    with open(filename, "w") as f:
        f.write("".join(sequence))


def write_output(filename, logprob, states):
    with open(filename, "w") as f:
        f.write(str(logprob))
        f.write("\n")
        for state in range(2):
            f.write(str(states.count(state)))
            f.write("\n")
        f.write("".join(map(str, states)))
        f.write("\n")


# Initialize matrix of dimensions T x D
def matrix(T, D):
    mat = []
    for i in range(T):
        newRow = []
        for j in range(D):
            newRow.append(float("-inf"))
        mat.append(newRow)
    return mat


def bestSequence(M, prev):
    seq = []
    max = M[len(M) - 1][0]
    maxIndex = 0
    for i in range(1, len(M[0])):
        if (M[len(M) - 1][i] > max):
            maxIndex = i
            max = M[len(M) - 1][i]
    length = len(M)
    for n in range(length):
        val = prev[length - n - 1][maxIndex]
        seq.append(val)
        maxIndex = val
    seq.reverse()
    return seq


hmm = HMM()

file = sys.argv[1]
sequence = read_sequence(file)
viterbi = hmm.viterbi(sequence)
logprob = hmm.logprob(sequence, viterbi)
name = "my_" + file[:-4] + '_output.txt'
write_output(name, logprob, viterbi)
