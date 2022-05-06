package toplist;

import org.junit.Test;
import org.junit.Assert;

public class PlayerTest {
	
	/**
	 * Teszteli, hogy a getTime() met�dus val�ban a j� �rt�kkel t�r-e vissza.
	 */
	@Test
	public void testMethod_getTime() {
		Player p = new Player("Bob", 8433, "Advanced");
		String actual = p.getTime();
		Assert.assertEquals("02:20:33", actual);
	}
}
