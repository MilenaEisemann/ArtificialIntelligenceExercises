/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eightpuzzle;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Milena
 */
public class Node implements Comparable<Node>{
    public int size = 3;
    public int[][] puzzle = new int[size][size];
    public int indexOfZeroX = 0;
    public int indexOfZeroY = 0;  
    public int heuristic = 0;
    public int depth = 0;
    public Node parent = this;
    ArrayList<Node> children = new ArrayList<>();
    
    static boolean useManhattan = true;	// To switch between heuristic calculations more easily
    
    public Node(int[][] puzzle){
    	for (int i = 0; i < size; i++){
    		for(int j = 0; j < size; ++j) {
    	    	this.puzzle[i][j] = puzzle[i][j];              
            } 
    	}
        if(useManhattan) {
        	this.heuristic = h2();
        } else {
        	this.heuristic = h1();
        }
    }
    
    //returns true if two Nodes represent the same state
    public boolean isSame(Node toCheck) {
    	for (int i = 0; i < this.puzzle.length; i++){
    		for(int j = 0; j < size; ++j) {
                if (this.puzzle[i][j] != toCheck.puzzle[i][j]){
                    return false;
                }
            } 
    	}
		return true;
    }
    
    //returns true if the current Node is the goal
    public boolean checkForGoal(Node goalNode){
        for (int i = 0; i < this.puzzle.length; i++){
            if (puzzle[i] != goalNode.puzzle[i]){
                return false;
            } 
        }
        return true;
    }
    
    //generate all possible following states
    public void expandNode(){
        for (int i = 0; i < size; i++){
        	for(int j = 0; j < size; ++j) {
            	if (this.puzzle[i][j] == 0){
            		indexOfZeroX = i;
            		indexOfZeroY = j;
            		break;
            	}	
        	}
        }
        moveRight();
        moveLeft();
        moveUp();
        moveDown();
    }
    
    /*
    the following fcts are used to generate all valid movements of one number in the current state of the eight puzzle
    */
    public void moveRight(){
        if (indexOfZeroX < size - 1){ //only move if zero is in col 0 or 1 in matrix
            int[][] movedPuzzle = new int[size][size];
            for(int i = 0; i < size; i++){
            	for(int j = 0; j < size; ++j) {
                    movedPuzzle[i][j] = this.puzzle[i][j];
            	}
            }
            movedPuzzle[indexOfZeroX][indexOfZeroY] = movedPuzzle[indexOfZeroX + 1][indexOfZeroY];
            movedPuzzle[indexOfZeroX + 1][indexOfZeroY] = 0;
            
            Node child = new Node(movedPuzzle);
            if(!(this.parent.isSame(child))){
            	children.add(child);
            	child.parent = this;
            	child.depth = this.depth + 1; 
                if(useManhattan) {
                	this.heuristic = h2();
                } else {
                	this.heuristic = h1();
                }
            }
        }
    }
    
    public void moveLeft(){
        if (indexOfZeroX > 0){ //only move if zero is in col 1 or 2 in matrix
            int[][] movedPuzzle = new int[size][size];
            for(int i = 0; i < size; i++){
            	for(int j = 0; j < size; ++j) {
            		movedPuzzle[i][j] = this.puzzle[i][j];
            	} 
            }
            movedPuzzle[indexOfZeroX][indexOfZeroY] = movedPuzzle[indexOfZeroX - 1][indexOfZeroY];
            movedPuzzle[indexOfZeroX - 1][indexOfZeroY] = 0;
            
            Node child = new Node(movedPuzzle);
            if(!(this.parent.isSame(child))){
            	children.add(child);
            	child.parent = this;
            	child.depth = this.depth + 1;
                if(useManhattan) {
                	this.heuristic = h2();
                } else {
                	this.heuristic = h1();
                }
            }
        }
    }
    
    //not allowed indexes 0 to 2
    public void moveUp(){
        if (indexOfZeroY > 0){ //only move if zero is in col 1 or 2 in matrix
            int[][] movedPuzzle = new int[size][size];
            for(int i = 0; i < size; i++){
            	for(int j = 0; j < size; ++j) {
                    movedPuzzle[i][j] = this.puzzle[i][j];	
            	}
            }
            movedPuzzle[indexOfZeroX][indexOfZeroY] = movedPuzzle[indexOfZeroX][indexOfZeroY - 1];
            movedPuzzle[indexOfZeroX][indexOfZeroY - 1] = 0;
            
            Node child = new Node(movedPuzzle);
            if(!(this.parent.isSame(child))){
            	children.add(child);
            	child.parent = this;
            	child.depth = this.depth + 1;
                if(useManhattan) {
                	this.heuristic = h2();
                } else {
                	this.heuristic = h1();
                }
            }
        }
    }
    
    //not allowed index 6 to 8
    public void moveDown(){
        if (indexOfZeroY < size - 1){ //only move if zero is in col 1 or 2 in matrix
            int[][] movedPuzzle = new int[size][size];
            for(int i = 0; i < size; i++){
            	for(int j = 0; j < size; ++j) {
                    movedPuzzle[i][j] = this.puzzle[i][j];	
            	}
            }
            movedPuzzle[indexOfZeroX][indexOfZeroY] = movedPuzzle[indexOfZeroX][indexOfZeroY + 1];
            movedPuzzle[indexOfZeroX][indexOfZeroY + 1] = 0;
            
            Node child = new Node(movedPuzzle);
            if(!(this.parent.isSame(child))){
            	children.add(child);
            	child.parent = this;
            	child.depth = this.depth + 1;
                if(useManhattan) {
                	this.heuristic = h2();
                } else {
                	this.heuristic = h1();
                }
            }
        }
    }
    
    //for final result
    public void printPathToNode(){
        Node nextNode = this;
        ArrayList<Node> Path = new ArrayList<>(); //path direction goal to start
        while (!(nextNode.parent.isSame(nextNode))){
            Path.add(nextNode);
            nextNode = nextNode.parent;
        }
            Path.add(nextNode);
        Collections.reverse(Path);      //reverse path so direction is from start to goal
        for (Node Node :Path){ 
           Node.printPuzzle();
        }  
    }
    
    //print out the current puzzle in Matrix
    public void printPuzzle(){
        System.out.println();
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
            		System.out.print(this.puzzle[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    
    // Amount of wrong positions
    public int h1() {

    	int val = 0;
    	int puzVal = 1;
    	for(int i = 0; i < size; ++i) {
    		for(int j = 0; j < size; ++j) {
        		if(this.puzzle[i][j] != puzVal && this.puzzle[i][j] != 0) {	++val; }
    			++puzVal;
    		}
    	}
    	return val;
    }						
    
    
    // heuristic 2: using Manhattan distance
    public int h2() {
    	int val = 0;
    	for(int i = 0; i < size; ++i) {
    		for(int j = 0; j < size; ++j) {
    			if(this.puzzle[j][i] != 0) {
    				val += Math.abs(((this.puzzle[j][i] + 2) % 3) - i)
    						+ Math.abs(((this.puzzle[j][i] -1) / 3) - j);
    			}
    		}
    	}
    	return val;		
    }		
    
    @Override
    public int compareTo(Node myNode) {
        return ((this.heuristic + this.depth)- (myNode.heuristic + myNode.depth));
    }
}
