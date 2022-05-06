package gui.game;
import gamelogic.*;
import gui.basic.SizedPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

public class FieldDisplay extends SizedPanel {
	private int size;
	private boolean active;
	private Field field;
	private Color color;
	private BufferedImage img;
	
	private boolean mouseleft;
	private boolean mouseright;
	
	/**
	 * L�trehozza az objektumot, �s inicializ�lja az adatagokat a kezd��llapotnak megfelel� �rt�kre.
	 * Hozz�adja az obejktumhoz a MouseListener-t, ami majd a felhaszn�l�i inputot veszi.
	 * 
	 * @param size	A FieldDisplay sz�less�ge, magass�ga
	 */
	public FieldDisplay(int size) {
		super(size, size);
		this.size = size;
		active = true;
		color = new Color(0, 204, 0);
		img = null;
		
		mouseleft = false;
		mouseright = false;
		
		MouseListener ml = new ClickListener();
		this.addMouseListener(ml);
		
	}
	
	/**
	 * A kirajzolja a FieldDisplay aktu�lis �llapot�t.
	 * 
	 * Ha van beolvasott k�p (img != null) az ker�l kirajzol�sra, m�s esetben, az �ppen
	 * aktu�lisan be�ll�tott sz�nt veszi fel az objektum.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		this.setBackground(Color.yellow);
		if (img != null) {
			g2.drawImage(img, 0, 0, size, size, null);
		}
		else {
			g2.setColor(color);
			g2.fillRect(0, 0, size, size);
		}
		g2.setColor(Color.black);
		g2.drawRect(0, 0, size, size);
	}
	
	public void setField(Field f) {
		field = f;
	}
	
	/**
	 * active adattag false-ra �ll�t�sa
	 * 
	 * Az objektum ezut�n m�r nem fogad felhaszn�l�i inputot.
	 */
	public void deactivate() {
		active = false;
	}
	
	/**
	 * Beolvas egy k�pet a param�terk�nt kapott f�jlel�r�s alapj�n.
	 * 
	 * Hib�s beolvas�s eset�n IOException dob�dhat.
	 * 
	 * @param name 	A beolvasand�k�p el�r�se
	 * @return		A beolvasott k�p
	 */
	public BufferedImage getImage(String name) {
		BufferedImage temp = null;
		try {
			temp = ImageIO.read(new File(name));
		} catch (IOException e) {System.out.println("Failed to load image.");}
		return temp;
	}
	
	/**
	 * Felker�l egy z�szl� a mez�re (megfelel� k�p kirajzol�sa megt�rt�nik).
	 */
	public void flagUp() {
		img = getImage("ingame_images/flag.png");
		repaint();
	}
	
	/**
	 * Leker�l egy z�szl� a mez�r�l.
	 * 
	 * Mivel z�szl� csak m�g felt�ratlan mez�n �llhat, ez�rt ha leker�l a z�szl�, a mez�
	 * visszaker�l az alap�rtelmezett (felfedetlen) �llapotba, amit a sz�rke sz�n jelez.
	 */
	public void flagDown() {
		img = null;
		color = new Color(0, 204, 0);
		repaint();
	}
	
	/**
	 * Felfed�sre ker�l egy mez�, amelyen nincs akna (az adjacent_mines �rt�k�nek megfelel�en
	 * a megfelel� k�p ker�l kirajzol�sra).
	 * 
	 * adjacent_mines = 0 eset�n a mez�re nem ker�l sz�m, csak a vil�gos sz�rke sz�n jelzi mag�ban,
	 * hogy a mez�t felfedt�k. Minden m�s esetben kirajzol�dik a szomsz�dos akn�k sz�m�nak
	 * megfelel� sz�m a mez�re.
	 * 
	 * @param adjacent_mines	A FieldDisplay-hez tartoz� Field szomsz�dos akn�inak sz�ma
	 */
	public void revealNoMine(int adjacent_mines) {
		switch (adjacent_mines) {
			case 0:
				img = null;
				color = Color.yellow;
				repaint();
				break;
			case 1:
				img = getImage("ingame_images/1mine.png");
				repaint();
				break;
			case 2:
				img = getImage("ingame_images/2mine.png");
				repaint();
				break;
			case 3:
				img = getImage("ingame_images/3mine.png");
				repaint();
				break;
			case 4:
				img = getImage("ingame_images/4mine.png");
				repaint();
				break;
			case 5:
				img = getImage("ingame_images/5mine.png");
				repaint();
				break;
			case 6:
				img = getImage("ingame_images/6mine.png");
				repaint();
				break;
			case 7:
				img = getImage("ingame_images/7mine.png");
				repaint();
				break;
			case 8:
				img = getImage("ingame_images/8mine.png");
				repaint();
				break;
			default:
				break;
		}
	}
	
	/**
	 * Felfedtek egy akn�val rendelkez� mez�t (megfelel� k�p kirajzol�sa megt�rt�nik).
	 */
	public void revealMine() {
		img = getImage("ingame_images/mine.png");
		repaint();
	}
	
	/**
	 * Felfedtek egy z�szl�val rendelkez� mez�t, amin nincs akna (megfelel� k�p kirajzol�sa megt�rt�nik).
	 */
	public void revealWrongFlag() {
		img = getImage("ingame_images/wrong_flag.png");
		repaint();
	}
	
	/**
	 * Az objektum kezd��llapotba ker�l.
	 */
	public void refresh() {
		active = true;
		color = new Color(0, 204, 0);
		img = null;
		repaint();
	}
	
	private class ClickListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
		
		}
		/**
		 * Benyom�sra ker�lt valamelyik eg�rgomb, elt�rolja mouseleft �s mouseright
		 * adatagokban, hogy melyik.
		 */
		@Override
		public void mousePressed(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				mouseleft = true;;
			}
			if (SwingUtilities.isRightMouseButton(e)) {
				mouseright = true;;
			}
		}
		
		/**
		 * Valamelyik eg�rgombot felengedt�k, mouseleft �s mouseright �rt�ke alapj�n meghat�rozhat�, hogy
		 * bal-, jobb- vagy duplakattint�s t�rt�nt, ez alapj�n megh�vja a Field megfelel� met�dus�t.
		 * 
		 * Balkattint�s eset�n a felhaszn�l� fel akarja t�rni a mez�t.
		 * Jobbkattint�s eset�n a felhaszn�l� fel akar tenni vagy le akar venni jel�l�st a mez�r�l.
		 * Duplakattint�s eset�n a felhaszn�l� fel akarja t�rni egy m�r felt�rt mez� szomsz�djait.
		 * 
		 * Minden esetben a FieldDisplay-hez tartoz� Field megfelel� met�dusa h�v�dik meg.
		 */
		@Override
		public void mouseReleased(MouseEvent e) {
			if (mouseleft && !mouseright) {
				if (active) field.reveal();
				mouseleft = false;
			}
			if (!mouseleft && mouseright) {
				if (active) field.getFlagged();
				mouseright = false;
			}
			if (mouseleft && mouseright) {
				if (active) field.revealNeighbours();
				mouseleft = false;
				mouseright = false;
			}
		}

		/**
		 * Az objektumot vil�gos sz�rk�re sz�nezi ezzel jelezve, hogy ez a kurzor �ltal kijel�lt mez�.
		 * 
		 * Csak m�g fel nem t�rt �s z�szl�val nem ell�tott mez�k�n t�rt�nik ez meg.
		 */
		@Override
		public void mouseEntered(MouseEvent e) {
			if (active && !field.isRevealed() && !field.isFlagged()) {
				color = Color.green;
				repaint();
			}
		}

		/**
		 * Ha a kurzor elhagyta a mez�t, a mez� vissza�ll alap�llapotba (sz�rke sz�n).
		 * 
		 * Csak m�g fel nem t�rt �s z�szl�val nem ell�tott mez�k�n t�rt�nik ez meg.
		 */
		@Override
		public void mouseExited(MouseEvent e) {
			if (!field.isRevealed() && !field.isFlagged()) {
				color = new Color(0, 204, 0);
				repaint();
			}
		}
	}
	
/////////////////////////////////////////FOR TEST//////////////////////////////////////////////////////////////////
	
	public boolean isActive() {
		return active;
	}
	
	public Color getColor() {
		return color;
	}
	
	public boolean isImageNull() {
		return img == null;
	}
}
