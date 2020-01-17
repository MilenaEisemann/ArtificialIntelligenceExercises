package eightpuzzle;

import java.util.ArrayList;

/**
 *
 * @author Milena
 */
public class DepthFirstSearch {
	
	private ArrayList<Node> newNodes = new ArrayList<Node>();
	
	public boolean depthFirst(Node Node, Node goalNode) {
		if (Node.isSame(goalNode)){
            System.out.println("Found solution");
            Node.printPathToNode();
            return true;
        }
		Node.expandNode();
		newNodes.addAll(Node.children);
		
		while (!newNodes.isEmpty()) {
			if (depthFirst(newNodes.remove(0), goalNode)) { return true; }
		}
		System.out.println("No solution found");
		return false;
	}
}

