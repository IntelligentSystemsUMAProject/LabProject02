package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

import logic.Tuple;

public class Driver {

	public static void main(String[] args) {
		int nRows = 60;
		int nColumns = 80;
		Tuple initialState;
		Tuple goalState;
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
		
		while (!initial && !goal) {
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
		
		System.out.println("////////////// MAZE STARTING //////////////");
		output.printf("////////////// MAZE STARTING //////////////\n");
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nColumns; j++) {
				System.out.printf("%c ", maze[i][j]);
				output.printf("%c ", maze[i][j]);
			}
			System.out.println();
			output.println();
		}
		System.out.println("////////////// MAZE FINISHING //////////////");
		output.printf("////////////// MAZE FINISHING //////////////\n");
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		output.close();
	}
}
