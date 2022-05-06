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
	 * L�trehozza az objektumot, inicializ�lja az adattagokat.
	 * 
	 * A Board-on bel�l k�t JPanel tal�lhat� (HeaderPanel, BodyPanel).
	 * Ezek BoxLayout-ban helyezkednek el, egym�s alatt. A BoxLayout tekintettel van
	 * a komponensek m�ret�re, �gy k�nnyebb tervezni a komponensek m�ret�vel.
	 * 
	 * Az inicializ�l�sokon, JPanel-ek l�trehoz�s�n, �s add-ol�s�n t�l, m�g megt�rt�nik a
	 * a GameMaster Field-jei �s a Board FieldDisplay-jei k�z�tt l�v� kapcsolat kialak�t�sa (setDisplayConnection()),
	 * illetve a Border be�ll�t�sa.
	 * 
	 * @param fsize A Boardon tal�lhat� FieldDisplay-ek m�rete
	 * @param hh	A HeaderPanel magass�ga
	 * @param gap	A Border vastags�ga
	 * @param g		A Boardhoz tartoz� GameMaster
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
	 * A HeaderPanel fel�p�t�s�t v�gzi, majd visszat�r vele.
	 * 
	 * A HeaderPanel tartalmazza, az id�m�r�s�re szolg�l� sz�ml�l�t, �s k�t gombot a j�t�k �jrakezd�s�hez, �s
	 * a men�be val� visszat�r�shez.
	 * 
	 * A HeaderPanel egy TimePanel-b�l �s k�t JButton-b�l �ll, ezek egy (1,2)-es GridLayout-ban helyezkednek el,
	 * ezen bel�l a gombok pedig egy BoxLayout-ban egym�s alatt.
	 * 
	 * A TimePanel �s az egym�s alatt lev� gombok is, a Board (Border-ek n�lk�li) sz�less�g�nek fel�t teszik ki.
	 * Magass�guk, pedig a teljes HeaderPanel magass�g�t (egy gomb csak a fel�t, mivel a gombok egym�s alatt tal�lhat�k).
	 * 
	 * @return	A fel�p�tett HeaderPanel
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
	 * A BodyPanel fel�p�t�s�t v�gzi, majd visszat�r vele.
	 * 
	 * A BodyPanel tartalmazza a j�t�kt�rk�nt szolg�l� aknamez�ket (FieldDisplay).
	 * 
	 * L�trehozza a GameMaster-nek megfelel� sor- �s oszlopsz�mmal rendelkez� FieldDisplay 2D-s t�mb�t, majd
	 * felt�lti FieldDisplay-ekkel, amelyek egy GridLayout-tal igazodnak egym�shoz.
	 * 
	 * @return A fel�p�tett BodyPanel
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
	 * Visszaadja a Board m�ret�t.
	 * A sz�less�g a GameMaster �ltal meghat�rozott oszlopsz�m, �s a mez�m�ret, illetve a Border m�rete alapj�n kalkul�l�dik.
	 * A magass�g a GameMaster �ltal meghat�rozott sorsz�m, �s a mez�m�ret, illetve
	 * a HeaderPanel magass�ga �s a Border m�rete alapj�n kalkul�l�dik
	 */
	@Override
    public Dimension getPreferredSize() {
    	Dimension d = new Dimension();
    	d.setSize(fieldsize * columns + 2*bordergap, fieldsize * rows + header_height + 2*bordergap);
        return d;
    }
	
	/**
	 * Be�ll�tja a Board h�tt�r sz�n�t.
	 */
    @Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(new Color(102, 178, 255));
	}
	
    /**
     * Kezd��llapotba hozza, a FieldDisplay-eket, �s l�trehozza a kapcsolatot k�zt�k �s
     * a GameMaster Field-jei k�z�tt, valamint a TimePanel-t is kez�d�llapotba rakja.
     * 
     * A TimePanel kezd��llapotba hoz�sa egy �j Counter lek�r�s�t is jelenti a CountMaster-t�l
     * (0-r�l induljon a sz�ml�l�s).
     * 
     * A j�t�k �jrakezd�se eset�n h�v�dik meg.
     */
	public void refresh() {
		for(int i = 0; i < rows; i++) 
			for(int j = 0; j < columns; j++) 
				fields[i][j].refresh();	
		fm.setDisplayConnection(fields);
		tp.refresh(cm.makeACounter());
	}
	
	/**
	 * Deaktiv�lja a FieldDisplayeket, nem fogadnak t�bb felhaszn�l�i inputot.
	 * 
	 * A j�t�k v�g�n h�v�dik meg.
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
