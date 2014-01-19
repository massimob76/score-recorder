package utils;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import utils.TimedHashMap;


public class TimedHashMapTest {
	
	private TimedHashMap<String, String> iut;
	
	@Before
	public void setUp() {
		Timer timer = new TimerImpl(4);
		iut = new TimedHashMap<String, String>(timer);
	}
	
	@Test
	public void shoudBeAbleToSaveAndRetrieveAnItem() {
		String key = "key";
		String value = "value";
		iut.put(key, value);
		assertEquals(value, iut.get(key));
	}
	
	@Test
	public void shoudNotBeAbleToRetrieveAnExpiredItem() throws InterruptedException {
		String key = "key";
		String value = "value";
		iut.put(key, value);
		assertEquals(value, iut.get(key));
		Thread.sleep(5);
		assertNull(iut.get(key));
	}
	
	@Test
	public void shouldReturnNullWhenAnItemDoesNotExists() {
		assertNull(iut.get("notexistent"));
	}
	
	@Test
	public void expiredItemsShouldBeRemovedFromTheMap() throws InterruptedException {
		iut.put("key", "value");
		assertNotNull(iut.get("key"));
		Thread.sleep(10);
		iut.put("last", "value");
		assertNull(iut.get("key"));
	}

}
