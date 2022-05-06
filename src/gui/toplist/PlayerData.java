package gui.toplist;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import toplist.Player;

public class PlayerData extends AbstractTableModel {
	private List<Player> players;
	
	/**
	 * Inicializálja az objektumot Player-ek egy rendezett listájával.
	 * 
	 * @param p Playerek rendezett listája
	 */
	public PlayerData(ArrayList<Player> p) {
		players = p;
	}
	
	public int getRowCount() {
		return players.size();
	}

	public int getColumnCount() {
		return 3;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Player p = players.get(rowIndex);
		switch (columnIndex) {
			case 0:
				return (rowIndex + 1) + ".";
			case 1:
				return p.getName();
			case 2:
				return p.getTime();
			default:
				return null;
		}
	}
	
	@Override
	public String getColumnName(int columnIndex) {
    	switch (columnIndex) {
	    	case 0:
	    		return "H.";
	    	case 1:
	    		return "Név";
	    	case 2:
				return "Idõ";
	    	default:
	    		return null;
    	}
	}
	
	public Class getColumnClass(int columnIndex) {
    	switch (columnIndex) {
	    	case 0:
	    		return String.class;
	    	case 1:
	    		return String.class;
	    	case 2:
	    		return String.class;
	    	default:
	    		return String.class;
    	}
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
    	return false;
    }
}
