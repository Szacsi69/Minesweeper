package gui.game;
import gamelogic.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Board extends JPanel {
	
	private FieldDisplay[][] fields;
	private TimePanel tp;
	private JButton retry;
	private JButton menu;
	
	private int columns;
	private int rows;
	private int fieldsize;
	private int header_height;
	private int bordergap;
	
	private FieldMaster fm;
	private CountMaster cm;
	
	private GameMaster g;
	
	/**
	 * Létrehozza az objektumot, inicializálja az adattagokat.
	 * 
	 * A Board-on belül két JPanel található (HeaderPanel, BodyPanel).
	 * Ezek BoxLayout-ban helyezkednek el, egymás alatt. A BoxLayout tekintettel van
	 * a komponensek méretére, így könnyebb tervezni a komponensek méretével.
	 * 
	 * Az inicializálásokon, JPanel-ek létrehozásán, és add-olásán túl, még megtörténik a
	 * a GameMaster Field-jei és a Board FieldDisplay-jei között lévõ kapcsolat kialakítása (setDisplayConnection()),
	 * illetve a Border beállítása.
	 * 
	 * @param fsize A Boardon található FieldDisplay-ek mérete
	 * @param hh	A HeaderPanel magassága
	 * @param gap	A Border vastagsága
	 * @param g		A Boardhoz tartozó GameMaster
	 */
	public Board(int fsize, int hh, int gap, GameMaster g) {
	
		this.g = g; 
		fm = g.getFieldMaster();
		cm = g.getCountMaster();
		
		fieldsize = fsize;
		header_height = hh;
		bordergap = gap;
		rows = fm.getRows();
		columns = fm.getColumns();
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel HeaderPanel = buildHeaderPanel();
		JPanel BodyPanel = buildBodyPanel();

		fm.setDisplayConnection(fields);
		
		this.add(HeaderPanel);
		this.add(BodyPanel);
		this.setBorder(BorderFactory.createEmptyBorder(bordergap, bordergap, bordergap, bordergap));
	}
	
	/**
	 * A HeaderPanel felépítését végzi, majd visszatér vele.
	 * 
	 * A HeaderPanel tartalmazza, az idõmérésére szolgáló számlálót, és két gombot a játék újrakezdéséhez, és
	 * a menübe való visszatéréshez.
	 * 
	 * A HeaderPanel egy TimePanel-bõl és két JButton-bõl áll, ezek egy (1,2)-es GridLayout-ban helyezkednek el,
	 * ezen belül a gombok pedig egy BoxLayout-ban egymás alatt.
	 * 
	 * A TimePanel és az egymás alatt levõ gombok is, a Board (Border-ek nélküli) szélességének felét teszik ki.
	 * Magasságuk, pedig a teljes HeaderPanel magasságát (egy gomb csak a felét, mivel a gombok egymás alatt találhatók).
	 * 
	 * @return	A felépített HeaderPanel
	 */
	public JPanel buildHeaderPanel() {
		JPanel HeaderPanel = new JPanel();
		
		tp = new TimePanel(fieldsize*columns/2, header_height, cm.makeACounter());
		
		JPanel ButtonPanel = new JPanel();
		ButtonPanel.setLayout(new BoxLayout(ButtonPanel, BoxLayout.Y_AXIS));
		retry = new GameButton(fieldsize*columns/2, header_height/2, "Retry", "retry", g);
		menu = new GameButton(fieldsize*columns/2, header_height/2, "Menu", "menu", g);
		ButtonPanel.add(retry);
		ButtonPanel.add(menu);
		
		HeaderPanel.setLayout(new GridLayout(1, 2));
		HeaderPanel.add(tp);
		HeaderPanel.add(ButtonPanel);
		return HeaderPanel;
	}
	
	/**
	 * A BodyPanel felépítését végzi, majd visszatér vele.
	 * 
	 * A BodyPanel tartalmazza a játéktérként szolgáló aknamezõket (FieldDisplay).
	 * 
	 * Létrehozza a GameMaster-nek megfelelõ sor- és oszlopszámmal rendelkezõ FieldDisplay 2D-s tömböt, majd
	 * feltölti FieldDisplay-ekkel, amelyek egy GridLayout-tal igazodnak egymáshoz.
	 * 
	 * @return A felépített BodyPanel
	 */
	public JPanel buildBodyPanel() {
		JPanel BodyPanel = new JPanel();
		BodyPanel.setLayout(new GridLayout(rows, columns));
		
		fields = new FieldDisplay[rows][columns];
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				fields[i][j] = new FieldDisplay(fieldsize);
				BodyPanel.add(fields[i][j]);
			}
		}
		return BodyPanel;
	}
	
	/**
	 * Visszaadja a Board méretét.
	 * A szélesség a GameMaster által meghatározott oszlopszám, és a mezõméret, illetve a Border mérete alapján kalkulálódik.
	 * A magasság a GameMaster által meghatározott sorszám, és a mezõméret, illetve
	 * a HeaderPanel magassága és a Border mérete alapján kalkulálódik
	 */
	@Override
    public Dimension getPreferredSize() {
    	Dimension d = new Dimension();
    	d.setSize(fieldsize * columns + 2*bordergap, fieldsize * rows + header_height + 2*bordergap);
        return d;
    }
	
	/**
	 * Beállítja a Board háttér színét.
	 */
    @Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(new Color(102, 178, 255));
	}
	
    /**
     * Kezdõállapotba hozza, a FieldDisplay-eket, és létrehozza a kapcsolatot köztük és
     * a GameMaster Field-jei között, valamint a TimePanel-t is kezõdállapotba rakja.
     * 
     * A TimePanel kezdõállapotba hozása egy új Counter lekérését is jelenti a CountMaster-tõl
     * (0-ról induljon a számlálás).
     * 
     * A játék újrakezdése esetén hívódik meg.
     */
	public void refresh() {
		for(int i = 0; i < rows; i++) 
			for(int j = 0; j < columns; j++) 
				fields[i][j].refresh();	
		fm.setDisplayConnection(fields);
		tp.refresh(cm.makeACounter());
	}
	
	/**
	 * Deaktiválja a FieldDisplayeket, nem fogadnak több felhasználói inputot.
	 * 
	 * A játék végén hívódik meg.
	 */
	public void endGame() {
		for(int i = 0; i < rows; i++) 
			for(int j = 0; j < columns; j++) 
				fields[i][j].deactivate();	
	}
	
/////////////////////////////////FOR TEST//////////////////////////////////////////
	
	public FieldDisplay[][] getFieldDisplays() {
		return fields;
	}
}
