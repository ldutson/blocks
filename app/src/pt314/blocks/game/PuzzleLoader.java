package pt314.blocks.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import pt314.blocks.game.block.Block;
import pt314.blocks.game.block.HorizontalBlock;
import pt314.blocks.game.block.TargetBlock;
import pt314.blocks.game.block.VerticalBlock;

/**
 * Loads puzzles from files.
 */
public class PuzzleLoader {

	private static final String PUZZLE_FOLDER = "res/puzzles/";
	
	/**
	 * Load a puzzle given its number.
	 * 
	 * @throws Exception if there is a problem reading the file
	 *         or the format is invalid.
	 */
	public static Puzzle load(int puzzleNumber) throws Exception {
		String number = String.format("%03d", puzzleNumber);
		String path = PUZZLE_FOLDER + "puzzle-" + number + ".txt"; 
		File file = new File(path);
		return load(file);
	}

	/**
	 * Load a puzzle from a file.
	 * 
	 * @throws Exception if there is a problem reading the file
	 *         or the format is invalid.
	 */
	public static Puzzle load(File file) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader(file));
		try {
			int[] size = readBoardSize(in);
			int numRows = size[0];
			int numCols = size[1];

			Board board = new Board(numRows, numCols);
			readBoardContent(board, in);
			
			return new Puzzle(board);
		}
		finally {
			in.close();
		}
	}

	/**
	 * Read the size of the board from a puzzle file.
	 * 
	 * @throws Exception if there is a problem reading the file
	 *         or if the size is invalid.
	 */
	private static int[] readBoardSize(BufferedReader in) throws Exception {
		String[] sizeStr = in.readLine().split(" ");
		int numRows = Integer.parseInt(sizeStr[0]); 
		int numCols = Integer.parseInt(sizeStr[1]);
		if (numRows < 1 || numCols < 1)
			throw new Exception("Invalid board size: "
					+ "rows=" + numRows + ", cols=" + numCols);
		int[] size = {numRows, numCols};
		return size;
	}
	
	/**
	 * Read the content of the board from a puzzle file.
	 * 
	 * @throws Exception if there is a problem reading the file
	 *         or if the content is invalid.
	 */
	private static void readBoardContent(Board board, BufferedReader in) throws Exception {
		int numRows = board.getNumRows();
		int numCols = board.getNumCols();
		
		// read content (blocks)
		for (int row = 0; row < numRows; row++) {
			String currentRow = in.readLine();
			for (int col = 0; col < numCols; col++) {
				Block block = createBlock(currentRow.charAt(col)); 
				board.placeBlockAt(block, row, col);
			}
		}
		
		// make sure there is only one target block, and get it's location
		int targetBlockRow = -1;
		int targetBlockCol = -1;
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				Block block = board.getBlockAt(row, col);
				if (block instanceof TargetBlock) {
					if (targetBlockRow != -1)
						throw new Exception("Multiple target blocks.");
					targetBlockRow = row;
					targetBlockCol = col;
				}
			}
		}
		
		// make sure there are no horizontal blocks to the right of the target block
		for (int col = targetBlockCol + 1; col < board.getNumCols(); col++) {
			Block block = board.getBlockAt(targetBlockRow, col);
			if (block instanceof HorizontalBlock)
				throw new Exception("Horizontal block to the right of the target block.");
		}
	}
	
	/**
	 * Create block from character representation used in puzzle files.
	 * 
	 * @throws Exception if the character does not represent a valid
	 *         block or an empty space.
	 */
	private static Block createBlock(char ch) throws Exception {
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
