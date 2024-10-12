# Water Sort Puzzle Solver

## Introduction

This project implements a search agent that solves the water sort puzzle. The puzzle consists of several bottles, each filled with different layers of colored liquids. The objective is to rearrange the liquids so that all layers in each bottle are of the same color. The agent achieves this by pouring from one bottle to another, subject to certain conditions.

### Project Features
- Implements multiple search strategies:
  - Breadth-First Search (BFS)
  - Depth-First Search (DFS)
  - Iterative Deepening Search (ID)
  - Uniform Cost Search (UCS)
  - Greedy Search with two heuristics
  - A* Search with two heuristics

## Class Diagram

Below is the class diagram illustrating the structure of the WaterSortSearch class and its relationship with other classes.

![Class Diagram](UML%20class.png)


## Search Algorithm Implementations

In this project, I implemented several search algorithms to solve the WaterSort problem using a flexible and reusable framework. The core of the implementation revolves around the `GenericSearch` class, which abstracts the common logic for different search strategies. Each specific search algorithm is implemented using this structure, with key differences in how they manage the search space (i.e., nodes) and how they prioritize exploration.

### 1. **GenericSearch Class**
- **Generic Method**: The `GenericSearch` class contains a `generalSearch` method that implements the fundamental logic for state exploration. This method takes two key parameters:
  1. **Problem**: The problem instance includes the initial state, a `goalTest` function to determine if the current state is a solution, and an `expand` function to generate possible states from a given state.
  2. **Quing Function**: The second parameter is the "quing function," which refers to the data structure used to store and manage the nodes. This can be a queue, stack, priority queue, or other structure depending on the search strategy being used.

- **Expand Function**: The `expand` method is responsible for generating the possible next states by applying all valid moves (pouring water between bottles). New states are added to the search space if they have not been explored or if they provide a lower-cost path than previously found.

- **Goal Test**: The search concludes when a node is found that satisfies the goal test, indicating that all bottles in the state are filled with a uniform color.

### 2. **Breadth-First Search (BFS)**
- **Quing Function**: For BFS, a **queue** is used as the quing function, ensuring nodes are processed in a **first-in, first-out (FIFO)** manner. BFS explores all nodes at the current depth before proceeding to deeper nodes.
- **Implementation**: In the `WaterSortSearch` class, BFS is implemented by calling `generalSearch(problem, new myLinkedList())`, where `myLinkedList` is a custom implementation of a queue.

### 3. **Depth-First Search (DFS)**
- **Quing Function**: DFS uses a **stack** as the quing function, processing nodes in a **last-in, first-out (LIFO)** manner. This allows DFS to explore as far as possible along each branch before backtracking.
- **Implementation**: DFS is implemented using a stack in `generalSearch(problem, new myStack())`, where `myStack` is a custom stack implementation.

### 4. **Uniform Cost Search (UCS)**
- **Quing Function**: UCS uses a **priority queue**, with nodes prioritized based on their cumulative cost. Nodes with the lowest cost are explored first, ensuring that the search always expands the least expensive path.
- **Implementation**: UCS is implemented using `generalSearch(problem, new myPQ(new NodeComparator(Strategy.UCS)))`, where `myPQ` is a priority queue and the `NodeComparator` prioritizes nodes based on their cost.

### 5. **Iterative Deepening Search (ID)**
- **Quing Function**: ID uses a **stack** but limits the depth of exploration. It iteratively deepens the depth limit until a solution is found.
- **Implementation**: Iterative Deepening is implemented with `generalSearch(problem, new myLimitedStack(depthLimit++))`, where the stack has a depth limit that increases with each iteration.

### 6. **Informed Search Algorithms**
For informed search algorithms (Greedy Search and A*), two heuristics are defined:
- **Heuristic 1**: The number of different layers from the bottom layer of each bottle.
- **Heuristic 2**: The number of bottles that do not have a uniform color.

These heuristics are used to estimate the cost to reach the goal and guide the search more efficiently.

- **Greedy Search (with Heuristic 1 and 2)**:
  - **Quing Function**: Greedy search uses a **priority queue**, where nodes are prioritized based on their heuristic value. The node with the lowest heuristic value (i.e., the most promising node) is explored first.
  - **Heuristic 1**: Prioritizes nodes where the least number of layers in each bottle differ from the bottom layer.
  - **Heuristic 2**: Prioritizes nodes with the fewest bottles that have multiple colors.
  - **Implementation**: Greedy search is implemented as `generalSearch(problem, new myPQ(new NodeComparator(Strategy.GREEDY_HEURISTIC1)))` and `generalSearch(problem, new myPQ(new NodeComparator(Strategy.GREEDY_HEURISTIC2)))`, depending on which heuristic is used.

- **A* Search (with Heuristic 1 and 2)**:
  - **Quing Function**: A* uses a **priority queue**, but it combines the node's cost with its heuristic evaluation. Nodes are prioritized based on the sum of the actual cost to reach the node and the heuristic value, ensuring that both cost and estimated remaining distance to the goal are considered.
  - **Implementation**: A* search is implemented as `generalSearch(problem, new myPQ(new NodeComparator(Strategy.A_STAR_HEURISTIC1)))` and `generalSearch(problem, new myPQ(new NodeComparator(Strategy.A_STAR_HEURISTIC2)))`.

#### 7. **Admissibility of Heuristics**

Admissibility is a crucial property for heuristics in A* search, meaning that the heuristic never overestimates the cost to reach the goal. For A* to guarantee the optimal solution, the heuristic must be admissible. Below is the argument for the admissibility of the two heuristics used in this project:

- **Heuristic 1 (Number of Different Layers)**: 
  This heuristic counts the number of layers in each bottle that are different from the bottom layer of the bottle. It essentially estimates how many moves are required to make all layers in each bottle uniform. Since no more moves than the number of differing layers are needed to solve the puzzle, this heuristic never overestimates the cost, making it admissible. The actual cost to reach the goal may be higher, but the heuristic provides a lower bound, ensuring admissibility.

- **Heuristic 2 (Number of Non-Uniform Bottles)**: 
  This heuristic counts the number of bottles that do not have uniform colors. It estimates the minimum number of actions required to make all bottles uniform. Since every non-uniform bottle will require at least one move to resolve, the heuristic provides a lower bound on the cost to reach the goal. Like Heuristic 1, it does not overestimate the actual cost, ensuring that this heuristic is also admissible.

Both heuristics provide optimistic estimates of the remaining cost to reach the goal, meaning that they never overestimate the true cost. This makes them **admissible** and ensures that A* search, when using these heuristics, will find the optimal solution to the WaterSort problem.




