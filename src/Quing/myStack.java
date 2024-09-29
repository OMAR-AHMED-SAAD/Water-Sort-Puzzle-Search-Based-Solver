package Quing;

import code.Node;

import java.util.Stack;

public class myStack extends Stack<Node> implements QuingFunc <Node>{

    public void enqueue(Node node) {
        this.push(node);
    }

    public Node dequeue() {
        return this.pop();
    }
    
	public boolean isEmpty() {
		return super.isEmpty();
	}

}
