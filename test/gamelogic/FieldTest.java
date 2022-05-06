package gamelogic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gui.game.Board;
import gui.game.FieldDisplay;
import management.GameManager;

public class FieldTest {
	
	GameMaster g;
	FieldMaster fm;
	Field[][] fields;
	
	/**
	 * Létrehoz egy GameMaster-t "Test" difficulty-val, ez egy egy olyan
	 * FieldMaster-t eredményez, amelynek 3x3 Field-je van 0 aknával.
	 * A Field-eknek adunk egy FieldDisplay-t is, azért hogy a teszt során ne
	 * történjen NullPointerException egy mezõ feltárásakor.
	 */
	@Before
	public void setUp() {
		try {
			g = new GameMaster("Test", null);
			
			fm = g.getFieldMaster();
			fields = fm.getFields();
			for(int i = 0; i  < 3; i++) 
				for(int j = 0; j < 3; j++)  
					fields[i][j].setFieldDisplay(new FieldDisplay(30));
				
		} catch (Exception e) {System.out.println("There was an error in the test.");}
	}
	
	/**
	 * Teszteli, hogy egy Field felfedésekor, a revealed flag valóban megfelelõen beállítódik-e.
	 */
	@Test
	public void testMethod_getRevealed() {
		fields[0][1].getRevealed();
		
		Assert.assertTrue(fields[0][1].isRevealed());
	}
	
	/**
	 * A Field reveal() metódusát teszteli. A 3x3-as területen, a második sor elsõ mezõjére elhelyezünk egy aknát,
	 * ugyanezen sorban a 3. mezõt pedig feltárjuk a reveal() metódussal. Helyes mûködés esetén az elsõ oszlopon kívül
	 * az összes mezõ feltárul.
	 */
	@Test
	public void testMethod_reveal() {
		fields[1][0].placeMine();
		fields[1][2].reveal();
		
		boolean first_column_not_revealed = !(fields[0][0].isRevealed() || fields[1][0].isRevealed() || fields[2][0].isRevealed());
		boolean other_columns_revealed = true;
		
		for(int j = 1; j < 3; j++) 
			for(int i = 0; i < 3; i++) 
				if (!fields[i][j].isRevealed()) other_columns_revealed = false;
			
		Assert.assertTrue(first_column_not_revealed && other_columns_revealed);
	}
	
	/**
	 * A Field revealNeighbours() metódusát teszteli. A 3x3-as területen, a második sor elsõ mezõjére elhelyezünk egy aknát,
	 * és meg jelöljük egy zászlóval, az alatta lévõ mezõt feltárjuk, majd ugyanerre a mezõre meghívjuk 
	 * a revealNeighbour() metódust.
	 * Helyes mûködés esetén a már feltárt mezõ mellé, a középsõ oszlop második és harmadik eleme és feltárul, a többi nem.
	 */
	@Test
	public void testMethod_revealNeighbours() {
		fields[1][0].placeMine();
		fields[1][0].getFlagged();
		
		fields[2][0].setAdjacentMines(1);
		fields[2][0].getRevealed();
		fields[2][0].revealNeighbours();
		
		boolean neighbours_revealed = fields[1][1].isRevealed() && fields[2][1].isRevealed();
		boolean others_not_revealed = !(fields[0][0].isRevealed() || fields[0][1].isRevealed() || fields[0][2].isRevealed() || fields[1][0].isRevealed() || fields[1][2].isRevealed() || fields[2][2].isRevealed());
		
		Assert.assertTrue(neighbours_revealed && others_not_revealed);
	}
	
	/**
	 * Teszteli, hogy a rendszer képes-e megfelelõen meghatározni, ha gyõzelem történt.
	 * Elhelyezünk egy aknát (azt hogy a Field-eken összesen egy akna található, a FieldMaster-nek
	 * manuálisan be kell állítani, hiszen az aknát is manuálisan helyeztük le, így a FieldMaster nem tud róla),
	 *  és a reveal() metódust használva feltárunk minden mezõt rajta kívül.
	 * Ha a GameMaster victory flag-je bebillent, az azt jelenti, hogy sikeresen érzékelte a gyõzelmet.
	 */
	@Test
	public void testVictory() {
		fields[1][0].placeMine();
		fm.setMines(1);
		
		fields[1][2].reveal();
		fields[0][0].reveal();
		fields[2][0].reveal();
		
		Assert.assertTrue(g.getVictory());
	}
	
	/**
	 * Teszteli, hogy a rendszer képes-e megfelelõen érzékelni, ha kudarc történt.
	 * Elhelyezünk egy aknát, és felfedjük. Ha a GameMaster victory adattagja false,
	 * sikeres a teszt.
	 */
	@Test
	public void testLose() {
		fields[1][0].placeMine();
		fields[1][0].reveal();
		
		Assert.assertFalse(g.getVictory());
	}
}
