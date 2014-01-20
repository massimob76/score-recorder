package login;
import java.util.UUID;

import utils.Timer;
import utils.TimerImpl;


public class Login {
		
	private final SessionsManager sessions;
	
	public Login(int timeout) {
		Timer timer = new TimerImpl(timeout);
		sessions = new SessionsManager(timer);
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
