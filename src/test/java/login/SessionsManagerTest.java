package login;
import static org.junit.Assert.*;
import login.SessionsManager;

import org.junit.Before;
import org.junit.Test;

import utils.Timer;
import utils.TimerImpl;



public class SessionsManagerTest {
	
	private SessionsManager iut;
	
	@Before
	public void setUp() {
		Timer timer = new TimerImpl(4);
		iut = new SessionsManager(timer);
	}
	
	@Test
	public void shoudBeAbleToSaveAndRetrieveASession() {
		String sessionKey = "sessionKey";
		Integer userId = 123;
		iut.put(sessionKey, userId);
		assertEquals(userId, iut.get(sessionKey));
	}
	
	@Test
	public void shoudNotBeAbleToRetrieveAnExpiredItem() throws InterruptedException {
		String sessionKey = "sessionKey";
		Integer userId = 123;
		iut.put(sessionKey, userId);
		assertEquals(userId, iut.get(sessionKey));
		Thread.sleep(5);
		assertNull(iut.get(sessionKey));
	}
	
	@Test
	public void shouldReturnNullWhenAnItemDoesNotExists() {
		assertNull(iut.get("notexistent"));
	}

}
