import abc
import itertools
from torch import nn
from torch.nn import functional as F
from torch import optim

import numpy as np
import torch
from torch import distributions

from cmpt720a3q1code.infrastructure import pytorch_util as ptu
from cmpt720a3q1code.policies.base_policy import BasePolicy


class MLPPolicy(BasePolicy, nn.Module, metaclass=abc.ABCMeta):

    def __init__(self,
                 ac_dim,
                 ob_dim,
                 n_layers,
                 size,
                 discrete=False,
                 learning_rate=1e-4,
                 training=True,
                 nn_baseline=False,
                 **kwargs
                 ):
        super().__init__(**kwargs)

        # init vars
        self.ac_dim = ac_dim
        self.ob_dim = ob_dim
        self.n_layers = n_layers
        self.discrete = discrete
        self.size = size
        self.learning_rate = learning_rate
        self.training = training
        self.nn_baseline = nn_baseline

        if self.discrete:
            self.logits_na = ptu.build_mlp(input_size=self.ob_dim,
                                           output_size=self.ac_dim,
                                           n_layers=self.n_layers,
                                           size=self.size)
            self.logits_na.to(ptu.device)
            self.mean_net = None
            self.logstd = None
            self.optimizer = optim.Adam(self.logits_na.parameters(),
                                        self.learning_rate)
        else:
            self.logits_na = None
            self.mean_net = ptu.build_mlp(input_size=self.ob_dim,
                                          output_size=self.ac_dim,
                                          n_layers=self.n_layers, size=self.size)
            self.logstd = nn.Parameter(
                torch.zeros(self.ac_dim, dtype=torch.float32,
                            device=ptu.device)
            )
            self.mean_net.to(ptu.device)
            self.logstd.to(ptu.device)
            self.optimizer = optim.Adam(
                itertools.chain([self.logstd], self.mean_net.parameters()),
                self.learning_rate
            )

        if nn_baseline:
            self.baseline = ptu.build_mlp(
                input_size=self.ob_dim,
                output_size=1,
                n_layers=self.n_layers,
                size=self.size,
            )
            self.baseline.to(ptu.device)
            self.baseline_optimizer = optim.Adam(
                self.baseline.parameters(),
                self.learning_rate,
            )
        else:
            self.baseline = None

    ##################################

    def save(self, filepath):
        torch.save(self.state_dict(), filepath)

    ##################################

    # query the policy with observation(s) to get selected action(s)
    def get_action(self, obs: np.ndarray) -> np.ndarray:
        # TODO: get this from Piazza
        # ............................................................
        obs = ptu.from_numpy(obs)
        distribution = self.forward(obs)
        action = distribution.sample()
        action = ptu.to_numpy(action)
        # ............................................................
        return action

    # update/train this policy
    def update(self, observations, actions, **kwargs):
        raise NotImplementedError

    # This function defines the forward pass of the network.
    # You can return anything you want, but you should be able to differentiate
    # through it. For example, you can return a torch.FloatTensor. You can also
    # return more flexible objects, such as a
    # `torch.distributions.Distribution` object. It's up to you!
    def forward(self, observation: torch.FloatTensor):
        if self.discrete:
            logit = self.logits_na(observation)
            action_distribution = distributions.categorical.Categorical(
                logits=logit)
        else:
            mean = self.mean_net(observation)
            std = torch.exp(self.logstd)
            action_distribution = distributions.multivariate_normal.MultivariateNormal(
                mean, torch.diag(std))

        return action_distribution


#####################################################
#####################################################


class MLPPolicyAC(MLPPolicy):
    def update(self, observations, actions, adv_n=None):
        # Updates the policy and return the loss based on the observations, actions, and advantage
        observations, actions, adv_n = map(lambda x: torch.from_numpy(x).to(ptu.device), [
                                           observations, actions, adv_n])

        # TODO: compute the loss that should be optimized when training with policy gradient
        # HINT1: Recall that the expression that we want to MAXIMIZE
        #        is the expectation over collected trajectories of
        #        sum_{t=0}^{T-1} [grad [log pi(a_t|s_t) * (Q_t - b_t)]]
        # HINT2: you will want to use the `log_prob` method on the distribution returned
        #        by the `forward` method that evaluates action probabilities

        ##### TO DO: compute action probabilties pi(a_t|s_t) #####
        action_prob = self.forward(observations).log_prob(actions)  # log pi(a_t|s_t)

        #### TO DO: compute loss function, which pytorch will minimize #####
        # HINT3: don't forget that `optimizer.step()` MINIMIZES a loss
        # loss = torch.mean(torch.sum((action_prob - adv_n)**2)) # MSE
        # loss = torch.mean(torch.sum(action_prob * adv_n))
        # loss = torch.mean(torch.sum((action_prob * adv_n)**2)) # LOSS squared
        # in HINT1 we are told to Sum, not Average
        loss = torch.sum((-1 * action_prob * adv_n))

        self.optimizer.zero_grad()
        loss.backward()
        self.optimizer.step()
        # ............................................................
        return loss  # .item() # Actor loss
