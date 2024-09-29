package Quing;

import code.Node;

import java.util.LinkedList;

public class myLinkedList extends LinkedList<Node> implements QuingFunc<Node> {

    public void enqueue(Node node) {
        this.add(node);
    }

    public Node dequeue() {
        return this.remove();
    }

	public boolean isEmpty() {
		return super.isEmpty();
	}
}
