
public class Score implements Comparable<Score> {

	private final int score;
	private final int userId;

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

	@Override
	public int compareTo(Score other) {
		return this.score - other.score;
	}

}
