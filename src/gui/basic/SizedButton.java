package gui.basic;

import java.awt.Dimension;
import javax.swing.JButton;

public class SizedButton extends JButton {
	protected int width;
	protected int height;
	
	/**
	 * L�trehozza az objektumot, inicializ�lja az adattagokat.
	 * 
	 * @param w	A gomb sz�less�ge
	 * @param h	A gomb magass�ga
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