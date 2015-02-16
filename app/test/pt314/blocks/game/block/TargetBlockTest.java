package pt314.blocks.game.block;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import pt314.blocks.game.block.Block;
import pt314.blocks.game.block.TargetBlock;

/**
 * JUnit tests for <code>TargetBlock</code>.
 */
public class TargetBlockTest {
	
	private Block block;

	@Before
	public void setUp() {
		block = new TargetBlock();
	}

	@Test
	public void testIsValidDirectionLeftRight() {
		assertTrue(block.isValidDirection(Direction.LEFT));
		assertTrue(block.isValidDirection(Direction.RIGHT));
	}

	@Test
	public void testIsValidDirectionUpDown() {
		assertFalse(block.isValidDirection(Direction.UP));
		assertFalse(block.isValidDirection(Direction.DOWN));
	}
}
