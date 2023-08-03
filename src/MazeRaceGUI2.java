
//imports 
import java.awt.GridLayout; //with this import, we do not need to set bounds 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class MazeRaceGUI2 extends JFrame implements ActionListener, KeyListener {
	
	// constants to represent fixed variables used in the games
		private final int CELL_SIZE = 25;
		private final int NUM_CELLS_WIDTH = 27;
		private final int NUM_CELLS_HEIGHT = 27;
		private final int NUM_COINS = 10;
		//private final int NUM_PORTALS = 2; 

		// constants of all images needed for the application
		private final ImageIcon WALL = new ImageIcon("images/bikini floor.png");
		private final ImageIcon OUT_OF_BOUNDS = new ImageIcon("images/bikini sky.png");
		private final ImageIcon PATH = new ImageIcon("images/grey square.png");
		private final ImageIcon COIN = new ImageIcon("images/gold coin.gif");
		private final ImageIcon MARIO[] = { new ImageIcon("images/mario0.gif"), new ImageIcon("images/mario1.gif"),
				new ImageIcon("images/mario2.gif"), new ImageIcon("images/mario3.gif") };
		private final ImageIcon TROPHY = new ImageIcon("images/trophy.png");
		private final ImageIcon PAUSE = new ImageIcon("images/pause.png");
		private final ImageIcon PORTAL = new ImageIcon("images/portal.jpg"); 

		//
		private Clip background;
		private Clip victory;

		// create player and set initial position
		private Player player = new Player(MARIO[1]);

		// creates mazePanel and maze of 2D array made up of cells
		private JPanel mazePanel = new JPanel();
		private Cell[][] maze = new Cell[NUM_CELLS_WIDTH][NUM_CELLS_HEIGHT];

		// creates variables that keep track of number of coins collected and time
		// passed
		private int numCoinsCollected = 0;
		private double time = 0.00;
	
		// create the score board and time and score variables to attach to score board
		private JPanel scoreboardPanel = new JPanel();
		private Timer gameTimer = new Timer(1, this);
		private JLabel scoreLabel = new JLabel("0");
		private JLabel timerLabel = new JLabel("0.00");
		private JLabel highScoreLabel = new JLabel(TROPHY);
		private JButton pauseButton = new JButton(PAUSE);
		
	public MazeRaceGUI2() {

		scoreboardPanelSetup();

		mazePanelSetup();

		frameSetup();
	}


	private void scoreboardPanelSetup() {
		
		// locate the position and set the layout to null
				scoreboardPanel.setBounds(0, 0, CELL_SIZE * NUM_CELLS_WIDTH, 30);
				scoreboardPanel.setLayout(null);

				// so timer could be located in the middle and below score label
				scoreLabel.setBounds(36, 0, 100, 25);
				timerLabel.setBounds(209, 0, 100, 25);
				highScoreLabel.setBounds(397, 0, 100, 25);

				//
				scoreLabel.setIcon(COIN);
				highScoreLabel.setText(String.format("%.2f", MazeRaceTest.highScore));

//				// Mainmenu
//				pauseButton.addActionListener(menubar);
				pauseButton.setBounds(593, 3, 42, 25);
				pauseButton.setFocusable(false);

				// add to frame
				scoreboardPanel.add(pauseButton);
				scoreboardPanel.add(scoreLabel);
				scoreboardPanel.add(timerLabel);
				scoreboardPanel.add(highScoreLabel);
		
	}

	private void mazePanelSetup() {
		
		// sets position, size and layout of maze
				mazePanel.setBounds(0, scoreboardPanel.getHeight(), CELL_SIZE * NUM_CELLS_WIDTH, CELL_SIZE * NUM_CELLS_HEIGHT);
				mazePanel.setLayout(new GridLayout(NUM_CELLS_WIDTH, NUM_CELLS_HEIGHT));

				// helper methods
				loadMaze();

				placeCoins();

				placePlayer();
		
	}

	private void loadMaze() {
		
		// creates variables that keep track of the index of each element
				int row = 0;
				char[] line;

				// tries to read file
				try {

					// saves file as Scanner
					Scanner inputFile = new Scanner(new File("maze.txt"));

					// loops through each element in the file
					while (inputFile.hasNext()) {

						// reads one line from file and converts it to character
						line = inputFile.nextLine().toCharArray();

						// convert lines to pictures
						for (int column = 0; column < line.length; column++)
							fillCell(line[column], row, column);

						// increments row variable to keep track of current row
						row++;

					}

					// closes file once it loops through each element
					inputFile.close();

					// catches file errors, if it's missing
				} catch (FileNotFoundException error) {

					System.out.println(error);
				}

		
	}

	private void fillCell(char c, int row, int column) {
		
		// fill 2D maze array with Cell object
				maze[row][column] = new Cell(row, column);

				// sets icon depending on whatever the character is
				if (c == 'W')
					maze[row][column].setIcon(WALL);

				else if (c == 'X')
					maze[row][column].setIcon(OUT_OF_BOUNDS);

				else if (c == '.')
					maze[row][column].setIcon(PATH);

				// adds icon to mazePanel
				mazePanel.add(maze[row][column]);

		
	}

	private void placeCoins() {
		
		// finds empty cells and sets picture on it
				for (int coin = 1; coin <= NUM_COINS; coin++) {

					Cell cell = findEmptyCell();
					maze[cell.getRow()][cell.getColumn()].setIcon(COIN);

				}

		
	}

	private void placePlayer() {
		
		Cell cell = findEmptyCell();

		// get position of empty cells
		player.setRow(cell.getRow());
		player.setColumn(cell.getColumn());

		// set picture of player on cell
		maze[cell.getRow()][cell.getColumn()].setIcon(player.getIcon());

		
	}

	private Cell findEmptyCell() {
		// creates new Cell
		Cell cell = new Cell();

		// try to find an empty cell, and test it to see if we are on path
		do {

			cell.setRow((int) (Math.random() * 24) + 2);
			cell.setColumn((int) (Math.random() * 24) + 2);

		} while (maze[cell.getRow()][cell.getColumn()].getIcon() != PATH);

		return cell;

	}

	private void frameSetup() {
		
		// sets initial frame settings
				setTitle("Samiksha's Maze Race");
				setSize(mazePanel.getWidth() + 15, mazePanel.getHeight() + 25 + scoreboardPanel.getHeight() + 10);
				setLayout(null);

				// adds mazePanel to the frame
				add(mazePanel);
				add(scoreboardPanel);
				
				// add KeyListener to frame
				addKeyListener(this); 

				// sets more frame settings
				setDefaultCloseOperation(EXIT_ON_CLOSE);
				setResizable(false);
				setVisible(true);

				// starts Game Timer
				gameTimer.start();
				
				System.out.println("hello");

		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		System.out.println("hello");
		
	}

	@Override
	public void keyPressed(KeyEvent key) {
		
				System.out.println("hi");
		// determines which key was pressed and performs that action
				if (key.getKeyCode() == KeyEvent.VK_W && maze[player.getRow() - 1][player.getColumn() + 0].getIcon() != WALL) {
					movePlayer(-1, 0, MARIO[0]);
				}

				else if (key.getKeyCode() == KeyEvent.VK_D
						&& maze[player.getRow() + 0][player.getColumn() + 1].getIcon() != WALL) { 
					movePlayer(0, 1, MARIO[1]);
				}

				else if (key.getKeyCode() == KeyEvent.VK_S
						&& maze[player.getRow() + 1][player.getColumn() + 0].getIcon() != WALL) {
					movePlayer(1, 0, MARIO[2]);
				}

				else if (key.getKeyCode() == KeyEvent.VK_A
						&& maze[player.getRow() + 0][player.getColumn() - 1].getIcon() != WALL) {
					movePlayer(0, -1, MARIO[3]);

				}
	}

	private void movePlayer(int dRow, int dColumn, ImageIcon position) {
		

		// replaces path picture on top of previous position of player
				maze[player.getRow()][player.getColumn()].setIcon(PATH);

				//checks to see if player steps on coin, if they do, their score increases
				if (maze[player.getRow() + dRow][player.getColumn() + dColumn].getIcon() == COIN) {

					numCoinsCollected++;

					scoreLabel.setText(Integer.toString(numCoinsCollected));
				
				}
				
				player.move(dRow, dColumn);
				maze[player.getRow()][player.getColumn()].setIcon(position);

				// 
				if (numCoinsCollected == NUM_COINS) {
					gameTimer.stop();
					playAgain();
					calculateHighScore();
					
					dispose();
				}
	}


	private void calculateHighScore() {
		//
		Object[] options = { "Yes", "No" }; // if next button is clicked, open second instructions page
		MazeRaceTest.playAgain = JOptionPane.showOptionDialog(this, "Would you like to play again?", "Replay",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]); // default
																											// button
																											// title
		//
		if (MazeRaceTest.playAgain == 0)
			new MazeRaceGUI2();
		
	}

	private void playAgain() {
		//
		if (MazeRaceTest.highScore == 0 || time < MazeRaceTest.highScore) {
			MazeRaceTest.highScore = time;
			JOptionPane.showMessageDialog(this,
					"WINNER! New High Score: " + String.format("%.2f", MazeRaceTest.highScore));
		}

		else
			JOptionPane.showMessageDialog(this, "WINNER!");
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		System.out.println("hi");
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// increments timer
		if (event.getSource() == gameTimer) {
			time += 0.01;
			timerLabel.setText(String.format("%.2f", time));

		}
		
	}

}
