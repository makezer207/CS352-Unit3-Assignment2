/*
Mitchell Kezer
CS352 Unit 3 Assignment 2
11/8/2026
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CaveExplorer {
	// Step 1: Zero parameter constructor to create the specified cave
	
	private char [][] cave;
	private String path = "";

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
		The code below finds the starting position 'S' in the cave and initializes the startRow and 
		startCol variables. It also checks if the starting position was found; if not, it returns false, 
		indicating that there is no valid path to the mirror pool.
		*/

		for (int row = 0; row < cave.length; row++) {
			for (int col = 0; col < cave[row].length; col++) {
				if (cave[row][col] == 'S') {
					startRow = row;
					startCol = col;
				}
			}
		}

		if (startRow == -1 || startCol == -1) {
			return false; 
		}

		/*
		The code below initializes a 2D boolean array to keep track of visited positions in the cave,
		and a StringBuilder to build the path taken. It also defines arrays for row and column changes
		corresponding to moving north, south, west, and east, as well as a String array representing 
		the directions.
		*/

		boolean [][] visited = new boolean[cave.length][cave[0].length];
		StringBuilder route = new StringBuilder();

		int [] rowDirection = {-1, 1, 0, 0}; // North, South, West, East
		int [] colDirection = {0, 0, -1, 1};
		String [] direction = {"n", "s", "w", "e"};

		int row = startRow;
		int col = startCol;

		/*
		The code below implements a loop that continues until the mirror pool 'M' is found or there are no valid 
		moves left. It checks each direction for valid moves, updates the current position, and appends the 
		direction to the route. If the mirror pool is found, it sets the path and returns true. If no valid moves 
		are left, it returns false.
		*/
	
		while (true) {
			if (cave[row][col] == 'M') {
				path = route.toString();
				return true; // Found the exit
			}

			visited[row][col] = true;

			int nextRow = -1;
			int nextCol = -1;
			String nextDirection = "";
			
			/*
			The code below checks each direction (north, south, west, east) to see if moving in that direction 
			is valid. A move is valid if it stays within the bounds of the cave, does not hit a rock 'R', and 
			has not been visited yet. If a valid move is found, it updates the nextRow, nextCol, and nextDirection 
			variables accordingly. The visited array is what prevents the program from going back to a previously 
			visited position, ensuring that the algorithm does not get stuck in an infinite loop.
			*/

			for (int i = 0 ; i < direction.length; i++) {
				int newRow = row + rowDirection[i];
				int newCol = col + colDirection[i];

				if (newRow >= 0 && newRow < cave.length && newCol >= 0 && newCol < cave[newRow].length 
					&& cave[newRow][newCol] != 'R' && !visited[newRow][newCol]) {
					nextRow = newRow;
					nextCol = newCol;
					nextDirection = direction[i];
					break;
			}
		}

		if (nextRow == -1 || nextCol == -1) {
			return false; 
		}

		route.append(nextDirection);
		row = nextRow;
		col = nextCol;
		}
	}

	// Step 4: getPath method
	
	// Step 5: One parameter constructor to read from a file
	/*
	public CaveExplorer (String fname) throws Exception {
	      Scanner in = new Scanner(new File(fname));

	      int rows = in.nextInt();
	      int cols = in.nextInt();  
    	  String s = in.nextLine(); // Skip newline character
    	   	  
    	  // Construct cave and populate with rest of data in the file
    	  
	}
	*/

	
	public static void main(String[] args) {
		System.out.println("Starting CaveExplorer");
		
		// Create a CaveExplorer object and print the starting layout
		
		// Call solve
		
		// Print the final layout, whether there is a path, and if so, what it is.
		
		// Step 5/6: Repeat for a different CaveExplorer object read from a file
		// Uncomment code below to start testing your 1-parameter constructor

		/*
		String fileName = "testdat.txt"; 
		try { 
			CaveExplorer ce2 = new CaveExplorer(fileName); 
		} 
		catch (FileNotFoundException e ) {
			System.out.println("Can't find file " + fileName); 
		}
		catch (Exception e) {
			System.out.println("Other error: " + e.getMessage());
		}
		*/
		 

		
		System.out.println("Finished CaveExplorer");
	}

}