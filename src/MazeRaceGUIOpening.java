
//imports
import javax.swing.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Samiksha Satthy
//This class is the opening frame that appears before the game and gives the user different options 
//(theme change, leaderboard, levels of difficulty)

public class MazeRaceGUIOpening {

	// GUI components needed for this class
	private JFrame frame = new JFrame();
	private JLabel title = new JLabel();
	private JButton beginnerButton = new JButton();
	private JButton expertButton = new JButton();
	private JButton theme = new JButton();
	private JTextArea initals = new JTextArea();
	private JLabel enter = new JLabel("Enter Initals: ");
	private JButton leaderboardButtonBeginner = new JButton();
	private JButton leaderboardButtonExpert = new JButton();

	// This ActionListener opens game frame and changes variables to accommodate
	// beginner level
	public ActionListener beginnerLevel = new ActionListener() {

		public void actionPerformed(ActionEvent event) {

			// check if initals text area is empty
			if (initals.getText().isEmpty())

				// reprompt user if it is empty
				JOptionPane.showMessageDialog(frame, "Please Type in your initals.", "Missing Information",
						JOptionPane.ERROR_MESSAGE);

			else {

				// otherwise, assign variable with user's initals
				MazeRaceApplication.initialsPlayer = initals.getText();

				// disable enemy, assign number of coins to 10 and number of portals to 2
				MazeRaceApplication.levelChoice = false;
				MazeRaceApplication.numCoins = 10;
				MazeRaceApplication.numPortals = 2;

				// open game frame and closes this current one
				frame.dispose();
				new MazeRaceGUI();

			}

		}
	};

	// This ActionListener opens game frame and changes variables to accommodate
	// expert level
	public ActionListener expertLevel = new ActionListener() {

		public void actionPerformed(ActionEvent event) {

			// check if initals text area is empty
			if (initals.getText().isEmpty())

				// reprompt user if it is empty
				JOptionPane.showMessageDialog(frame, "Please Type in your initals.", "Missing Information",
						JOptionPane.ERROR_MESSAGE);

			else {

				// otherwise, assign variable with user's initals
				MazeRaceApplication.initialsPlayer = initals.getText();

				// enable enemy, assign number of coins to 20 and number of portals to 1
				MazeRaceApplication.levelChoice = true;
				MazeRaceApplication.numCoins = 20;
				MazeRaceApplication.numPortals = 1;
				initals.setEditable(false);

				// open game frame and closes this current one
				frame.dispose();
				new MazeRaceGUI();

			}

		}

	};

	// This ActionListener is used when the user clicks the "Change Theme" button
	public ActionListener boardTheme = new ActionListener() {

		public void actionPerformed(ActionEvent event) {

			// closes this frame and opens the board choices frame
			frame.dispose();
			new MazeRaceGUIDisplayBoardChoices();

		}

	};

	// This ActionListener is used when the "Leaderboard - Beginner" is pressed
	public ActionListener leaderboardBeginner = new ActionListener() {

		public void actionPerformed(ActionEvent event) {

			// assigns variable text to access according text file and opens the display
			// leaderboard frame
			MazeRaceApplication.leaderboardChoice = "highscoresBeginner";
			new MazeRaceGUIDisplayLeaderboard();

		}

	};

	// This ActionListener is used when the "Leaderboard - Expert" is pressed
	public ActionListener leaderboardExpert = new ActionListener() {

		public void actionPerformed(ActionEvent event) {

			// assigns variable text to access according text file and opens the display
			// leaderboard frame
			MazeRaceApplication.leaderboardChoice = "highscoresExpert";
			new MazeRaceGUIDisplayLeaderboard();

		}

	};

	// constructor method
	public MazeRaceGUIOpening() {

		// title settings - bounds, font, text
		title.setBounds(120, 25, 300, 90);
		title.setFont(new Font("Engravers MT", Font.ITALIC, 18));
		title.setText("MazeRunner");

		// Beginner Button settings - bounds, text, ActionListener
		beginnerButton.setBounds(153, 102, 112, 20);
		beginnerButton.setText("Beginner");
		beginnerButton.addActionListener(beginnerLevel);

		// Expert Button settings - bounds, text, ActionListener
		expertButton.setBounds(153, 127, 112, 20);
		expertButton.setText("Expert");
		expertButton.addActionListener(expertLevel);

		// Change Theme settings - bounds, text, ActionListener
		theme.setBounds(138, 152, 153, 20);
		theme.setText("Change Theme");
		theme.addActionListener(boardTheme);

		// Leadeboard - Beginner settings - bounds, text, ActionListener
		leaderboardButtonBeginner.setBounds(130, 177, 174, 20);
		leaderboardButtonBeginner.setText("Leaderboard - Beginner");
		leaderboardButtonBeginner.addActionListener(leaderboardBeginner);

		// Leaderboard - Expert settings - bounds, text, ActionListener
		leaderboardButtonExpert.setBounds(130, 203, 174, 20);
		leaderboardButtonExpert.setText("Leaderboard - Expert");
		leaderboardButtonExpert.addActionListener(leaderboardExpert);

		// Set bounds of Label that prompts user to enter initals
		enter.setBounds(115, 245, 83, 20);

		// Set bounds of textArea where user can type in their initals
		initals.setBounds(199, 243, 128, 20);

		// add everything to the frame
		frame.getContentPane().add(title);
		frame.getContentPane().add(beginnerButton);
		frame.getContentPane().add(expertButton);
		frame.getContentPane().add(theme);
		frame.getContentPane().add(leaderboardButtonBeginner);
		frame.getContentPane().add(leaderboardButtonExpert);
		frame.getContentPane().add(enter);
		frame.getContentPane().add(initals);

		// frame settings - size, layout, background, close, visibility
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(new Color(0xE6F3F8));
		frame.setSize(459, 311);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
