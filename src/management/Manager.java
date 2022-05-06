package management;

public class Manager {
	GameManager gm;
	WindowManager wm;
	TopListManager tm;
	
	/**
	 * L�trehozza a managereket, kialak�tja a k�zt�k l�v� kapcsolatokat,
	 * a TopListManager f�jlb�l bet�lti az adatokat, a WindowManager pedig megjelen�ti a men�t.
	 */
	public void startProgram() {
		gm = new GameManager();
		wm = new WindowManager();
		tm = new TopListManager("toplist.xml");
		
		wm.setGameManager(gm);
		wm.setTopListManager(tm);
		gm.setWindowManager(wm);
		gm.setTopListManager(tm);
		
		tm.load();
		wm.start();
	}
}
