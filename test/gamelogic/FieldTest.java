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
	 * L�trehoz egy GameMaster-t "Test" difficulty-val, ez egy egy olyan
	 * FieldMaster-t eredm�nyez, amelynek 3x3 Field-je van 0 akn�val.
	 * A Field-eknek adunk egy FieldDisplay-t is, az�rt hogy a teszt sor�n ne
	 * t�rt�njen NullPointerException egy mez� felt�r�sakor.
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
	 * Teszteli, hogy egy Field felfed�sekor, a revealed flag val�ban megfelel�en be�ll�t�dik-e.
	 */
	@Test
	public void testMethod_getRevealed() {
		fields[0][1].getRevealed();
		
		Assert.assertTrue(fields[0][1].isRevealed());
	}
	
	/**
	 * A Field reveal() met�dus�t teszteli. A 3x3-as ter�leten, a m�sodik sor els� mez�j�re elhelyez�nk egy akn�t,
	 * ugyanezen sorban a 3. mez�t pedig felt�rjuk a reveal() met�dussal. Helyes m�k�d�s eset�n az els� oszlopon k�v�l
	 * az �sszes mez� felt�rul.
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
	 * A Field revealNeighbours() met�dus�t teszteli. A 3x3-as ter�leten, a m�sodik sor els� mez�j�re elhelyez�nk egy akn�t,
	 * �s meg jel�lj�k egy z�szl�val, az alatta l�v� mez�t felt�rjuk, majd ugyanerre a mez�re megh�vjuk 
	 * a revealNeighbour() met�dust.
	 * Helyes m�k�d�s eset�n a m�r felt�rt mez� mell�, a k�z�ps� oszlop m�sodik �s harmadik eleme �s felt�rul, a t�bbi nem.
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
	 * Teszteli, hogy a rendszer k�pes-e megfelel�en meghat�rozni, ha gy�zelem t�rt�nt.
	 * Elhelyez�nk egy akn�t (azt hogy a Field-eken �sszesen egy akna tal�lhat�, a FieldMaster-nek
	 * manu�lisan be kell �ll�tani, hiszen az akn�t is manu�lisan helyezt�k le, �gy a FieldMaster nem tud r�la),
	 *  �s a reveal() met�dust haszn�lva felt�runk minden mez�t rajta k�v�l.
	 * Ha a GameMaster victory flag-je bebillent, az azt jelenti, hogy sikeresen �rz�kelte a gy�zelmet.
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
	 * Teszteli, hogy a rendszer k�pes-e megfelel�en �rz�kelni, ha kudarc t�rt�nt.
	 * Elhelyez�nk egy akn�t, �s felfedj�k. Ha a GameMaster victory adattagja false,
	 * sikeres a teszt.
	 */
	@Test
	public void testLose() {
		fields[1][0].placeMine();
		fields[1][0].reveal();
		
		Assert.assertFalse(g.getVictory());
	}
}
