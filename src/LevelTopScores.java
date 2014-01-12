import java.util.HashMap;
import java.util.TreeMap;


public class LevelTopScores {
	
	private final int MAX_TOP_SCORES = 15;
	
	private final HashMap<Integer, Integer> scoresByUserId = new HashMap<Integer, Integer>();
	private final TreeMap<Integer, Integer> scoresByScore = new TreeMap<Integer, Integer>();
	
	public void record(int userId, int score) {
		Integer oldScore = scoresByUserId.get(userId);
		if ((oldScore==null)||(oldScore.compareTo(score)==-1)) {
			this.scoresByUserId.put(userId, score);
			this.scoresByScore.put(score, userId);
		}
	}
	
	public String toCSVString() {
		StringBuilder sb = new StringBuilder();
		
		for (Integer userId: scoresByUserId.keySet()) {
			sb.append(userId.toString());
			sb.append("=");
			sb.append(scoresByUserId.get(userId).toString());
			sb.append(",");
		}
		
		if (sb.length() > 0) sb.deleteCharAt(sb.length() - 1);

		return sb.toString();
	}

}
