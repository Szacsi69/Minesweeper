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
	 * Megjeleníti a menüt.
	 */
	public void start() {
		menu = new MenuWindow(600, 750, this);
	}
	
	/**
	 * Megnyitja az ablakot, amiben zajlik a játék.
	 * 
	 * Elõször bekér a felhasználótól JOptionPane formájában egy nehézségi szintet és egy nevet.
	 * Ha erre az adatbekérésre a felhasználó kielégítõ választ adott, akkor eltünteti a menüablakot, és
	 * megjeleníti a játékablakot, lekérve a GameManager-tõl a megfelelõ GameMaster-t.
	 * 
	 * Ha a GameMaster létrehozásakor Exception keletkezett, azt elkapja, és kiírja az Exception üzenetét.
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
	 * A metódus bezárja a játék ablakát és megjeleníti a menü ablakát.
	 */
	public void returnToMenu() {
		gw.setVisible(false);
		gw.dispose();
		menu.setVisible(true);
	}
	
	/**
	 * Megjeleníti a toplista ablakát.
	 */
	public void toplist() {
		JDialog tw = new TopListWindow(300, 300, tm);
	}
	
	/**
	 * A program kilép.
	 */
	public void exit() {
		System.exit(0);
	}

	
}
