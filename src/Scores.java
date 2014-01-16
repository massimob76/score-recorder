import java.util.HashMap;


public class Scores {
	
	private HashMap<Integer, LevelTopScores> scores = new HashMap<Integer, LevelTopScores>();

	public void post(int userId, int level, int score) {
		LevelTopScores levelTopScores = scores.get(level);
		if (levelTopScores == null) {
			levelTopScores = new LevelTopScores();
			scores.put(level, levelTopScores);
		}
		levelTopScores.record(userId, score);
	}
	
	public String retrieve(int level) {
		LevelTopScores levelTopScores = scores.get(level);
		if (levelTopScores == null) return "";
		return levelTopScores.toCSVString();
	}

}
