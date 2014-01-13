import java.util.Date;


public class Clock {
	
	private final long timeout;
	
	public Clock(int timeout) {
		this.timeout = timeout;
	}
	
	public long getCurrent() {
		return new Date().getTime();
	}
	
	public boolean isExpired(long time) {
		return (getCurrent() - time > timeout);
	}

}
