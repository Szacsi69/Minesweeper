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
	 * Inicializálja az adattagokat, érvénytelen aknaszám esetén kivételt dob.
	 * 
	 * @param rCount 		Sorok száma
	 * @param cCount 		Oszlopok száma
	 * @param mCount 		Aknák száma
	 * @param g				A GameMaster, amely fennhatósága alá tartozik a FieldMaster
	 * @throws Exception	Érvénytelen aknaszám esetén MineException dobódik
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
	 * A már inicializált adattagok segítségével értéket ad a többi attribútumnak is.
	 * Létrehozza a Field-eket, szétosztja közöttük véletlenszerûen az aknákat, illetve
	 * beállítja a szomszédságokat.
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
	 * Újratölti a Fieldeket (kezdõállapot, véletlenszerû aknák) egy újrakezdett játékhoz.
	 */
	public void refresh() {
		buildUpFields();
	}
	
	/**
	 * Beállítja a kapcsolatot Field és FieldDisplay között mindkét irányban.
	 * 
	 * @param fd A Field-ekhez kapcsolandó FieldDisplay-ek 2D-es tömbje.
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
	 * Csökkenti a még felfedetlen mezõk számát. Abban az esetben, ha a felfedetlen mezõk száma
	 * megegyezik az aknák számával, a felhasználó megnyerte a játékot (endGameWon() metódus).
	 */
	public void MinelessFieldRevealed() {
		unrevealed_fields--;
		if (unrevealed_fields == mines) {
			endGameWon();
		}
	}
	/**
	 * A gyõztes játékot kezeli le a metódus. A még felfedetlen (mivel gyõzelem történt, ezek biztosan aknát tartalmaznak)
	 * és zászló nélküli mezõk megjelölõdnek egy zászlóval.
	 * 
	 * A GameMaster victory flag-jét bebillenti, és szól a GameMasternek, hogy a játék befejezõdött (endGame() metódus).
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
	 * A vesztes játékot kezeli le a metódus. Minden mezõ, amin akna vagy zászló van felfedésre kerül
	 * (így látja a felhasználó, hogy hol voltak aknák és hova rakott rossz zászlókat, ha rakott).
	 * 
	 * A GameMaster victory attribútumát false-ra állítja, és szól a GameMasternek, hogy a játék befejezõdött (endGame() metódus).
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
