import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class CacheTest {
	
	private CachedMap<String, String> iut;
	
	@Before
	public void setUp() {
		iut = new CachedMap<String, String>(4);
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
	public void sizeShouldReturnTheCorrectValue() {
		iut.put("key1", "value");
		iut.put("key2", "value");
		iut.put("key3", "value");
		assertEquals(3, iut.size());
	}
	
	@Test
	public void expiredItemsShouldBeRemovedFromTheCache() throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			iut.put("key" + i, "value");
			Thread.sleep(1);
			System.gc();
		}
		assertTrue(iut.size() < 10);
	}

}
