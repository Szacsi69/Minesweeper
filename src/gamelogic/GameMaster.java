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
	 * Létrehozza a GameMaster-t a megfelelõ beállításokkal.
	 * A difficulty paraméter alapján határozza meg, hogy milyen FieldMaster-re lesz szüksége a játék elindításához.
	 * A "Test" difficulty-t csak a tesztek használják, a játékban nem jelenik meg.
	 * 
	 * A FieldMaster létrehozásakor dobódhat MineException (érvénytelen aknaszám), illetve ismeretlen nehézségtípus esetén
	 * UnknownDifficultyException.
	 * 
	 * @param difficulty	Milyen nehézségû legyen az indítandó játék
	 * @param gm			A GameManager, amely fennhatósága alá a GameMaster tartozik
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
	 * Kezdõállapotba hozza a FieldMaster-t és a Board-ot a játék újrakezdése végett. 
	 */
	public void refresh() {
		fm.refresh();
		board.refresh();
	}
	
	public void setVictory(boolean b) {
		victory = b;
	}
	
	/**
	 * A játék befejezésért felelõs.
	 * Leállítja a CountMaster számlálását.
	 * Szól a Board-nak, hogy a FieldDisplayek ne fogadjanak több felhasználói inputot.
	 * 
	 * A victory adattag true értéke esetén szól a GameManagernek, hogy egy gyõztes játék történt (gameWon() metódus).
	 * 
	 * A NullPointerException lekezelése a teszt esetek helyes lefutása végett történik meg.
	 * (így a nem kell inicializálni, a tesztkörnyezet létrehozásakor, a teszt szempontjából irreleváns objektumokat)
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
	 * A metódus szól a GameManagernek, hogy felhasználói igény keletkezett a játék befejezésére és a 
	 * menübe való visszatérésre.
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
