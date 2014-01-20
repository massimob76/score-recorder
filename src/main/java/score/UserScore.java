package score;

public class UserScore implements Comparable<UserScore> {

	private final int score;
	private final int userId;
	private volatile UserScore nextScore;

	public UserScore(int userId, int score) {
		this.userId = userId;
		this.score = score;
	}

	public int getScore() {
		return score;
	}

	public int getUserId() {
		return userId;
	}
	
	public UserScore getNextScore() {
		return nextScore;
	}

	public void setNextScore(UserScore nextScore) {
		this.nextScore = nextScore;
	}

	@Override
	public int compareTo(UserScore other) {
		int scoreDiff = this.score - other.score;
		if (scoreDiff != 0) {
			return scoreDiff;
		} else {
			return this.userId - other.userId;
		}
	}

}
