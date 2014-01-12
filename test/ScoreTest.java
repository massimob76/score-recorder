import static org.junit.Assert.*;

import org.junit.Test;


public class ScoreTest {
	
	
	@Test
	public void scoreShouldRecordScoreAndUserId() {
		int score = 123;
		int userId = 456;
		Score iut = new Score(score, userId);
		assertEquals(score, iut.getScore());
		assertEquals(userId, iut.getUserId());
	}
	
	@Test
	public void shouldCompareToAnotherScore() {
		Score a = new Score(12, 123);
		Score b = new Score(23, 10);
		assertTrue(a.compareTo(b) < 0);
	}

}
