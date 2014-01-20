package login;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import login.SessionCleaner;
import login.TimedUser;

import org.junit.Test;

import utils.Timer;
import utils.TimerImpl;

public class SessionCleanerTest {
	
	private SessionCleaner iut;
	
	@Test
	public void shouldClearUpExpiredSessions() throws InterruptedException {
		
	    int timeout = 4;
		
		Map<String, TimedUser> sessions = new HashMap<String, TimedUser>();
		Timer timer = new TimerImpl(timeout);
		sessions.put("sessionKey", new TimedUser(123, timer.getCurrent()));
		
		iut = new SessionCleaner(sessions, timer);
		
		int delay = 0;
		int period = 5;
		TimeUnit timeUnit = TimeUnit.MILLISECONDS;
		iut.start(delay, period, timeUnit);		
		assertNotNull(sessions.get("sessionKey"));
		
		Thread.sleep(8);
		
		assertNull(sessions.get("sessionKey"));
	}

}
