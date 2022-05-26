package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import logic.Astar;
import logic.Maze;
import logic.Node;
import logic.Tuple;

public class Driver {

	public static void main(String[] args) throws FileNotFoundException {
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
			char[][] routedMaze = astar.reconstructPath();
			m.printMaze(output, routedMaze);
			System.out.printf("Path found, Cost: %d\n", result.getG());
			output.printf("Path found, Cost: %d\n", result.getG());
		} else {
			System.out.println("A* couldn not find a valid path");
			output.println("A* couldn not find a valid path");
		}
		
		
		// Statistics;
		double[] stepsNeeded = new double[40]; // [obstacle level][iteration]
		int[] exitFound = new int[40];
		int numIterations = 100;
		int cnt = 0;
		percentageObstacles = 0.11;
		while(percentageObstacles <= 0.50) {
			stepsNeeded[cnt] = 0;
			exitFound[cnt] = 0;
			for(int i = 0; i<numIterations; i++) {
				Maze mm = new Maze(nRows, nColumns, percentageObstacles);
				Astar testAlgo = new Astar(mm.getInitialState(), mm.getGoalState(), mm.getMaze());
				stepsNeeded[cnt] += testAlgo.getAnswer().getG();
				if (mm.getGoalState().equals(testAlgo.getAnswer().getPos())) {
					exitFound[cnt] += 1;
				} else {
					exitFound[cnt] += 0;
				}
			}
			stepsNeeded[cnt] = stepsNeeded[cnt]/numIterations;
			exitFound[cnt] = exitFound[cnt];
			percentageObstacles += 0.01;
			cnt++;
		}
		
		// printStatistics
		System.out.printf("%% of obstacles\tSteps needed\tSuccess Rate\n");
		output.printf("%% of obstacles\tSteps needed\tSuccess Rate\n");
		PrintWriter stat = new PrintWriter(new File("stats.csv"));
		stat.printf("sep=,\n");
		stat.printf("%% of obstacles,Steps needed,Success Rate\n");
		percentageObstacles = 0.11;
		for(int i = 0; i< stepsNeeded.length; i++) {
			System.out.printf	("%.2f\t\t%.2f\t\t%d\n",percentageObstacles, stepsNeeded[i],exitFound[i]);
			output.printf		("%.2f\t\t%.2f\t\t%d\n",percentageObstacles, stepsNeeded[i],exitFound[i]);
			stat.printf			("%.2f,%.2f,%d\n",percentageObstacles, stepsNeeded[i],exitFound[i]);
			percentageObstacles += 0.01;
		}
		stat.close();
		output.close();
	}
	
	
}
