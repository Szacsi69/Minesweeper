package gui.toplist;
import management.TopListManager;
import toplist.Player;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TopListWindow extends JDialog {
	
	/**
	 * L�trehozza az objektumot, be�ll�tja a megfelel� �rt�keket.
	 * Felvesz mag�ra egy JTabbedPane-t h�rom f�llel (Beginner, Intermediate, Advanced)
	 * �s a megfelel� panelekkel.
	 * 
	 * @param w 	Az ablak sz�less�ge
	 * @param h		Az ablak magass�ga
	 * @param tm	A toplist�t kezel� TopListManager
	 */
	public TopListWindow(int w, int h, TopListManager tm) {
		this.setTitle("Minesweeper Toplist");
		this.setResizable(false);
		
		JTabbedPane jtp = new JTabbedPane();
		jtp.addTab("Beginner", createPanel(w, h, "Beginner", tm));
		jtp.addTab("Intermediate", createPanel(w, h, "Intermediate", tm));
		jtp.addTab("Advanced", createPanel(w, h, "Advanced", tm));
		this.add(jtp);
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	/**
	 * L�trehoz egy JPanelt a megfelel� toplist�t megjelen�t� t�bl�zattal, �s visszat�r vele.
	 * 
	 * A TopListManager-t�l elk�ri a difficulty param�ternek megfelel� rendezett Player list�t, ez alapj�n
	 * l�trehozza a JTable-t. Be�ll�tja az oszlopok sz�less�g�t, a sz�veg igaz�t�s�t, �s belerakja
	 * egy JScrollPane-be, aminek a param�terek alapj�n be�ll�tja a m�ret�t.
	 * 
	 * @param width			A JPanel sz�less�ge
	 * @param height		A JPanel magass�ga
	 * @param difficulty	A neh�zs�g, aminek a toplist�j�t tartalmazni fogja a panel t�bl�zata
	 * @param tm			A toplist�t kezel� TopListManager
	 * @return				A l�trehozott, fel�p�tett JPanel
	 */
	public JPanel createPanel(int width, int height, String difficulty, TopListManager tm) {
		JPanel panel = new JPanel();
		
		ArrayList<Player> list = tm.getList(difficulty);
		PlayerData data = new PlayerData(list);
		JTable table = new JTable(data);
		
		table.getColumnModel().getColumn(0).setMinWidth(40);
		table.getColumnModel().getColumn(0).setMaxWidth(40);
		table.getColumnModel().getColumn(2).setMinWidth(60);
		table.getColumnModel().getColumn(2).setMaxWidth(60);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(String.class, centerRenderer);
		
		
		JScrollPane scrollpane = new JScrollPane(table);
		scrollpane.setPreferredSize(new Dimension(width, height));
		panel.add(scrollpane);
		return panel;
	}
}
