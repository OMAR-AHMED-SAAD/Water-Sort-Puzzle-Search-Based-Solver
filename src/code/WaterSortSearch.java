package code;

import java.util.HashMap;
import java.util.HashSet;

import Quing.*;

import java.util.HashMap;

public class WaterSortSearch extends GenericSearch {

    public WaterSortSearch(State initialState) {
        super(initialState);
    }

    public static String solve(String initialState, String strategy, boolean visualize) {
        State initial = new State(initialState);
        WaterSortSearch problem = new WaterSortSearch(initial);
        Node resultNode;

        switch (strategy) {
            case "BF":
                resultNode = breadthFirstSearch(problem);
                break;
            case "DF":
                resultNode = depthFirstSearch(problem);
                break;
            case "ID":
                resultNode = iterativeDeepeningSearch(problem);
                break;
            case "UC":
                resultNode = uniformCostSearch(problem);
                break;
            case "GR1":
                resultNode = greedySearch1(problem);
                break;
            case "GR2":
                resultNode = greedySearch2(problem);
                break;
            case "AS1":
                resultNode = aStarSearch1(problem);
                break;
            case "AS2":
                resultNode = aStarSearch2(problem);
                break;
            default:
                return "Invalid strategy";
        }



        if (resultNode.isNull)
            return "NOSOLUTION";
        else {
            if (visualize) {
                new WaterSortVisualizer(resultNode);
            }
            return resultNode.getPath() + ";" + resultNode.cost + ";" + problem.nodesExpanded;
        }


    }

    private static Node breadthFirstSearch(WaterSortSearch problem) {
        return generalSearch(problem, new myLinkedList());
    }

    private static Node depthFirstSearch(WaterSortSearch problem) {
        return generalSearch(problem, new myStack());
    }

    private static Node iterativeDeepeningSearch(WaterSortSearch problem) {
        int depthLimit = 0;
        int currMaxDepth = -1;
        while (true) {
//			problem.explored = new HashSet<State>();
            problem.exploredMap = new HashMap<State, Integer>();
            Node result = generalSearch(problem, new myLimitedStack(depthLimit++));
            if (!result.isNull || result.getDepth() == currMaxDepth)
                return result;
            currMaxDepth = result.getDepth();
        }
    }

    private static Node uniformCostSearch(WaterSortSearch problem) {
        return generalSearch(problem, new myPQ(new NodeComparator(Strategy.UCS)));
    }

    private static Node greedySearch1(WaterSortSearch problem) {
        return generalSearch(problem, new myPQ(new NodeComparator(Strategy.GREEDY_HEURISTIC1)));
    }

    private static Node greedySearch2(WaterSortSearch problem) {
        return generalSearch(problem, new myPQ(new NodeComparator(Strategy.GREEDY_HEURISTIC2)));
    }

    private static Node aStarSearch1(WaterSortSearch problem) {
        return generalSearch(problem, new myPQ(new NodeComparator(Strategy.A_STAR_HEURISTIC1)));
    }

    private static Node aStarSearch2(WaterSortSearch problem) {
        return generalSearch(problem, new myPQ(new NodeComparator(Strategy.A_STAR_HEURISTIC2)));
    }

    @Override
    public void expand(Node node, QuingFunc<Node> nodes) {
        State parentState = node.state;
        for (int i = 0; i < parentState.bottles.length; i++)
            for (int j = 0; j < parentState.bottles.length; j++) {
                if (i == j)
                    continue;
                State newState = parentState.getNewState(i, j);
                // if (newState != null && !explored.contains(newState)) {
                //					explored.add(newState);
                Integer exploredCost = exploredMap.get(newState);
                if (newState != null
                        && (exploredCost == null || exploredCost >= (node.cost + newState.costFromParent))) {
                    exploredMap.put(newState, node.cost + newState.costFromParent);
                    Node newNode = new Node(newState, node, "pour_" + i + "_" + j, node.depth + 1,
                            node.cost + newState.costFromParent);
                    nodes.enqueue(newNode);
                }
            }

    }

}
