import java.util.concurrent.ConcurrentHashMap;


public class Scores {
	
	private ConcurrentHashMap<Integer, LevelTopScores> scores = new ConcurrentHashMap<Integer, LevelTopScores>();

	public void post(int userId, int level, int score) {
		LevelTopScores levelTopScores = scores.get(level);
		if (levelTopScores == null) {
			levelTopScores = new LevelTopScores();
			LevelTopScores previousTopScores = scores.putIfAbsent(level, levelTopScores);
			if (previousTopScores != null) levelTopScores = previousTopScores;
		}
		levelTopScores.record(userId, score);
	}
	
	public String retrieve(int level) {
		LevelTopScores levelTopScores = scores.get(level);
		if (levelTopScores == null) return "";
		return levelTopScores.toCSVString();
	}

}
