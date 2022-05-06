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
	 * L�trehoz egy Board-ot egy GameMaster-rel.
	 */
	@Before
	public void setUp() {
		try {
			g = new GameMaster("Beginner", null);
			b = new Board(30, 60, 8, g);
		} catch (Exception e) {System.out.println("There was an error in the test.");}
	}
	
	/**
	 * Teszteli, hogy a refresh() met�dus ut�n, a FieldDisplay-ek t�nyleg vissza�llnak-e kezd��llapotba.
	 * Ehhez el�sz�r v�letszer�en "�sszekoszoljuk" a FieldDisplay-ek egy r�sz�t, majd megh�juk a refresh() met�dust.
	 * Ha ezut�n minden FieldDisplay kezd��llapotban van, a met�dus j�l m�k�dik.
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
	 * Teszteli, hogy az endGame() met�dus val�ban deaktiv�l-e minden FieldDisplay-t.
	 * A met�dus megh�v�sa ut�n megn�zz�k, hogy van-e olyan FieldDisplay, ami m�g akt�v.
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
