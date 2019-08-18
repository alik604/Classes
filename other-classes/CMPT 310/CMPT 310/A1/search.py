# search.py
# ---------
# Licensing Information:  You are free to use or extend these projects for
# educational purposes provided that (1) you do not distribute or publish
# solutions, (2) you retain this notice, and (3) you provide clear
# attribution to UC Berkeley, including a link to http://ai.berkeley.edu.
#
# Attribution Information: The Pacman AI projects were developed at UC Berkeley.
# The core projects and autograders were primarily created by John DeNero
# (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# Student side autograding was added by Brad Miller, Nick Hay, and
# Pieter Abbeel (pabbeel@cs.berkeley.edu).


#####################################################
#####################################################
# Please enter the number of hours you spent on this
# assignment here
num_hours_i_spent_on_this_assignment = 0
#####################################################
#####################################################

#####################################################
#####################################################
# Give one short piece of feedback about the course so far. What
# have you found most interesting? Is there a topic that you had trouble
# understanding? Are there any changes that could improve the value of the
# course to you? (We will anonymize these before reading them.)
"""
<Your feedback goes here>

"""
#####################################################
#####################################################



"""
In search.py, you will implement generic search algorithms which are called by
Pacman agents (in searchAgents.py).
"""

import util

class SearchProblem:
    """
    This class outlines the structure of a search problem, but doesn't implement
    any of the methods (in object-oriented terminology: an abstract class).

    You do not need to change anything in this class, ever.
    """

    def getStartState(self):
        """
        Returns the start state for the search problem.
        """
        util.raiseNotDefined()

    def isGoalState(self, state):
        """
          state: Search state

        Returns True if and only if the state is a valid goal state.
        """
        util.raiseNotDefined()

    def getSuccessors(self, state):
        """
          state: Search state

        For a given state, this should return a list of triples, (successor,
        action, stepCost), where 'successor' is a successor to the current
        state, 'action' is the action required to get there, and 'stepCost' is
        the incremental cost of expanding to that successor.
        """
        util.raiseNotDefined()

    def getCostOfActions(self, actions):
        """
         actions: A list of actions to take

        This method returns the total cost of a particular sequence of actions.
        The sequence must be composed of legal moves.
        """
        util.raiseNotDefined()


def tinyMazeSearch(problem):
    """
    Returns a sequence of moves that solves tinyMaze.  For any other maze, the
    sequence of moves will be incorrect, so only use this for tinyMaze.
    """
    from game import Directions
    s = Directions.SOUTH
    w = Directions.WEST
    return  [s, s, w, s, w, w, s, w]




def convert_direction(directions):
    from game import Directions
    if(directions=='South'):
        return Directions.SOUTH
    elif(directions=='North'):
        return Directions.NORTH
    elif(directions=='West'):
        return Directions.WEST
    elif(directions=='East'):
        return Directions.EAST

def depthFirstSearch(problem):
    """
    Questoin 1.1
    Search the deepest nodes in the search tree first.

    Your search algorithm needs to return a list of actions that reaches the
    goal. Make sure to implement a graph search algorithm.

    To get started, you might want to try some of these simple commands to
    understand the search problem that is being passed in:

    print ( problem.getStartState() )
    print (problem.isGoalState(problem.getStartState()) )
    print ( problem.getSuccessors(problem.getStartState()) )

    """
    "*** YOUR CODE HERE ***"
    from game import Directions

    visited = []
    stack = [(problem.getStartState(), 'None', 0, [])]
    while stack:
        (vertex, directions, distance, cur_actions) = stack.pop()

        if (problem.isGoalState(vertex)):
            print(problem.getCostOfActions(cur_actions))
            return cur_actions
        if vertex not in visited:
            visited.append((vertex, directions, distance))
            addson = problem.getSuccessors(vertex)
            for next in addson:
                (cur_vertex, cur_direction, cur_distance) = next
                if (cur_vertex, cur_direction, cur_distance) not in visited:
                    stack.append((cur_vertex, cur_direction, cur_distance, cur_actions + [cur_direction]))


def breadthFirstSearch(problem):
    """Questoin 1.2
     Search the shallowest nodes in the search tree first.
     """
    "*** YOUR CODE HERE ***"
    from collections import deque
    from game import Directions
    # queue structure in python
    Queue = deque([(problem.getStartState(),'',0,[])])
    visited = []
    while Queue:
        # print (visited)

        (vertex,directions,distance,next_actions) = Queue.popleft()
        # print(vertex,next_actions)
        if problem.isGoalState(vertex):
                return next_actions
        if vertex not in visited:
            # visited.append((vertex,directions,distance))
            visited.append(vertex)
            addson = problem.getSuccessors(vertex)
            for next in addson:
                (next_vertex,next_direction,next_distance) = next
                print(next)
                if next_vertex not in visited:
                    Queue.append((next_vertex,next_direction,next_distance,next_actions+[next_direction]))


def nullHeuristic(state, problem=None):
    """
    A heuristic function estimates the cost from the current state to the nearest
    goal in the provided SearchProblem.  This heuristic is trivial.
    """
    return 0

def aStarSearch(problem, heuristic=nullHeuristic):
    """Question 1.3
    Search the node that has the lowest combined cost and heuristic first."""
    "*** YOUR CODE HERE ***"
    from queue import PriorityQueue
    frontier = PriorityQueue()
    frontier.put((0, (problem.getStartState(), [])))

    cur_cost = {}
    cur_cost[problem.getStartState()] = 0
    while frontier:
        (priority, cur) = frontier.get()
        vertex = cur[0]
        actions = cur[1]
        if problem.isGoalState(vertex):
            return actions
        for next in problem.getSuccessors(vertex):
            (cur_vertex, cur_direction, cur_distance) = next
            new_cost = cur_cost[vertex] + cur_distance
            if cur_vertex not in cur_cost or new_cost < cur_cost[vertex]:
                cur_cost[cur_vertex] = new_cost
                priority = new_cost + heuristic(cur_vertex, problem)
                frontier.put((priority, (cur_vertex, actions + [convert_direction(cur_direction)])))



# Abbreviations
bfs = breadthFirstSearch
dfs = depthFirstSearch
astar = aStarSearch
