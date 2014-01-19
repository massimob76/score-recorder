package utils;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Provides a very basic implementation of a map (only put and get methods)
 * Elements are automatically removed when expired.
 * 
 * An element is considered expired when the elapsed time between its creation time 
 * and the current time is greater than the timeout.
 * The expired element will be removed at the next insertion or get operation.
 */

public class TimedHashMap<K, V> {

	private ConcurrentHashMap<K, V> map = new ConcurrentHashMap<K, V>();
	private ConcurrentLinkedDeque<TimedKey> deque = new ConcurrentLinkedDeque<TimedKey>();
	
	private final Timer timer;
	
	public TimedHashMap(Timer timer) {
		this.timer = timer;
	}

	public V put(K key, V value) {
		V previousValue = map.put(key, value);
		deque.offerFirst(new TimedKey(key));
		cleanExpired();
		return previousValue;
	}
	
	public V get(K key) {
		cleanExpired();
		return map.get(key);
	}
	
	private void cleanExpired() {
		TimedKey timedKey;
		while ((timedKey = deque.pollLast()) != null) {
			if (timedKey.isExpired()) {
				map.remove(timedKey.key);
			} else {
				deque.offerLast(timedKey);
				break;
			}
		}
	}
	
	private class TimedKey {
		private final long createdAt;
		private final K key;
		
		public TimedKey(K key) {
			this.createdAt = timer.getCurrent();
			this.key = key;
		}
		
		public boolean isExpired() {
			return timer.isExpired(createdAt);
		}
	}

}
