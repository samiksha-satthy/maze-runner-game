
//imports 
import java.awt.GridLayout; //with this import, we do not need to set bounds 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

//Samiksha Satthy 
//This class creates the actual maze game, and modifies different variables based on the user's picked level of difficulty

public class MazeRaceGUI extends JFrame implements KeyListener {

	private JFrame frame = new JFrame();

	// constants to represent fixed variables used in the games
	private final int CELL_SIZE = 25;
	private final int NUM_CELLS_WIDTH = 27;
	private final int NUM_CELLS_HEIGHT = 27;

	// constants of all images needed for the application
	private final ImageIcon PATH = new ImageIcon("images/grey square.png");
	private final ImageIcon COIN = new ImageIcon("images/gold coin.gif");
	private final ImageIcon MARIO[] = { new ImageIcon("images/mario0.gif"), new ImageIcon("images/mario1.gif"),
			new ImageIcon("images/mario2.gif"), new ImageIcon("images/mario3.gif") };
	private final ImageIcon ENEMY[] = { new ImageIcon("images/sonic0.gif"), new ImageIcon("images/sonic1.gif"),
			new ImageIcon("images/sonic2.gif"), new ImageIcon("images/sonic3.gif") };
	private final ImageIcon TROPHY = new ImageIcon("images/trophy.png");
	private final ImageIcon PAUSE = new ImageIcon("images/pause.png");
	private final ImageIcon PORTAL = new ImageIcon("images/portal.jpg");

	// variables to represent the wall and out of bounds images
	private static ImageIcon wall;
	private static ImageIcon outOfBounds;

	// variables to keep track of any coin or portal images that have been replaced
	// by enemy image
	private boolean coinReplace = false;
	private boolean portalReplace = false;

	// variables to represent different music and sound effects in game
	private static Clip background;
	private static Clip victory;
	private static Clip coinCollection;
	private static Clip death;

	// create enemy and set initial image position
	private Character enemy = new Character(ENEMY[1]);

	// create player and set initial image position
	private Character player = new Character(MARIO[1]);

	// creates mazePanel and maze of 2D array made up of cells
	private JPanel mazePanel = new JPanel();
	private Cell[][] maze = new Cell[NUM_CELLS_WIDTH][NUM_CELLS_HEIGHT];

	// creates variables that keep track of number of coins collected and time
	// passed
	private int numCoinsCollected = 0;
	private double time = 0.00;

	// variables that generate random directions for the ghost
	private int alternateDirection;
	private int previousChoice = 0;

	// This ActionListener keeps track of each hundreth of a second after the game
	// has started
	public ActionListener timeIncrement = new ActionListener() {

		public void actionPerformed(ActionEvent event) {

			// increments timer
			if (event.getSource() == gameTimer) {
				time += 0.01;

				// sets time label as the new time
				timerLabel.setText(String.format("%.2f", time));

				// create variable that stores time module 0.30 as two decimal places
				BigDecimal bd = new BigDecimal(time % 0.2).setScale(1, RoundingMode.HALF_UP);
				double timeRounded = bd.doubleValue();

				// check if the time variable rounded to two decimal places is equal to 0 and if
				// user selected 'expert' level
				if (timeRounded == 0 && MazeRaceApplication.levelChoice) {

					// check if the player and enemy are on the same row
					if (player.getRow() == enemy.getRow()) {

						// and if the player is situated more left than the enemy, move the enemy left
						if (player.getColumn() > enemy.getColumn()
								&& maze[enemy.getRow() + 0][enemy.getColumn() + 1].getIcon() != wall)
							moveCharacter(0, 1, ENEMY[3], enemy);

						// and if the player is situated more right than the enemy, move the enemy right
						else if (player.getColumn() < enemy.getColumn()
								&& maze[enemy.getRow() + 0][enemy.getColumn() - 1].getIcon() != wall)
							moveCharacter(0, -1, ENEMY[1], enemy);

						// and if the player if player and enemy are on the same column, declare game
						// over
						else if (player.getColumn() == enemy.getColumn()) {

							// play death music, stop background
							background.stop();
							playDeathMusic();

							// display dialog informing user that game is over
							JOptionPane.showMessageDialog(frame, "GAME OVER! YOU HAVE BEEN CAUGHT BY THE ENEMY",
									"Game Over", JOptionPane.ERROR_MESSAGE);

							// stop timer
							gameTimer.stop();

							playAgain();
						}

					}

					// check if player and enemy are on same column
					else if (player.getColumn() == enemy.getColumn()) {

						// if the player is situated more down than the enemy, move enemy down
						if (player.getRow() > enemy.getRow()
								&& maze[enemy.getRow() + 1][enemy.getColumn() + 0].getIcon() != wall)
							moveCharacter(1, 0, ENEMY[2], enemy);

						// if player is situated more up than the enemy, move enemy up
						else if (player.getRow() < enemy.getRow()
								&& maze[enemy.getRow() - 1][enemy.getColumn() + 0].getIcon() != wall)
							moveCharacter(-1, 0, ENEMY[0], enemy);
					}

					// checks if player and enemy aren't in the same column nor row
					else {

						// generate random number between 1-4
						alternateDirection = (int) (1 + (Math.random() * 4));

						// checks if the value of randomly generated value and that the previous choice
						// was not the exact opposite option to refrain enemy from moving up or down and
						// staying side to side
						if (alternateDirection == 1 && previousChoice != 2) {

							// checks if move does not meet with the wall, then moves enemy
							if (maze[enemy.getRow() + 0][enemy.getColumn() + 1].getIcon() != wall)
								moveCharacter(0, 1, ENEMY[3], enemy);

							// saves this direction choice to compare
							previousChoice = alternateDirection;

						}

						else if (alternateDirection == 2 && previousChoice != 1) {

							if (maze[enemy.getRow() + 0][enemy.getColumn() - 1].getIcon() != wall)
								moveCharacter(0, -1, ENEMY[1], enemy);

							previousChoice = alternateDirection;

						}

						else if (alternateDirection == 3 && previousChoice != 4) {

							if (maze[enemy.getRow() + 1][enemy.getColumn() + 0].getIcon() != wall)
								moveCharacter(1, 0, ENEMY[2], enemy);

							previousChoice = alternateDirection;

						}

						else if (alternateDirection == 4 && previousChoice != 3) {

							if (maze[enemy.getRow() - 1][enemy.getColumn() + 0].getIcon() != wall)
								moveCharacter(-1, 0, ENEMY[0], enemy);

							previousChoice = alternateDirection;
						}
					}
				}
			}

		}

		private void playDeathMusic() {

			// From: https://stackoverflow.com/questions/6045384/playing-mp3-and-wav-in-java
			// tries to run the sound track
			try {

				// saves music file to AudioInputStrem
				AudioInputStream audioInputStream = AudioSystem
						.getAudioInputStream(new File("music/death.wav").getAbsoluteFile());

				// gets the clip, starts playing it and loops it
				death = AudioSystem.getClip();
				death.open(audioInputStream);
				death.start();
				death.loop(Clip.LOOP_CONTINUOUSLY);

				// catches any errors found witin the sound file
			} catch (Exception ex) {
				System.out.println("Error with playing sound.");
				ex.printStackTrace();
			}

		}

	};

	// This ActionListener opens the menu when user clicks on pause button
	public ActionListener menubar = new ActionListener() {

		public void actionPerformed(ActionEvent event) {

			// stop both music and timer when menubar is opened
			gameTimer.stop();
			background.stop();

			// opens dialog and presents user with many options
			Object[] options = { "Continue", "Quit", "Restart" };
			int button = JOptionPane.showOptionDialog(frame,
					"The game is currently paused. Click what you want to do next.", "Paused",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, // do not use a custom Icon
					options, // the titles of buttons
					options[0]); // default button title

			// if continue button is chosen, resume game
			if (button == 0) {

				// start music and timer again
				gameTimer.start();
				background.start();

				// close JDialog
				JOptionPane.getRootFrame().dispose();

			}

			// if quit button is chosen, bring user back to opening frame
			else if (button == 1) {

				// close dialog and this frame
				JOptionPane.getRootFrame().dispose();
				frame.dispose();

				// open opening frame
				new MazeRaceGUIOpening();

			}

			// if restart button is chose, restart all the settings of the game
			else if (button == 2) {

				// dispose this frame and open a new one
				frame.dispose();
				new MazeRaceGUI();
			}

		}

	};

	// create the score board and time and score variables to attach to score board
	private JPanel scoreboardPanel = new JPanel();
	private Timer gameTimer = new Timer(1, timeIncrement);
	private JLabel scoreLabel = new JLabel("0");
	private JLabel timerLabel = new JLabel("0.00");
	private JLabel highScoreLabel = new JLabel(TROPHY);
	private JButton pauseButton = new JButton(PAUSE);

	// constructor method
	public MazeRaceGUI() {

		setBoardTheme();

		playMusic();

		scoreboardPanelSetup();

		mazePanelSetup();

		frameSetup();

	}

	// This method sets the board theme based on the user's choice
	private void setBoardTheme() {

		// sets the wall and out of bounds with the image of user's selection
		wall = new ImageIcon("images/b" + MazeRaceApplication.getBoardChoice + " wall.png");
		outOfBounds = new ImageIcon("images/b" + MazeRaceApplication.getBoardChoice + " oob.png");

	}

	// This method starts to play the background music
	private void playMusic() {

		// From: https://stackoverflow.com/questions/6045384/playing-mp3-and-wav-in-java
		// tries to run the sound track
		try {

			// saves music file to AudioInputStrem
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(new File("music/background theme.wav").getAbsoluteFile());

			// gets the clip, starts playing it and loops it
			background = AudioSystem.getClip();
			background.open(audioInputStream);
			background.start();
			background.loop(Clip.LOOP_CONTINUOUSLY);

			// catches any errors found witin the sound file
		} catch (Exception ex) {
			System.out.println("Error with playing sound.");
			ex.printStackTrace();
		}

	}

	// This method sets up scoreboard panel to hold time and number of coins
	// collected
	private void scoreboardPanelSetup() {

		// locate the position and set the layout to null
		scoreboardPanel.setBounds(0, 0, CELL_SIZE * NUM_CELLS_WIDTH, 30);
		scoreboardPanel.setLayout(null);

		// so timer could be located in the middle and below score label
		scoreLabel.setBounds(36, 0, 100, 25);
		timerLabel.setBounds(209, 0, 100, 25);
		highScoreLabel.setBounds(397, 0, 100, 25);
		pauseButton.setBounds(593, 3, 42, 25);

		// add images beside JLabels
		scoreLabel.setIcon(COIN);

		// set highscore label text as the value of public high score variable depdning
		// on level chosen
		if (MazeRaceApplication.levelChoice)
			highScoreLabel.setText(String.format("%.2f", MazeRaceApplication.highScoreExpert));

		else
			highScoreLabel.setText(String.format("%.2f", MazeRaceApplication.highScoreBeginner));

		// Mainmenu
		pauseButton.addActionListener(menubar);
		pauseButton.setFocusable(false);

		// add components to frame
		scoreboardPanel.add(pauseButton);
		scoreboardPanel.add(scoreLabel);
		scoreboardPanel.add(timerLabel);
		scoreboardPanel.add(highScoreLabel);

	}

	// this method sets up the size of the maze
	private void mazePanelSetup() {

		// sets position, size and layout of maze
		mazePanel.setBounds(0, scoreboardPanel.getHeight(), CELL_SIZE * NUM_CELLS_WIDTH, CELL_SIZE * NUM_CELLS_HEIGHT);
		mazePanel.setLayout(new GridLayout(NUM_CELLS_WIDTH, NUM_CELLS_HEIGHT));

		// helper methods
		loadMaze();

		placeCoins();

		placePlayer();

		placePortal();

		// checks if user selected expert level because enemy is only for expert level
		if (MazeRaceApplication.levelChoice)
			placeEnemy();

	}

	// this method loads the maze from file into application
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

	// this method fills the characters by taking in the position of the empty cell
	private void fillCell(char character, int row, int column) {

		// fill 2D maze array with Cell object
		maze[row][column] = new Cell(row, column);

		// sets icon depending on whatever the character is
		if (character == 'W')
			maze[row][column].setIcon(wall);

		else if (character == 'X')
			maze[row][column].setIcon(outOfBounds);

		else if (character == '.')
			maze[row][column].setIcon(PATH);

		// adds icon to mazePanel
		mazePanel.add(maze[row][column]);

	}

	// this method places the coins around the path on empty cells
	private void placeCoins() {

		// finds empty cells and sets picture on it
		for (int coin = 1; coin <= MazeRaceApplication.numCoins; coin++) {

			// finds an empty cell not occupied by anything else
			Cell cell = findEmptyCell();

			// sets picture of coin on empty cell
			maze[cell.getRow()][cell.getColumn()].setIcon(COIN);

		}

	}

	// this method places the player on path on an empty cell
	private void placePlayer() {

		Cell cell = findEmptyCell();

		// get position of empty cells
		player.setRow(cell.getRow());
		player.setColumn(cell.getColumn());

		// set picture of player on cell
		maze[cell.getRow()][cell.getColumn()].setIcon(player.getIcon());

	}

	// this method places a portal on path on an empty cell
	private void placePortal() {

		// finds empty cells and sets picture on it
		for (int portal = 1; portal <= MazeRaceApplication.numPortals; portal++) {

			// call method to find empty cell not occupied by anything else
			Cell cell = findEmptyCell();

			// set a picture of portal on the empty cell
			maze[cell.getRow()][cell.getColumn()].setIcon(PORTAL);

		}

	}

	// this method places the enemy
	private void placeEnemy() {

		// creates new variable from Cell class
		Cell cell;

		// do while loop to find an empty cell on path
		do {

			cell = findEmptyCell();

			// until enemy is placed on empty cell that is not in the same row or column as
			// player, to ensure fair start to user
		} while (cell.getRow() == player.getRow() && cell.getColumn() == player.getColumn());

		// get position of empty cells
		enemy.setRow(cell.getRow());
		enemy.setColumn(cell.getColumn());

		// set picture of player on cell
		maze[cell.getRow()][cell.getColumn()].setIcon(enemy.getIcon());

	}

	// locates a cell that doesn't have anything else on it and is on a path
	private Cell findEmptyCell() {

		// creates new Cell
		Cell cell = new Cell();

		// try to find a random empty cell
		do {

			// set row and column of cell once random number is generated
			cell.setRow((int) (Math.random() * 24) + 2);
			cell.setColumn((int) (Math.random() * 24) + 2);

			// test it to see if we are on path
		} while (maze[cell.getRow()][cell.getColumn()].getIcon() != PATH);

		// return cell
		return cell;

	}

	// this method sets up the frame
	private void frameSetup() {

		// sets initial frame settings
		frame.setTitle("Samiksha's Maze Race");
		frame.setSize(mazePanel.getWidth() + 15, mazePanel.getHeight() + 25 + scoreboardPanel.getHeight() + 10);
		frame.setLayout(null);

		// adds mazePanel to the frame
		frame.add(mazePanel);
		frame.add(scoreboardPanel);

		// add KeyListener to frame
		frame.addKeyListener(this);

		// sets more frame settings
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		// starts Game Timer
		gameTimer.start();

	}

	// this method gets the pressed key on the user's keyboard
	@Override
	public void keyPressed(KeyEvent key) {

		// determines which key was pressed and checks to see if that movement does not
		// make player bump into wall, if it doesn't then move the player with that
		// movement and set a new Image position as well as define that it is the player
		// variable from the Player class
		if (key.getKeyCode() == KeyEvent.VK_UP && maze[player.getRow() - 1][player.getColumn() + 0].getIcon() != wall)
			moveCharacter(-1, 0, MARIO[0], player);

		else if (key.getKeyCode() == KeyEvent.VK_RIGHT
				&& maze[player.getRow() + 0][player.getColumn() + 1].getIcon() != wall)
			moveCharacter(0, 1, MARIO[1], player);

		else if (key.getKeyCode() == KeyEvent.VK_DOWN
				&& maze[player.getRow() + 1][player.getColumn() + 0].getIcon() != wall)
			moveCharacter(1, 0, MARIO[2], player);

		else if (key.getKeyCode() == KeyEvent.VK_LEFT
				&& maze[player.getRow() + 0][player.getColumn() - 1].getIcon() != wall)
			moveCharacter(0, -1, MARIO[3], player);

	}

	// this method moves the player/enemy
	private void moveCharacter(int dRow, int dColumn, ImageIcon position, Character character) {

		// checks to see if player steps on coin
		if (maze[character.getRow() + dRow][character.getColumn() + dColumn].getIcon() == COIN && character == player) {

			// set their previous position as path image
			maze[character.getRow()][character.getColumn()].setIcon(PATH);

			// increment number of coins variable each time coin is collected
			numCoinsCollected++;

			playCoinCollectionMusic();

			// update label on scoreboard
			scoreLabel.setText(Integer.toString(numCoinsCollected));

			// sets new row and column value of player
			character.move(dRow, dColumn);

			// sets the icon at the new location of the player
			maze[character.getRow()][character.getColumn()].setIcon(position);

		}

		// checks to see if player steps on portal
		else if (maze[character.getRow() + dRow][character.getColumn() + dColumn].getIcon() == PORTAL
				&& character == player) {

			// set their previous position as path image
			maze[character.getRow()][character.getColumn()].setIcon(PATH);

			// create new cell variable and call method
			Cell cell = findCoin();

			// set player row and column as cell row and column
			player.setRow(cell.getRow());
			player.setColumn(cell.getColumn());

			// increment coins collected variable and update label on scoreboard
			numCoinsCollected++;
			scoreLabel.setText(Integer.toString(numCoinsCollected));

			// set icon of player at the new position of player
			maze[character.getRow()][character.getColumn()].setIcon(position);

		}

		// checks to see if character is player
		else if (character == player) {

			// assigns previous position with path image
			maze[character.getRow()][character.getColumn()].setIcon(PATH);

			// sets new row and column value of player
			character.move(dRow, dColumn);

			// sets the icon at the new location of the player
			maze[character.getRow()][character.getColumn()].setIcon(position);
		}

		// checks to see if the enemy variable called method
		else if (character == enemy) {

			// checks to see if enemy replaced coin image in previous movement
			if (coinReplace) {

				// sets coin icon to same position after enemy moves
				maze[character.getRow()][character.getColumn()].setIcon(COIN);

				// assigns variable to false to ensure no extra coin images are added
				coinReplace = false;

			}

			// checks to see if enemy replaced portal image in previous movement
			else if (portalReplace) {

				// sets portal icon to same position after enemy moves
				maze[character.getRow()][character.getColumn()].setIcon(PORTAL);

				// assigns variable to false to ensure no extra portal images are added
				portalReplace = false;

			}

			else {

				// sets path image to previous position of enemy
				maze[character.getRow()][character.getColumn()].setIcon(PATH);
			}

			// sets new row and column value of enemy
			character.move(dRow, dColumn);

			// checks to see if enemy is landing on coin with next move
			if (maze[character.getRow()][character.getColumn()].getIcon() == COIN)
				coinReplace = true;

			// checks to see if enemy is landing on portal with next move
			else if (maze[character.getRow()][character.getColumn()].getIcon() == PORTAL)
				portalReplace = true;

			// sets the icon at the new location of the enemy
			maze[character.getRow()][character.getColumn()].setIcon(position);

		}

		// checks to see if the number of coins collected is equal to total number of
		// coins in program
		if (numCoinsCollected == MazeRaceApplication.numCoins) {

			// stop timer and music
			gameTimer.stop();
			background.stop();

			// call subprograms
			playVictoryTheme();

			determineHighScore();

			loadHighscore();

			playAgain();
		}

	}

	private void playCoinCollectionMusic() {

		// From: https://stackoverflow.com/questions/6045384/playing-mp3-and-wav-in-java
		// tries to run the sound track
		try {

			// saves music file to AudioInputStrem
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(new File("music/coin collection.wav").getAbsoluteFile());

			// gets the clip, starts playing it
			coinCollection = AudioSystem.getClip();
			coinCollection.open(audioInputStream);
			coinCollection.start();

			// catches any errors with the file and outputs corresponding message
		} catch (Exception ex) {
			System.out.println("Error with playing sound.");
			ex.printStackTrace();
		}

	}

	// This method finds the position of a coin and sets it as the new position of
	// player
	private Cell findCoin() {

		Cell cell = new Cell();

		// searches the entire maze
		do {

			// set row and column of cell once random number is generated
			cell.setRow((int) (Math.random() * 24) + 2);
			cell.setColumn((int) (Math.random() * 24) + 2);

			// test it to see if we are on coin
		} while (maze[cell.getRow()][cell.getColumn()].getIcon() != COIN);

		return cell;

	}

	// This method plays victory music when player gets all coins
	private void playVictoryTheme() {

		// From: https://stackoverflow.com/questions/6045384/playing-mp3-and-wav-in-java
		// tries to run the sound track
		try {

			// saves music file to AudioInputStrem
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(new File("music/victory theme.wav").getAbsoluteFile());

			// gets the clip, starts playing it and loops it
			victory = AudioSystem.getClip();
			victory.open(audioInputStream);
			victory.loop(Clip.LOOP_CONTINUOUSLY);
			victory.start();

			// catches any errors wrong with the file and prints corresponding message
		} catch (Exception ex) {
			System.out.println("Error with playing sound.");
			ex.printStackTrace();
		}

	}

	// This method determines new high score or compare user's score with high score
	private void determineHighScore() {

		// checks if game played is user's first expert game or their time is lower than
		// their
		// saved highscore
		if ((MazeRaceApplication.highScoreExpert == 0 || time < MazeRaceApplication.highScoreExpert)
				&& MazeRaceApplication.levelChoice) {

			// saves time as variable's new value
			MazeRaceApplication.highScoreExpert = time;

			// dialog appears to inform user that they have a new high score
			JOptionPane.showMessageDialog(frame,
					"WINNER! New High Score This Game: " + String.format("%.2f", MazeRaceApplication.highScoreExpert));
		}

		// checks if game played is user's first beginner game or their time is lower
		// than their
		// saved highscore
		else if ((MazeRaceApplication.highScoreBeginner == 0 || time < MazeRaceApplication.highScoreBeginner)
				&& !MazeRaceApplication.levelChoice) {

			// saves time as variable's new value
			MazeRaceApplication.highScoreBeginner = time;

			// dialog appears to inform user that they have a new high score
			JOptionPane.showMessageDialog(frame, "WINNER! New High Score This Game: "
					+ String.format("%.2f", MazeRaceApplication.highScoreBeginner));
		}

		// if user did not score a high score, general dialog appears
		else
			JOptionPane.showMessageDialog(frame, "WINNER!");

	}

	// This method loads the player's initials and their highscore this game onto
	// external text file
	private void loadHighscore() {

		/*
		 * From: https://youtu.be/ScUJx4aWRi0
		 * https://javaconceptoftheday.com/how-to-sort-a-text-file-in-java/
		 * https://stackoverflow.com/questions/8996460/using-bufferedreader-to-take-
		 * input-in-java
		 */
		// tries to write information into file
		try {

			// saves file as a bufferedwriter, and disables overwrite
			BufferedWriter highscoreWrite = null;

			// checks to see if user selected expert and saves highscore into different text
			// file
			if (MazeRaceApplication.levelChoice) {
				highscoreWrite = new BufferedWriter(new FileWriter("highscoresExpert.txt", true));
				// appends information onto new line on the text file
				highscoreWrite.append(MazeRaceApplication.initialsPlayer + " "
						+ String.format("%.2f", MazeRaceApplication.highScoreExpert));
			}

			// checks to see if user selected beginner and saves highscore into different
			// text file
			else {
				highscoreWrite = new BufferedWriter(new FileWriter("highscoresBeginner.txt", true));
				// appends information onto new line on the text file
				highscoreWrite.append(MazeRaceApplication.initialsPlayer + " "
						+ String.format("%.2f", MazeRaceApplication.highScoreBeginner));

			}

			// write next high score of new line in text file
			highscoreWrite.newLine();

			// closes buffered writer once done appending information
			highscoreWrite.close();

		}

		// catches file errors, if it's missing
		catch (Exception e) {

		}

	}

	// This method prompts the user if they would like to play another round of the
	// game
	private void playAgain() {

		// display dialog to prompt user
		Object[] options = { "Yes", "No" };
		MazeRaceApplication.playAgain = JOptionPane.showOptionDialog(frame, "Would you like to play again?", "Replay",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

		// if yes button is clicked
		if (MazeRaceApplication.playAgain == 0) {

			// checks to see if user won or lost by being killed by enemy
			if (victory != null)

				// if victory music is playing, stop it
				victory.stop();

			if (death != null)

				// if death music is playing, stop it
				death.stop();

			// dispose this frame and open a new one
			frame.dispose();
			new MazeRaceGUI();

		}

		// if no button is clicked
		else if (MazeRaceApplication.playAgain == 1) {

			// checks to see if user won or lost by being killed by enemy
			if (victory != null)

				// if victory music is playing, stop it
				victory.stop();

			if (death != null)

				// if death music is playing, stop it
				death.stop();

			// dispose this frame and open the opening frame
			frame.dispose();
			new MazeRaceGUIOpening();

		}

	}

	// Not used, Must be kept to satisfy the KeyListener interface
	@Override
	public void keyReleased(KeyEvent e) {

	}

	// Not used, Must be kept to satisfy the KeyListener interface
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
