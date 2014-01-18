import java.util.concurrent.atomic.AtomicReference;


public class LevelTopScores {
	
	private final int MAX_TOP_SCORES = 15;
	
	private final AtomicReference<Score> head = new AtomicReference<Score>();
	
	public void record(int userId, int score) {
		Score newScore = new Score(score, userId);
		
		AtomicReference<Score> pointer = head;
		Score pointedScore;
		
		boolean isInserted = false;
		
		do {
			
			pointedScore = pointer.get();
			
			if (pointedScore == null || pointedScore.getScore() <= score) {
				newScore.setNextScore(pointedScore);
				isInserted = pointer.compareAndSet(pointedScore, newScore);
			} else {
				pointer = pointedScore.getNext();
			}
			
		} while(!isInserted);
		
		removeDuplicatedUser(userId);

	}
	
	private void removeDuplicatedUser(int userId) {
		
		AtomicReference<Score> pointer = head;
		Score pointedScore;
		
		boolean firstTimeFound = false;
		boolean removed = false;
		
		while((pointedScore = pointer.get()) != null) {
			
			if (pointedScore.getUserId() == userId) {
				if (firstTimeFound) {
					
					removed = pointedScore.removeThis(pointer);
					if (!removed) continue; // does not shift the pointer, so can try agan to remove the element
					
				} else {
					firstTimeFound = true;
				}
			}
			
			pointer = pointedScore.getNext();

		}
		
	}
//	
//	private void removeOverflow() {
//		if (topScores.size() > MAX_TOP_SCORES) topScores.removeLast();
//	}

	public String toCSVString() {
		StringBuilder sb = new StringBuilder();
		
		Score score = head.get();
		
		while (score!=null) {
			sb.append(score.getUserId());
			sb.append("=");
			sb.append(score.getScore());
			sb.append(",");
			score = score.getNext().get();
		}
		
		if (sb.length() > 0) sb.deleteCharAt(sb.length() - 1);

		return sb.toString();
	}

}
