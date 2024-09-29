package Quing;

import code.Node;

public interface QuingFunc <T extends Node>{

    void enqueue(Node node);

    Node dequeue();
    
    boolean isEmpty();
}
