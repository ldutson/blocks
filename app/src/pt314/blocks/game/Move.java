package pt314.blocks.game;

import pt314.blocks.game.block.Direction;

/**
 * Utility methods related to moving blocks.
 */
public class Move {

	public static int getNewRow(int row, Direction dir, int dist) {
		int newRow = row;
		if (dir == Direction.UP)
			newRow -= dist;
		else if (dir == Direction.DOWN)
			newRow += dist;
		return newRow;
	}

	public static int getNewCol(int col, Direction dir, int dist) {
		int newCol = col;
		if (dir == Direction.LEFT)
			newCol -= dist;
		else if (dir == Direction.RIGHT)
			newCol += dist;
		return newCol;
	}
	
	/**
	 * Checks if the path is clear for a block to move.
	 * Assumes the path is within the bounds of the board.
	 * 
	 * @param row the starting row of the block.
	 * @param col the starting column of the block.
	 * 
	 * @return <code>true</code> if and only if there are
	 *         no other blocks in the path.
	 */
	public static boolean isPathClear(Board board,
			int row, int col, Direction dir, int dist) {

		int dx = 0;
		int dy = 0;
		if (dir == Direction.UP)
			dy = -1;
		else if (dir == Direction.DOWN)
			dy = 1;
		else if (dir == Direction.LEFT)
			dx = -1;
		else if (dir == Direction.RIGHT)
			dx = 1;
		
		// check all cells from block location to destination
		int tmpRow = row;
		int tmpCol = col;
		for (int i = 0; i < dist; i++) {
			tmpRow += dy;
			tmpCol += dx;
			if (board.getBlockAt(tmpRow, tmpCol) != null)
				return false; // another block in the way
		}
		return true;
	}
}
