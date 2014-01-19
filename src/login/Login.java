package login;
import java.util.UUID;

import utils.TimedHashMap;
import utils.Timer;
import utils.TimerImpl;


public class Login {
	
	private static final int TEN_MIN_IN_MILLIS = 10 * 60 * 1000;
	
	private final TimedHashMap<String, Integer> sessions;
	
	public Login() {
		Timer timer = new TimerImpl(TEN_MIN_IN_MILLIS);
		sessions = new TimedHashMap<String, Integer>(timer);
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
