package score;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import score.LevelScores;


public class LevelScoresTest {
	
	private LevelScores iut;
	
	@Before
	public void setUp() {
		iut = new LevelScores();
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
	public void shouldRecordTwoNewScore() {
		iut.record(1, 12);
		iut.record(2, 10);
		assertEquals("1=12,2=10", iut.toCSVString());
	}
	
	@Test
	public void highestScorePerUserOnlyShouldBeReturned() {
		iut.record(123, 12);
		iut.record(123, 150);
		iut.record(123, 120);
		iut.record(123, 150);
		assertEquals("123=150", iut.toCSVString());
	}
	
	@Test
	public void scoreListShouldBeOrderedByDescendingOrder() {
		iut.record(12, 15);
		iut.record(15, 123);
		iut.record(8, 20);
		assertEquals("15=123,8=20,12=15", iut.toCSVString());
	}
	
	@Test
	public void scoreListWithManySameScores() {
		iut.record(123, 150);
		iut.record(14, 150);
		iut.record(123, 150);
		iut.record(127, 150);
		assertEquals("127=150,123=150,14=150", iut.toCSVString());
	}
	
	@Test
	public void scoreListShouldContainNoMoreThan15Scores() {
		for (int i = 0; i < 20; i++) {
			iut.record(i, i);
		}
		assertEquals(15, iut.toCSVString().split(",").length);
	}

}
