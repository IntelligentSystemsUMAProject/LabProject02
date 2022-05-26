package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import logic.Astar;
import logic.Maze;
import logic.Node;
import logic.Tuple;

public class Driver {

	public static void main(String[] args) {
		final int nRows = 60;
		final int nColumns = 80;
		double percentageObstacles = 0.30;

		PrintWriter output = null;
		try {
			output = new PrintWriter(new File("output.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Maze m = new Maze(nRows, nColumns, percentageObstacles);

		// Initialize Maze
		char[][] maze = m.getMaze();		
		// Calculate Initial and Goal Sates
		
		Tuple initialState = m.getInitialState();
		Tuple goalState = m.getGoalState();

		m.printMaze(output, maze);
		Astar astar = new Astar(initialState, goalState, maze);
		
		Node result = astar.getAnswer();
		if (goalState.equals(result.getPos())) {
			System.out.printf("Path found, Cost: %d\n", result.getG());
			output.printf("Path found, Cost: %d\n", result.getG());
			char[][] routedMaze = astar.reconstructPath();
			m.printMaze(output, routedMaze);
		} else {
			System.out.println("A* couldn not find a valid path");
			output.println("A* couldn not find a valid path");
		}
		output.close();
	}
}
