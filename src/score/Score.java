package score;

public class Score implements Comparable<Score> {

	private final int score;
	private final int userId;
	private volatile Score nextScore;

	public Score(int score, int userId) {
		this.score = score;
		this.userId = userId;
	}

	public int getScore() {
		return score;
	}

	public int getUserId() {
		return userId;
	}
	
	public Score getNextScore() {
		return nextScore;
	}

	public void setNextScore(Score nextScore) {
		this.nextScore = nextScore;
	}

	@Override
	public int compareTo(Score other) {
		return this.score - other.score;
	}

}
