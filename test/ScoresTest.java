import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class ScoresTest {
	
	private Scores iut;
	
	@Before
	public void setUp() {
		iut = new Scores();
	}
	
	@Test
	public void shouldBeAbleToPostAScoreForALevel() {
		int userId = 123;
		int level = 1;
		int score = 45;
		iut.post(userId, level, score);
	}
	
	@Test
	public void shouldBeAbleToPostAndRetrieveAScore() {
		int userId = 123;
		int level = 1;
		int score = 45;
		iut.post(userId, level, score);
		assertEquals("123=45", iut.retrieve(level));
	}
	
	@Test
	public void shouldBeAbleToPostAndRetrieveMultipleScore() {
		iut.post(123, 1, 14);
		iut.post(124, 2, 15);
		iut.post(123, 1, 16);
		iut.post(130, 1, 50);
		assertEquals("130=50,123=16", iut.retrieve(1));
	}
	
	@Test
	public void retrieveShouldReturnAnEmptyStringWhenNoScoresHaveBeenPosted() {
		int level = 1;
		assertEquals("", iut.retrieve(level));
	}

}
