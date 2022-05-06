package gamelogic;
import gui.game.FieldDisplay;

import java.util.Random;

public class FieldMaster {
	private GameMaster gm;
	
	private Field[][] fields;
	private int unrevealed_fields;
	private int mines;
	private int rows;
	private int columns;
	
	/**
	 * Inicializ�lja az adattagokat, �rv�nytelen aknasz�m eset�n kiv�telt dob.
	 * 
	 * @param rCount 		Sorok sz�ma
	 * @param cCount 		Oszlopok sz�ma
	 * @param mCount 		Akn�k sz�ma
	 * @param g				A GameMaster, amely fennhat�s�ga al� tartozik a FieldMaster
	 * @throws Exception	�rv�nytelen aknasz�m eset�n MineException dob�dik
	 */
	public FieldMaster(int rCount, int cCount, int mCount, GameMaster g) throws Exception {
		gm = g;
		
		mines = mCount;
		rows = rCount;
		columns = cCount;
			
		if (mines > rows*columns || mines < 0) throw new MineException();	
		
		buildUpFields();
	}
	
	/**
	 * A m�r inicializ�lt adattagok seg�ts�g�vel �rt�ket ad a t�bbi attrib�tumnak is.
	 * L�trehozza a Field-eket, sz�tosztja k�z�tt�k v�letlenszer�en az akn�kat, illetve
	 * be�ll�tja a szomsz�ds�gokat.
	 */
	public void buildUpFields() {
		unrevealed_fields = rows * columns;
		fields = new Field[rows][columns];
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < columns; j++)
				fields[i][j] = new Field(this);
		
		
		Random r = new Random();
		int  remaining_mines = mines;
		while (remaining_mines > 0) {
			int x = r.nextInt(rows);
			int y = r.nextInt(columns);
			
			if (fields[x][y].placeMine()) remaining_mines--;
		}
		
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				
				if (i == 0) {
					if (j == 0) {
						fields[i][j].addNeighbour(fields[i][j+1]);
						fields[i][j].addNeighbour(fields[i+1][j]);
						fields[i][j].addNeighbour(fields[i+1][j+1]);
					}
					else if (j == columns-1) {
						fields[i][j].addNeighbour(fields[i][j-1]);
						fields[i][j].addNeighbour(fields[i+1][j-1]);
						fields[i][j].addNeighbour(fields[i+1][j]);
					}
					else {
						fields[i][j].addNeighbour(fields[i][j-1]);
						fields[i][j].addNeighbour(fields[i][j+1]);
						fields[i][j].addNeighbour(fields[i+1][j-1]);
						fields[i][j].addNeighbour(fields[i+1][j]);
						fields[i][j].addNeighbour(fields[i+1][j+1]);
					}
				}
				
				else if (i == rows-1) {
					if (j == 0) {
						fields[i][j].addNeighbour(fields[i][j+1]);
						fields[i][j].addNeighbour(fields[i-1][j+1]);
						fields[i][j].addNeighbour(fields[i-1][j]);
					}
					else if (j == columns-1) {
						fields[i][j].addNeighbour(fields[i][j-1]);
						fields[i][j].addNeighbour(fields[i-1][j-1]);
						fields[i][j].addNeighbour(fields[i-1][j]);
					}
					else {
						fields[i][j].addNeighbour(fields[i][j-1]);
						fields[i][j].addNeighbour(fields[i][j+1]);
						fields[i][j].addNeighbour(fields[i-1][j-1]);
						fields[i][j].addNeighbour(fields[i-1][j]);
						fields[i][j].addNeighbour(fields[i-1][j+1]);
					}
				}
				
				else {
					if (j == 0) {
						fields[i][j].addNeighbour(fields[i-1][j]);
						fields[i][j].addNeighbour(fields[i-1][j+1]);
						fields[i][j].addNeighbour(fields[i][j+1]);
						fields[i][j].addNeighbour(fields[i+1][j]);
						fields[i][j].addNeighbour(fields[i+1][j+1]);
					}
					else if (j == columns-1) {
						fields[i][j].addNeighbour(fields[i-1][j]);
						fields[i][j].addNeighbour(fields[i-1][j-1]);
						fields[i][j].addNeighbour(fields[i][j-1]);
						fields[i][j].addNeighbour(fields[i+1][j]);
						fields[i][j].addNeighbour(fields[i+1][j-1]);
					}
					else {
						fields[i][j].addNeighbour(fields[i-1][j-1]);
						fields[i][j].addNeighbour(fields[i-1][j]);
						fields[i][j].addNeighbour(fields[i-1][j+1]);
						fields[i][j].addNeighbour(fields[i][j-1]);
						fields[i][j].addNeighbour(fields[i][j+1]);
						fields[i][j].addNeighbour(fields[i+1][j-1]);
						fields[i][j].addNeighbour(fields[i+1][j]);
						fields[i][j].addNeighbour(fields[i+1][j+1]);
					}
				}
			}
		}
	}
	
	/**
	 * �jrat�lti a Fieldeket (kezd��llapot, v�letlenszer� akn�k) egy �jrakezdett j�t�khoz.
	 */
	public void refresh() {
		buildUpFields();
	}
	
	/**
	 * Be�ll�tja a kapcsolatot Field �s FieldDisplay k�z�tt mindk�t ir�nyban.
	 * 
	 * @param fd A Field-ekhez kapcsoland� FieldDisplay-ek 2D-es t�mbje.
	 */
	public void setDisplayConnection(FieldDisplay[][] fd) {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				fields[i][j].setFieldDisplay(fd[i][j]);
				fd[i][j].setField(fields[i][j]);
			}
		}
	}
	
	/**
	 * Cs�kkenti a m�g felfedetlen mez�k sz�m�t. Abban az esetben, ha a felfedetlen mez�k sz�ma
	 * megegyezik az akn�k sz�m�val, a felhaszn�l� megnyerte a j�t�kot (endGameWon() met�dus).
	 */
	public void MinelessFieldRevealed() {
		unrevealed_fields--;
		if (unrevealed_fields == mines) {
			endGameWon();
		}
	}
	/**
	 * A gy�ztes j�t�kot kezeli le a met�dus. A m�g felfedetlen (mivel gy�zelem t�rt�nt, ezek biztosan akn�t tartalmaznak)
	 * �s z�szl� n�lk�li mez�k megjel�l�dnek egy z�szl�val.
	 * 
	 * A GameMaster victory flag-j�t bebillenti, �s sz�l a GameMasternek, hogy a j�t�k befejez�d�tt (endGame() met�dus).
	 */
	public void endGameWon() {
		for(int i = 0; i  < rows; i++) {
			for(int j = 0; j < columns; j++) {
				if (fields[i][j].isMined() && !fields[i][j].isFlagged())
					fields[i][j].getFlagged();
			}
		}
		gm.setVictory(true);
		gm.endGame();
	}
	
	/**
	 * A vesztes j�t�kot kezeli le a met�dus. Minden mez�, amin akna vagy z�szl� van felfed�sre ker�l
	 * (�gy l�tja a felhaszn�l�, hogy hol voltak akn�k �s hova rakott rossz z�szl�kat, ha rakott).
	 * 
	 * A GameMaster victory attrib�tum�t false-ra �ll�tja, �s sz�l a GameMasternek, hogy a j�t�k befejez�d�tt (endGame() met�dus).
	 */
	public void endGameLost() {
		for(int i = 0; i  < rows; i++) {
			for(int j = 0; j < columns; j++) {
				if (fields[i][j].isMined() || fields[i][j].isFlagged())
					fields[i][j].getRevealed();
			}
		}
		gm.setVictory(false);
		gm.endGame();
	}
	
	public int getRows() {
		return rows;
	}
	public int getColumns() {
		return columns;
	}
	
	
///////////////////////////////FOR TEST/////////////////////////////////////////////////////////////////////	
	
	public FieldMaster() {}
	
	public Field[][] getFields() {
		return fields;
	}
	
	public void setMines(int m) {
		mines = m;
	}
}
