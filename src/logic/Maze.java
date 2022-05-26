package logic;

import java.io.PrintWriter;
import java.util.Random;

public class Maze {
	private Random rand = new Random();
	private int nRows;
	private int nColumns;
	private Tuple initialState;
	private Tuple goalState;
	private double percentageObstacles;
	private char[][] maze;
	
	public Maze(int nRows, int nColumns, double percentageObstacles) {
		this.nRows = nRows;
		this.nColumns = nColumns;
		this.percentageObstacles = percentageObstacles;
		this.maze = generateMaze();
		Tuple[] states = getStates();
		this.initialState = states[0];
		this.goalState = states[1];
	}
	
	
	public int getnRows() {
		return nRows;
	}


	public int getnColumns() {
		return nColumns;
	}


	public Tuple getInitialState() {
		return initialState;
	}


	public Tuple getGoalState() {
		return goalState;
	}


	public double getPercentageObstacles() {
		return percentageObstacles;
	}


	public char[][] getMaze() {
		return maze;
	}


	private char[][] generateMaze() {
		// Calculate Obstacles
		int nObstacles = (int) (nRows * nColumns * percentageObstacles);
		int obstacleRow;
		int obstacleColumn;
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
	
	private Tuple[] getStates() {
		boolean initial = false;
		boolean goal = false;
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
	
	public void printMaze(PrintWriter output, char[][] maze) {
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
}
