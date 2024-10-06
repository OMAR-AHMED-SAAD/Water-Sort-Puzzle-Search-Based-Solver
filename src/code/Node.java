package code;

public class Node {
	State state;
	Node parent;
	String operator;
	int depth;
	int cost;
	boolean isNull = true;

	public Node(int depth) {
		this.depth = depth;
	}

	public Node(State state, Node parent, String operator, int depth, int cost) {
		this.state = state;
		this.parent = parent;
		this.operator = operator;
		this.depth = depth;
		this.cost = cost;
		this.isNull = false;
	}

	public int evaluate(Strategy strategy) {
		switch (strategy) {
		case GREEDY_HEURISTIC1:
		case A_STAR_HEURISTIC1:
			return state.getHeuristic1();
		case GREEDY_HEURISTIC2:
		case A_STAR_HEURISTIC2:
			return state.getHeuristic2();
		default:
			return state.getHeuristic1();
		}
	}

	public int getDepth() {
		return depth;
	}

	public boolean isGoal() {
		return state.isGoal();
	}

	public String getPath() {
		StringBuilder path = new StringBuilder();
		Node current = this;
		while (current.depth != 0) {
			path.insert(0, current.operator + ",");
			current = current.parent;
		}
		path.deleteCharAt(path.length() - 1);
		return path.toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(operator).append("--").append(state.costFromParent).append("-->");
		sb.append("State: ").append(state);
		sb.append("Depth: ").append(depth);
		sb.append(" Cost: ").append(cost).append("\n");
		return sb.toString();
	}

}
