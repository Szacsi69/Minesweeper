package gui.menu;
import management.WindowManager;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class MenuWindow extends JFrame {
	
	/** 
	 * L�trehozza az objektumot, be�ll�tja a megfelel� �rt�keket.
	 * L�trehoz egy JPanel-t �s felveszi mag�ra, ez fogja tartalmazni a teljes men�t.
	 * A kapott param�tereket tov�bb adja a MenuPanel konstruktor�nak, mivel a MenuPanel haszn�lja majd fel �ket.
	 * 
	 * @param w 	A men� sz�less�ge
	 * @param h 	A men� magass�ga
	 * @param wm	Az ablakokat kezel� WindowManager
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
