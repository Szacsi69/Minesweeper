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
	 * Inicializ�lja az attrib�tumokat az alap�rtelmezett �rt�kre. Az adjacent_mines �rt�ket
	 * egy k�s�bbi f�ggv�ny fogja be�ll�tani, ez�rt azt kezdetben egy �rv�nytelen �rt�kre �ll�tja be.
	 * 
	 * @param f A FieldMaster, aminek a fennhat�s�ga al� tartozik az adott Field
	 */
	public Field(FieldMaster f) {
		fm = f;
		neighbours = new ArrayList<Field>();
		flagged = false;
		revealed = false;
		adjacent_mines = -1;
	}
	
	/**
	 * Elhelyez�nk egy akn�t a Field-en a mine flag bebillent�s�vel. Att�l f�gg�en, hogy ez
	 * a m�velet sikeres volt-e (volt-e m�r eddig is akna a mez�n) igaz vagy hamis �rt�kkel t�r
	 * vissza a met�dus.
	 * 
	 * @return Sikeres volt-e az akna lehelyez�se
	 */
	public boolean placeMine() {
		if (mine == true) return false;
		else {
			mine = true;
			return true;
		}
	}
	
	/**
	 * Felvesz egy Field-et a szomsz�dok k�z�.
	 * 
	 * @param f A szomsz�dos Field
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
	 * A Field-re egy z�szl�t helyez fel, vagy helyez le r�la a flag attrib�tum �ll�t�s�val.
	 * Ez a m�velet csak olyan Field-eken hajthat� v�gre, amik m�g nincsenek felfedve.
	 * Ha eddig volt rajta z�szl�, akkor leveszi r�la, ha nem volt, akkor felrakja.
	 * Megh�v�dik a Field-hez tartoz� FieldDisplay megfelel� f�ggv�nye.
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
	 * Akna eset�n, ha nem volt rajta z�szl�, kirajzol�dik az akna a FieldDisplay-en.
	 * Ha volt rajta z�szl� akkor nem t�rt�nik a FieldDisplay-en semmi, hiszen a felhaszn�l� m�r jel�lte az akn�t
	 * a z�szl�val.
	 * 
	 * Ha nem volt akna, de z�szl� igen, akkor a FieldDisplayen kirajzol�dik, az ikon, ami azt jelzi a felhaszn�l�nak,
	 * hogy rosszul felt�telezte, hogy ott akna van.
	 * Ha ilyen felfed�s t�rt�nik az egyben a j�t�k elveszt�s�t is jelenti, hiszen z�szl�val ell�tott Field-et a
	 * revealNeighbours() met�dus tud felfedni, ebb�l viszont az is k�vetkezik, hogy mivel a felhaszn�l� egy z�szl�t rossz helyre
	 * tett, egy akna biztosan fel lesz fedve. Ez�rt ebben az esetben FieldMaster MinelessFieldRevealed met�dusa nem h�v�dik meg,
	 * hiszen az a gy�zelmet vizsg�lja.
	 * 
	 * Ha nem volt akna �s z�szl� se, akkor az adjacent_mines �rt�k�nek megfelel�en rajzol ki �rt�ket a FieldDisplay.
	 * Mivel akna �s z�szl� mentes Field-ek getRevealed() met�dusa, csak is a reveal() met�dusb�l h�v�dhat meg, ez�rt
	 * adjacent_mines attrib�tum itt m�r biztosan a megfelel� �rt�ket hordozza. Megh�v�dik a FieldMaster MinelessFieldRevealed
	 * met�dusa.
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
	 * A Field, illetve esetlegesen k�rnyezet�nek, a felfed�s�t v�gzi.
	 * Csak olyan Field-et fedhet fel, amik m�g nincsenek felfedve, illetve nincs rajtuk z�szl�.
	 * 
	 * Ha a Field-en akna tal�lhat�, a felhaszn�l� elvesztette a j�t�kot, �gy megh�v�dik a FieldMaster
	 * endGameLost() met�dusa.
	 * 
	 * Ha nincs akna, akkor megsz�molja, a szomsz�dok k�z�l mennyin van akna (itt t�rt�nik adjacent_mines
	 * �rt�k�nek be�ll�t�sa). Ezut�n a megt�rt�nik a Field felfed�se (getRevealed() met�dus).
	 * Majd, ha nincs szomsz�dos akna, rekurz�v m�don (az aknakeres� szab�lyainak megfelel�en) a szomsz�dos mez�k
	 * (�s esetlegesen azok k�rnyezete is) felfed�sre ker�lnek.
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
	 * Egy m�r felfedett mez� szomsz�djainak a felfed�s�t v�gzi.
	 * Ahhoz hogy a felfed�s megt�rt�njen felt�tel, hogy a mez� szomsz�dai k�z�l annyi meg legyen jel�lve
	 * z�szl�val, amennyi akna tal�lhatt� a szomsz�ds�g�ban.
	 * Ekkor a szomsz�dos mez�k (�s adjacent_mines = 0 eset�n azok k�rnyezete is) felfed�sre ker�lnek,
	 * azon mez�k kiv�tel�vel, amik meg vannak jel�lve z�szl�val �s val�ban van alattuk akna.
	 * Ezek nem ker�lnek felfed�sre, hiszen a felhaszn�l� j�l felt�telezte az akna hol l�t�t.
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
