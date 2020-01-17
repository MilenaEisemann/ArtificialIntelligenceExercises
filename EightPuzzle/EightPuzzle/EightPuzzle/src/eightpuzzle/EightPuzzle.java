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
public class EightPuzzle {

    /**
     * @param args the command line arguments
     */
	
	private static long timer;
    private static int[][] puzzle = {
            
//          {1, 0, 3},	// Baby mode
//          {4, 2, 6},	// Depth: 3
//          {7, 5, 8}
          
          {2, 0, 4},	// This one is the maximum for the lecture
          {6, 7, 1},	// Depth: 23
          {8, 5, 3}
          
//          {8, 6, 7},	// Deepest Node
//          {2, 5, 4},	// Depth: 31
//          {3, 0, 1}
         
//          {0, 1, 2},	// This one was for testing Depth First
//          {4, 5, 3},	// Depth: 4
//          {7, 8, 6}
      		
      };
    private static int[][] goalState = {
          {1, 2, 3},
          {4, 5, 6},
          {7, 8, 0}
      };
	
    public static void main(String[] args) {
    	
        Node myPuzzle = new Node(puzzle);
        Node myGoalState = new Node(goalState);
        ArrayList<Node> nodeListStart = new ArrayList<>();
        nodeListStart.add(myPuzzle);   
        
        System.out.print("Startsituation: "); 
        myPuzzle.printPuzzle();
        System.out.print("Goal: ");
        myGoalState.printPuzzle(); 
        
        /*
        Just outcomment the algorithm you want to test for this test problem
        */
        
//        System.out.print("Now starting Breadth First Search!\n");
//        BreadthFirstSearch search = new BreadthFirstSearch();
//        timer = System.currentTimeMillis();
//        search.breadthFirst(nodeListStart, myGoalState);
//        System.out.println("Time: " + (System.currentTimeMillis() - timer)/1000.);
        
//        System.out.print("Now starting Depth First Search!" + "\n");
//        DepthFirstSearch dfs = new DepthFirstSearch();
//        timer = System.currentTimeMillis();
//        dfs.depthFirst(myPuzzle,  myGoalState);
//        System.out.println("Time: " + (System.currentTimeMillis() - timer)/1000.);
        
//        System.out.print("Now starting Iterative Deepening!\n");
//        IterativeDeepening id = new IterativeDeepening();
//        timer = System.currentTimeMillis();
//        id.iterate(myPuzzle, myGoalState);
//        System.out.println("Time: " + (System.currentTimeMillis() - timer)/1000.);
        
        System.out.println(myPuzzle.heuristic); 
        System.out.print("Now starting A*!\n");
        AStar star = new AStar();
        timer = System.currentTimeMillis();
        star.search(myPuzzle, myGoalState);
        System.out.println("Time: " + (System.currentTimeMillis() - timer)/1000.);
    
    }
    
}
