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
  1. **Problem**: The problem instance includes the initial state, a `goalTest` function to determine if the current state is a solution, and an `expand` function to generate possible states from a given state. The problem also includes the state transitions and cost management for nodes.
  2. **Quing Function**: The second parameter is the "quing function," which refers to the data structure used to store and manage the nodes. This can be a queue, stack, priority queue, or other structure depending on the search strategy being used.

- **Expand Function**: The `expand` method is responsible for generating the possible next states by applying all valid moves (pouring water between bottles). The state expansion occurs through the creation of new nodes, each representing a state transition. The method evaluates the cost of reaching the new state and enqueues it if it hasn't been explored yet or if it offers a lower cost than previously discovered.

- **Goal Test**: The search concludes when a node is found that satisfies the goal test, which is defined in the `State` class. A node reaches the goal if all bottles in the state have uniform layers, i.e., all the layers in each bottle are of the same color.

### 2. **Node Class**
The `Node` class represents a node in the search tree. It includes:
- **State**: Each node contains a `state`, represented by an instance of the `State` class. This stores the configuration of all the bottles at a particular step in the search process.
- **Parent Node**: The `parent` reference points to the node from which the current node was expanded. This allows backtracking to generate the path to the goal.
- **Operator**: The `operator` describes the action taken to reach this node (e.g., pouring from one bottle to another).
- **Depth and Cost**: The `depth` tracks how deep the node is in the search tree, and `cost` accumulates the total cost to reach this node.

The `Node` class plays a crucial role in the search process by determining whether the current state is the goal (`isGoal()`), and providing the path taken to reach the node (`getPath()`), which is useful for visualization.

### 3. **State Class**
The `State` class represents a configuration of bottles at any point in time. It has the following responsibilities:
- **Bottles Array**: The state holds an array of `Bottle` objects representing the current state of each bottle in the puzzle.
- **State Transitions**: The `getNewState` method handles state transitions by pouring water from one bottle to another and generating a new state from that action.
- **Goal Test**: The `isGoal()` method checks whether the current state satisfies the goal condition — all bottles must have uniform layers.

The `State` class is essential in managing transitions between different configurations of the puzzle and evaluating how close a state is to the goal through its two heuristic functions:
- **Heuristic 1**: Counts the number of layers in each bottle that differ from the bottom layer.
- **Heuristic 2**: Counts the number of bottles that do not have uniform layers.

### 4. **Bottle Class**
The `Bottle` class models the behavior of each bottle in the WaterSort problem. It tracks the layers of liquid inside the bottle and provides methods for checking the bottle’s status and performing operations like pouring:
- **Layers Array**: Each bottle has an array of `layers` representing the colors of the liquid from bottom to top.
- **Pouring Logic**: The `pourTo()` method simulates pouring liquid from one bottle to another, ensuring that only valid moves are made. The move is valid if the receiving bottle is not full, and the top layer of the source bottle matches the top layer of the receiving bottle.
- **Uniformity Check**: The `isAllLayersSame()` method checks if all the non-empty layers in the bottle are the same, which is critical for determining whether a bottle is in a goal state.

### 5. **Breadth-First Search (BFS)**
- **Quing Function**: For BFS, a **queue** is used as the quing function, ensuring nodes are processed in a **first-in, first-out (FIFO)** manner. BFS explores all nodes at the current depth before proceeding to deeper nodes.
- **Implementation**: In the `WaterSortSearch` class, BFS is implemented by calling `generalSearch(problem, new myLinkedList())`, where `myLinkedList` is a custom implementation of a queue.

### 6. **Depth-First Search (DFS)**
- **Quing Function**: DFS uses a **stack** as the quing function, processing nodes in a **last-in, first-out (LIFO)** manner. This allows DFS to explore as far as possible along each branch before backtracking.
- **Implementation**: DFS is implemented using a stack in `generalSearch(problem, new myStack())`, where `myStack` is a custom stack implementation.

### 7. **Uniform Cost Search (UCS)**
- **Quing Function**: UCS uses a **priority queue**, with nodes prioritized based on their cumulative cost. Nodes with the lowest cost are explored first, ensuring that the search always expands the least expensive path.
- **Implementation**: UCS is implemented using `generalSearch(problem, new myPQ(new NodeComparator(Strategy.UCS)))`, where `myPQ` is a priority queue and the `NodeComparator` prioritizes nodes based on their cost.

### 8. **Iterative Deepening Search (ID)**
- **Quing Function**: ID uses a **stack** but limits the depth of exploration. It iteratively deepens the depth limit until a solution is found.
- **Implementation**: Iterative Deepening is implemented with `generalSearch(problem, new myLimitedStack(depthLimit++))`, where the stack has a depth limit that increases with each iteration.

### 9. **Informed Search Algorithms**
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

#### 10. **Admissibility of Heuristics**

Admissibility is a crucial property for heuristics in A* search, meaning that the heuristic never overestimates the cost to reach the goal. For A* to guarantee the optimal solution, the heuristic must be admissible. Below is the argument for the admissibility of the two heuristics used in this project:

- **Heuristic 1 (Number of Different Layers)**: 
  This heuristic counts the number of layers in each bottle that are different from the bottom layer of the bottle. It essentially estimates how many moves are required to make all layers in each bottle uniform. Since no more moves than the number of differing layers are needed to solve the puzzle, this heuristic never overestimates the cost, making it admissible. The actual cost to reach the goal may be higher, but the heuristic provides a lower bound, ensuring admissibility.

- **Heuristic 2 (Number of Non-Uniform Bottles)**: 
  This heuristic counts the number of bottles that do not have uniform colors. It estimates the minimum number of actions required to make all bottles uniform. Since every non-uniform bottle will require at least one move to resolve, the heuristic provides a lower bound on the cost to reach the goal. Like Heuristic 1, it does not overestimate the actual cost, ensuring that this heuristic is also admissible.

Both heuristics provide optimistic estimates of the remaining cost to reach the goal, meaning that they never overestimate the true cost. This makes them **admissible** and ensures that A* search, when using these heuristics, will find the optimal solution to the WaterSort problem.

## Performance of Search Strategies

In the Water Sort Search problem, we use different search strategies to find the solution. Each strategy is evaluated based on two key criteria: **Optimality** (whether it guarantees the best solution) and **Completeness** (whether it guarantees finding a solution if one exists). Below is a breakdown of how each search strategy performs in this context.

### 1. Breadth-First Search (BFS)
- **Optimality**: BFS is **optimal** for the Water Sort problem when all operations (pouring liquids between bottles) have equal costs. This is because BFS explores all possible states at the current depth (number of pour operations) before moving to deeper levels, ensuring that the first solution it finds uses the fewest operations.
- **Completeness**: BFS is **complete**, meaning it guarantees finding a solution if one exists. However, it can be memory-intensive because it must store all generated states at each depth level, which can be problematic for large bottle configurations.

### 2. Depth-First Search (DFS)
- **Optimality**: DFS is **not optimal** in the Water Sort problem. It might find a solution quickly, but that solution is not necessarily the one that requires the fewest pour operations. DFS explores one possible sequence of pours as deeply as possible before backtracking, often leading to suboptimal solutions.
- **Completeness**: DFS is **not complete** for the Water Sort problem. If the search dives too deeply down one sequence of states (bottle configurations) without finding a solution, it may miss other, shorter solutions or even fail to find a solution at all if it encounters an infinite depth.

### 3. Iterative Deepening Search (ID)
- **Optimality**: Iterative Deepening Search is **optimal** for the Water Sort problem, as it ensures the solution found uses the minimum number of pour operations, assuming all operations have equal cost. It combines the space efficiency of DFS with the completeness of BFS.
- **Completeness**: ID is **complete** for the Water Sort problem. It gradually explores deeper levels, eventually finding a solution if one exists, while avoiding the memory consumption issues of BFS.

### 4. Uniform Cost Search (UCS)
- **Optimality**: UCS is **optimal** in the Water Sort problem. UCS expands states based on the cost of reaching them (in this case, the number of pours). It guarantees finding the solution with the lowest cost (fewest pours) if the costs of operations vary.
- **Completeness**: UCS is **complete**, meaning it will find a solution if one exists, regardless of the bottle configuration or the number of pours required.

### 5. Greedy Search 1 (GR1)
- **Optimality**: Greedy Search 1 is **not optimal** for the Water Sort problem. It uses the heuristic of minimizing the number of different layers from the bottom layer of each bottle. While this heuristic may lead to quick solutions, it does not guarantee that the solution found is the one with the fewest pour operations.
- **Completeness**: Greedy Search 1 is **not complete**. It may get stuck in a local minimum where it cannot make further improvements, and it may fail to find a solution even if one exists.

### 6. Greedy Search 2 (GR2)
- **Optimality**: Greedy Search 2 is **not optimal** for the Water Sort problem. It uses a heuristic that prioritizes the number of bottles that do not have a uniform color. Like Greedy Search 1, it may find a solution quickly, but there is no guarantee that it is the most efficient solution in terms of pour operations.
- **Completeness**: Greedy Search 2 is **not complete** for the same reason as Greedy Search 1. It may not explore all possible bottle configurations and can fail to find a solution if it gets stuck in a local minimum.

### 7. A* Search 1 (AS1)
- **Optimality**: A* Search 1 is **optimal** in the Water Sort problem. It uses the heuristic of minimizing the number of different layers from the bottom layer of each bottle, combined with the cost to reach the current state. This ensures that A* finds the solution with the fewest number of pours, given the heuristic.
- **Completeness**: A* Search 1 is **complete** and guarantees finding a solution if one exists. The combination of heuristic guidance and cost-based expansion ensures both completeness and optimality.

### 8. A* Search 2 (AS2)
- **Optimality**: A* Search 2 is also **optimal** in the Water Sort problem. It uses the heuristic that minimizes the number of bottles that do not have a uniform color, along with the cost to reach the current state. This ensures that A* finds the most efficient solution in terms of the number of pour operations.
- **Completeness**: A* Search 2 is **complete** and guarantees finding a solution if one exists. It benefits from both the guidance of the heuristic and cost-based expansion.

---





