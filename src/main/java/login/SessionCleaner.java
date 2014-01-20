package login;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import utils.Timer;


public class SessionCleaner implements Runnable {
	
	private final Map<String, TimedUser> sessions;
	private final Timer timer;
	
	public SessionCleaner(Map<String, TimedUser> sessions, Timer timer) {
		this.sessions = sessions;
		this.timer = timer;
	}
	
	public void start(int delay, int period, TimeUnit timeUnit) {
		ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
		exec.scheduleAtFixedRate(this, delay, period, timeUnit);
	}

	@Override
	public void run() {
		for (String sessionKey: sessions.keySet()) {
			TimedUser timedUser = sessions.get(sessionKey);
			if (timedUser!=null 
					&& timer.isExpired(timedUser.getCreatedAt())) {
				sessions.remove(sessionKey);
			}
		}
	}
		
}
