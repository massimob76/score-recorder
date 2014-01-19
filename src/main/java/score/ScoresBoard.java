package score;
import java.util.concurrent.ConcurrentHashMap;



public class ScoresBoard {
	
	private ConcurrentHashMap<Integer, LevelScores> scores = new ConcurrentHashMap<Integer, LevelScores>();

	public void post(int userId, int level, int score) {
		LevelScores levelTopScores = scores.get(level);
		if (levelTopScores == null) {
			levelTopScores = new LevelScores();
			LevelScores previousTopScores = scores.putIfAbsent(level, levelTopScores);
			if (previousTopScores != null) levelTopScores = previousTopScores;
		}
		levelTopScores.record(userId, score);
	}
	
	public String retrieve(int level) {
		LevelScores levelTopScores = scores.get(level);
		if (levelTopScores == null) return "";
		return levelTopScores.toCSVString();
	}

}
