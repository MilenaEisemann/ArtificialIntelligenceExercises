package eightpuzzle;

/**
 *
 * @author Milena
 */
public class IterativeDeepening {
	
	private int maxDepth = 0;
	
	public void iterate(Node currentNode, Node goalNode) {	
		while(!depthFirstB(currentNode, goalNode, 0, maxDepth))  {
			System.out.println("Depth: " + maxDepth);
			++maxDepth;
		}
		return;
	}
	
	public boolean depthFirstB(Node currentNode, Node goalNode, int currentDepth, int max) {
		if(currentNode.isSame(goalNode)) {
			System.out.println("Solution found at depth: " + currentDepth);
			currentNode.printPathToNode();
			return true;
		}	
		currentNode.expandNode();
		while(!(currentNode.children.isEmpty()) && currentDepth < max) {			
				if(depthFirstB(currentNode.children.remove(0), goalNode, currentDepth + 1, max)) { return true; }
		}
		return false;
	}
}