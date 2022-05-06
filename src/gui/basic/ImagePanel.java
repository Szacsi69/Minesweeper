package gui.basic;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImagePanel extends SizedPanel {
	
	public BufferedImage img;
	public String imgfile_name;
	
	/**
	 * Létrehozza az objektumot, inicializálja az adattagokat.
	 * 
	 * @param w				A panel szélessége
	 * @param h				A panel magassága
	 * @param imgfilename	A kirajzolandó kép elérése
	 */
	public ImagePanel(int w, int h, String imgfilename) {
		super(w, h);
		imgfile_name = imgfilename;
	}
	
	/**
	 * Kirajzolja a beolvasott képet, a panel teljes szélességében, magasságában.
	 */
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		img = getImage(imgfile_name);
		g2.drawImage(img, 0, 0, super.width, super.height, null);
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
	
}
