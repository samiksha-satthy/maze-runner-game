import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Samiksha Satthy
//This class display the different available board choices 

public class MazeRaceGUIDisplayBoardChoices extends JFrame {

	// images needed for this frame
	ImageIcon board1 = new ImageIcon("images/board 1.png");
	ImageIcon board2 = new ImageIcon("images/board 2.png");
	ImageIcon board3 = new ImageIcon("images/board 3.png");
	ImageIcon board4 = new ImageIcon("images/board 4.png");
	ImageIcon horizontalLine = new ImageIcon("images/line.png");

	// GUI components needed for this frame
	JLabel title = new JLabel("CHOOSE A BOARD!");
	JButton board1Button = new JButton(board1);
	JLabel board1Label = new JLabel("1. Valentines");
	JButton board2Button = new JButton(board2);
	JLabel board2Label = new JLabel("2. Halloween");
	JButton board3Button = new JButton(board3);
	JLabel board3Label = new JLabel("3. Easter");
	JButton board4Button = new JButton(board4);
	JLabel board4Label = new JLabel("4. Christmas");
	JButton save = new JButton("SAVE");
	JButton back = new JButton("BACK TO HOME");
	JLabel separator = new JLabel(horizontalLine);

	// This ActionListener is used when the first board choice is selected
	public ActionListener board1Select = new ActionListener() {

		public void actionPerformed(ActionEvent event) {

			// assigns 1 to variable
			MazeRaceApplication.getBoardChoice = 1;

			// dialog is displayed to inform user that board selection has been saved
			JOptionPane.showMessageDialog(null, "Board Saved", "Save Confirmation", JOptionPane.INFORMATION_MESSAGE);
		}

	};

	// This ActionListener is used when the second board choice is selected
	public ActionListener board2Select = new ActionListener() {

		public void actionPerformed(ActionEvent event) {

			// assigns 2 to variable
			MazeRaceApplication.getBoardChoice = 2;

			// dialog is displayed to inform user that board selection has been saved
			JOptionPane.showMessageDialog(null, "Board Saved", "Save Confirmation", JOptionPane.INFORMATION_MESSAGE);

		}

	};

	// This ActionListener is used when the third board choice is selected
	public ActionListener board3Select = new ActionListener() {

		public void actionPerformed(ActionEvent event) {

			// assigns 3 to variable
			MazeRaceApplication.getBoardChoice = 3;

			// dialog is displayed to inform user that board selection has been saved
			JOptionPane.showMessageDialog(null, "Board Saved", "Save Confirmation", JOptionPane.INFORMATION_MESSAGE);
		}

	};

	// This ActionListener is used when the fourth board choice is selected
	public ActionListener board4Select = new ActionListener() {

		public void actionPerformed(ActionEvent event) {

			// assigns 4 to variable
			MazeRaceApplication.getBoardChoice = 4;

			// dialog is displayed to inform user that board selection has been saved
			JOptionPane.showMessageDialog(null, "Board Saved", "Save Confirmation", JOptionPane.INFORMATION_MESSAGE);
		}

	};

	// This ActionListener is used when the "Back to Home" button is selected
	public ActionListener backSelect = new ActionListener() {

		public void actionPerformed(ActionEvent event) {

			// opens the opening frame and closes this one
			dispose();
			new MazeRaceGUIOpening();

		}

	};

	// constructor method
	public MazeRaceGUIDisplayBoardChoices() {

		// title of frame settings - font, bounds
		title.setFont(new Font("Perpetua Titling MT", Font.PLAIN, 20));
		title.setBounds(183, 28, 223, 40);

		// board labels settings - font, bounds
		board1Label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		board1Label.setBounds(113, 101, 113, 20);
		board2Label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		board2Label.setBounds(351, 101, 98, 20);
		board3Label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		board3Label.setBounds(113, 311, 82, 20);
		board4Label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		board4Label.setBounds(351, 311, 87, 20);

		// board button settings - bounds, ActionListener
		board1Button.setBounds(89, 132, 150, 150);
		board1Button.addActionListener(board1Select);
		board2Button.setBounds(322, 132, 150, 150);
		board2Button.addActionListener(board2Select);
		board3Button.setBounds(89, 342, 150, 150);
		board3Button.addActionListener(board3Select);
		board4Button.setBounds(322, 342, 150, 150);
		board4Button.addActionListener(board4Select);

		// "Back to Home" settings - bounds, ActionListener
		back.setBounds(418, 521, 140, 31);
		back.addActionListener(backSelect);

		// separator line settings - bounds
		separator.setBounds(130, 61, 266, 20);

		// add components to frame
		getContentPane().add(title);
		getContentPane().add(board1Label);
		getContentPane().add(board2Label);
		getContentPane().add(board3Label);
		getContentPane().add(board4Label);
		getContentPane().add(board1Button);
		getContentPane().add(board2Button);
		getContentPane().add(board3Button);
		getContentPane().add(board4Button);
		getContentPane().add(back);
		getContentPane().add(separator);

		// frame settings - size, layout, background, relative location and visibility
		setSize(600, 600);
		getContentPane().setBackground(new Color(0xE6F3F8));
		setLayout(null);
		setLocationRelativeTo(null);
		setVisible(true);

	}

}
