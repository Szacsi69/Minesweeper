package management;

public class Manager {
	GameManager gm;
	WindowManager wm;
	TopListManager tm;
	
	/**
	 * Létrehozza a managereket, kialakítja a köztük lévõ kapcsolatokat,
	 * a TopListManager fájlból betölti az adatokat, a WindowManager pedig megjeleníti a menüt.
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
