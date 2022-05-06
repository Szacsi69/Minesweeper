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
	 * Hozzáad egy Player-t a listához, ha az adott nehézségi szinttel még nem szerepel a listában.
	 * Ha szerepel, és jobb idõt csinált, mint ami a listában szerepel, akkor régi adatot átírja az újra.
	 * Ha rosszabb idõt csinált, akkor nem változtat a listán.
	 * 
	 * @param pl	A listához hozzáadandó Player
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
	 * Visszaadja azon Playerek rendezett listáját, akik nehézségi szintje a paraméterként kapott nehézségi szintnek
	 * megfelel. A rendezettség az idõ szerinti növekvõ sorrendet jelenti.
	 * 
	 * @param difficulty	Nehézségi szint, amely alapján a Player-ek listáját várjuk
	 * @return				Playerek rendezett listája
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
