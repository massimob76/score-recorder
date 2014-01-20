package score;
import static org.junit.Assert.*;

import org.junit.Test;

import score.UserScore;


public class UserScoreTest {
	
	
	@Test
	public void scoreShouldRecordScoreAndUserId() {
		int userId = 456;
		int score = 123;
		UserScore iut = new UserScore(userId, score);
		assertEquals(score, iut.getScore());
		assertEquals(userId, iut.getUserId());
	}
	
	@Test
	public void shouldCompareToAnotherUserScore() {
		UserScore a = new UserScore(123, 12);
		UserScore b = new UserScore(10, 23);
		assertTrue(a.compareTo(b) < 0);
	}
	
	@Test
	public void shouldCompareToAnotherUserScoreHavingTheSameScore() {
		UserScore a = new UserScore(123, 12);
		UserScore b = new UserScore(10, 12);
		assertTrue(a.compareTo(b) > 0);
		
		UserScore c = new UserScore(123, 12);
		assertTrue(a.compareTo(c) == 0);
	}

}
