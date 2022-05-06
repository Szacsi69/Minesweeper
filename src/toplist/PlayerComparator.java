package toplist;

import java.util.Comparator;

public class PlayerComparator implements Comparator<Player> {
	
	/**
	 * �sszehasonl�t k�t Playert, azt veszi el�r�bb akinek kisebb az seconds attrib�tuma 
	 * (gyorsabban nyerte meg a j�t�kot).
	 */
	public int compare(Player p1, Player p2) {
		return (int) (p1.getSeconds() - p2.getSeconds());
	}
}