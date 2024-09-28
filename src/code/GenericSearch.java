package code;

import Quing.QuingFunc;

import java.util.*;

public abstract class  GenericSearch {

    State initialState;

    public static Node generalSearch(GenericSearch problem, QuingFunc<Node> nodes){
        Node root = new Node(problem.initialState,null,null,0,0 );
        nodes.add(root);
        while(!nodes.isEmpty())
        {
            Node node = nodes.deque();
            if(node.isGoal())
                return node;
            nodes =problem.expand(nodes,node);
        }
        return null;
    }

    public abstract QuingFunc<Node> expand(QuingFunc<Node> nodes, Node node );





    public static void main(String[] args) {

        Deque<Integer> stack = new Stack<>();
        Deque<Integer> linked = new LinkedList<>();
        Deque<Integer> queue = new PriorityQueue<>();
        queue.add(1);
        queue.add(2);
        stack.add(1);
        stack.add(2);
        linked.add(1);
        linked.add(2);
        linked.add(3);

        System.out.println(queue.remove());
        System.out.println(stack.);
        System.out.println(linked.remove());

    }
}
