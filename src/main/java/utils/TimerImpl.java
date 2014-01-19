package utils;
import java.util.Date;


public class TimerImpl implements Timer {
	
	private final long timeout;
	
	public TimerImpl(int timeout) {
		this.timeout = timeout;
	}
	
	@Override
	public long getCurrent() {
		return new Date().getTime();
	}
	
	@Override
	public boolean isExpired(long time) {
		return (getCurrent() - time > timeout);
	}

}
