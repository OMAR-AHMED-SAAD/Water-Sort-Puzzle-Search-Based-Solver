package code;

public class Node {
    State state;
    Node parent;
    String operator;
     private int depth;
    int cost;

    public int getDepth() {
        return depth;
    }

    public Node(State state, Node parent, String operator, int depth, int cost)
    {
        this.state=state;
        this.parent=parent;
        this.operator = operator;
        this.depth = depth;
        this.cost = cost;
    }

    public boolean isGoal()
    {
        return state.isGoal();
    }





}


