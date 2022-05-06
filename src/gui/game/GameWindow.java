package gui.game;
import gamelogic.GameMaster;

import javax.swing.JFrame;


public class GameWindow extends JFrame {
	
	/**
	 * L�trehozza az objektumot, be�ll�tja a megfelel� �rt�keket.
	 * L�trehoz egy Board objektumot �s felveszi mag�ra,
	 * ez fogja tartalmazni a teljes j�t�kteret. A l�trehozott Board-ot hozz�rendeli a GameMaster-hez.
	 * A kapott param�tereket tov�bb adja a Board konstruktor�nak, mivel a Board haszn�lja majd fel �ket.
	 * 
	 * @param fieldsize 	Egy aknamez� m�rete a j�t�kban
	 * @param header_height	A header r�sz m�rete a j�t�kablakban
	 * @param bordergap		A Border m�rete
	 * @param g				A j�t�kot kezel� GameMaster
	 */
	public GameWindow(int fieldsize, int header_height, int bordergap, GameMaster g) {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Minesweeper");
		this.setResizable(false);
		
		Board board = new Board(fieldsize, header_height, bordergap, g);
		g.setBoard(board);
		
		this.add(board);

		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
