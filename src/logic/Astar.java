package logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Astar {

	public static List<Tuple> getSuccessors(char[][] maze, Tuple pos) {
		List<Tuple> succs = new ArrayList<>();
		int x = pos.getX();
		int y = pos.getY();

		if (x != 0) {
			if (maze[x - 1][y] != '*') {
				succs.add(new Tuple(x - 1, y));
			}
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

		return succs;
	}

	public static List<Node> getNeighbors(List<Tuple> successorsList, Node current) {
		List<Node> neighborsList = new ArrayList<>();
		for (Tuple succ : successorsList) {
			neighborsList.add(new Node(current.getG() + 1, succ, current));
		}

		return neighborsList;
	}

	public static Node getMinimumF(Set<Node> openSet, Tuple goal) {
		Node result = null;
		int minimumCost = Integer.MAX_VALUE;
		for (Node node : openSet) {
			int estimatedCost = node.getf(goal);
			if (minimumCost > estimatedCost) {
				minimumCost = estimatedCost;
				result = node;
			}
		}
		return result;
	}

	public static Node algorithm(Node current, Tuple goal, char[][] maze) {
		Set<Node> closedset = new HashSet<Node>();
		Set<Node> openset = new HashSet<Node>();
		openset.add(current);
		boolean isFinish = false;
		while (!openset.isEmpty() && !isFinish) {
			current = getMinimumF(openset, goal);
			if (current.getPos().equals(goal)) {
				isFinish = true;
			} else {
				openset.remove(current);
				closedset.add(current);
				List<Node> neighborsList = getNeighbors(getSuccessors(maze, current.getPos()), current);
				for (Node neig : neighborsList) {
					if (closedset.contains(neig)) {
					} else {
						int tentative_g = current.getG() + 1;
						if (!openset.contains(neig) || tentative_g < neig.getG()) {
							neig.setG(tentative_g);
							neig.setParent(current);
							if (!openset.contains(neig)) {
								openset.add(neig);
							}
						}
					}
				}
			}
		}
		return current;
	}

}
