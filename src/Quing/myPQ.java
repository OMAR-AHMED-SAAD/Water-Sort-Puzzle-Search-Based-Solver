package Quing;

import java.util.PriorityQueue;
import code.Node;
public class myPQ extends PriorityQueue<Node> implements QuingFunc<Node>{


    public void enqueue(Node node){
        this.offer(node);
    }

    public Node dequeue(){
        return this.poll();
    }
    
	public boolean isEmpty() {
		return super.isEmpty();
	}

}
