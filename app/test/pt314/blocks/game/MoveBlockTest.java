package pt314.blocks.game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit tests for <code>VerticalBlock</code>.
 */
public class MoveBlockTest {

	private GameBoard testGameBoard = new GameBoard(5, 5);

	@Before
	public void setUp() {
		testGameBoard.placeBlockAt(new HorizontalBlock(), 1, 1);
		testGameBoard.placeBlockAt(new VerticalBlock(), 1, 2);
	}

	@Test
	public void testMovingEmptyCell() {
		assertFalse(testGameBoard.moveBlock(2, 2, Direction.UP, 1));
	}

	@Test
	public void testMovingOutOfBounds() {
		assertFalse(testGameBoard.moveBlock(1, 1, Direction.LEFT, 2));
		assertFalse(testGameBoard.moveBlock(1, 1, Direction.RIGHT, 4));
		assertFalse(testGameBoard.moveBlock(1, 2, Direction.UP, 3));
		assertFalse(testGameBoard.moveBlock(1, 2, Direction.DOWN, 4));
	}

	@Test
	public void testInvalidDirection() {
		assertFalse(testGameBoard.moveBlock(1, 1, Direction.UP, 1)); // up is invalid for the horizontal block
		assertFalse(testGameBoard.moveBlock(1, 2, Direction.LEFT, 1)); // left is invalid for the vertical block
	}

	@Test
	public void testValidDirection() {
		assertTrue(testGameBoard.moveBlock(1, 1, Direction.LEFT, 1));
		assertTrue(testGameBoard.moveBlock(1, 2, Direction.DOWN, 1));
	}

	@Test
	public void testCollision() {
		assertFalse(testGameBoard.moveBlock(1, 1, Direction.RIGHT, 1));
	}

	@Test
	public void testJumpBlockCollision() {
		assertFalse(testGameBoard.moveBlock(1, 1, Direction.RIGHT, 3));
	}

}
