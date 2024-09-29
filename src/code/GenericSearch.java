package code;

import java.util.HashSet;

import Quing.QuingFunc;

public abstract class GenericSearch {

	State initialState;
	HashSet<State> explored = new HashSet<State>();
	static int nodesExpanded = 0;

	public GenericSearch(State initialState) {
		this.initialState = initialState;
	}

	public static Node generalSearch(GenericSearch problem, QuingFunc<Node> nodes) {
		Node root = new Node(problem.initialState, null, null, 0, 0);
		nodes.enqueue(root);
		int maxDepth = 0;
		while (!nodes.isEmpty()) {
			Node node = nodes.dequeue();
			nodesExpanded++;
			maxDepth = Math.max(maxDepth, node.depth);
			if (node.isGoal())
				return node;
			problem.expand(node, nodes);
		}
		return new Node(maxDepth);
	}

	public abstract void expand(Node node, QuingFunc<Node> nodes);

}
