package Quing;

import code.Node;

public class myLimitedStack extends myStack {

    private final int limit;

    public myLimitedStack(int limit) {
        this.limit = limit;
    }

    public void enque(Node node) {
        if (node.getDepth() > limit)
            return;
        this.push(node);
    }


}