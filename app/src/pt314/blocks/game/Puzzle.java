package pt314.blocks.game;

import pt314.blocks.game.block.Block;
import pt314.blocks.game.block.TargetBlock;

/**
 * A puzzle includes a <code>Board</code> with an initial
 * configuration, and may include other information about
 * the state of the game.
 */
public class Puzzle {

	private Board board;
	
	public Puzzle(Board board) {
		this.board = board;
	}

	public Board getBoard() {
		return board;
	}

	/**
	 * A puzzle is solved when the target block reaches
	 * the rightmost column of the board.
	 */
	public boolean isSolved() {
		// determine target block column
		// TODO: simplify this
		int targetCol = 0;
		for (int row = 0; row < board.getNumRows(); row++) {
			for (int col = 0; col < board.getNumCols(); col++) {
				Block block = board.getBlockAt(row, col);
				if (block instanceof TargetBlock)
					targetCol = col;
			}
		}
		return targetCol == board.getNumCols() - 1;
	}

}
