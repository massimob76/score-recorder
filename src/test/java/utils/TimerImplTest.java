package utils;
import static org.junit.Assert.*;

import org.junit.Test;

import utils.TimerImpl;


public class TimerImplTest {
	
	private TimerImpl clock;
	
	@Test
	public void isExpiredWorksCorrectly() throws InterruptedException {
		clock = new TimerImpl(4);
		long currentTime = clock.getCurrent();
		assertFalse(clock.isExpired(currentTime));
		Thread.sleep(5);
		assertTrue(clock.isExpired(currentTime));
	}

}
