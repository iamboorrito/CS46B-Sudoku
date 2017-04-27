package sudoku;

import java.util.*;


public class Grid 
{
	private int[][]						values;
	

	//
	// DON'T CHANGE THIS.
	//
	// See TestGridSupplier for examples of input.
	// Dots in input strings represent 0s in values[][].
	//
	public Grid(String[] rows)
	{
		values = new int[9][9];
		for (int j=0; j<9; j++)
		{
			String row = rows[j];
			char[] charray = row.toCharArray();
			for (int i=0; i<9; i++)
			{
				char ch = charray[i];
				if (ch != '.')
					values[j][i] = ch - '0';
			}
		}
	}
	
	
	//
	// DON'T CHANGE THIS.
	//
	public String toString()
	{
		String s = "";
		for (int j=0; j<9; j++)
		{
			for (int i=0; i<9; i++)
			{
				int n = values[j][i];
				if (n == 0)
					s += '.';
				else
					s += (char)('0' + n);
			}
			s += "\n";
		}
		return s;
	}
	
	//	Construct a 9x9 sudoku grid
	private Grid(int[][] values){
		this.values = new int[9][9];
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				this.values[i][j] = values[i][j];
			}
		}
	}
	
	// Finds an empty member of values[][]. Returns an array list of 9 grids that look like the current grid,
	// except the empty member contains 1, 2, 3 .... 9. Returns null if the current grid is full.
	public ArrayList<Grid> next9Grids()
	{		

		if( isFull() )
			return null;
		
		ArrayList<Grid> nextGrids = new ArrayList<Grid>(9);
		
		for(int i = 0; i < values.length; i++){
			for(int j = 0; j < values[0].length; j++){
				
				//Found empty spot, fill with appropriate value for next grid
				if(values[i][j] == 0){
					
					for(int k = 1; k < 10; k++){
						values[i][j] = k;
						
						nextGrids.add(new Grid(values));
					}	// end for(k)
					
					values[i][j] = 0;
					return nextGrids;
				}		// end if
			}	 		// end for(j)
		}		 		// end for(i)
		
		return null;
	}
	
	// Returns true if this grid is legal. A grid is legal if no row, column, or 
	// 3x3 block contains a repeated 1, 2, 3, 4, 5, 6, 7, 8, or 9.
	//
	public boolean isLegal(){
		
		HashSet<Integer> checkCols = new HashSet<Integer>(9);
		HashSet<Integer> checkRows = new HashSet<Integer>(9);
		int value = 0;
		
		// Check rows and columns
		for(int i = 0; i < values.length; i++){
			for(int j = 0; j < values[0].length; j++){
				
				if(values[i][j] != 0){						// Only compare nonempty cells
					
					if( checkCols.contains(values[i][j]) )  // If repeat is found, board is not legal
						return false;
					
					checkCols.add(values[i][j]);
				}		// end if
				
				if(values[j][i] != 0){
					
					if( checkRows.contains(values[j][i]) )
						return false;
					
					checkRows.add(values[j][i]);
				}		// end if
				
			}	 		// end for(j)
			
			// Clear the HashSets for reuse
			checkCols.clear();
			checkRows.clear();
		}		 		// end for(i)
		
		// Iterate through the 3x3 grids
		for(int i = 0; i < values.length; i+=3){
			for(int j = 0; j < values[0].length; j+=3){
				for(int k = 0; k < 3; k++){  			    // For each element in the grid, add it a HashSet to be compared
					for(int m = 0; m < 3; m++){
						value = values[i+k][j+m];		    // Save value of current cell
		
						if(value != 0){					    // Compare nonzero entries
							if(checkCols.contains(value))
								return false;
							
							checkCols.add(value);
						}//end if
					}	 //end for(m)
				}		 //end for(k)
				checkCols.clear();					        // Clear HashSet for reuse on next block
			}			 //end for(j)
		}				 //end for(i)
		
		return true;
	}

	// Returns true if every cell member of values[][] is a digit from 1-9.
	//
	public boolean isFull()
	{
		for(int i = 0; i < values.length; i++)
			for(int j = 0; j < values[0].length; j++)
				if(values[i][j] == 0)						// Grid is not full
					return false;
		
		return true;
	}
	
	// Returns true if x is a Grid and, for every (i,j), 
	// x.values[i][j] == this.values[i][j].
	//
	public boolean equals(Object x)
	{
		Grid g = (Grid)x;
		
		for(int i = 0; i < values.length; i++)
			for(int j = 0; j < values[0].length; j++)
				if(values[i][j] != g.values[i][j])			 // Grids differ
					return false;
		
		return true;
	}
}
