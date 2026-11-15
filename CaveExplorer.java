/*
Mitchell Kezer
CS352 Unit 4 Assignment 2
11/15/2026
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class CaveExplorer {
	// Step 1: Zero parameter constructor to create the specified cave
	
	private char [][] cave;
	private String path = "";

	/* New helper class to help solve() store a position and route.
	*/

	private static class Position {
		int row;
		int col;
		String route;

		public Position(int row, int col, String route) {
			this.row = row;
			this.col = col;
			this.route = route;
		}
	}

	public CaveExplorer() {
		cave = new char[][] {
			{'R', 'R', 'R', 'R', 'R', 'R'},
			{'R', '.', '.', 'S', 'R', 'R'},
			{'R', '.', 'R', 'R', 'R', 'R'},
			{'R', '.', 'M', 'R', 'R', 'R'},
			{'R', 'R', 'R', 'R', 'R', 'R'}
		};
	}

	// Step 2: toString method
	
	/*
	The code below implements the toString method, which returns a string representation of the cave. 
	It uses a StringBuilder to efficiently build the string by iterating through each row and cell of the cave 
	array, appending each cell to the result, and adding a newline character after each row. Finally, it returns 
	the constructed string.
	*/

	@Override 
	
	public String toString() {
		StringBuilder result = new StringBuilder();

		for (char[] row : cave) {
			for (char cell : row) {
				result.append(cell);
			}
			result.append('\n');
		}

		return result.toString();
	}

	// Step 3: solve method
	
	public boolean solve() {
		path = "";

		int startRow = -1;
		int startCol = -1;

		/*
		The code below finds the starting position 'S' in the cave array by iterating through each cell.
		If 'S' is found, it records the row and column indices. If 'S' is not found, it returns false, indicating 
		that there is no starting position to begin the search for a path to the mirror pool.
		*/

		for (int row = 0; row < cave.length && startRow == -1; row++) {
			for (int col = 0; col < cave[row].length; col++) {
				if (cave[row][col] == 'S') {
					startRow = row;
					startCol = col;
					break;
				}
			}
		}

		if (startRow == -1 || startCol == -1) {
			return false; 
		}

		/*
		The code below initializes a 2D boolean array to keep track of visited positions in the cave. It also 
		initializes a stack to facilitate the depth-first search (DFS) algorithm. The row and column direction 
		arrays define the possible movements (north, south, west, east), and the corresponding direction array 
		stores the string representation of each movement. The starting position is marked as visited, and the 
		initial position is pushed onto the stack with an empty route. The while loop continues until the stack is 
		empty, popping the current position and checking if it is the mirror pool 'M'. If found, it stores the route 
		and returns true. Otherwise, it explores all possible moves, marking new positions as visited and pushing them 
		onto the stack with the updated route. If no valid moves are left and the exit is not found, it returns false.
		*/

		boolean [][] visited = new boolean[cave.length][cave[0].length];
		Stack<Position> stack = new Stack<>();

		int [] rowDirection = {-1, 1, 0, 0}; // North, South, West, East
		int [] colDirection = {0, 0, -1, 1};
		String [] direction = {"n", "s", "w", "e"};

		visited [startRow][startCol] = true;
		stack.push(new Position(startRow, startCol, "")); 

		while (!stack.empty()) {
			Position current = stack.pop();

			if (cave[current.row][current.col] == 'M') {
				path = current.route;
				return true; // Found the exit
			}

			for (int i = direction.length - 1; i >= 0; i--) {
				int newRow = current.row + rowDirection[i];
				int newCol = current.col + colDirection[i];

				boolean checkMove = newRow >= 0 && newRow < cave.length 
					&& newCol >= 0 && newCol < cave[newRow].length;
				
				if (checkMove && cave[newRow][newCol] != 'R' && !visited[newRow][newCol]) {
					visited[newRow][newCol] = true;
					stack.push(new Position(newRow, newCol, current.route + direction[i]));
				}
			}
		}

		path = "";
		return false; // No valid moves left and exit not found
	} 


	public String getPath() {
		return path;
	}

	// Step 5: One parameter constructor to read from a file
	
	/*
	The code below implements a one-parameter constructor that reads the cave layout from a specified file.
	It uses a Scanner to read the number of rows and columns, then constructs the cave array and populates it 
	with the data from the file.
	*/

	public CaveExplorer (String fname) throws Exception {
	      Scanner in = new Scanner(new File(fname));

	      int rows = in.nextInt();
	      int cols = in.nextInt();  
    	  String s = in.nextLine(); // Skip newline character
    	   	  
    	  // Construct cave and populate with rest of data in the file

		  cave = new char[rows][cols];

		  for (int row = 0; row < rows; row++) {
				s = in.nextLine();

			  	for (int col = 0; col < cols; col++) {
				  	cave[row][col] = s.charAt(col);
			  }
		  }
		  
		  in.close();
	}
	

	
	public static void main(String[] args) {
		System.out.println("Starting CaveExplorer");
		
		// Create a CaveExplorer object and print the starting layout
		
		// Call solve
		
		// Print the final layout, whether there is a path, and if so, what it is.
		
		// Step 5/6: Repeat for a different CaveExplorer object read from a file
		// Uncomment code below to start testing your 1-parameter constructor

		/*
		The code below creates a CaveExplorer object using the one-parameter constructor that reads the cave 
		layout from a file. It prints the cave layout, calls the solve method, and prints whether a path to the 
		mirror pool was found.
		*/

		String fileName = "testdat.txt"; 
		try { 
			CaveExplorer ce1 = new CaveExplorer(fileName); 
			System.out.println("Cave layout from file:");
			System.out.print(ce1.toString());
			System.out.println();

			boolean solvedPath = ce1.solve();
			System.out.println("Solved path: " + solvedPath);

			if (solvedPath) {
				System.out.println("Path to mirror pool: " + ce1.getPath());
			}
		} 
		catch (FileNotFoundException e ) {
			System.out.println("Can't find file " + fileName); 
		}
		catch (Exception e) {
			System.out.println("Other error: " + e.getMessage());
		}

		/*
		Second CaveExplorer object read from a different file.
		*/

		String fileName2 = "testdat2.txt"; 
		try { 
			CaveExplorer ce2 = new CaveExplorer(fileName2); 
			System.out.println();
			System.out.println("Second cave layout from file:");
			System.out.print(ce2.toString());
			System.out.println();

			boolean solvedPath = ce2.solve();
			System.out.println("Solved path: " + solvedPath);

			if (solvedPath) {
				System.out.println("Path to mirror pool: " + ce2.getPath());
			}
		} 
		catch (FileNotFoundException e ) {
			System.out.println("Can't find file " + fileName2); 
		}
		catch (Exception e) {
			System.out.println("Other error: " + e.getMessage());
		}

		System.out.println();
		System.out.println("Finished CaveExplorer");
	}
}