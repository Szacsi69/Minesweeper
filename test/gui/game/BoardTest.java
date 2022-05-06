package gui.game;

import java.awt.Color;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gamelogic.GameMaster;
import management.GameManager;

public class BoardTest {
	
	Board b;
	GameMaster g;
	
	/**
	 * Létrehoz egy Board-ot egy GameMaster-rel.
	 */
	@Before
	public void setUp() {
		try {
			g = new GameMaster("Beginner", null);
			b = new Board(30, 60, 8, g);
		} catch (Exception e) {System.out.println("There was an error in the test.");}
	}
	
	/**
	 * Teszteli, hogy a refresh() metódus után, a FieldDisplay-ek tényleg visszaállnak-e kezdõállapotba.
	 * Ehhez elõször véletszerûen "összekoszoljuk" a FieldDisplay-ek egy részét, majd meghíjuk a refresh() metódust.
	 * Ha ezután minden FieldDisplay kezdõállapotban van, a metódus jól mûködik.
	 */
	@Test
	public void testMethod_refresh() {
		FieldDisplay[][] f = b.getFieldDisplays();
		Random r = new Random();
		for(int i = 0; i < 10; i++) {
			int x = r.nextInt(g.getFieldMaster().getRows());
			int y = r.nextInt(g.getFieldMaster().getColumns());
			
			int action = r.nextInt(3);
			switch (action) {
				case 0:
					f[x][y].flagUp();
					break;
				case 1:
					f[x][y].revealMine();
					break;
				case 3:
					f[x][y].revealWrongFlag();
					break;
				default:
					break;
			}
		}
		b.refresh();
		boolean refreshed = true;
		for(int i = 0; i < g.getFieldMaster().getRows(); i++) {
			for(int j = 0; j < g.getFieldMaster().getColumns(); j++) {
				if (!(f[i][j].isActive() && f[i][j].isImageNull() && Color.gray == f[i][j].getColor())) refreshed = false;
			}
		}
		Assert.assertTrue(refreshed);
	}
	
	/**
	 * Teszteli, hogy az endGame() metódus valóban deaktivál-e minden FieldDisplay-t.
	 * A metódus meghívása után megnézzük, hogy van-e olyan FieldDisplay, ami még aktív.
	 * Ha nincs, akkor sikeres a teszt.
	 */
	@Test
	public void testMethod_endGame() {
		FieldDisplay[][] f = b.getFieldDisplays();
		b.endGame();
		boolean ended = true;
		for(int i = 0; i < g.getFieldMaster().getRows(); i++) {
			for(int j = 0; j < g.getFieldMaster().getColumns(); j++) {
				if (f[i][j].isActive()) ended = false;
			}
		}
		Assert.assertTrue(ended);
	}
}
