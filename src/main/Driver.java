package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

import logic.Astar;
import logic.Node;
import logic.Tuple;

public class Driver {

	public static void main(String[] args) {
		int nRows = 60;
		int nColumns = 80;
		Tuple initialState = null;
		Tuple goalState = null;
		double percentageObstacles = 0.30;

		PrintWriter output = null;
		try {
			output = new PrintWriter(new File("output.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Initialize Maze
		char[][] maze = generateMaze(nRows, nColumns, percentageObstacles);

		// Calculate Initial and Goal Sates
		Tuple[] states = getStates(maze, nRows, nColumns);
		initialState = states[0];
		goalState = states[1];

		printMaze(nRows, nColumns, output, maze);

		Node result = Astar.algorithm(initialState, goalState, maze);
		if (goalState.equals(result.getPos())) {
			System.out.printf("Path found, Cost: %d\n", result.getG());
			output.printf("Path found, Cost: %d\n", result.getG());
			char[][] routedMaze = reconstructPath(result, maze);
			printMaze(nRows, nColumns, output, routedMaze);
		} else {
			System.out.println("A* couldn not find a valid path");
			output.println("A* couldn not find a valid path");
		}

		output.close();
	}

	private static char[][] generateMaze(int nRows, int nColumns, double percentageObstacles) {
		// Calculate Obstacles
		int nObstacles = (int) (nRows * nColumns * percentageObstacles);
		int obstacleRow;
		int obstacleColumn;
		Random rand = new Random();
		char[][] maze = new char[nRows][nColumns];
		
		// Generating empty maze;
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nColumns; j++) {
				maze[i][j] = ' ';
			}
		}
		
		for (int cnt = 0; cnt < nObstacles;) {
			obstacleRow = rand.nextInt(nRows);
			obstacleColumn = rand.nextInt(nColumns);

			if (maze[obstacleRow][obstacleColumn] != '*') {
				maze[obstacleRow][obstacleColumn] = '*';
				cnt++;
			}
		}
		return maze;
	}

	private static Tuple[] getStates(char[][] maze, int nRows, int nColumns) {
		boolean initial = false;
		boolean goal = false;
		Random rand = new Random();
		int row;
		int column;
		Tuple[] result = new Tuple[2];

		while (!initial || !goal) {
			if (!initial) {
				row = rand.nextInt(nRows);
				column = rand.nextInt(nColumns);
				if (maze[row][column] == ' ') {
					maze[row][column] = 'I';
					result[0] = new Tuple(row, column);
					initial = true;
				}
			}
			if (!goal) {
				row = rand.nextInt(nRows);
				column = rand.nextInt(nColumns);
				if (maze[row][column] == ' ') {
					maze[row][column] = 'G';
					result[1] = new Tuple(row, column);
					goal = true;
				}
			}
		}
		return result;
	}

	private static void printMaze(int nRows, int nColumns, PrintWriter output, char[][] maze) {
		System.out.printf("////////////// MAZE STARTING //////////////\n  ");
		output.printf("////////////// MAZE STARTING //////////////\n  ");
		// printing column index
		for (int j = 0; j < nColumns; j++) {
			System.out.printf("%d ", j % 10);
			output.printf("%d ", j % 10);
		}
		System.out.println();
		output.println();

		for (int i = 0; i < nRows; i++) {
			// print row number;
			System.out.printf("%d ", i % 10);
			output.printf("%d ", i % 10);
			for (int j = 0; j < nColumns; j++) {
				System.out.printf("%c ", maze[i][j]);
				output.printf("%c ", maze[i][j]);
			}
			// print row number;
			System.out.printf("%d ", i % 10);
			output.printf("%d ", i % 10);
			System.out.println();
			output.println();
		}
		// printing column index
		System.out.printf("  ");
		output.printf("  ");
		for (int j = 0; j < nColumns; j++) {
			System.out.printf("%d ", j % 10);
			output.printf("%d ", j % 10);
		}
		System.out.printf("\n////////////// MAZE FINISHING //////////////\n");
		output.printf("\n////////////// MAZE FINISHING //////////////\n");
	}

	private static char[][] reconstructPath(Node result, char[][] maze) {
		result = result.getParent();
		while (result.getParent() != null) {
			int x = result.getPos().getX();
			int y = result.getPos().getY();
			maze[x][y] = '#';
			result = result.getParent();
		}
		return maze;
	}
}
