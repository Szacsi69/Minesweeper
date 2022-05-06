package management;

import java.util.ArrayList;

import filehandling.ReadXMLFile;
import filehandling.WriteXMLFile;
import toplist.Player;
import toplist.TopList;

public class TopListManager {
	
	private TopList toplist;
	private String playername;
	
	private String filename;
	
	public TopListManager(String filename) {
		this.filename = filename;
	}
	
	public void setActualPlayerName(String name) {
		playername = name;
	}
	
	/**
	 * Beolvassa a Player-ek list�j�t a toplist adattagj�ba.
	 */
	public void load() {
		toplist = ReadXMLFile.readFile(filename);
	}
	
	/**
	 * toplist adattag tartalm�t f�jlba �rja.
	 */
	public void save() {
		WriteXMLFile.writeFile(filename, toplist);
	}
	
	/**
	 * Hozz�ad egy Player-t a toplist�hoz (toplist adattag), majd pedig f�jlba is �rja ennek a tartalm�t.
	 * 
	 * @param difficulty	A Player-hez tartoz� neh�zs�gi szint
	 * @param time			A Player-hez tartoz� id�
	 */
	public void addPlayerToTopList(String difficulty, long time) {
		Player p = new Player(playername, time, difficulty);
		toplist.addPlayer(p);
		save();
	}
	
	/**
	 * Visszaad valamilyen neh�zs�gi szint szerinti toplist�t (Player-ek rendezett list�ja)
	 * 
	 * @param difficulty	Neh�zs�gi szint
	 * @return				Player-ek rendezett list�ja
	 */
	public ArrayList<Player> getList(String difficulty) {
		return toplist.getList(difficulty);
	}
}
