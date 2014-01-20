package login;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import utils.Timer;


public class SessionsManager {
	
	private static final int INITIAL_DELAY = 0;
	private static final int PERIOD = 10;

	private final Timer timer;
	private final ConcurrentHashMap<String, TimedUser> sessions;
	private final SessionCleaner sessionCleaner;
	
	public SessionsManager(Timer timer) {
		this.timer = timer;
		this.sessions = new ConcurrentHashMap<String, TimedUser>();
		this.sessionCleaner = new SessionCleaner(sessions, timer);
		
		sessionCleaner.start(INITIAL_DELAY, PERIOD, TimeUnit.MINUTES);
	}

	public void put(String sessionKey, int userId) {
		sessions.put(sessionKey, new TimedUser(userId, timer.getCurrent()));
	}
	
	public Integer get(String sessionKey) {
		TimedUser timedUser = sessions.get(sessionKey);
		if (timedUser != null 
				&& !timer.isExpired(timedUser.getCreatedAt())) {
			return timedUser.getUserId();
		}
		return null;
	}

}
