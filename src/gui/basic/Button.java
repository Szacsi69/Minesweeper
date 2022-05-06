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
	 * Létrehozza a Button-t, inicializálja az adattagokat az alapértelmezett értékekre.
	 * 
	 * Beállítja a középreigazítást, a kikapcsolaja a content area kirajzolását (így az majd egyedi módon megtehetõ lesz),
	 * és beállít egy fekete keretet a gomb körül.
	 * 
	 * @param w	A gomb szélessége
	 * @param h	A gomb magassága	 
	 * @param n	A szöveg, amely majd kiíródik a gombra
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
	 * A gomb kirajzolását végzi, a megfelelõ állapotban, a megfelelõ színnel.
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
	 * Kirajzol egy String-et a komponens közepére, figyelembe véve a String hosszát,
	 * és a használt Font-ot.
	 * 
	 * @param g	Használt Graphics
	 * @param s	Kirajzolandó String
	 * @param f	Használt Font
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
