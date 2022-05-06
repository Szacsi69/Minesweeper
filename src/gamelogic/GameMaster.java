package gamelogic;
import gui.game.Board;
import management.GameManager;

public class GameMaster {
	
	private GameManager gm;
	
	private FieldMaster fm;
	private CountMaster cm;
	
	boolean victory;
	String difficulty;
	
	private Board board;
	
	/**
	 * L�trehozza a GameMaster-t a megfelel� be�ll�t�sokkal.
	 * A difficulty param�ter alapj�n hat�rozza meg, hogy milyen FieldMaster-re lesz sz�ks�ge a j�t�k elind�t�s�hoz.
	 * A "Test" difficulty-t csak a tesztek haszn�lj�k, a j�t�kban nem jelenik meg.
	 * 
	 * A FieldMaster l�trehoz�sakor dob�dhat MineException (�rv�nytelen aknasz�m), illetve ismeretlen neh�zs�gt�pus eset�n
	 * UnknownDifficultyException.
	 * 
	 * @param difficulty	Milyen neh�zs�g� legyen az ind�tand� j�t�k
	 * @param gm			A GameManager, amely fennhat�s�ga al� a GameMaster tartozik
	 * @throws Exception	A MineException vagy UnknownDifficultyException, amik keletkezhetnek
	 */
	public GameMaster(String difficulty, GameManager gm) throws Exception {
		this.gm = gm;
		this.difficulty = difficulty;
		switch (difficulty) {
			case "Beginner":
				fm = new FieldMaster(8, 8, 10, this);
				break;
			case "Intermediate":
				fm = new FieldMaster(16, 16, 40, this);
				break;
			case "Advanced":
				fm = new FieldMaster(24, 24, 99, this);
				break;
			case "Test":
				fm = new FieldMaster(3, 3, 0, this);
				break;
			default:
				throw new UnknownDifficultyException();
		}
		cm = new CountMaster();
	}
	
	public void setBoard(Board b) {
		board = b;
	}
	
	public FieldMaster getFieldMaster() {
		return fm;
	}
	
	public CountMaster getCountMaster() {
		return cm;
	}
	
	/**
	 * Kezd��llapotba hozza a FieldMaster-t �s a Board-ot a j�t�k �jrakezd�se v�gett. 
	 */
	public void refresh() {
		fm.refresh();
		board.refresh();
	}
	
	public void setVictory(boolean b) {
		victory = b;
	}
	
	/**
	 * A j�t�k befejez�s�rt felel�s.
	 * Le�ll�tja a CountMaster sz�ml�l�s�t.
	 * Sz�l a Board-nak, hogy a FieldDisplayek ne fogadjanak t�bb felhaszn�l�i inputot.
	 * 
	 * A victory adattag true �rt�ke eset�n sz�l a GameManagernek, hogy egy gy�ztes j�t�k t�rt�nt (gameWon() met�dus).
	 * 
	 * A NullPointerException lekezel�se a teszt esetek helyes lefut�sa v�gett t�rt�nik meg.
	 * (�gy a nem kell inicializ�lni, a tesztk�rnyezet l�trehoz�sakor, a teszt szempontj�b�l irrelev�ns objektumokat)
	 */
	public void endGame() {
		try {
			cm.stopCounter();
			board.endGame();
			
			if (victory)
				gm.gameWon(difficulty, cm.getTime());
		} catch (NullPointerException e) { /*Because of testing*/ }
	}
	
	/**
	 * A met�dus sz�l a GameManagernek, hogy felhaszn�l�i ig�ny keletkezett a j�t�k befejez�s�re �s a 
	 * men�be val� visszat�r�sre.
	 */
	public void backToMenu() {
		gm.backToMenu();
	}
	
//////////////////////////////////////FOR TEST////////////////////////////////////////////////
	
	public void setFieldMaster(FieldMaster f) {
		fm = f;
	}
	public boolean getVictory() {
		return victory;
	}
}
