package pt314.blocks.game.block;

/**
 * Possible move directions.
 */
public enum Direction {
	
	UP, DOWN, LEFT, RIGHT;
	
	public boolean isHorizontal() { 
		return this == LEFT || this == RIGHT;
	}
	
	public boolean isVertical() { 
		return this == UP || this == DOWN;
	}
}
