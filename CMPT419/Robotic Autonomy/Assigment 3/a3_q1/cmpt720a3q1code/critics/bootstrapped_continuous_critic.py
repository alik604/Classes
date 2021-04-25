from .base_critic import BaseCritic
from torch import nn
from torch import optim
import torch

from cmpt720a3q1code.infrastructure import pytorch_util as ptu


class BootstrappedContinuousCritic(nn.Module, BaseCritic):
    """
        Notes on notation:

        Prefixes and suffixes:
        ob - observation
        ac - action
        _no - this tensor should have shape (batch self.size /n/, observation dim)
        _na - this tensor should have shape (batch self.size /n/, action dim)
        _n  - this tensor should have shape (batch self.size /n/)

        Note: batch self.size /n/ is defined at runtime.
        is None
    """

    def __init__(self, hparams):
        super().__init__()
        self.ob_dim = hparams['ob_dim']
        self.ac_dim = hparams['ac_dim']
        self.discrete = hparams['discrete']
        self.size = hparams['size']
        self.n_layers = hparams['n_layers']
        self.learning_rate = hparams['learning_rate']

        # critic parameters
        self.num_target_updates = hparams['num_target_updates']
        self.num_grad_steps_per_target_update = hparams['num_grad_steps_per_target_update']
        self.gamma = hparams['gamma']
        self.critic_network = ptu.build_mlp(
            self.ob_dim,
            1,
            n_layers=self.n_layers,
            size=self.size,
        )
        self.critic_network.to(ptu.device)
        self.loss = nn.MSELoss()
        self.optimizer = optim.Adam(
            self.critic_network.parameters(),
            self.learning_rate,
        )

    def forward(self, obs):
        return self.critic_network(obs).squeeze(1)

    def forward_np(self, obs):
        obs = ptu.from_numpy(obs)
        predictions = self(obs)
        return ptu.to_numpy(predictions)

    def update(self, ob_no, ac_na, next_ob_no, reward_n, terminal_n):
        """
            Update the parameters of the critic.

            let sum_of_path_lengths be the sum of the lengths of the paths sampled from
                Agent.sample_trajectories
            let num_paths be the number of paths sampled from Agent.sample_trajectories

            arguments:
                ob_no: shape: (sum_of_path_lengths, ob_dim)
                next_ob_no: shape: (sum_of_path_lengths, ob_dim). The observation after taking one step forward
                reward_n: length: sum_of_path_lengths. Each element in reward_n is a scalar containing
                    the reward for each timestep
                terminal_n: length: sum_of_path_lengths. Each element in terminal_n is either 1 if the episode ended
                    at that timestep of 0 if the episode did not end

            returns:
                training loss
        """
        # The code should implement the pseudocode below: do the following (
        # self.num_grad_steps_per_target_update * self.num_target_updates)
        # times:
        # every self.num_grad_steps_per_target_update steps (which includes the
        # first step), recompute the target values by
        #     a) calculating V(s') by querying the critic with next_ob_no
        #     b) and computing the target values as r(s, a) + gamma * V(s')
        # every time, update this critic using the observations and targets
        #
        # HINT: don't forget to use terminal_n to cut off the V(s') (ie set it
        #       to 0) when a terminal state is reached
        #
        #
        # ............................................................
        num_iter = self.num_grad_steps_per_target_update*self.num_target_updates
        reward_n = ptu.from_numpy(reward_n).to(ptu.device)
        ob_no = ptu.from_numpy(ob_no).to(ptu.device)  # current state
        ac_na = ptu.from_numpy(ac_na).to(ptu.device)
        next_ob_no = ptu.from_numpy(next_ob_no).to(ptu.device)  # next state
        terminal_n = ptu.from_numpy(terminal_n).to(ptu.device)

        # print(f'reward_n.shape {reward_n.shape}')
        # print(f'ob_no.shape {ob_no.shape}')

        ##### TO DO: estimate the value/return from current state #####
        # unneeded, but im told to..
        target_value = reward_n + self.gamma * \
            self.forward(ob_no) * (1-terminal_n)
        loss_value = None
        # cormfirm to the official scheme

        for itr in range(num_iter):

            # target update
            if itr % self.num_grad_steps_per_target_update == 0:

                ##### TO DO: estimate the value/return from current state #####
                # or ob_no? in 80 epcohs of cartpole, there was no difference
                target_value_ = self.forward(next_ob_no)
                # target_value_ = target_value_.detach().cpu().numpy()
                target_value = reward_n + self.gamma * target_value_ * \
                    (1-terminal_n)  # reward_n[itr] # terminal_n[itr]
                target_value = target_value.detach()

            # current predicted value and loss

            ##### TO DO: evaluate the value network #####
            value = self.forward(ob_no)

            ##### TO DO: compute MSE loss (HINT: see class declaration) #####
            # gradient descent
            self.optimizer.zero_grad()
            loss_value = self.loss(target_value, value)
            loss_value.backward()
            self.optimizer.step()
        return loss_value
