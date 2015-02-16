package pt314.blocks.game.block;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import pt314.blocks.game.block.Block;
import pt314.blocks.game.block.VerticalBlock;

/**
 * JUnit tests for <code>VerticalBlock</code>.
 */
public class VerticalBlockTest {
	
	private Block block;

	@Before
	public void setUp() {
		block = new VerticalBlock();
	}

	@Test
	public void testIsValidDirectionUpDown() {
		assertTrue(block.isValidDirection(Direction.UP));
		assertTrue(block.isValidDirection(Direction.DOWN));
	}

	@Test
	public void testIsValidDirectionLeftRight() {
		assertFalse(block.isValidDirection(Direction.LEFT));
		assertFalse(block.isValidDirection(Direction.RIGHT));
	}
}
