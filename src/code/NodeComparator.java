package code;

import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {

	Strategy strategy;

	public NodeComparator(Strategy strategy) {
		this.strategy = strategy;
	}

	@Override
	public int compare(Node node1, Node node2) {
		switch (strategy) {
		case UCS:
			return node1.cost - node2.cost;
		case GREEDY_HEURISTIC1:
		case GREEDY_HEURISTIC2:
			return node1.evaluate(strategy) - node2.evaluate(strategy);
		case A_STAR_HEURISTIC1:
		case A_STAR_HEURISTIC2:
			return (node1.cost + node1.evaluate(strategy)) - (node2.cost + node2.evaluate(strategy));
		default:
			return node1.cost - node2.cost;
		}
	}
}
