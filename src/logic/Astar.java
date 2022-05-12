package logic;

import java.util.ArrayList;
import java.util.List;

public class Astar {

	public List<Tuple> getSuccessors(char[][] maze, int row, int column) {
		List<Tuple> succs = new ArrayList<>();

		if (row != 0) {
			if (maze[row - 1][column] != '*') {
				succs.add(new Tuple(row - 1, column));
			}
			if (row != 59) {
				if (maze[row + 1][column] != '*') {
					succs.add(new Tuple(row + 1, column));
				}
			}
			if (column != 0) {
				if (maze[row][column - 1] != '*') {
					succs.add(new Tuple(row, column - 1));
				}
			}
			if (column != 79) {
				if (maze[row][column + 1] != '*') {
					succs.add(new Tuple(row, column + 1));
				}
			}
		}
		return succs;
	}
	

}
