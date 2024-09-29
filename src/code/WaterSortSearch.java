package code;

import java.util.HashSet;

import Quing.*;

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
			resultNode = problem.breadthFirstSearch(problem);
			break;
		case "DF":
			resultNode = problem.depthFirstSearch(problem);
			break;
		case "ID":
			resultNode = problem.iterativeDeepeningSearch(problem);
			break;
		case "UC":
			resultNode = problem.uniformCostSearch(problem);
			break;
		default:
			return "Invalid strategy";
		}

		if (resultNode.isNull)
			return "NOSOLUTION";
		else
			return resultNode.getPath() + ";" + resultNode.cost + ";" + GenericSearch.nodesExpanded;

	}

	@Override
	public void expand(Node node, QuingFunc<Node> nodes) {
		State parentState = node.state;
		for (int i = 0; i < parentState.bottles.length; i++)
			for (int j = 0; j < parentState.bottles.length; j++) {
				if (i == j)
					continue;
				State newState = parentState.getNewState(i, j);
				if (newState != null && !explored.contains(newState)) {
					explored.add(newState);
					Node newNode = new Node(newState, node, "pour_" + i + "_" + j, node.depth + 1,
							node.cost + newState.costFromParent);
					nodes.enqueue(newNode);
				}
			}

	}

	private Node breadthFirstSearch(WaterSortSearch problem) {
		return GenericSearch.generalSearch(problem, new myLinkedList());
	}

	private Node depthFirstSearch(WaterSortSearch problem) {
		return GenericSearch.generalSearch(problem, new myStack());
	}

	private Node iterativeDeepeningSearch(WaterSortSearch problem) {
		int depthLimit = 0;
		int currMaxDepth = -1;
		while (true) {
			explored = new HashSet<State>();
			Node result = GenericSearch.generalSearch(problem, new myLimitedStack(depthLimit++));
			if (!result.isNull || result.getDepth() == currMaxDepth)
				return result;
			currMaxDepth = result.getDepth();
		}
	}

	private Node uniformCostSearch(WaterSortSearch problem) {
		return GenericSearch.generalSearch(problem, new myPQ());
	}

}
