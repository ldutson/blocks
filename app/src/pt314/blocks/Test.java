package pt314.blocks;

import pt314.blocks.game.Board;
import pt314.blocks.game.block.Block;
import pt314.blocks.game.block.Direction;
import pt314.blocks.game.block.HorizontalBlock;

/**
 * Just a test...
 */
public class Test {

	public static void main(String[] args) throws Exception {
		
		Board board = new Board(3, 5);
		
		Block block1 = new HorizontalBlock();
		
		board.placeBlockAt(block1, 0, 0);
		
		board.print();
		for (int i = 0; i < 5; i++) {
			try {
				boolean result = board.moveBlock(0, i, Direction.RIGHT, 1);
				System.out.println(result);
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
			board.print();
		}
	}
}
