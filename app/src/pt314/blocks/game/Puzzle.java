package pt314.blocks.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import pt314.blocks.game.block.Block;
import pt314.blocks.game.block.HorizontalBlock;
import pt314.blocks.game.block.TargetBlock;
import pt314.blocks.game.block.VerticalBlock;

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
	 * Load a puzzle from a file.
	 * 
	 * @throws Exception if there is a problem reading the file
	 *         or the format is invalid.
	 */
	public static Puzzle load(File file) throws Exception {
		
		BufferedReader in = new BufferedReader(new FileReader(file));

		String[] sizeStr = in.readLine().split(" ");
		int numRows = Integer.parseInt(sizeStr[0]); 
		int numCols = Integer.parseInt(sizeStr[1]);

		Board board = new Board(numCols, numRows);
		for (int row = 0; row < numRows; row++) {
			String currentRow = in.readLine();
			for (int col = 0; col < numCols; col++) {
				Block block = getBlock(currentRow.charAt(col)); 
				board.placeBlockAt(block, row, col);
			}
		}
		in.close();
		return new Puzzle(board);
	}

	/**
	 * Get block from character representation used in puzzle files.
	 * 
	 * @throws Exception if the character does not represent a valid
	 *         block or an empty space.
	 */
	private static Block getBlock(char ch) throws Exception {
		if (ch == '.')
			return null; // empty space
		else if (ch == 'T')
			return new TargetBlock();
		else if (ch == 'H')
			return new HorizontalBlock();
		else if (ch == 'V')
			return new VerticalBlock();
		else
			throw new Exception("Invalid character: \'" + ch + "\'"); 
	}
}
