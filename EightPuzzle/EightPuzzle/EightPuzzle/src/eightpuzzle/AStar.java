package eightpuzzle;

import java.util.PriorityQueue;

/**
 *
 * @author Milena
 */

public class AStar{

	public boolean search(Node currentNode, Node goalNode) {
		
		PriorityQueue<Node> list = new PriorityQueue<Node>();
		list.add(currentNode);
		while(true) {
			if(list == null) {
				System.out.println("No solution...");
				return false;
			}
			currentNode = list.peek();
			currentNode.printPuzzle();	// Just for testing
			System.out.println("Heuristic cost: " + currentNode.heuristic);	// Just for testing
			list.remove();
			if(currentNode.isSame(goalNode)) {
				System.out.println("Solution found!");
				currentNode.printPathToNode();
				return true;
			}
			currentNode.expandNode();
			list.addAll(currentNode.children);
		}
	}	
}
