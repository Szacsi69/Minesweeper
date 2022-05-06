package gui.game;
import gui.basic.SizedPanel;
import gamelogic.Counter;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class TimePanel extends SizedPanel {
	
	private Counter counter;
	
	/**
	 * Létrehozza az objektumot, beállítja magát a Counter display-eként, és 
	 * elindítja a Counter számlálást.
	 * 
	 * @param w A panel szélessége
	 * @param h A panel magassága
	 * @param c	A TimePanel-hez tartozó Counter
	 */
	public TimePanel(int w, int h, Counter c) {
		super(w, h);
		counter = c;
		counter.setDisplay(this);
		counter.start();
	}
	
	/**
	 * Kirajzolja magára a Counter-ben található eddig eltelt idõt MM:SS formátumban.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.orange);
		Font font = new Font("Arial", Font.PLAIN, super.height/2);
		
		String min = Long.toString(counter.getMinutes() % 60);
		String sec = Long.toString(counter.getSeconds() % 60);
		StringBuilder sb_min = new StringBuilder(min);
		if (sb_min.length() < 2 ) sb_min.insert(0, "0");
		StringBuilder sb_sec = new StringBuilder(sec);
		if (sb_sec.length() < 2 ) sb_sec.insert(0, "0");
		
		String time = sb_min.toString() + ":" + sb_sec.toString();
		
		drawCenteredString(g, time, font);
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
		int x = 0 + (super.width - fm.stringWidth("99:99")) / 2;
		int y = 0 + (super.height - fm.getHeight()) / 2 + fm.getAscent();
		g.setFont(f);
		g.setColor(Color.red);
		g.drawString(s, x, y);
		
	}
	
	/**
	 * A TimePanel kezdõállapotba kerül.
	 * Ehhez szükség van egy új Counter-re, amely 0-tól kezd ismét számolni.
	 * Az objektum beállítja magát a Counter display-eként, és 
	 * elindítja a számlálást, majd újrarajzolja magát a most már
	 * új Counter értékének megfelelõen.
	 * 
	 * @param c A TimePanel-hez tartozó új Counter
	 */
	public void refresh(Counter c) {
		counter = c;
		counter.setDisplay(this);
		counter.start();
		repaint();
	}
}
