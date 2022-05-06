package gui.menu;
import management.WindowManager;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class MenuWindow extends JFrame {
	
	/** 
	 * Létrehozza az objektumot, beállítja a megfelelõ értékeket.
	 * Létrehoz egy JPanel-t és felveszi magára, ez fogja tartalmazni a teljes menüt.
	 * A kapott paramétereket tovább adja a MenuPanel konstruktorának, mivel a MenuPanel használja majd fel õket.
	 * 
	 * @param w 	A menü szélessége
	 * @param h 	A menü magassága
	 * @param wm	Az ablakokat kezelõ WindowManager
	 */
	public MenuWindow(int w, int h, WindowManager wm) {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Minesweeper");
		this.setResizable(false);
		
		JPanel mp = new MenuPanel(w, h, wm);
		this.add(mp);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
