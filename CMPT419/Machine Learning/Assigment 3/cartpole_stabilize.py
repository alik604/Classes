import tensorflow as tf
import tensorflow.contrib.slim as slim
import numpy as np
import gym
import matplotlib.pyplot as plt

'''
refs: 
https://medium.com/@jonathan_hui/rl-policy-gradients-explained-9b13b688b146
https://medium.com/@jonathan_hui/rl-policy-gradients-explained-advanced-topic-20c2b81a9a8b

'''


def compute_advantage(j, r, gamma):
    ### Part f) Advantage computation
    """ Computes the advantage function from data
        Inputs:
            j     -- list of time steps 
                    (eg. j == [0, 1, 2, 3, 0, 1, 2, 3, 4, 5] means that there 
                     are two episodes, one with four time steps and another with
                     6 time steps)
            r     -- list of rewards from episodes corresponding to time steps j
            gamma -- discount factor

        Output:
            advantage -- vector of advantages correponding to time steps j
    """
    '''
     Ref: I had a hard time with this. I needed help
     https://www.cs.toronto.edu/~rgrosse/courses/csc321_2018/slides/lec21.pdf
     https://medium.com/@thechrisyoon/deriving-policy-gradients-and-implementing-reinforce-f887949bd63
     https://lilianweng.github.io/lil-log/2018/05/05/implementing-deep-reinforcement-learning-models.html#monte-carlo-policy-gradient
     class mates - waited over night before coding 
     main reference: https://www.youtube.com/watch?v=IS0V8z8HXrM

     I could make the code more efficient, but code takes less than 300s for me, thats fast enough

        # rewards = np.array(r)
        # G = np.zeros_like(rewards)

        # for t in range(len(rewards)): # TODO use j. make avg = sum/#of_J
        #     G_sum = 0 
        #     discount = 1
        #     for k in range(t,len(rewards)):
        #         G_sum += rewards[k] * discount
        #         discount *=gamma
        #     G[t] = G_sum
        # mean = np.mean(G)
        # std = np.std(G) if np.std(G) > 0 else 1
        # advantage = (G-mean)#/std
        # return advantage

    '''
    # I do not need update_frequency. 
    reward = r
    advantage, reward_list, episode_list, rewards_util_list, episode = [], [], [], [], []
    for index, element in enumerate(j):
        # if index != element: # these two are the same
        #    print(index == element)
        if index == 0:
            episode.append(element)
            rewards_util_list.append(reward[index])
        elif element == 0:
            episode_list.append(episode)
            episode = [0] #re init
            reward_list.append(rewards_util_list)
            rewards_util_list = [reward[index]] #re init
        elif index == len(j)-1:
            episode.append(element)
            episode_list.append(episode)
            rewards_util_list.append(reward[index])
            reward_list.append(rewards_util_list)
        else:
            episode.append(element)
            rewards_util_list.append(reward[index])

    episode, rewards_util_list = None, None 
    for index, episode in enumerate(episode_list):
        b=0 
        discount = 1
        for t in range(len(episode)): # python list comp syntax would be a massive speedup, but would need a discount[] 
            b += discount * reward_list[index][t]
            discount *= gamma
        for t in episode:
            discounted_reward = 0 
            discount = 1
            for idx in range(t,len(episode)):
                discounted_reward += discount * reward_list[index][idx]
                discount *=gamma
                advantage_t = discounted_reward - b 
            advantage.append(advantage_t)


    # normalize - optional. recomended in docs, but not in class slides 
    # improves minimum training time 

    # mean = np.mean(advantage)
    # std = np.std(advantage) if np.std(advantage) > 0 else 1
    # advantage = (advantage-mean)/std
    return advantage

    '''

    '''

class agent():
    
    def __init__(self, lr, s_size, a_size, h1_size, h2_size):
        # ref: https://github.com/lilianweng/deep-reinforcement-learning-gym/blob/master/playground/utils/tf_ops.py
        """ Initialize the RL agent 
            Inputs:
                lr      -- learning rate
                s_size  -- # of states
                a_size  -- # of actions (output of policy network)
                h1_size -- # of neurons in first hidden layer of policy network
                h2_size -- # of neurons in second hidden layer of policy network
            """
        # Data consists of a list of states, actions, and rewards
        self.actions = tf.placeholder(tf.int32, shape=[None])
        self.advantage = tf.placeholder(tf.float32, shape=[None])
        self.inputs = tf.placeholder(tf.float32,shape=[None, s_size]) # states 


        ### --- Part c) Define the policy network ---
        # Input should be the state (defined above)
        # Output should be the probability distribution of actions
        self.layer_1 = tf.layers.dense(self.inputs, h1_size,
                                                        activation=tf.nn.relu,
                                                        kernel_initializer=tf.contrib.layers.xavier_initializer()) 
                                                        # "ideal"

        self.hidden_layer_2 = tf.layers.dense(self.layer_1, h2_size,
                                                        activation=tf.nn.relu,
                                                        kernel_initializer=tf.contrib.layers.xavier_initializer())

        self.hidden_layer_3 = tf.layers.dense(self.hidden_layer_2, a_size,
                                                        activation=None,
                                                        kernel_initializer=tf.contrib.layers.xavier_initializer())
        
        ### -- Part d) Compute probabilities of realized actions (from data) --
        # Indices of policy network outputs (which are probabilities) 
        # corresponding to action data

        #// use with `action_distribution = sess.run(agent.output_layer ,feed_dict={agent.inputs:[state]})` 
        # softmax ONLY FOR action_distribution in main loop 
        self.output_layer = tf.nn.softmax(self.hidden_layer_3)

        ### -- Part e) Define loss function for policy improvement procedure --
        # IMPORTANT! -> uses hidden_layer_3, which has no activation 
        self.loss_temp = self.advantage * tf.nn.sparse_softmax_cross_entropy_with_logits(logits=self.hidden_layer_3, labels=self.actions)
        self.loss = tf.reduce_mean(self.loss_temp)

        ### -------------------------------------------------------------------
        
        '''
        part D & E secound way 
        self.action_probs = tf.squeeze(tf.nn.softmax(self.hidden_layer_3))
        self.picked_action_prob = tf.gather(self.action_probs, self.actions)
        self.loss = -tf.log(self.picked_action_prob) * self.advantage
        '''



        # Gradient computation
        tvars = tf.trainable_variables()
        self.gradients = tf.gradients(self.loss, tvars)
        
        # Apply update step
        optimizer = tf.train.AdamOptimizer(learning_rate=lr)
        self.update_batch = optimizer.apply_gradients(zip(self.gradients, tvars))

        # More modern approch
        #self.update_batch = tf.train.AdamOptimizer(learning_rate=lr).minimize(self.loss)

    # --- end def ---
# --- end class ---  


##### Main script #####
env = gym.make('CartPole-v1')  # Initialize gym environment
gamma = 0.99  # Discount factor

# https://github.com/openai/gym/blob/master/gym/wrappers/monitoring/video_recorder.py
# video_recorder(env, 'video.mp4',True)

# initialize tensor flow model
tf.reset_default_graph()

### --- Part g) create the RL agent ---
agent = agent(0.001, 4, 2, 8, 8)
# uncomment fill in arguments of the above line to initialize an RL agent whose
# policy network contains two hidden layers with 8 neurons each

### -----------------------------------

gamma = 0.99  # Discount rate
total_episodes = 2500#*3  # maximum # of training episodes
max_steps = 500  # maximum # of steps per episode (overridden in gym environment)
update_frequency = 1  # Seting this to be != 1, will cause very slow learning. i testied to 10,000 episodes number of episodes between policy network updates


init = tf.global_variables_initializer()
with tf.Session() as sess:
    # Initialization
    sess.run(init)
    i = 0
    episode_advatage_list = []
    running_reward_list = []

    #gradBuffer = sess.run(tf.trainable_variables())

    # While we have episodes to train
    while i < total_episodes:
        state = env.reset()
        running_reward = 0
        history = [] # Init the array that keep track the history in an episode

        # omited in submission. internet says to do this. 
        # sess.run(agent.update_batch, feed_dict=dict(zip(agent.gradients, gradBuffer)))
        # for idx,grad in enumerate(gradBuffer):
        #     gradBuffer[idx] = grad * 0
        

        for j in range(max_steps):
            if i % 400 == 0 or i >= total_episodes-2:
                env.render() # appears to be on a single thread. waste a lot of time 

            ### ------------ Part g) -------------------
            ### Probabilistically pick an action given policy network outputs.
            action_distribution = sess.run(agent.output_layer ,feed_dict={agent.inputs:[state]})
            action = np.random.choice(action_distribution[0],p=action_distribution[0])
            action = np.argmax(action_distribution == action)
            ### ----------------------------------------
            # Get reward for taking an action, and store data from the episode
            state_1, reward, done, info = env.step(action)
            
            # Append this step in the history of the episode
            history.append([state, action, reward, state_1,j])
            
            # Now we are in this state (state is now state 1)
            state = state_1
            running_reward += reward
            
            if done == True:
                # Update network every "update_frequency" episodes
                ## I am not doing this. my way works trainings better and fast. my computer can actually handle this way
                if i % update_frequency == 0 and i != 0: 
                    history = np.array(history)
                    # history[:,2] = compute_advantage(history[:,2])
                    history[:,2] = compute_advantage(history[:,4], history[:,2],gamma)

                    ### --- Part g) Perform policy update ---
                    feed_dict={agent.advantage:history[:,2],
                        agent.actions:history[:,1],agent.inputs:np.vstack(history[:,0])}
                    sess.run([agent.update_batch], feed_dict=feed_dict)

                    ''' omited in submission. internet says to do this. 
                    # _, grads = sess.run([agent.loss, agent.gradients], feed_dict=feed_dict)
                    # for idx,grad in enumerate(grads):
                    #     gradBuffer[idx] += grad
                    # sess.run(agent.update_batch, feed_dict=dict(zip(agent.gradients, gradBuffer)))
                    # for idx,grad in enumerate(gradBuffer):
                    #     gradBuffer[idx] = grad * 0
                    ### -------------------------------------
                    '''

                    episode_advatage_list.append((i, sum(history[:,2])))
                    print('episode: ', i,'\tscore: ', running_reward, '\tsum of discounted reward: ',sum(history[:,2])) # tscore works bst if update_frequency = 1 
                    history = []
                    running_reward_list.append(running_reward)
                    running_reward = 0
                break
              
        # if i % 100 == 0:
        #     print("Episode: {}".format(i),
        #             "Avg Reward of last max_steps: {}".format(np.mean(running_reward_list[-max_steps:])))
        i += 1


    # yes this is a odd spot... mid while loop. but you can read my code and tell "what, when, & how"
    def plotLearning(scores, filename = "Running Average of rewards from env.step(action)", x=None, window=10):   
        N = len(scores)
        running_avg = np.empty(N)
        for t in range(N):
            running_avg[t] = np.mean(scores[max(0, t-window):(t+1)])
        if x is None:
            x = [i for i in range(N)]
        plt.ylabel('Score')       
        plt.xlabel('CartPole episode')    
        plt.title("Running Average of rewards from env.step(action). window= "+ str(window))         
        plt.plot(x, running_avg)
        plt.savefig(filename)
        plt.show()

    plotLearning(running_reward_list,'Running Average of rewards from env_step_action', window = 10)

    def plotDicountedRewardPerEPI(scores = episode_advatage_list, filename = 'Sum of discounted reward in each episode vs episode number'):   
        x = [i[0] for i in episode_advatage_list]
        y = [i[1] for i in episode_advatage_list]
        # print('x ', x)
        # print('y ', y)

        plt.ylabel('Sum of discounted reward in each episode')       
        plt.xlabel('Episode number')    
        plt.title("Sum of discounted reward in each episode vs. episode number")         
        plt.plot(x, y)
        plt.savefig(filename)
        plt.show()
    plotDicountedRewardPerEPI(episode_advatage_list)

        # --- end while ---
    
# --- end of script ---