package gui.game;
import gamelogic.GameMaster;

import javax.swing.JFrame;


public class GameWindow extends JFrame {
	
	/**
	 * Létrehozza az objektumot, beállítja a megfelelõ értékeket.
	 * Létrehoz egy Board objektumot és felveszi magára,
	 * ez fogja tartalmazni a teljes játékteret. A létrehozott Board-ot hozzárendeli a GameMaster-hez.
	 * A kapott paramétereket tovább adja a Board konstruktorának, mivel a Board használja majd fel õket.
	 * 
	 * @param fieldsize 	Egy aknamezõ mérete a játékban
	 * @param header_height	A header rész mérete a játékablakban
	 * @param bordergap		A Border mérete
	 * @param g				A játékot kezelõ GameMaster
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
