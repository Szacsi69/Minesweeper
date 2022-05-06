package management;
import gamelogic.GameMaster;
import gui.game.GameWindow;
import gui.menu.MenuWindow;
import gui.toplist.TopListWindow;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class WindowManager {
	private GameManager gm;
	private TopListManager tm;
	
	private JFrame menu;
	private JFrame gw;
	
	public void setGameManager(GameManager gm) {
		this.gm = gm;
	}

	public void setTopListManager(TopListManager tm) {
		this.tm = tm;
	}
	
	/**
	 * Megjelen�ti a men�t.
	 */
	public void start() {
		menu = new MenuWindow(600, 750, this);
	}
	
	/**
	 * Megnyitja az ablakot, amiben zajlik a j�t�k.
	 * 
	 * El�sz�r bek�r a felhaszn�l�t�l JOptionPane form�j�ban egy neh�zs�gi szintet �s egy nevet.
	 * Ha erre az adatbek�r�sre a felhaszn�l� kiel�g�t� v�laszt adott, akkor elt�nteti a men�ablakot, �s
	 * megjelen�ti a j�t�kablakot, lek�rve a GameManager-t�l a megfelel� GameMaster-t.
	 * 
	 * Ha a GameMaster l�trehoz�sakor Exception keletkezett, azt elkapja, �s ki�rja az Exception �zenet�t.
	 */
	public void startGame() {
		Object[] difficulties = {"Beginner", "Intermediate", "Advanced"};
		String difficulty = (String) JOptionPane.showInputDialog(menu, "Choose difficulty:\n" + "Beginner - 8x8, 10 mine\n"
														+ "Intermediate - 16x16, 40 mine\n" + "Advanced - 24x24, 99 mine", "Minesweeper", 
														JOptionPane.PLAIN_MESSAGE, null, difficulties, "Beginner");
		if (difficulty != null) {
			String playername = (String) JOptionPane.showInputDialog(menu, "Enter Name:", "Minesweeper", JOptionPane.PLAIN_MESSAGE);
			if (playername != null && playername.length() > 0) {
				tm.setActualPlayerName(playername);
				try {
					GameMaster g = gm.createGameMaster(difficulty);
					menu.setVisible(false);
					gw = new GameWindow(30, 60, 6, g);
				} catch (Exception e) {System.out.println(e.getMessage());}
				
			}
		}
	}
	
	/**
	 * A met�dus bez�rja a j�t�k ablak�t �s megjelen�ti a men� ablak�t.
	 */
	public void returnToMenu() {
		gw.setVisible(false);
		gw.dispose();
		menu.setVisible(true);
	}
	
	/**
	 * Megjelen�ti a toplista ablak�t.
	 */
	public void toplist() {
		JDialog tw = new TopListWindow(300, 300, tm);
	}
	
	/**
	 * A program kil�p.
	 */
	public void exit() {
		System.exit(0);
	}

	
}
