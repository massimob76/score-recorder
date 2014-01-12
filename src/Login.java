import java.util.HashMap;
import java.util.UUID;


public class Login {
	
	private HashMap<String, Integer> sessions = new HashMap<String, Integer>();
	
	public String getSessionKey(Integer userId) {
		String sessionKey = UUID.randomUUID().toString().replace("-", "");
		sessions.put(sessionKey, userId);
		return sessionKey;
	}
	
	public Integer getUserId(String sessionKey) {
		return sessions.get(sessionKey);
	}

}
