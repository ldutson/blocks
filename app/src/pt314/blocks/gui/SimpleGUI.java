package pt314.blocks.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import pt314.blocks.game.Block;
import pt314.blocks.game.Direction;
import pt314.blocks.game.GameBoard;
import pt314.blocks.game.HorizontalBlock;
import pt314.blocks.game.TargetBlock;
import pt314.blocks.game.VerticalBlock;

/**
 * Simple GUI test...
 */
public class SimpleGUI extends JFrame implements ActionListener {

	File fileIn = new File("res" + File.separator + "puzzles" + File.separator + "puzzle-003.txt");

	private GameBoard board;

	// currently selected block
	private Block selectedBlock;
	private int selectedBlockRow;
	private int selectedBlockCol;

	private GridButton[][] buttonGrid;
	private char[][] initGrid;

	private JMenuBar menuBar;
	private JMenu gameMenu, helpMenu;
	private JMenuItem newGameMenuItem;
	private JMenuItem exitMenuItem;
	private JMenuItem aboutMenuItem;

	private static int numRows;
	private static int numCols;
	private int targetRow;

	public SimpleGUI() {
		super("Blocks");

		initMenus();

		try {
			readStartGame(fileIn);
		} catch (IOException e) {
			e.printStackTrace();
		}

		initBoard();

		pack();
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	private void initMenus() {
		menuBar = new JMenuBar();

		gameMenu = new JMenu("Game");
		menuBar.add(gameMenu);

		helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);

		newGameMenuItem = new JMenuItem("New game");
		newGameMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				new SimpleGUI();

				JOptionPane.showMessageDialog(SimpleGUI.this, "The default game will be loaded");
			}
		});
		gameMenu.add(newGameMenuItem);

		gameMenu.addSeparator();

		exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		gameMenu.add(exitMenuItem);

		aboutMenuItem = new JMenuItem("About");
		aboutMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(SimpleGUI.this, "Sliding blocks!");
			}
		});
		helpMenu.add(aboutMenuItem);

		setJMenuBar(menuBar);
	}

	private void initBoard() {
		board = new GameBoard(numCols, numRows);
		buttonGrid = new GridButton[numCols][numRows];

		setLayout(new GridLayout(numCols, numRows));
		for (int row = 0; row < numCols; row++) {
			for (int col = 0; col < numRows; col++) {
				GridButton cell = new GridButton(row, col);
				cell.setPreferredSize(new Dimension(64, 64));
				cell.addActionListener(this);
				cell.setOpaque(true);
				buttonGrid[row][col] = cell;
				add(cell);
			}
		}

		for (int x = 0; x < numRows; x ++) {
			for (int y = 0; y < numCols; y ++) {
				if (initGrid[x][y] == 'V') {
					board.placeBlockAt(new VerticalBlock(), x, y);
				}
				else if (initGrid[x][y] == 'H') {
					board.placeBlockAt(new HorizontalBlock(), x, y);
				}
				else if (initGrid[x][y] == 'T') {
					targetRow = x;
					board.placeBlockAt(new TargetBlock(), x, y);
				}
			}
		}
		
		// add some blocks for testing...
//		board.placeBlockAt(new HorizontalBlock(), 0, 0);
//		board.placeBlockAt(new HorizontalBlock(), 4, 4);
//		board.placeBlockAt(new VerticalBlock(), 1, 3);
//		board.placeBlockAt(new VerticalBlock(), 3, 1);
//		board.placeBlockAt(new TargetBlock(), 2, 2);

		updateUI();
	}


	private void readStartGame(File fin) throws IOException {
		FileInputStream fis = new FileInputStream(fin);
		int index = 0;

		//Construct BufferedReader from InputStreamReader
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));

		String line = null;
		line = br.readLine();
		
		index = line.indexOf(' ');
		System.out.println(index);

		String rowString = line.substring(0, index);
		String colString = line.substring(index+1);
		
		numRows = stringNum(rowString);
		numCols = stringNum(colString);
		
		if (numRows < 1 || numCols < 1) {
			br.close();
			throw new IllegalArgumentException();
		}
		initGrid = new char[numRows][numCols];

		for (int row = 0; row < numRows; row ++) {
			line = br.readLine();
			for (int col = 0; col < numCols; col ++) {
				initGrid[row][col] = line.charAt(col);
				System.out.print(initGrid[row][col]);
			}
			System.out.println();
		}

		br.close();

	}

	private int stringNum(String convertString) {
		int num = 0;
		
		for (int i = 0; i < convertString.length(); i ++) {
			num *= 10;
			num += convertString.charAt(i) - '0';
		}
		
		return num;
	}
	
			
	// Update display based on the state of the board...
	// TODO: make this more efficient
	private void updateUI() {
		for (int row = 0; row < numCols; row++) {
			for (int col = 0; col < numCols; col++) {
				Block block = board.getBlockAt(row, col);
				JButton cell = buttonGrid[row][col];
				if (block == null)
					cell.setBackground(Color.PINK);
				else if (block instanceof TargetBlock)
					cell.setBackground(Color.RED);
				else if (block instanceof HorizontalBlock)
					cell.setBackground(Color.GREEN);
				else if (block instanceof VerticalBlock)
					cell.setBackground(Color.CYAN);
			}
		}
	}

	/**
	 * Handle board clicks.
	 * 
	 * Movement is done by first selecting a block, and then
	 * selecting the destination.
	 * 
	 * Whenever a block is clicked, it is selected, even if
	 * another block was selected before.
	 * 
	 * When an empty cell is clicked after a block is selected,
	 * the block is moved if the move is valid.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Handle grid button clicks...
		GridButton cell = (GridButton) e.getSource();
		int row = cell.getRow();
		int col = cell.getCol();
		System.out.println("Clicked (" + row + ", " + col + ")");

		if (selectedBlock == null || board.getBlockAt(row, col) != null) {
			selectBlockAt(row, col);
		}
		else {
			moveSelectedBlockTo(row, col);
		}
	}

	/**
	 * Select block at a specific location.
	 * 
	 * If there is no block at the specified location,
	 * the previously selected block remains selected.
	 * 
	 * If there is a block at the specified location,
	 * the previous selection is replaced.
	 */
	private void selectBlockAt(int row, int col) {
		Block block = board.getBlockAt(row, col);
		if (block != null) {
			selectedBlock = block;
			selectedBlockRow = row;
			selectedBlockCol = col;
		}
	}

	/**
	 * Try to move the currently selected block to a specific location.
	 * 
	 * If the move is not possible, nothing happens.
	 */
	private void moveSelectedBlockTo(int row, int col) {

		int vertDist = row - selectedBlockRow;
		int horzDist = col - selectedBlockCol;

		if (vertDist != 0 && horzDist != 0) {
			System.err.println("Invalid move!");
			return;
		}

		Direction dir = getMoveDirection(selectedBlockRow, selectedBlockCol, row, col);
		int dist = Math.abs(vertDist + horzDist);

		if (!board.moveBlock(selectedBlockRow, selectedBlockCol, dir, dist)) {
			System.err.println("Invalid move!");
		}
		else {
			selectedBlock = null;
			updateUI();
		}
		
		Block block = board.getBlockAt(targetRow, numCols-1);
		if (block instanceof TargetBlock) {
			JOptionPane.showMessageDialog(SimpleGUI.this, "You WIN !!!!!");
		}
	}

	/**
	 * Determines the direction of a move based on
	 * the starting location and the destination.
	 *  
	 * @return <code>null</code> if both the horizontal distance
	 * 	       and the vertical distance are not zero. 
	 */
	private Direction getMoveDirection(int startRow, int startCol, int destRow, int destCol) {
		int vertDist = destRow - startRow;
		int horzDist = destCol - startCol;
		if (vertDist < 0)
			return Direction.UP;
		if (vertDist > 0)
			return Direction.DOWN;
		if (horzDist < 0)
			return Direction.LEFT;
		if (horzDist > 0)
			return Direction.RIGHT;
		return null;
	}
}
