package login;
import java.util.UUID;

import utils.TimedHashMap;


public class Login {
	
	private static final int TEN_MIN_IN_MILLIS = 10 * 60 * 1000;
	
	private final TimedHashMap<String, Integer> sessions;
	
	public Login() {
		sessions = new TimedHashMap<String, Integer>(TEN_MIN_IN_MILLIS);
	}
	
	Login(TimedHashMap<String, Integer> timedHashMap) {
		this.sessions = timedHashMap;
	}
	
	public String getSessionKey(Integer userId) {
		String sessionKey = UUID.randomUUID().toString().replace("-", "");
		sessions.put(sessionKey, userId);
		return sessionKey;
	}
	
	public Integer getUserId(String sessionKey) {
		return sessions.get(sessionKey);
	}

}
