package gui.game;

import gamelogic.GameMaster;
import gui.basic.Button;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameButton extends Button {
	
	private GameMaster g;
	
	/**
	 * L�trehozza az objektumot, �s inicializ�lja az adatagokat, be�ll�tja a gombhoz tartoz�
	 * actioncommandot �s ActionListener-t.
	 * 
	 * @param w 	A gomb sz�less�ge
	 * @param h		A gomb magass�ga
	 * @param n		A sz�veg, amely majd ki�j�dik a gombra
	 * @param act	Az actioncommand
	 * @param g		A j�t�kot kezel� GameMaster
	 */
	public GameButton(int w, int h, String n, String act, GameMaster g) {
		super(w, h, n);
		this.g = g;
		this.setActionCommand(act);
		ActionListener al = new PushListener();
		this.addActionListener(al);
	}
	
	private class PushListener implements ActionListener {
		
		/**
		 * Gombnyom�s eset�n megh�vja a GameMaster actioncommand-nak megfelel� met�dus�t.
		 * 
		 * A retry actioncommand a j�t�k �jrakezd�s�nek a sz�nd�k�t jelzi.
		 * A menu actioncommand a men�be val� visszat�r�snek a sz�nd�k�t jelzi.
		 */
		public void actionPerformed(ActionEvent ae) {
			if (ae.getActionCommand().equals("retry")) {
				g.refresh();
			}
			if (ae.getActionCommand().equals("menu")) {
				g.backToMenu();
			}
		}
	}
}
