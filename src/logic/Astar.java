package logic;

import java.util.ArrayList;
import java.util.List;

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

	public int getHeuristic(Tuple pos, Tuple exit) {
		int xDistance = Math.abs(exit.getX() - pos.getX() );
		int yDistance = Math.abs(exit.getY() - pos.getY() );
		
		return xDistance + yDistance;
	}

}
