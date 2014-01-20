package score;

import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;


public class LevelScores {
	
	private static final int MAX_SCORES = 15;
	
	private final ConcurrentSkipListSet<UserScore> scores = new ConcurrentSkipListSet<UserScore>();
    private final AtomicInteger counter = new AtomicInteger();
    private UserScore minimumUserScore;
	
	public void record(int userId, int score) {
		
		UserScore newUserScore = new UserScore(userId, score);
		
		if (minimumUserScore != null 
				&& newUserScore.compareTo(minimumUserScore) <= 0) return;
		
		UserScore oldUserScore = findIfUserAlreadyPresent(userId);
		
		if (oldUserScore == null) {
			
			if (scores.add(newUserScore)) counter.incrementAndGet();
			
		} else if (oldUserScore.compareTo(newUserScore) < 0) {
			
			if (scores.add(newUserScore) && !scores.remove(oldUserScore)) 
				counter.incrementAndGet();
		}
		
		removeOverflowAndSetMinimumUserScore();

	}


	private UserScore findIfUserAlreadyPresent(int userId) {
		Iterator<UserScore> iterator = scores.iterator();
		
		while(iterator.hasNext()) {
			UserScore userScore = iterator.next();
			if (userScore.getUserId() == userId) return userScore;
		}
		
		return null;
	}


	public String toCSVString() {
		
		StringBuilder sb = new StringBuilder();
		
		Iterator<UserScore> iterator = scores.descendingIterator();
		
		while (iterator.hasNext()) {
			UserScore userScore = iterator.next();
			sb.append(userScore.getUserId());
			sb.append("=");
			sb.append(userScore.getScore());
			sb.append(",");
		}
		
		if (sb.length() > 0) sb.deleteCharAt(sb.length() - 1);

		return sb.toString();
	}
	
	private void removeOverflowAndSetMinimumUserScore() {
		if (counter.compareAndSet(MAX_SCORES + 1, MAX_SCORES)) {
			scores.pollFirst();
			minimumUserScore = scores.first();
		}
	}

}
