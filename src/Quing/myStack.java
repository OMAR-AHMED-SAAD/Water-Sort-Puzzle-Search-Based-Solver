package Quing;

import code.Node;

import java.util.Stack;

public class myStack extends Stack<Node> implements QuingFunc {

    public void enque(Node node) {
        this.push(node);
    }

    public void deque() {
        this.pop();
    }

}
