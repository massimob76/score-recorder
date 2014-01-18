package score;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;


public class LevelScores {
	
	private final int MAX_TOP_SCORES = 15;
	
	private final LinkedList<Score> topScores = new LinkedList<Score>();

	public synchronized void record(int userId, int score) {
		ListIterator<Score> iterator = topScores.listIterator();
		
		while (iterator.hasNext()) {
			Score currentScore = iterator.next();
			if (currentScore.getScore() <= score) {
				iterator.previous();
				iterator.add(new Score(score, userId));
				removeDuplicatedUser(iterator, userId);
				removeOverflow();
				return;
			}
			if (currentScore.getUserId() == userId) return; // user already recorded
		}
		
		iterator.add(new Score(score, userId));
		removeOverflow();
	}


	public synchronized String toCSVString() {
		StringBuilder sb = new StringBuilder();
		
		Iterator<Score> iterator = topScores.iterator();
		while (iterator.hasNext()) {
			Score score = iterator.next();
			sb.append(score.getUserId());
			sb.append("=");
			sb.append(score.getScore());
			sb.append(",");
		}
		
		if (sb.length() > 0) sb.deleteCharAt(sb.length() - 1);

		return sb.toString();
	}
	
	
	private void removeDuplicatedUser(ListIterator<Score> iterator, int userId) {
		while(iterator.hasNext()) {
			Score currentScore = iterator.next();
			if (currentScore.getUserId() == userId) {
				iterator.remove();
				return;
			}
		}
		
	}
	
	private void removeOverflow() {
		if (topScores.size() > MAX_TOP_SCORES) topScores.removeLast();
	}

}
