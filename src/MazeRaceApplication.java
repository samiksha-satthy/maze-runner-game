
//imports
import javax.swing.ImageIcon;

/* 
 * Name: Samiksha Satthy
 * 
 * Date: May 28, 2023
 * 
 * Course Code: ICS3U1-01 Mr. Fernandes
 * 
 * Title: SDP #2 - Maze Race
 * 
 * Description: MazeRace is an original game whereby the object of the game 
 * 				is to race your character around a maze attempting to collect gold coins with the help of portals 
 * 				and avoid an enemy in selected levels. The game begins by placing the character in a random 
 * 				location in a maze with a number of randomly placed gold coins and portals. 
 * 				The user then uses the keyboard to navigate through the maze in order to reach the coins. 
 * 				The game currently has two levels of difficulty: a beginner level for users who have just started playing the 
 * 				game and an expert level to avoid an enemy. The objective is to collect all the coins as fast as possible. 
 * 
 * Major Skills: GUI components, reading and writing text files, player movement via keyboard controls, conditional statements, 
 * 				 repetition statements, Array, ArrayList, Objects, Classes, Methods, Audio Clips
 * 
 * Added Features: Basic - 
 * 				   1) Get player image to Face the Proper Direction as they move
 * 				   2) Add more Accurate Timing (ex. tenths of seconds)
 * 				   3) Add a Menubar - with a number of options (File - New Game, Quit, etc.)
 * 				   4) Add a separate Opening Screen before the game starts
 * 				   5) Add a High Score label for the current game session
 * 				   6) Add Different Board Themes - user can select from different themes
 * 				   7) Add Music and Sound Effects (ex. “Cha-ching” when you get a coin)
 * 				   8) Add Doorways/Portals
 * 				   9) Add New Levels (with different difficulty, number of coins/portals, enemy)
 * 				   Advanced - 
 * 				   1) Add a Highscore with a player’s initials - save this information to and external text file; 
 * 					  shows when the game is played and can get replaced by a new higher score. Saves top 5 scores. 
 * 				   2) Add an Enemy that chases the player around the maze 
 * 
 * Areas of Concern: none
 */

//This class runs the application
public class MazeRaceApplication {

	// public array to keep track of top 5 leaderboard scores
	public static LeaderboardScores[] leaderboardArray = new LeaderboardScores[5];

	// public variables accessed by all classes in application
	public static double highScoreBeginner = 0;
	public static double highScoreExpert = 0;
	public static int playAgain;
	public static String initialsPlayer;
	public static int getBoardChoice = 0;
	public static boolean levelChoice;
	public static String leaderboardChoice;
	public static int numCoins = 10;
	public static int numPortals = 2;

	// main method - runs the program
	public static void main(String[] args) {

		// runs opening frame
		new MazeRaceGUIOpening();

	}

}
