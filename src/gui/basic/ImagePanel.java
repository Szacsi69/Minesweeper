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
	 * L�trehozza az objektumot, inicializ�lja az adattagokat.
	 * 
	 * @param w				A panel sz�less�ge
	 * @param h				A panel magass�ga
	 * @param imgfilename	A kirajzoland� k�p el�r�se
	 */
	public ImagePanel(int w, int h, String imgfilename) {
		super(w, h);
		imgfile_name = imgfilename;
	}
	
	/**
	 * Kirajzolja a beolvasott k�pet, a panel teljes sz�less�g�ben, magass�g�ban.
	 */
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		img = getImage(imgfile_name);
		g2.drawImage(img, 0, 0, super.width, super.height, null);
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
	
}
