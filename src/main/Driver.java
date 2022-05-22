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
		char[][] maze = new char[nRows][nColumns];

		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nColumns; j++) {
				maze[i][j] = ' ';
			}
		}

		// Calculate Obstacles
		int nObstacles = (int) (nRows * nColumns * percentageObstacles);
		int obstacleRow;
		int obstacleColumn;
		Random rand = new Random();

		for (int cnt = 0; cnt < nObstacles;) {
			obstacleRow = rand.nextInt(nRows);
			obstacleColumn = rand.nextInt(nColumns);

			if (maze[obstacleRow][obstacleColumn] != '*') {
				maze[obstacleRow][obstacleColumn] = '*';
				cnt++;
			}
		}

		// Calculate Initial and Goal Sates
		boolean initial = false;
		boolean goal = false;

		while (!initial || !goal) {
			if (!initial) {
				obstacleRow = rand.nextInt(nRows);
				obstacleColumn = rand.nextInt(nColumns);
				if (maze[obstacleRow][obstacleColumn] == ' ') {
					maze[obstacleRow][obstacleColumn] = 'I';
					initialState = new Tuple(obstacleRow, obstacleColumn);
					initial = true;
				}
			}
			if (!goal) {
				obstacleRow = rand.nextInt(nRows);
				obstacleColumn = rand.nextInt(nColumns);
				if (maze[obstacleRow][obstacleColumn] == ' ') {
					maze[obstacleRow][obstacleColumn] = 'G';
					goalState = new Tuple(obstacleRow, obstacleColumn);
					goal = true;
				}
			}
		}

		printMaze(nRows, nColumns, output, maze);

		Node initialNode = new Node(0, initialState, null);

		Node result = Astar.algorithm(initialNode, goalState, maze);
		if (goalState.equals(result.getPos())) {
			System.out.println("Path found");
			output.println("Path found");
			char[][] routedMaze = reconstructPath(result, maze);
			printMaze(nRows, nColumns, output, routedMaze);
		} else {
			System.out.println("A* couldn not find a valid path");
			output.println("A* couldn not find a valid path");
		}

		output.close();
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
