package simpledoku;

/*
 * Resources Used:
 * Discord Group Chat: Many of my peers had the same questions as I did and the answers that were provided were of help to me.
 * Online Websites: I used online websites to help me brainstorm about how to write the methods and finish this class correctly.
 */
import java.util.*;

public class SimpledokuGrid {

	private int				nCellsPerSide;
	private int[][]			values;
	
	
	public int[][] getValues() {
		return values;
	}


	public void setValues(int[][] values) {
		this.values = values;
	}


	public SimpledokuGrid(int nCellsPerSide)
	{
		this.nCellsPerSide = nCellsPerSide;
		values = new int[nCellsPerSide][nCellsPerSide];
	}
	
	
	// This is called a "copy constructor". A copy constructor has 1 argument, which is the same type as
	// the current class. The constructor should make the new instance look just like the "source"
	// instance. CAUTION: The source and the current grid should each have their own "values"
	// array. So don't say "this.values = source.values". You have to create a new values
	// array, and copy numbers from source.values into the new array, one at a time.
	public SimpledokuGrid(SimpledokuGrid source)
	{
		//System.out.println("in copy constructor....");
		this.nCellsPerSide = source.nCellsPerSide;
		
		values = new int[source.values.length][source.values.length];
		for (int i = 0; i < source.values.length; i++) {
			for (int j = 0; j < source.values[i].length; j++) {
				values[i][j] = source.values[i][j];
			}
		}
	}	
	
	
	//
	// Returns true if the input list contains a repeated value that isn't zero.
	// Call this from isLegal(). DON’T CHANGE THIS METHOD, BUT UNDERSTAND HOW IT WORKS.
	//
	private boolean containsNonZeroRepeats(ArrayList<Integer> checkUs)
	{
		HashSet<Integer> set = new HashSet<>();
		for (Integer i: checkUs)
		{
			if (i != 0)
			{
				//could I write this a different way? DON'T CHANGE THE CODE, but THINK about it.
				if (set.contains(i))
					return true;
				else
					set.add(i);
			}
		}
		return false;
	}
	
	
	public boolean isLegal()
	{
		// Check all rows. For each row, put the corresponding numbers from
		// the "values" array into an ArrayList<Integer>. Then pass the array
		// list to containsNonZeroRepeats(). If containsNonZeroRepeats() return true,
		// then this grid isn't legal, so return false.	
		for (int i = 0; i < values.length; i++) {
			ArrayList<Integer> rowsCheck = new ArrayList<Integer>();
			for (int j = 0; j < values[0].length; j++) {
				rowsCheck.add(values[i][j]);
			}
			if (containsNonZeroRepeats(rowsCheck)) {
				//System.out.println("containsNonZeroRepeats(rowsCheck) == true....");
				//System.out.println(rowsCheck);
				return false;	
			}
		}
		


		// Check all columns. For each column, put the corresponding numbers from
		// the "values" array into an ArrayList<Integer>. Then pass the array
		// list to containsNonZeroRepeats(). If containsNonZeroRepeats() return true,
		// then this grid isn't legal, so return false.
		for (int i = 0; i < values[0].length; i++) {
			ArrayList<Integer> columnsCheck = new ArrayList<Integer>();
			for (int j = 0; j < values.length; j++ ) {
				columnsCheck.add(values[j][i]);
			}
			if (containsNonZeroRepeats(columnsCheck) == true) {
				//System.out.println("containsNonZeroRepeats(columnsCheck) == true....");
				return false;
			}
		}
		

		// Check top-left to bottom-right diagonal. 
			ArrayList<Integer> leftToRightDiagonalCheck = new ArrayList<Integer>();
			for (int i = 0; i < values.length; i++) {
				leftToRightDiagonalCheck.add(values[i][i]);
				if (containsNonZeroRepeats(leftToRightDiagonalCheck) == true) {
					//System.out.println("containsNonZeroRepeats(leftToRightDiagonalCheck) == true....");
					return false;
				}
			}
		
		
	
		// Check top-right to bottom-left diagonal. 
			ArrayList<Integer> rightToLeftDiagonalCheck = new ArrayList<Integer>();
			for (int i = 0; i < values.length; i++) {
				rightToLeftDiagonalCheck.add(values[i][values.length - i - 1]);
				if (containsNonZeroRepeats(rightToLeftDiagonalCheck) == true) {
					//System.out.println("containsNonZeroRepeats(rightToLeftDiagonalCheck) == true....");
					return false;
				}
			}
		
		// If we haven't returned yet, this grid must be legal.
		return true;
	}
	
	
	// Returns true if all members of the values[][] array are non-zero.
	public boolean isFull()
	{
		boolean full = true;
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < values.length; j++) {
				if (values[i][j] == 0) {
					full = false;
				}
			}
		}
		return full;
	}	
	
	
	
	// Returns the Evaluation corresponding to the state of this grid. The return will be
	// Evaluation.ABANDON, Evaluation.ACCEPT, or Evaluation.CONTINUE. Abandon if illegal,
	// accept if legal and the grid is full, continue if legal and incomplete.
	public Evaluation evaluate()
	{
		if (this.isLegal() != true) {
			return Evaluation.ABANDON;
		}
		else if (this.isLegal() == true && this.isFull() == true) {
			return Evaluation.ACCEPT;
		}
		else {
			return Evaluation.CONTINUE;
		}
	}
	
	
	// Returns a size=2 array of ints that index the next empty cell in the values[][] array.
	// The 2 ints in the returned array are the first and second indices into the values[][] array.
	// Returns null if this grid is full.  DON’T CHANGE THIS METHOD, BUT UNDERSTAND HOW IT WORKS.
	private int[] getIndicesOfNextEmptyCell()
	{
		for (int x=0; x<nCellsPerSide; x++)
			for (int y=0; y<nCellsPerSide; y++)
				if (values[x][y] == 0)
					return new int[] {x, y};
		
		return null;
	}
	
	
	//
	// COMPLETE THIS
	//
	//
	// Finds an empty member of "values". Returns an array list containing nCellsPerSide grids that look 
	// like the current grid, except the empty member contains 1, 2, 3 .... nCellsPerSide. 
	// 
	//
	// Example: if this grid = 12300
	//                         00000
	//                         00000
	//                         00000
	//                         00000
	//
	// Then the returned array list contains:
	//
	//      12310        12320        12330        12340        12350
	//      00000        00000        00000        00000        00000
	//      00000        00000        00000        00000        00000
	//      00000        00000        00000        00000        00000
	//      00000        00000        00000        00000        00000
	//
	// To create each new grids, use the copy constructor and pass in "this" grid. Then
	// change one member of the "values" of the new grid.
	// CAUTION: Make sure you are generating new grids!
	ArrayList<SimpledokuGrid> generateAllNextGrids()
	{		
		int[] indicesOfNext = getIndicesOfNextEmptyCell();
		//System.out.println(indicesOfNext[0]+","+indicesOfNext[1]);
		//System.out.println(this.nCellsPerSide);
		//System.out.println(this.values.length);
		ArrayList<SimpledokuGrid> nextGrids = new ArrayList<SimpledokuGrid>();
		// Generate some grids and put them in nextGrids. Be sure that every
		// grid is a separate object.
		for (int i=1; i <= this.nCellsPerSide; i++) {
			SimpledokuGrid grid = new SimpledokuGrid(this);
			//System.out.println(i+".."+grid.nCellsPerSide);
			
			grid.values[indicesOfNext[0]][indicesOfNext[1]] = i;	
			nextGrids.add(grid);
		}
		return nextGrids;
	}
	
	
	// Use this for debugging if it's helpful.  DON’T CHANGE THIS METHOD, BUT UNDERSTAND HOW IT WORKS.
	public String toString()
	{
		//System.out.println("in to string....");
		
		String s = "";
		for (int j=0; j<nCellsPerSide; j++)
		{
			for (int i=0; i<nCellsPerSide; i++)
			{
				if (values[j][i] == 0)
					s += ".";
				else
					s += ("" + values[j][i]);
			}
			s += "\n";
		}
		return s;
	}
	
	//You can add a main method here for debugging. 
//	public static void main(String[] args) {
//		//System.out.println("Start execution....");
//		
//		SimpledokuGrid sdg1 = new SimpledokuGrid(4);
//		int[][] data = {
//				{1,2,3,4},
//				{3,4,1,2},
//				{4,3,2,1},
//				{2,1,4,3}		
//				};
//
//		int[][] data2 = {
//				{1,2,3,0},
//				{1,1,1,1},
//				{1,1,1,1},
//				{1,1,1,1}		
//				};
//		
// 		sdg1.setValues(data2);
// 		
// 		SimpledokuGrid sdg2 = new SimpledokuGrid(sdg1);
// 		for (int i = 0; i < sdg2.values.length; i++) {
//			for (int j = 0; j < sdg2.values[i].length; j++) {
//				System.out.println(sdg2.values[i][j]);
//			}
//		}
// 		
// 		System.out.println(sdg2.isLegal());
//	}
	public static void main(String[] args) {
        
        SimpledokuGrid s = new SimpledokuGrid(3);
        
        //System.out.println(s.toString());
        
        for (SimpledokuGrid simpledokuGrid : s.generateAllNextGrids()) {
            System.out.println(simpledokuGrid.toString());
        }
//        
//        System.out.println(s.isLegal());
    }
} 		

	
	

