package Quing;

import code.Node;

import java.util.LinkedList;

public class myLinkedList extends LinkedList<Node> implements QuingFunc {

    public void enque(Node node) {
        this.add(node);
    }

    public void deque() {
        this.remove();
    }

}
