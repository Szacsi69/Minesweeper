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
	 * L�trehozza az objektumot, be�ll�tja mag�t a Counter display-ek�nt, �s 
	 * elind�tja a Counter sz�ml�l�st.
	 * 
	 * @param w A panel sz�less�ge
	 * @param h A panel magass�ga
	 * @param c	A TimePanel-hez tartoz� Counter
	 */
	public TimePanel(int w, int h, Counter c) {
		super(w, h);
		counter = c;
		counter.setDisplay(this);
		counter.start();
	}
	
	/**
	 * Kirajzolja mag�ra a Counter-ben tal�lhat� eddig eltelt id�t MM:SS form�tumban.
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
	 * Kirajzol egy String-et a komponens k�zep�re, figyelembe v�ve a String hossz�t,
	 * �s a haszn�lt Font-ot.
	 * 
	 * @param g	Haszn�lt Graphics
	 * @param s	Kirajzoland� String
	 * @param f	Haszn�lt Font
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
	 * A TimePanel kezd��llapotba ker�l.
	 * Ehhez sz�ks�g van egy �j Counter-re, amely 0-t�l kezd ism�t sz�molni.
	 * Az objektum be�ll�tja mag�t a Counter display-ek�nt, �s 
	 * elind�tja a sz�ml�l�st, majd �jrarajzolja mag�t a most m�r
	 * �j Counter �rt�k�nek megfelel�en.
	 * 
	 * @param c A TimePanel-hez tartoz� �j Counter
	 */
	public void refresh(Counter c) {
		counter = c;
		counter.setDisplay(this);
		counter.start();
		repaint();
	}
}
