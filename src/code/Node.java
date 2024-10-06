package code;

public class Node implements Comparable<Node> {
    State state;
    Node parent;
    String operator;
    int depth;
    int cost;
    int evalutionCost;
    boolean isNull = true;
    Strategy strategy = Strategy.NORMAL;

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
    public int compareTo(Node o) {
        if (strategy == Strategy.NORMAL)
            return this.cost - o.cost;
        else
//			TODO: Implement the Heuristic 1 and Heuristic 2 strategies
            return 0;
    }

    @Override
    public String toString() {
        String sb = operator + "--" + state.costFromParent + "-->" +
                "State: " + state +
                "Depth: " + depth +
                " Cost: " + cost + "\n";
        return sb;

    }

}
