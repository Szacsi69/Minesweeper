package gui.menu;
import gui.basic.Button;
import management.WindowManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class MenuButton extends Button {
	
	private WindowManager wm;
	
	/**
	 * L�trehozza az objektumot, �s inicializ�lja az adatagokat, be�ll�tja a gombhoz tartoz�
	 * actioncommandot �s ActionListener-t.
	 * 
	 * @param w 	A gomb sz�less�ge
	 * @param h		A gomb magass�ga
	 * @param n		A sz�veg, amely majd ki�r�dik a gombra
	 * @param act	Az actioncommand
	 * @param wm	Az ablakokat kezel� WindowManager
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
		 * Gombnyom�s eset�n megh�vja a WindowManager actioncommand-nak megfelel� met�dus�t.
		 * 
		 * A play actioncommand a j�t�k elkezd�s�nek a sz�nd�k�t jelzi.
		 * A toplist actioncommand a toplista megtekint�s�nek a sz�nd�k�t jelzi.
		 * Az exit actioncommand a programb�l val� kil�p�snek a sz�nd�k�t jelzi.
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
