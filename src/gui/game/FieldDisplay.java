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
	 * Létrehozza az objektumot, és inicializálja az adatagokat a kezdõállapotnak megfelelõ értékre.
	 * Hozzáadja az obejktumhoz a MouseListener-t, ami majd a felhasználói inputot veszi.
	 * 
	 * @param size	A FieldDisplay szélessége, magassága
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
	 * A kirajzolja a FieldDisplay aktuális állapotát.
	 * 
	 * Ha van beolvasott kép (img != null) az kerül kirajzolásra, más esetben, az éppen
	 * aktuálisan beállított színt veszi fel az objektum.
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
	 * active adattag false-ra állítása
	 * 
	 * Az objektum ezután már nem fogad felhasználói inputot.
	 */
	public void deactivate() {
		active = false;
	}
	
	/**
	 * Beolvas egy képet a paraméterként kapott fájlelérés alapján.
	 * 
	 * Hibás beolvasás esetén IOException dobódhat.
	 * 
	 * @param name 	A beolvasandókép elérése
	 * @return		A beolvasott kép
	 */
	public BufferedImage getImage(String name) {
		BufferedImage temp = null;
		try {
			temp = ImageIO.read(new File(name));
		} catch (IOException e) {System.out.println("Failed to load image.");}
		return temp;
	}
	
	/**
	 * Felkerül egy zászló a mezõre (megfelelõ kép kirajzolása megtörténik).
	 */
	public void flagUp() {
		img = getImage("ingame_images/flag.png");
		repaint();
	}
	
	/**
	 * Lekerül egy zászló a mezõrõl.
	 * 
	 * Mivel zászló csak még feltáratlan mezõn állhat, ezért ha lekerül a zászló, a mezõ
	 * visszakerül az alapértelmezett (felfedetlen) állapotba, amit a szürke szín jelez.
	 */
	public void flagDown() {
		img = null;
		color = new Color(0, 204, 0);
		repaint();
	}
	
	/**
	 * Felfedésre kerül egy mezõ, amelyen nincs akna (az adjacent_mines értékének megfelelõen
	 * a megfelelõ kép kerül kirajzolásra).
	 * 
	 * adjacent_mines = 0 esetén a mezõre nem kerül szám, csak a világos szürke szín jelzi magában,
	 * hogy a mezõt felfedték. Minden más esetben kirajzolódik a szomszédos aknák számának
	 * megfelelõ szám a mezõre.
	 * 
	 * @param adjacent_mines	A FieldDisplay-hez tartozó Field szomszédos aknáinak száma
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
	 * Felfedtek egy aknával rendelkezõ mezõt (megfelelõ kép kirajzolása megtörténik).
	 */
	public void revealMine() {
		img = getImage("ingame_images/mine.png");
		repaint();
	}
	
	/**
	 * Felfedtek egy zászlóval rendelkezõ mezõt, amin nincs akna (megfelelõ kép kirajzolása megtörténik).
	 */
	public void revealWrongFlag() {
		img = getImage("ingame_images/wrong_flag.png");
		repaint();
	}
	
	/**
	 * Az objektum kezdõállapotba kerül.
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
		 * Benyomásra került valamelyik egérgomb, eltárolja mouseleft és mouseright
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
		 * Valamelyik egérgombot felengedték, mouseleft és mouseright értéke alapján meghatározható, hogy
		 * bal-, jobb- vagy duplakattintás történt, ez alapján meghívja a Field megfelelõ metódusát.
		 * 
		 * Balkattintás esetén a felhasználó fel akarja tárni a mezõt.
		 * Jobbkattintás esetén a felhasználó fel akar tenni vagy le akar venni jelölést a mezõrõl.
		 * Duplakattintás esetén a felhasználó fel akarja tárni egy már feltárt mezõ szomszédjait.
		 * 
		 * Minden esetben a FieldDisplay-hez tartozó Field megfelelõ metódusa hívódik meg.
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
		 * Az objektumot világos szürkére színezi ezzel jelezve, hogy ez a kurzor által kijelölt mezõ.
		 * 
		 * Csak még fel nem tárt és zászlóval nem ellátott mezõkön történik ez meg.
		 */
		@Override
		public void mouseEntered(MouseEvent e) {
			if (active && !field.isRevealed() && !field.isFlagged()) {
				color = Color.green;
				repaint();
			}
		}

		/**
		 * Ha a kurzor elhagyta a mezõt, a mezõ visszaáll alapállapotba (szürke szín).
		 * 
		 * Csak még fel nem tárt és zászlóval nem ellátott mezõkön történik ez meg.
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
