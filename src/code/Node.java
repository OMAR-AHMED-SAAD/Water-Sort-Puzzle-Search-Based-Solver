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

    //	 get the number of layers remaining to move for each bottle to be of the same color
    public int getHeuristic1() {
        int h = 0;

        for (Bottle bottle : state.bottles)
            h += bottle.layersRemToSameColor();

        return h;
    }

    public int getHeuristic2() {
        return 0;
    }

    public int evaluate(Strategy strategy) {
        switch (strategy) {
            case GREEDY_HEURISTIC1:
            case A_STAR_HEURISTIC1:
                return getHeuristic1();
            case GREEDY_HEURISTIC2:
            case A_STAR_HEURISTIC2:
                return getHeuristic2();
            default:
                return getHeuristic1();
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
        String sb = operator + "--" + state.costFromParent + "-->" +
                "State: " + state +
                "Depth: " + depth +
                " Cost: " + cost + "\n";
        return sb;

    }

}