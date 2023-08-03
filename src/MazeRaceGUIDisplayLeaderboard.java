
//imports
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import javax.swing.*;
import java.awt.Font;
import java.awt.Color;

//Samiksha Satthy
//This class displays both the expert and beginner leaderboard based on what button the user picked 

public class MazeRaceGUIDisplayLeaderboard extends JFrame {

	// images needed in frame
	ImageIcon LEADERBOARD_TEXT_BACK = new ImageIcon("images/leaderboard0.png");
	ImageIcon LEADERBOARD_RANK_BACK = new ImageIcon("images/leaderboardRankBack.png");

	// GUI component arrays needed to make frame
	public JLabel[] ranksLabel = new JLabel[5];
	public JLabel[] initialsLabel = new JLabel[5];
	public JLabel[] timeLabel = new JLabel[5];
	public JLabel[] leaderboardTextBack = new JLabel[5];

	// GUI variables needed to make frame
	public JLabel rankTitle = new JLabel("Rank");
	public JLabel initialTitle = new JLabel("Initials");
	public JLabel timeTitle = new JLabel("Time");
	public JLabel leaderboardHeader = new JLabel();

	// constructor method
	public MazeRaceGUIDisplayLeaderboard() {

		fillLeaderboard();

		createAddComponents();
	}

	// This method fills the leaderboard array with the top 5 highest scores in text
	// file
	private void fillLeaderboard() {

		/*
		 * From: https://youtu.be/ScUJx4aWRi0
		 * https://javaconceptoftheday.com/how-to-sort-a-text-file-in-java/
		 * https://stackoverflow.com/questions/8996460/using-bufferedreader-to-take-
		 * input-in-java
		 */
		// tries to read file
		try {

			// assigns BufferedReader to file which contains text that was saved when the
			// user clicked the according leaderboard button
			BufferedReader reader = new BufferedReader(new FileReader(MazeRaceApplication.leaderboardChoice + ".txt"));

			// create ArrayList to keep track of highscores in text file
			ArrayList<LeaderboardScores> lines = new ArrayList<LeaderboardScores>();

			// BufferedReader reads each line in the document and saves value to String data
			// type
			String currentLine = reader.readLine();

			// loop until there are no more lines in file
			while (currentLine != null) {

				// high score is split when space is found in line and stored in String array
				String[] highScore = currentLine.split(" ");

				// saves inital on current line to inital variable
				String inital = highScore[0];

				// saves time on current line to time variable
				double time = Double.valueOf(highScore[1]);

				// add both inital and time on line to leaderboard scores ListArray
				lines.add(new LeaderboardScores(inital, time));

				// Buffered Reader reads next line in file
				currentLine = reader.readLine();
			}

			// sort ArrayList in order of increasing time
			Collections.sort(lines, Comparator.comparing(LeaderboardScores::getTime));

			// assigns BufferedWriter to file which contains text that was saved when user
			// clicked the according leaderboard button
			BufferedWriter writer = new BufferedWriter(
					new FileWriter(MazeRaceApplication.leaderboardChoice + "Sorted.txt"));

			// loops through each element in the leaderboard ArrayList
			for (LeaderboardScores leaderboard : lines) {

				// write the initals of that element in ArrayList to text file
				writer.write(leaderboard.getInital());

				// write time of that element in ArrayList to text file
				writer.write(" " + leaderboard.getTime());

				// add new line to text file to read in next element of ArrayList
				writer.newLine();
			}

			// close BufferedReader
			reader.close();

			// close BufferedWriter
			writer.close();

		}

		// catches any error with the file and prints out corresponding text
		catch (IOException e) {
			System.out.println("File Error");
		}

		/*
		 * From: https://youtu.be/ScUJx4aWRi0
		 * https://javaconceptoftheday.com/how-to-sort-a-text-file-in-java/
		 * https://stackoverflow.com/questions/8996460/using-bufferedreader-to-take-
		 * input-in-java
		 */
		// tries to read the Sorted file into the application
		try {

			// stores file as Scanner Object
			Scanner inputFile = new Scanner(new File(MazeRaceApplication.leaderboardChoice + "Sorted.txt"));

			// loops through each element in file
			for (int index = 0; index < MazeRaceApplication.leaderboardArray.length; index++) {

				// reads one line from file and converts it to corresponding type in Laptop
				// Object Class
				String inital = inputFile.next();
				double time = inputFile.nextDouble();

				// fills the laptop array with elements
				MazeRaceApplication.leaderboardArray[index] = new LeaderboardScores(inital, time);

			}

			// closes file
			inputFile.close();

			// catches any errors with the file and prints out corresponding text
		} catch (FileNotFoundException e) {

			System.out.println("File Error");
		}

	}

	// This method creates all the GUI components, adds their settings and adds them
	// to the frame
	private void createAddComponents() {

		// create each rank Label in GUI component array
		ranksLabel[0] = new JLabel("1. ");
		ranksLabel[1] = new JLabel("2. ");
		ranksLabel[2] = new JLabel("3. ");
		ranksLabel[3] = new JLabel("4. ");
		ranksLabel[4] = new JLabel("5. ");

		// loops through each index in the initials GUI component array and sets text to
		// each initial in
		// top 5 scores
		for (int index = 0; index < initialsLabel.length; index++) {
			initialsLabel[index] = new JLabel(MazeRaceApplication.leaderboardArray[index].getInital());
		}

		// loops through each index in the time GUI component array and sets text to
		// each time in
		// top 5 scores
		for (int index = 0; index < timeLabel.length; index++) {
			timeLabel[index] = new JLabel("" + MazeRaceApplication.leaderboardArray[index].getTime() + "s");
		}

		// loops through each index in the background text image in GUI component array
		// and assign it with the background image (aesthetic purposes)
		for (int index = 0; index < leaderboardTextBack.length; index++)
			leaderboardTextBack[index] = new JLabel(LEADERBOARD_TEXT_BACK);

		// checks if user selected the "Leaderboard - Expert" button
		if (MazeRaceApplication.leaderboardChoice.contains("Expert")) {
			// sets according text and bounds
			leaderboardHeader.setText("LEADERBOARD - EXPERT");
			leaderboardHeader.setBounds(90, 11, 409, 47);
		}

		else {
			// else sets according text and bounds for beginner leaderboard
			leaderboardHeader.setText("LEADERBOARD - BEGINNER");
			leaderboardHeader.setBounds(80, 11, 409, 47);
		}

		// sets font and adds header to frame
		leaderboardHeader.setFont(new Font("Kristen ITC", Font.PLAIN, 23));
		getContentPane().add(leaderboardHeader);

		// sets each Rank Label in ranksLabel GUI component array - bounds, color, icon
		// and adds it to frame
		for (int indexRanks = 0; indexRanks < ranksLabel.length; indexRanks++) {

			getContentPane().add(ranksLabel[indexRanks]);
			ranksLabel[indexRanks].setBounds(50, 110 * (indexRanks + 1), 60, 60);
			ranksLabel[indexRanks].setBackground(new Color(0x53CA36));
			ranksLabel[indexRanks].setIcon(LEADERBOARD_RANK_BACK);
			ranksLabel[indexRanks].setHorizontalTextPosition(JLabel.CENTER);

		}

		// sets bounds of each Initial Label in initialsLabel GUI component array and
		// adds it to frame
		for (int indexInitals = 0; indexInitals < initialsLabel.length; indexInitals++) {

			getContentPane().add(initialsLabel[indexInitals]);
			initialsLabel[indexInitals].setBounds(235, 110 * (indexInitals + 1), 60, 60);
		}

		// sets bounds of each Times Label in timeLabel GUI component array and adds it
		// to frame
		for (int indexTimes = 0; indexTimes < timeLabel.length; indexTimes++) {

			getContentPane().add(timeLabel[indexTimes]);
			timeLabel[indexTimes].setBounds(425, 110 * (indexTimes + 1), 60, 60);
		}

		// sets bounds of each text background in leaderboardTextBack GUI component
		// array and adds it to frame
		for (int index = 0; index < leaderboardTextBack.length; index++) {

			leaderboardTextBack[index].setBounds(120, 110 * (index + 1), 375, 60);
			getContentPane().add(leaderboardTextBack[index]);
		}

		// rank title settings - font, bounds and adds it to the frame
		rankTitle.setFont(new Font("Agency FB", Font.BOLD, 20));
		rankTitle.setBounds(34, 66, 111, 24);
		getContentPane().add(rankTitle);

		// initials title settings - font, bounds and adds it to frame
		initialTitle.setFont(new Font("Agency FB", Font.BOLD, 20));
		initialTitle.setBounds(225, 66, 111, 24);
		getContentPane().add(initialTitle);

		// time title settings - font, bounds and adds it to the frame
		timeTitle.setFont(new Font("Agency FB", Font.BOLD, 20));
		timeTitle.setBounds(425, 66, 111, 24);
		getContentPane().add(timeTitle);

		// frame settings - size, layout, location and visibility
		// leaderboardHeader.setBackground(new Color(253, 253, 253));
		setSize(550, 700);
		setLayout(null);
		setLocationRelativeTo(null);
		setVisible(true);

	}

}
