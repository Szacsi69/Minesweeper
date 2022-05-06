package toplist;

import org.junit.Test;
import org.junit.Assert;

public class PlayerTest {
	
	/**
	 * Teszteli, hogy a getTime() metódus valóban a jó értékkel tér-e vissza.
	 */
	@Test
	public void testMethod_getTime() {
		Player p = new Player("Bob", 8433, "Advanced");
		String actual = p.getTime();
		Assert.assertEquals("02:20:33", actual);
	}
}
