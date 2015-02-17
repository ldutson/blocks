package pt314.blocks.game;

import static org.junit.Assert.*;

import org.junit.Test;

import pt314.blocks.game.block.Direction;
import pt314.blocks.game.block.HorizontalBlock;
import pt314.blocks.game.block.VerticalBlock;

/**
 * JUnit tests for <code>Board</code>.
 */
//TODO: check that blocks are not moved when a move is invalid
public class BoardTest {

	@Test(expected=Exception.class)
	public void testMoveBlockNull() throws Exception {
		Board board = new Board(1, 2);
		assertNull(board.getBlockAt(0, 0));
		board.moveBlock(0, 0, Direction.RIGHT, 1);
	}

	@Test(expected=Exception.class)
	public void testMoveBlockInvalidDirectionUp() throws Exception {
		Board board = new Board(5, 5);
		board.placeBlockAt(new HorizontalBlock(), 2, 2);
		board.moveBlock(2, 2, Direction.UP, 1);
	}

	@Test(expected=Exception.class)
	public void testMoveBlockInvalidDirectionDown() throws Exception {
		Board board = new Board(5, 5);
		board.placeBlockAt(new HorizontalBlock(), 2, 2);
		board.moveBlock(2, 2, Direction.DOWN, 1);
	}

	@Test(expected=Exception.class)
	public void testMoveBlockInvalidDirectionLeft() throws Exception {
		Board board = new Board(5, 5);
		board.placeBlockAt(new VerticalBlock(), 2, 2);
		board.moveBlock(2, 2, Direction.LEFT, 1);
	}

	@Test(expected=Exception.class)
	public void testMoveBlockInvalidDirectionRight() throws Exception {
		Board board = new Board(5, 5);
		board.placeBlockAt(new VerticalBlock(), 2, 2);
		board.moveBlock(2, 2, Direction.RIGHT, 1);
	}

	@Test(expected=Exception.class)
	public void testMoveBlockOutOfBoundsUp() throws Exception {
		Board board = new Board(5, 5);
		board.placeBlockAt(new VerticalBlock(), 2, 2);
		board.moveBlock(2, 2, Direction.UP, 3);
	}

	@Test(expected=Exception.class)
	public void testMoveBlockOutOfBoundsDown() throws Exception {
		Board board = new Board(5, 5);
		board.placeBlockAt(new VerticalBlock(), 2, 2);
		board.moveBlock(2, 2, Direction.DOWN, 3);
	}

	@Test(expected=Exception.class)
	public void testMoveBlockOutOfBoundsLeft() throws Exception {
		Board board = new Board(5, 5);
		board.placeBlockAt(new HorizontalBlock(), 2, 2);
		board.moveBlock(2, 2, Direction.LEFT, 3);
	}

	@Test(expected=Exception.class)
	public void testMoveBlockOutOfBoundsRight() throws Exception {
		Board board = new Board(5, 5);
		board.placeBlockAt(new HorizontalBlock(), 2, 2);
		board.moveBlock(2, 2, Direction.RIGHT, 3);
	}

	@Test(expected=Exception.class)
	public void testMoveBlockPathNotClearUp() throws Exception {
		Board board = new Board(5, 5);
		board.placeBlockAt(new VerticalBlock(), 2, 2);
		board.placeBlockAt(new HorizontalBlock(), 1, 2);
		board.moveBlock(2, 2, Direction.UP, 1);
	}

	@Test(expected=Exception.class)
	public void testMoveBlockPathNotClearDown() throws Exception {
		Board board = new Board(5, 5);
		board.placeBlockAt(new VerticalBlock(), 2, 2);
		board.placeBlockAt(new HorizontalBlock(), 3, 2);
		board.moveBlock(2, 2, Direction.DOWN, 1);
	}

	@Test(expected=Exception.class)
	public void testMoveBlockPathNotClearLeft() throws Exception {
		Board board = new Board(5, 5);
		board.placeBlockAt(new HorizontalBlock(), 2, 2);
		board.placeBlockAt(new HorizontalBlock(), 2, 1);
		board.moveBlock(2, 2, Direction.LEFT, 1);
	}

	@Test(expected=Exception.class)
	public void testMoveBlockPathNotClearRight() throws Exception {
		Board board = new Board(5, 5);
		board.placeBlockAt(new HorizontalBlock(), 2, 2);
		board.placeBlockAt(new HorizontalBlock(), 2, 3);
		board.moveBlock(2, 2, Direction.RIGHT, 1);
	}

	@Test
	public void testMoveBlockDistanceZero() throws Exception {
		Board board = new Board(1, 3);
		board.placeBlockAt(new HorizontalBlock(), 0, 0);
		boolean result = board.moveBlock(0, 0, Direction.RIGHT, 0);
		assertFalse(result);
		assertNotNull(board.getBlockAt(0, 0));
		assertNull(board.getBlockAt(0, 1));
		assertNull(board.getBlockAt(0, 2));
	}

	@Test
	public void testMoveBlockDistanceOne() throws Exception {
		Board board = new Board(1, 3);
		board.placeBlockAt(new HorizontalBlock(), 0, 0);
		boolean result = board.moveBlock(0, 0, Direction.RIGHT, 1);
		assertTrue(result);
		assertNull(board.getBlockAt(0, 0));
		assertNotNull(board.getBlockAt(0, 1));
		assertNull(board.getBlockAt(0, 2));
	}

	@Test
	public void testMoveBlockDistanceGreaterThanOne() throws Exception {
		Board board = new Board(1, 3);
		board.placeBlockAt(new HorizontalBlock(), 0, 0);
		boolean result = board.moveBlock(0, 0, Direction.RIGHT, 2);
		assertTrue(result);
		assertNull(board.getBlockAt(0, 0));
		assertNull(board.getBlockAt(0, 1));
		assertNotNull(board.getBlockAt(0, 2));
	}
}
