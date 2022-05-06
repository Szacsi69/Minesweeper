package gui.menu;
import gui.basic.ImagePanel;
import gui.basic.SizedPanel;
import management.WindowManager;

import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;



public class MenuPanel extends SizedPanel {
	
	/**
	 * Létrehozza az objektumot, és felépíti annak tartalmát.
	 * 
	 * A MenuPanel két Jpanel-t tartalmaz (title, ButtonPanel), amelyeken
	 * egy-egy kép található (ImagePanel). Ezek BoxLayout-ban egymás alatt helyezkednek el.
	 * 
	 * A ButtonPanelen belül található továbbá három gomb, melyek szintén BoxLayout-ban egymás
	 * alatt találhatóak.
	 * 
	 * A paraméterként kapott WindowManager a gombok konstruktorainak adódik tovább.
	 * 
	 * @param w		A panel szélessége
	 * @param h		A panel magassága
	 * @param wm	Az ablakokat kezelõ WindowManager
	 */
	public MenuPanel(int w, int h, WindowManager wm) {
		super(w, h);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel title = new ImagePanel(w, h/4, "menu_images/menu_title.png");
		add(title); 
		
		JPanel ButtonPanel = new ImagePanel(w, 3 * h/4, "menu_images/menu_background.png");
		JButton play = new MenuButton(w/2, h/6, "Play", "play", wm);
		JButton toplist = new MenuButton(w/2, h/6, "Toplist", "toplist", wm);
		JButton exit = new MenuButton(w/2, h/6, "Exit", "exit", wm);
		
		ButtonPanel.setLayout(new BoxLayout(ButtonPanel, BoxLayout.Y_AXIS));
		ButtonPanel.add(Box.createRigidArea(new Dimension(0, h/16)));
		ButtonPanel.add(play);
		ButtonPanel.add(Box.createRigidArea(new Dimension(0, h/16)));
		ButtonPanel.add(toplist);
		ButtonPanel.add(Box.createRigidArea(new Dimension(0, h/16)));
		ButtonPanel.add(exit);
		ButtonPanel.add(Box.createRigidArea(new Dimension(0, h/16)));
		add(ButtonPanel);
	}
}
