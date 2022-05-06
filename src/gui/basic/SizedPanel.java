package gui.basic;

import java.awt.Dimension;
import javax.swing.JPanel;

public class SizedPanel extends JPanel {
	protected int width;
	protected int height;
	
	/**
	 * L�trehozza az objektumot, inicializ�lja az adattagokat.
	 * 
	 * @param w	A panel sz�less�ge
	 * @param h	A panel magass�ga
	 */
	public SizedPanel(int w, int h) {
		width = w;
		height = h;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}
	
	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}
	
	@Override
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}
}