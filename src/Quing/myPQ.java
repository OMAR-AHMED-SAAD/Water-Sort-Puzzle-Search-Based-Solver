package Quing;

import java.util.PriorityQueue;
import code.Node;
import code.NodeComparator;

public class myPQ extends PriorityQueue<Node> implements QuingFunc<Node> {

	public myPQ(NodeComparator nodeComparator) {
		super(nodeComparator);
	}

	public void enqueue(Node node) {
		this.offer(node);
	}

	public Node dequeue() {
		return this.poll();
	}

	public boolean isEmpty() {
		return super.isEmpty();
	}

}
