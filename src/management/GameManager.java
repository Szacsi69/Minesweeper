package management;

import gamelogic.GameMaster;

public class GameManager {
	private WindowManager wm;
	private TopListManager tm;
	
	/**
	 * L�trehoz egy GameMaster-t a param�terk�nt kapott neh�zs�gi szinttel, �s visszaadja.
	 * 
	 * A GameMaster l�trehoz�sakor keletkezhet MineException �s UnknownDifficultyException is,
	 * ezeket a dobhatja a met�dus.
	 * 
	 * @param difficulty	A neh�zs�gi szint, amin a j�t�kot j�tszani szeretn�nk
	 * @return				A l�trehozott GameMaster
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
	 * Sz�l a TopListManager-nek, hogy az aktu�lis j�t�kost adja hozz� a toplist�hoz
	 * a megadott neh�zs�gi szinttel �s id�vel.
	 * 
	 * Egy nyert j�t�k eset�n h�v�dik meg a met�dus.
	 * 
	 * @param difficulty	A neh�zs�gi szint, amin a j�t�kot megnyert�k
	 * @param time			Az id�, ami alatt megnyert�k a j�t�kot
	 */
	public void gameWon(String difficulty, long time) {
		tm.addPlayerToTopList(difficulty, time);
	}
	
	/**
	 * Sz�l a WindowManagernek, hogy a felhaszn�l� vissza akar l�pni a j�t�kb�l a men�be.
	 */
	public void backToMenu() {
		wm.returnToMenu();
	}
}
