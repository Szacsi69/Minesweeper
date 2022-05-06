package gui.basic;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.BorderFactory;

public class Button extends SizedButton {
	
	private String name;
	private Color background;
	private Color background_pressed;
	private Color background_hover;
	private Color textcolor;
	
	/**
	 * L�trehozza a Button-t, inicializ�lja az adattagokat az alap�rtelmezett �rt�kekre.
	 * 
	 * Be�ll�tja a k�z�preigaz�t�st, a kikapcsolaja a content area kirajzol�s�t (�gy az majd egyedi m�don megtehet� lesz),
	 * �s be�ll�t egy fekete keretet a gomb k�r�l.
	 * 
	 * @param w	A gomb sz�less�ge
	 * @param h	A gomb magass�ga	 
	 * @param n	A sz�veg, amely majd ki�r�dik a gombra
	 */
	public Button(int w, int h, String n) {
		super(w, h);
		name = n;
		background = new Color(0, 204, 0);
		background_pressed = new Color(0, 255, 128);
		background_hover = Color.green;
		textcolor = Color.black;
		
		setAlignmentX(Component.CENTER_ALIGNMENT);
		super.setContentAreaFilled(false);
		this.setBorder(BorderFactory.createLineBorder(Color.black, 5));
	}
	
	/**
	 * A gomb kirajzol�s�t v�gzi, a megfelel� �llapotban, a megfelel� sz�nnel.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (this.getModel().isPressed()) {
			g.setColor(background_pressed);
		}
		else if (this.getModel().isRollover() ) {
			g.setColor(background_hover);
		}
		else {
			g.setColor(background);
		}
		g.fillRect(0, 0, width, height);
		Font font = new Font("Arial", Font.BOLD, height/2);
		drawCenteredString(g, name, font);
	}
	
	/**
	 * Kirajzol egy String-et a komponens k�zep�re, figyelembe v�ve a String hossz�t,
	 * �s a haszn�lt Font-ot.
	 * 
	 * @param g	Haszn�lt Graphics
	 * @param s	Kirajzoland� String
	 * @param f	Haszn�lt Font
	 */
	public void drawCenteredString(Graphics g, String s, Font f) {
		FontMetrics fm = g.getFontMetrics(f);
		int x = 0 + (super.width - fm.stringWidth(s)) / 2;
		int y = 0 + (super.height - fm.getHeight()) / 2 + fm.getAscent();
		g.setFont(f);
		g.setColor(textcolor);
		g.drawString(s, x, y);
	}
}
