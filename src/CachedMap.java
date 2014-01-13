import java.util.LinkedList;
import java.util.ListIterator;
import java.util.WeakHashMap;


public class CachedMap<K, V> {

	private WeakHashMap<K, ValueAndTime> map = new WeakHashMap<K, ValueAndTime>();
	private LinkedList<KeyAndTime> list = new LinkedList<KeyAndTime>();
	
	private final Clock clock;
	
	public CachedMap(int timeout) {
		clock = new Clock(timeout);
	}

	
	public void put(K key, V value) {
		long currentTime = clock.getCurrent();
		KeyAndTime keyAndTime = new KeyAndTime(key, currentTime);
		ValueAndTime valueAndTime = new ValueAndTime(value, currentTime);
		map.put(key, valueAndTime);
		list.add(keyAndTime);
		cleanExpired();
	}
	
	public V get(K key) {
		ValueAndTime valueAndTime = map.get(key);
		if (clock.isExpired(valueAndTime.createdAt)) return null;
		return valueAndTime.value;
	}
	
	public int size() {
		return map.size();
	}
	
	private void cleanExpired() {
		ListIterator<KeyAndTime> iterator = list.listIterator();
		while(iterator.hasNext()) {
			KeyAndTime keyAndTime = iterator.next();
			if (clock.isExpired(keyAndTime.createdAt)) {
				iterator.remove();
			} else {
				return;
			}
		}
	}
	
	private class KeyAndTime {
		private final K key;
		private final long createdAt;
		
		public KeyAndTime(K key, long createdAt) {
			this.key = key;
			this.createdAt = createdAt;
		}
	}
	
	private class ValueAndTime {
		private final V value;
		private final long createdAt;
		
		public ValueAndTime(V value, long createdAt) {
			this.value = value;
			this.createdAt = createdAt;
		}
	}


}
