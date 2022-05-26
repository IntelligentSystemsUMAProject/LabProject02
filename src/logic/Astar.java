package logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Astar {
	
	private Node answer;
	private Tuple initialState;
	private Tuple finalState;
	private char[][] maze;
	
	public Astar(Tuple initialState, Tuple finalState, char[][] maze) {
		this.initialState = initialState;
		this.finalState = finalState;
		this.maze = maze;
		this.answer = algorithm(initialState, finalState, maze);
	}
	
	

	public Node getAnswer() {
		return answer;
	}

	public Tuple getInitialState() {
		return initialState;
	}

	public Tuple getFinalState() {
		return finalState;
	}

	public char[][] getMaze() {
		return maze;
	}
	
	public char[][] reconstructPath() {
		char[][] routedMaze = this.maze;
		answer = answer.getParent();
		while (answer.getParent() != null) {
			int x = answer.getPos().getX();
			int y = answer.getPos().getY();
			routedMaze[x][y] = '#';
			answer = answer.getParent();
		}
		return routedMaze;
	}

	private List<Tuple> getSuccessors(char[][] maze, Tuple pos) {
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

	private List<Node> getNeighbors(List<Tuple> successorsList, Node current) {
		List<Node> neighborsList = new ArrayList<>();
		for (Tuple succ : successorsList) {
			neighborsList.add(new Node(current.getG() + 1, succ, current));
		}
		return neighborsList;
	}

	private Node getMinimumF(Set<Node> openSet, Tuple goal) {
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

	private Node algorithm(Tuple initialState, Tuple goal, char[][] maze) {
		Set<Node> closedSet = new HashSet<Node>();
		Set<Node> openSet = new HashSet<Node>();
		Node current = new Node(0, initialState, null);
		openSet.add(current);
		boolean isFinish = false;
		while (!openSet.isEmpty() && !isFinish) {
			current = getMinimumF(openSet, goal);
			if (current.getPos().equals(goal)) {
				isFinish = true;
			} else {
				openSet.remove(current);
				closedSet.add(current);
				List<Node> neighborsList = getNeighbors(getSuccessors(maze, current.getPos()), current);
				for (Node neighbor : neighborsList) {
					if (closedSet.contains(neighbor)) {
					} else {
						int tentative_g = current.getG() + 1;
						if (!openSet.contains(neighbor) || tentative_g < neighbor.getG()) {
							neighbor.setG(tentative_g);
							neighbor.setParent(current);
							if (!openSet.contains(neighbor)) {
								openSet.add(neighbor);
							}
						}
					}
				}
			}
		}
		return current;
	}

}
