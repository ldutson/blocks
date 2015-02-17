package pt314.blocks.game;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import pt314.blocks.game.block.HorizontalBlockTest;
import pt314.blocks.game.block.TargetBlockTest;
import pt314.blocks.game.block.VerticalBlockTest;

@RunWith(Suite.class)
@SuiteClasses({
	BoardTest.class,
	HorizontalBlockTest.class,
	VerticalBlockTest.class,
	TargetBlockTest.class
})

public class AllTests {}
