package pt314.blocks;

import pt314.blocks.game.Board;
import pt314.blocks.game.block.Block;
import pt314.blocks.game.block.Direction;
import pt314.blocks.game.block.HorizontalBlock;

/**
 * Just a test...
 */
public class Test {

	public static void main(String[] args) {
		
		Board board = new Board(5, 3);
		
		Block block1 = new HorizontalBlock();
		
		board.placeBlockAt(block1, 0, 0);
		
		board.print();
		for (int i = 0; i < 5; i++) {
			boolean result = board.moveBlock(0, i, Direction.RIGHT, 1);
			System.out.println(result);
			board.print();
		}
	}
}
