/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eightpuzzle;

import java.util.ArrayList;

/**
 *
 * @author Milena
 */
public class BreadthFirstSearch {
    
    public boolean breadthFirst(ArrayList<Node> Nodelist,  Node goalNode){
        ArrayList<Node> newNodes = new ArrayList<Node>();
        for (Node Node : Nodelist){         //foreach element in Nodelist
            if (Node.isSame(goalNode)){
                System.out.println("Found solution");
                Node.printPathToNode();
                return true;
            }
            Node.expandNode();
            newNodes.addAll(Node.children);
        }
        if (!newNodes.isEmpty()){
            breadthFirst(newNodes, goalNode);
            return false;
        } else {
            System.out.println("There is no solution");
            return false;     
        }
    }    
}