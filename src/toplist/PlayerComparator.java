package toplist;

import java.util.Comparator;

public class PlayerComparator implements Comparator<Player> {
	
	/**
	 * Összehasonlít két Playert, azt veszi elõrébb akinek kisebb az seconds attribútuma 
	 * (gyorsabban nyerte meg a játékot).
	 */
	public int compare(Player p1, Player p2) {
		return (int) (p1.getSeconds() - p2.getSeconds());
	}
}