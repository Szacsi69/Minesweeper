package management;

import gamelogic.GameMaster;

public class GameManager {
	private WindowManager wm;
	private TopListManager tm;
	
	/**
	 * Létrehoz egy GameMaster-t a paraméterként kapott nehézségi szinttel, és visszaadja.
	 * 
	 * A GameMaster létrehozásakor keletkezhet MineException és UnknownDifficultyException is,
	 * ezeket a dobhatja a metódus.
	 * 
	 * @param difficulty	A nehézségi szint, amin a játékot játszani szeretnénk
	 * @return				A létrehozott GameMaster
	 * @throws Exception	MineException, illetve UnknownDifficultyException
	 */
	public GameMaster createGameMaster(String difficulty) throws Exception {
		return new GameMaster(difficulty, this);
	}

	public void setWindowManager(WindowManager wm) {
		this.wm = wm;
	}
	
	public void setTopListManager(TopListManager tm) {
		this.tm = tm;
	}
	
	/**
	 * Szól a TopListManager-nek, hogy az aktuális játékost adja hozzá a toplistához
	 * a megadott nehézségi szinttel és idõvel.
	 * 
	 * Egy nyert játék esetén hívódik meg a metódus.
	 * 
	 * @param difficulty	A nehézségi szint, amin a játékot megnyerték
	 * @param time			Az idõ, ami alatt megnyerték a játékot
	 */
	public void gameWon(String difficulty, long time) {
		tm.addPlayerToTopList(difficulty, time);
	}
	
	/**
	 * Szól a WindowManagernek, hogy a felhasználó vissza akar lépni a játékból a menübe.
	 */
	public void backToMenu() {
		wm.returnToMenu();
	}
}
