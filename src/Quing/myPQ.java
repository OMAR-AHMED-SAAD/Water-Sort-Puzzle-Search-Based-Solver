package Quing;

import java.util.PriorityQueue;
import code.Node;
public class myPQ extends PriorityQueue<Node> implements QuingFunc{


    public void enque(Node node){
        this.offer(node);
    }

    public void deque(){
        this.poll();
    }

}
