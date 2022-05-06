package gamelogic;
import gui.game.FieldDisplay;

import java.util.ArrayList;

public class Field {
	private FieldMaster fm;
	private ArrayList<Field> neighbours;
	private FieldDisplay fielddisplay;
	private boolean mine;
	private boolean flagged;
	private boolean revealed;
	private int adjacent_mines;
	
	/**
	 * Inicializálja az attribútumokat az alapértelmezett értékre. Az adjacent_mines értéket
	 * egy késõbbi függvény fogja beállítani, ezért azt kezdetben egy érvénytelen értékre állítja be.
	 * 
	 * @param f A FieldMaster, aminek a fennhatósága alá tartozik az adott Field
	 */
	public Field(FieldMaster f) {
		fm = f;
		neighbours = new ArrayList<Field>();
		flagged = false;
		revealed = false;
		adjacent_mines = -1;
	}
	
	/**
	 * Elhelyezünk egy aknát a Field-en a mine flag bebillentésével. Attól függõen, hogy ez
	 * a mûvelet sikeres volt-e (volt-e már eddig is akna a mezõn) igaz vagy hamis értékkel tér
	 * vissza a metódus.
	 * 
	 * @return Sikeres volt-e az akna lehelyezése
	 */
	public boolean placeMine() {
		if (mine == true) return false;
		else {
			mine = true;
			return true;
		}
	}
	
	/**
	 * Felvesz egy Field-et a szomszédok közé.
	 * 
	 * @param f A szomszédos Field
	 */
	public void addNeighbour(Field f) {
		neighbours.add(f);
	}
	
	public void setFieldDisplay(FieldDisplay fd) {
		fielddisplay = fd;
	}
	
	public boolean isMined() {
		return mine;
	}
	
	public boolean isRevealed() {
		return revealed;
	}
	
	public boolean isFlagged() {
		return flagged;
	}
	
	/**
	 * A Field-re egy zászlót helyez fel, vagy helyez le róla a flag attribútum állításával.
	 * Ez a mûvelet csak olyan Field-eken hajtható végre, amik még nincsenek felfedve.
	 * Ha eddig volt rajta zászló, akkor leveszi róla, ha nem volt, akkor felrakja.
	 * Meghívódik a Field-hez tartozó FieldDisplay megfelelõ függvénye.
	 */
	public void getFlagged() {
		if (!revealed) {
			if (!flagged) {
				flagged = true;
				fielddisplay.flagUp();
			}
			else {
				flagged = false;
				fielddisplay.flagDown();
			}
		}
	}
	
	/**
	 * A Field-et felfedik.
	 * 
	 * Akna esetén, ha nem volt rajta zászló, kirajzolódik az akna a FieldDisplay-en.
	 * Ha volt rajta zászló akkor nem történik a FieldDisplay-en semmi, hiszen a felhasználó már jelölte az aknát
	 * a zászlóval.
	 * 
	 * Ha nem volt akna, de zászló igen, akkor a FieldDisplayen kirajzolódik, az ikon, ami azt jelzi a felhasználónak,
	 * hogy rosszul feltételezte, hogy ott akna van.
	 * Ha ilyen felfedés történik az egyben a játék elvesztését is jelenti, hiszen zászlóval ellátott Field-et a
	 * revealNeighbours() metódus tud felfedni, ebbõl viszont az is következik, hogy mivel a felhasználó egy zászlót rossz helyre
	 * tett, egy akna biztosan fel lesz fedve. Ezért ebben az esetben FieldMaster MinelessFieldRevealed metódusa nem hívódik meg,
	 * hiszen az a gyõzelmet vizsgálja.
	 * 
	 * Ha nem volt akna és zászló se, akkor az adjacent_mines értékének megfelelõen rajzol ki értéket a FieldDisplay.
	 * Mivel akna és zászló mentes Field-ek getRevealed() metódusa, csak is a reveal() metódusból hívódhat meg, ezért
	 * adjacent_mines attribútum itt már biztosan a megfelelõ értéket hordozza. Meghívódik a FieldMaster MinelessFieldRevealed
	 * metódusa.
	 */
	public void getRevealed() {
		revealed = true;
		if (mine) {
			if (!flagged)
				fielddisplay.revealMine();
			else {}
		}
		else {
			if (flagged) fielddisplay.revealWrongFlag();
			else {
				fielddisplay.revealNoMine(adjacent_mines);
				fm.MinelessFieldRevealed();
			}
		}
	}
	
	/**
	 * A Field, illetve esetlegesen környezetének, a felfedését végzi.
	 * Csak olyan Field-et fedhet fel, amik még nincsenek felfedve, illetve nincs rajtuk zászló.
	 * 
	 * Ha a Field-en akna található, a felhasználó elvesztette a játékot, így meghívódik a FieldMaster
	 * endGameLost() metódusa.
	 * 
	 * Ha nincs akna, akkor megszámolja, a szomszédok közül mennyin van akna (itt történik adjacent_mines
	 * értékének beállítása). Ezután a megtörténik a Field felfedése (getRevealed() metódus).
	 * Majd, ha nincs szomszédos akna, rekurzív módon (az aknakeresõ szabályainak megfelelõen) a szomszédos mezõk
	 * (és esetlegesen azok környezete is) felfedésre kerülnek.
 	 */
	public void reveal() {
		if (!revealed && !flagged) {
			if (mine) {
				fm.endGameLost();
				return;
			}
			else {
				adjacent_mines = 0;
				for(Field f : neighbours)
					if (f.isMined())
						adjacent_mines++;
				getRevealed();
				if (adjacent_mines == 0) 
					for(Field f : neighbours)
						f.reveal();
			}
		}
	}
	
	/**
	 * Egy már felfedett mezõ szomszédjainak a felfedését végzi.
	 * Ahhoz hogy a felfedés megtörténjen feltétel, hogy a mezõ szomszédai közül annyi meg legyen jelölve
	 * zászlóval, amennyi akna találhattó a szomszédságában.
	 * Ekkor a szomszédos mezõk (és adjacent_mines = 0 esetén azok környezete is) felfedésre kerülnek,
	 * azon mezõk kivételével, amik meg vannak jelölve zászlóval és valóban van alattuk akna.
	 * Ezek nem kerülnek felfedésre, hiszen a felhasználó jól feltételezte az akna hol létét.
	 */
	public void revealNeighbours() {
		if (revealed) {
			int flagged_neighbours = 0;
			for(Field f : neighbours)
				if (f.isFlagged())
					flagged_neighbours++;
			if (adjacent_mines == flagged_neighbours) {
				for(Field f : neighbours) {
					if (!f.isMined() && f.isFlagged()) f.getRevealed();
					else f.reveal();
				}
			}
		}
	}
	
////////////////////////////////////////FOR TEST///////////////////////////////////////////////////////////////////	
	
	public void setAdjacentMines(int m) {
		adjacent_mines = m;
	}
}
