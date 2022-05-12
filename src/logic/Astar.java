package logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Astar {

	public List<Tuple> getSuccessors(char[][] maze, Tuple pos) {
		List<Tuple> succs = new ArrayList<>();
		int x = pos.getX();
		int y = pos.getY();

		if (x != 0) {
			if (maze[x - 1][y] != '*') {
				succs.add(new Tuple(x - 1, y));
			}
			if (x != 59) {
				if (maze[x + 1][y] != '*') {
					succs.add(new Tuple(x + 1, y));
				}
			}
			if (y != 0) {
				if (maze[x][y - 1] != '*') {
					succs.add(new Tuple(x, y - 1));
				}
			}
			if (y != 79) {
				if (maze[x][y + 1] != '*') {
					succs.add(new Tuple(x, y + 1));
				}
			}
		}
		return succs;
	}

	public static int getHeuristic(Tuple pos, Tuple exit) {
		int xDistance = Math.abs(exit.getX() - pos.getX() );
		int yDistance = Math.abs(exit.getY() - pos.getY() );
		
		return xDistance + yDistance;
	}
	public Set<Node> algorithm(Node current, Node goal){
		Set<Node> closedset = new HashSet<Node>();
		Set<Node> openset = new HashSet<Node>();
		openset.add(current);
		openset.add(goal);
		boolean isFinish = false;
		while(!openset.isEmpty() && !isFinish) {
			current = openset.
			if(current == goal) {
				isFinish = true;
			}else {
				
				
				:(
				
				
			}
			
			
			
			
			
			
			
			
			
		}
		
		return closedset;
	}
	
	public int getf(Node node, Tuple goal) {
		
		return node.getG() + getHeuristic(node.getPos(), goal);
	}
	
}
