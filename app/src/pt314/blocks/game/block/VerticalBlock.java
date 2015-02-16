package pt314.blocks.game.block;


/**
 * This type of block can only move vertically (up or down).
 */
public class VerticalBlock extends Block {

	public VerticalBlock() {}

	@Override
	public boolean isValidDirection(Direction dir) {
		return dir == Direction.UP || dir == Direction.DOWN;
	}
}
