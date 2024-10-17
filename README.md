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

In the **Water Sort Search** problem, each search strategy has been evaluated based on **Optimality**, **Completeness**, **Memory Usage**, **CPU Utilization**, and **Number of Expanded Nodes**. It is important to note that repeated states are tracked, which prevents infinite loops, making some strategies that would typically not be complete, effectively complete for this problem.

1. Breadth-First Search (BFS)

	•	Optimality: BFS is optimal when all operations (pouring liquids) have equal costs, ensuring the fewest number of pour operations so it's not optimal in our case.
	•	Completeness: BFS is complete and will always find a solution if it exists.
	•	Memory Usage: 7,807 KB
	•	CPU Utilization: 28.79% (+28.79%)
	•	Nodes Expanded: 543 nodes

2. Depth-First Search (DFS)

	•	Optimality: DFS is not optimal, as it explores one path deeply before backtracking, often leading to suboptimal solutions.
	•	Completeness: Although DFS is not typically complete, tracking repeated states ensures it avoids infinite loops, making it effectively complete for the Water Sort problem.
	•	Memory Usage: 2,228 KB
	•	CPU Utilization: 29.49%
	•	Nodes Expanded: 76 nodes

3. Iterative Deepening Search (ID)

	•	Optimality: ID is optimal, combining the memory efficiency of DFS and the solution optimality of BFS.
	•	Completeness: ID is complete and will find a solution if one exists.
	•	Memory Usage: 434 KB
	•	CPU Utilization: 29.54%
	•	Nodes Expanded: 629 nodes

4. Uniform Cost Search (UCS)

	•	Optimality: UCS is optimal, expanding the lowest-cost nodes first and guaranteeing the fewest number of pours.
	•	Completeness: UCS is complete, ensuring a solution if it exists.
	•	Memory Usage: 5,422 KB
	•	CPU Utilization: 18.18%
	•	Nodes Expanded: 540 nodes

5. Greedy Search 1 (GR1)

	•	Optimality: Greedy Search 1 is not optimal, as it uses a heuristic that doesn’t guarantee the fewest pours.
	•	Completeness: Normally, Greedy Search 1 would not be complete, but tracking repeated states prevents loops, making it complete in this case.
	•	Memory Usage: 81 KB
	•	CPU Utilization: 19.68%
	•	Nodes Expanded: 7 nodes

6. Greedy Search 2 (GR2)

	•	Optimality: Greedy Search 2 is not optimal, similar to GR1, focusing on a heuristic that may not lead to the most efficient solution.
	•	Completeness: Greedy Search 2 is complete due to repeated states being tracked.
	•	Memory Usage: 161 KB
	•	CPU Utilization: 20.54%
	•	Nodes Expanded: 8 nodes

7. A* Search 1 (AS1)

	•	Optimality: A* Search 1 is optimal, combining the heuristic with cost-based expansion to guarantee the fewest pours.
	•	Completeness: A* Search 1 is complete and will always find a solution if one exists.
	•	Memory Usage: 563 KB
	•	CPU Utilization: 20.38%
	•	Nodes Expanded: 33 nodes

8. A* Search 2 (AS2)

	•	Optimality: A* Search 2 is optimal, using a different heuristic to ensure the fewest pours.
	•	Completeness: A* Search 2 is complete and ensures a solution if one exists.
	•	Memory Usage: 3,797 KB
	•	CPU Utilization: 18.22%
	•	Nodes Expanded: 182 nodes

## Commentary on Results

From the results, we observe some important trends across the search strategies:

- **Breadth-First Search (BFS)** and **Uniform Cost Search (UCS)** both consume the highest amounts of memory and expand a large number of nodes, making them resource-intensive. Despite their optimality(UCS) and completeness, they may not be the best choice for larger or more complex problems where memory is limited.

- **Greedy Searches (GR1 and GR2)** are fast and resource-efficient, consuming the least memory and expanding the fewest nodes. However, their lack of optimality and completeness limits their reliability. In this specific problem, tracking repeated states ensures that they avoid getting stuck in loops, so they can be considered **complete**.
  
- **Depth-First Search (DFS)** is obviously not optimal in this example, combined with high CPU utilization, which makes it less desirable for ensuring optimal solutions. Tracking repeated states prevents DFS from revisiting the same states, thus avoiding infinite loops and making it **complete** for this problem.

- **Iterative Deepening (ID)** It expands the most nodes due to iterating and doing the dfs for several levels.

- **A_Star Searches (AS1 and AS2)** provide a balance between optimality, completeness, and resource efficiency. While they use more memory than the greedy approaches, they guarantee finding the most optimal solution while expanding fewer nodes than UCS. **A* Search 1** tends to use less memory and CPU than **A* Search 2**, making it slightly more efficient overall.



