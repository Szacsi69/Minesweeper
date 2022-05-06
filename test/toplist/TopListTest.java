package toplist;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class TopListTest {
	TopList t;
	Player p1;
	Player p2;
	Player p3;
	
	/**
	 * Létrehoz egy TopList objektumot Playerekbõl.
	 */
	@Before
	public void setUp() {
		ArrayList<Player> players = new ArrayList<Player>();
		p1 = new Player("John", 745, "Intermediate");
		p2 = new Player("Lisa", 300, "Intermediate");
		p3 = new Player("Eva", 408, "Intermediate");
		
		players.add(p1);
		players.add(new Player("Bob", 114, "Beginner"));
		players.add(p2);
		players.add(new Player("David", 122, "Beginner"));
		players.add(new Player("Rudolf", 933, "Advanced"));
		players.add(p3);
		
		t = new TopList(players);
	}
	
	/**
	 * Teszteli, hogy a getList() metódus valóvan azokat a játékosokat adja-e vissza
	 * az ArrayList-ben, akiket vár.
	 */
	@Test
	public void testMethod_getList() {
		ArrayList<Player> expected = new ArrayList<Player>();
		expected.add(p2);
		expected.add(p3);
		expected.add(p1);
		
		ArrayList<Player> actual = t.getList("Intermediate");
		Assert.assertTrue(expected.equals(actual));
	}
	
	/**
	 * Teszteli, hogy ha egy olyan játékost akarunk hozzáadni a listához, aki rontott az idején, akkor
	 * a hozzáadás megtörténik-e vagy az eredeti érték marad a listában.
	 * Az utóbbi esetén sikeres a teszt.
	 */
	@Test
	public void testMethod_addPlayer_1() {
		Player p = new Player("Bob", 200, "Beginner");
		ArrayList<Player> expected = t.getPlayers();
		t.addPlayer(p);
		ArrayList<Player> actual = t.getPlayers();
		Assert.assertTrue(expected.equals(actual));
	}
	
	/**
	 * Teszteli, hogy ha egy olyan játékost akarunk hozzáadni a listához, aki javított az idején, akkor
	 * a felülírás megtörténik-e. Ha igen, sikeres a teszt.
	 */
	@Test
	public void testMethod_addPlayer_2() {
		Player p = new Player("Lisa", 247, "Intermediate");
		ArrayList<Player> expected = t.getPlayers();
		expected.set(2, p);
		t.addPlayer(p);
		ArrayList<Player> actual = t.getPlayers();
		Assert.assertTrue(expected.equals(actual));
	}
	
	/**
	 * Teszteli, ha egy új játékost akarunk hozzáadni a listához, akkor a hozzáadás sikeresen megtörténik-e.
	 */
	@Test
	public void testMethod_addPlayer_3() {
		Player p = new Player("Peter", 685, "Advanced");
		ArrayList<Player> expected = t.getPlayers();
		expected.add(p);
		t.addPlayer(p);
		ArrayList<Player> actual = t.getPlayers();
		Assert.assertTrue(expected.equals(actual));
	}
}
