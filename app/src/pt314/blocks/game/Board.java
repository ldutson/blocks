package pt314.blocks.game;

import pt314.blocks.game.block.Block;
import pt314.blocks.game.block.Direction;
import pt314.blocks.game.block.HorizontalBlock;
import pt314.blocks.game.block.TargetBlock;
import pt314.blocks.game.block.VerticalBlock;

public class Board {

	private int numRows;
	private int numCols;
	private Block[][] blocks;
	
	public Board(int numRows, int numCols) {
		this.numRows = numRows;
		this.numCols = numCols;
		blocks = new Block[numRows][numCols];
	}
	
	public int getNumRows() {
		return numRows;
	}
	
	public int getNumCols() {
		return numCols;
	}
	
	/**
	 * Place block at the specified location.
	 * 
	 * If there is a block at the location, it is replaced by the new block.
	 */
	public void placeBlockAt(Block block, int row, int col) {
		blocks[row][col] = block;
	}
	
	/**
	 * Get block from a specific location.
	 * 
	 * Returns <code>null</code> if the location is empty of if it is out of bounds. 
	 */
	public Block getBlockAt(int row, int col) {
		if (!isWithinBounds(row, col))
			return null;
		return blocks[row][col];
	}

	/**
	 * Move block at the specified location.
	 * 
	 * @param dir direction of movement.
	 * @param dist absolute movement distance.
	 * 
	 * @return <code>true</code> if and only if the block is moved.
	 * 
	 * @throws Exception if any of the following is true:
	 *         - there is no block at the specified location,
	 *         - the block cannot move in the specified direction,
	 *         - the new destination is out of the bounds of the board, or
	 *         - the path to the destination is not clear.
	 */
	public boolean moveBlock(int row, int col, Direction dir, int dist) throws Exception {
		
		if (dist == 0)
			return false;
		
		Block block = blocks[row][col];

		if (block == null)
			throw new Exception("No block to move.");
		
		if (!block.isValidDirection(dir))
			throw new Exception("Invalid direction: " + dir);
		
		int newRow = Move.getNewRow(row, dir, dist);
		int newCol = Move.getNewCol(col, dir, dist);
		if (!isWithinBounds(newRow, newCol))
			throw new Exception("Destination out of bounds: "
					+ "row=" + newRow + ", col=" + newCol);
		
		if (!Move.isPathClear(this, row, col, dir, dist))
			throw new Exception("Path is not clear: block in the way.");

		placeBlockAt(block, newRow, newCol);
		placeBlockAt(null, row, col);
		return true;
	}
	
	/**
	 * Check if a location is inside the board.
	 */
	public boolean isWithinBounds(int row, int col) {
		if (row < 0 || row >= numRows)
			return false;
		if (col < 0 || col >= numCols)
			return false;
		return true;
	}

	/**
	 * Print the board to standard out.
	 */
	public void print() {
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				Block block = blocks[row][col];
				char ch = '.';
				if (block instanceof TargetBlock)
					ch = 'T';
				else if (block instanceof HorizontalBlock)
					ch = 'H';
				else if (block instanceof VerticalBlock)
					ch = 'V';
				System.out.print(ch);
			}
			System.out.println();
		}
		System.out.println();
	}
}
