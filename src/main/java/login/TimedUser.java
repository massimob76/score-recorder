package login;

public class TimedUser {
	
	private final long createdAt;
	private final int userId;
	
	public TimedUser(int userId, long createdAt) {
		this.userId = userId;
		this.createdAt = createdAt;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public long getCreatedAt() {
		return createdAt;
	}

}
