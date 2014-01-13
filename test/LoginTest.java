import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class LoginTest {
	
	private Login login;
	
	@Before
	public void setUp() {
		login = new Login();
	}
	
	@Test
	public void getSessionKeyShouldGenerateAKeyWithoutStrangeCharacters() {
		Integer userId = 123;
		String key = login.getSessionKey(userId);
		assertNotNull(key);
		assertTrue(key.matches("^[0-9a-zA-Z]+$"));
	}
	
	@Test
	public void getUserIdShouldReturnTheUserIdForThatSession() {
		Integer userId = 123;
		String sessionKey = login.getSessionKey(userId);
		assertEquals(userId, login.getUserId(sessionKey));
	}
	
	@Test
	public void getUserIdShouldReturnNullWhenTheSessionDoesNotExists() {
		String sessionKey = "notexistent";
		assertNull(login.getUserId(sessionKey));
	}

}