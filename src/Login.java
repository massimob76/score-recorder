import java.util.UUID;


public class Login {
	
	private static final int TEN_MIN_IN_MILLIS = 10 * 60 * 1000;
	
	private final CachedMap<String, Integer> sessions;
	
	public Login() {
		sessions = new CachedMap<String, Integer>(TEN_MIN_IN_MILLIS);
	}
	
	Login(CachedMap<String, Integer> cachedMap) {
		this.sessions = cachedMap;
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
