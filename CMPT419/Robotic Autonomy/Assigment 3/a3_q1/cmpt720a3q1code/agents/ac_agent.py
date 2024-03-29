from collections import OrderedDict

from cmpt720a3q1code.critics.bootstrapped_continuous_critic import \
    BootstrappedContinuousCritic
from cmpt720a3q1code.infrastructure.replay_buffer import ReplayBuffer
from cmpt720a3q1code.infrastructure.utils import *
from cmpt720a3q1code.policies.MLP_policy import MLPPolicyAC
from .base_agent import BaseAgent
import torch
import numpy as np

from cmpt720a3q1code.infrastructure import pytorch_util as ptu


class ACAgent(BaseAgent):
    def __init__(self, env, agent_params):
        super(ACAgent, self).__init__()

        self.env = env
        self.agent_params = agent_params

        self.gamma = self.agent_params['gamma']
        self.standardize_advantages = self.agent_params['standardize_advantages']

        self.actor = MLPPolicyAC(
            self.agent_params['ac_dim'],
            self.agent_params['ob_dim'],
            self.agent_params['n_layers'],
            self.agent_params['size'],
            self.agent_params['discrete'],
            self.agent_params['learning_rate'],
        )
        self.critic = BootstrappedContinuousCritic(self.agent_params)

        self.replay_buffer = ReplayBuffer()

    def train(self, ob_no, ac_na, re_n, next_ob_no, terminal_n):
        # TODO Implement the following pseudocode:
        # for agent_params['num_critic_updates_per_agent_update'] steps,
        #     update the critic

        # advantage = estimate_advantage(...)

        # for agent_params['num_actor_updates_per_agent_update'] steps,
        #     update the actor
        loss = OrderedDict()
        # ............................................................
        critic_loss = 0
        actor_loss = 0
        for _ in range(self.agent_params['num_critic_updates_per_agent_update']):
            critic_loss = self.critic.update(
                ob_no, ac_na, next_ob_no, re_n, terminal_n)
        loss['Critic_Loss'] = critic_loss
        advantage = self.estimate_advantage(
            ob_no, next_ob_no, re_n, terminal_n)

        for _ in range(self.agent_params['num_actor_updates_per_agent_update']):
            actor_loss = self.actor.update(ob_no, ac_na, advantage)
        loss['Actor_Loss'] = actor_loss

        return loss

    def estimate_advantage(self, ob_no, next_ob_no, rewards, terminal_n):
        # change dtype send to device
        ob, next_ob, rewards, done = map(lambda x: torch.from_numpy(
            x).to(ptu.device), [ob_no, next_ob_no, rewards, terminal_n])

        # TODO Implement the following pseudocode:

        # 1) query the critic with s, to get V(s)
        # torch.from_numpy(ob_no) # .float().to(device)
        value = self.critic(ob)

        # 2) query the critic with next_values, to get V(s')
        next_value = self.critic(next_ob).squeeze() * (1 - done)

        # 3) estimate the Q value as Q(s, a) = r(s, a) + gamma*V(s')
        adv_n = rewards + (self.gamma * next_value) - value

        # 4) calculate advantage (adv_n) as A(s, a) = Q(s, a) - V(s)
        adv_n = adv_n - value
        # this has to be here because the below code is Numpy. normally id return `adv_n.cpu().detach().numpy()`
        adv_n = adv_n.cpu().detach().numpy()

        # next_values = np.zeros_like(rewards)
        # print(f'rewards is {rewards}')
        # for i in reversed(range(len(rewards))):
        #     # if terminal_n[i] == 1:
        #     #     # print(f'i is {i} at break point')
        #     #     break
        #     print(f'last_value.shape {last_value.shape}')
        #     print(f'rewards[i].shape {rewards[i]}')

        #     next_values[i] = rewards[i] + self.gamma * last_value.detach().numpy() * (1-terminal_n)
        #     last_value = next_values[i]
        #     print(f'in ac_agent: next_values[i] is {next_values[i]} | terminal_n[i] is {terminal_n[i]}')
        # or
        # last_value = self.critic(next_values) # .float().to(device)
        # next_values = rewards + self.gamma * last_value.detach().numpy() * (1-terminal_n)
        # adv_n = next_values - values.to('cpu').detach().numpy()

        if self.standardize_advantages:
            adv_n = (adv_n - np.mean(adv_n)) / (np.std(adv_n) + 1e-8)
        return adv_n

    def add_to_replay_buffer(self, paths):
        self.replay_buffer.add_rollouts(paths)

    def sample(self, batch_size):
        return self.replay_buffer.sample_recent_data(batch_size)
