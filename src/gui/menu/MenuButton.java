package gui.menu;
import gui.basic.Button;
import management.WindowManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class MenuButton extends Button {
	
	private WindowManager wm;
	
	/**
	 * Létrehozza az objektumot, és inicializálja az adatagokat, beállítja a gombhoz tartozó
	 * actioncommandot és ActionListener-t.
	 * 
	 * @param w 	A gomb szélessége
	 * @param h		A gomb magassága
	 * @param n		A szöveg, amely majd kiíródik a gombra
	 * @param act	Az actioncommand
	 * @param wm	Az ablakokat kezelõ WindowManager
	 */
	public MenuButton(int w, int h, String n, String act, WindowManager wm) {
		super(w, h, n);
		this.wm = wm;
		
		this.setActionCommand(act);
		ActionListener al = new PushListener();
		this.addActionListener(al);
	}
	
	private class PushListener implements ActionListener {
		
		/**
		 * Gombnyomás esetén meghívja a WindowManager actioncommand-nak megfelelõ metódusát.
		 * 
		 * A play actioncommand a játék elkezdésének a szándékát jelzi.
		 * A toplist actioncommand a toplista megtekintésének a szándékát jelzi.
		 * Az exit actioncommand a programból való kilépésnek a szándékát jelzi.
		 */
		public void actionPerformed(ActionEvent ae) {
			if (ae.getActionCommand().equals("play")) {
				wm.startGame();
			}
			if (ae.getActionCommand().equals("toplist")) {
				wm.toplist();
			}
			if (ae.getActionCommand().equals("exit")) {
				wm.exit();
			}
		}
	}
}
