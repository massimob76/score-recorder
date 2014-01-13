import static org.junit.Assert.*;

import org.junit.Test;


public class ClockTest {
	
	private Clock clock;
	
	@Test
	public void isExpiredWorksCorrectly() throws InterruptedException {
		clock = new Clock(4);
		long currentTime = clock.getCurrent();
		assertFalse(clock.isExpired(currentTime));
		Thread.sleep(5);
		assertTrue(clock.isExpired(currentTime));
	}

}
