import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class LevelTopScoresTest {
	
	private LevelTopScores iut;
	
	@Before
	public void setUp() {
		iut = new LevelTopScores();
	}
	
	@Test
	public void shouldReturnEmptyStringWhenNoRecords() {
		assertEquals("", iut.toCSVString());
	}
	
	@Test
	public void shouldRecordANewScore() {
		int userId = 123;
		int score = 456;
		iut.record(userId, score);
		assertEquals("123=456", iut.toCSVString());
	}
	
	@Test
	public void highestScorePerUserOnlyShouldBeReturned() {
		iut.record(123, 12);
		iut.record(123, 150);
		iut.record(123, 120);
		assertEquals("123=150", iut.toCSVString());
	}
	
	@Test
	public void scoreListShouldBeOrderedByDescendingOrder() {
		iut.record(12, 15);
		iut.record(15, 123);
		iut.record(8, 20);
		assertEquals("15=123,8=20,12=15", iut.toCSVString());
	}

}
