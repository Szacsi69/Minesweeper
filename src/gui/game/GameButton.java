package gui.game;

import gamelogic.GameMaster;
import gui.basic.Button;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameButton extends Button {
	
	private GameMaster g;
	
	/**
	 * Létrehozza az objektumot, és inicializálja az adatagokat, beállítja a gombhoz tartozó
	 * actioncommandot és ActionListener-t.
	 * 
	 * @param w 	A gomb szélessége
	 * @param h		A gomb magassága
	 * @param n		A szöveg, amely majd kiíjódik a gombra
	 * @param act	Az actioncommand
	 * @param g		A játékot kezelõ GameMaster
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
		 * Gombnyomás esetén meghívja a GameMaster actioncommand-nak megfelelõ metódusát.
		 * 
		 * A retry actioncommand a játék újrakezdésének a szándékát jelzi.
		 * A menu actioncommand a menübe való visszatérésnek a szándékát jelzi.
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
