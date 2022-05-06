package gui.basic;

import java.awt.Dimension;
import javax.swing.JButton;

public class SizedButton extends JButton {
	protected int width;
	protected int height;
	
	/**
	 * Létrehozza az objektumot, inicializálja az adattagokat.
	 * 
	 * @param w	A gomb szélessége
	 * @param h	A gomb magassága
	 */
	public SizedButton(int w, int h) {
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