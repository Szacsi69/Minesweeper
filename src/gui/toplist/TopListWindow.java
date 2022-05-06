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
	 * Létrehozza az objektumot, beállítja a megfelelõ értékeket.
	 * Felvesz magára egy JTabbedPane-t három füllel (Beginner, Intermediate, Advanced)
	 * és a megfelelõ panelekkel.
	 * 
	 * @param w 	Az ablak szélessége
	 * @param h		Az ablak magassága
	 * @param tm	A toplistát kezelõ TopListManager
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
	 * Létrehoz egy JPanelt a megfelelõ toplistát megjelenítõ táblázattal, és visszatér vele.
	 * 
	 * A TopListManager-tõl elkéri a difficulty paraméternek megfelelõ rendezett Player listát, ez alapján
	 * létrehozza a JTable-t. Beállítja az oszlopok szélességét, a szöveg igazítását, és belerakja
	 * egy JScrollPane-be, aminek a paraméterek alapján beállítja a méretét.
	 * 
	 * @param width			A JPanel szélessége
	 * @param height		A JPanel magassága
	 * @param difficulty	A nehézség, aminek a toplistáját tartalmazni fogja a panel táblázata
	 * @param tm			A toplistát kezelõ TopListManager
	 * @return				A létrehozott, felépített JPanel
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
