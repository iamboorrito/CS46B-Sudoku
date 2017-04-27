package sudoku;

import java.util.*;


public class Solver 
{
	private Grid						problem;
	private ArrayList<Grid>				solutions;
	
	
	public Solver(Grid problem)
	{
		this.problem = problem;
	}
	
	
	public void solve()
	{
		solutions = new ArrayList<>();
		solveRecurse(problem);
	}
	
	// Standard backtracking recursive solver.
	private void solveRecurse(Grid grid)
	{		
		Evaluation eval = evaluate(grid);
		
		if (eval == Evaluation.ABANDON)
		{
			// Abandon evaluation of this illegal board.
			return;
		}
		else if (eval == Evaluation.ACCEPT)
		{
			// A complete and legal solution. Add it to solutions.
			solutions.add(grid);
		}
		else
		{
			// Here if eval == Evaluation.CONTINUE. Generate all 9 possible next grids. Recursively 
			// call solveRecurse() on those grids.
			
			ArrayList<Grid> nextGrids = grid.next9Grids();
			for(Grid g : nextGrids)
				solveRecurse(g);
		}
	}
	
	// Returns Evaluation.ABANDON if the grid is illegal. 
	// Returns ACCEPT if the grid is legal and complete.
	// Returns CONTINUE if the grid is legal and incomplete.
	//
	public Evaluation evaluate(Grid grid)
	{

		boolean full = grid.isFull();
		boolean legal = grid.isLegal();
		
		if(!legal)
			return Evaluation.ABANDON;
		if(!full)
			return Evaluation.CONTINUE;
		else
			return Evaluation.ACCEPT;
		
	}

	
	public ArrayList<Grid> getSolutions()
	{
		return solutions;
	}
	
	
	public static void main(String[] args)
	{
		
		String[] puzzle =
			{
				"8........",
				"..36.....",
				".7..9.2..",
				".5...7...",
				"....457..",
				"...1...3.",
				"..1....68",
				"..85...1.",
				".9....4.."		
			};
		
		Grid g = new Grid(puzzle);
		Solver solver = new Solver(g);
		
		double time1 = 0;
		double time2 = 0;
		
		time1 = System.currentTimeMillis();
		solver.solve();
		time2 = System.currentTimeMillis();
		
		System.out.println("Time in Milliseconds: "+(time2-time1));
		
		// Print out your solution, or test if it equals() the solution in TestGridSupplier.
		System.out.println(solver.getSolutions().get(0));
		//System.out.println(solver.getSolutions().get(0).equals(TestGridSupplier.getSolution3()));
	}
}
