package login;
import java.util.UUID;

import utils.TimedHashMap;
import utils.Timer;
import utils.TimerImpl;


public class Login {
		
	private final TimedHashMap<String, Integer> sessions;
	
	public Login(int timeout) {
		Timer timer = new TimerImpl(timeout);
		sessions = new TimedHashMap<String, Integer>(timer);
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
