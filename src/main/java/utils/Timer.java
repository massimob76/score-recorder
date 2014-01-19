package utils;

public interface Timer {
	
	public long getCurrent();
	
	public boolean isExpired(long time);

}
