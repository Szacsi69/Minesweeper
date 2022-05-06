package toplist;

import java.util.ArrayList;

import filehandling.ReadXMLFile;
import filehandling.WriteXMLFile;

public class TopList {
	
	private ArrayList<Player> players;
	
	public TopList(ArrayList<Player> players) {
		this.players = players;
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	/**
	 * Hozz�ad egy Player-t a list�hoz, ha az adott neh�zs�gi szinttel m�g nem szerepel a list�ban.
	 * Ha szerepel, �s jobb id�t csin�lt, mint ami a list�ban szerepel, akkor r�gi adatot �t�rja az �jra.
	 * Ha rosszabb id�t csin�lt, akkor nem v�ltoztat a list�n.
	 * 
	 * @param pl	A list�hoz hozz�adand� Player
	 */
	public void addPlayer(Player pl) {
		boolean already_has_player = false;
		for(int i = 0; i < players.size(); i++) {
			if(players.get(i).getName().equals(pl.getName()) && players.get(i).getDifficulty().equals(pl.getDifficulty())) {
				already_has_player = true;
				if (players.get(i).getSeconds() > pl.getSeconds()) 
					players.set(i, pl);
			}
		}
		if (!already_has_player) players.add(pl);
	}
	
	/**
	 * Visszaadja azon Playerek rendezett list�j�t, akik neh�zs�gi szintje a param�terk�nt kapott neh�zs�gi szintnek
	 * megfelel. A rendezetts�g az id� szerinti n�vekv� sorrendet jelenti.
	 * 
	 * @param difficulty	Neh�zs�gi szint, amely alapj�n a Player-ek list�j�t v�rjuk
	 * @return				Playerek rendezett list�ja
	 */
	public ArrayList<Player> getList(String difficulty) {
		ArrayList<Player> l = new ArrayList<Player>();
		for(Player p : players) {
			if (p.getDifficulty().equals(difficulty))
				l.add(p);
		}
		l.sort(new PlayerComparator());
		return l;
	}
}
