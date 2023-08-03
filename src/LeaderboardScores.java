
//template class to save Leaderboard Score 
public class LeaderboardScores {

	// fields
	private String inital;
	private double time;

	// constructor method
	public LeaderboardScores(String inital, double time) {
		super();
		this.inital = inital;
		this.time = time;
	}

	// getters and setters
	public String getInital() {
		return inital;
	}

	public void setInital(String inital) {
		this.inital = inital;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	// prints out each instance variable
	@Override
	public String toString() {
		return "LeaderboardScores [inital=" + inital + ", time=" + time + "]";
	}

}
