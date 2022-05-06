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
	 * Beolvassa a Player-ek listáját a toplist adattagjába.
	 */
	public void load() {
		toplist = ReadXMLFile.readFile(filename);
	}
	
	/**
	 * toplist adattag tartalmát fájlba írja.
	 */
	public void save() {
		WriteXMLFile.writeFile(filename, toplist);
	}
	
	/**
	 * Hozzáad egy Player-t a toplistához (toplist adattag), majd pedig fájlba is írja ennek a tartalmát.
	 * 
	 * @param difficulty	A Player-hez tartozó nehézségi szint
	 * @param time			A Player-hez tartozó idõ
	 */
	public void addPlayerToTopList(String difficulty, long time) {
		Player p = new Player(playername, time, difficulty);
		toplist.addPlayer(p);
		save();
	}
	
	/**
	 * Visszaad valamilyen nehézségi szint szerinti toplistát (Player-ek rendezett listája)
	 * 
	 * @param difficulty	Nehézségi szint
	 * @return				Player-ek rendezett listája
	 */
	public ArrayList<Player> getList(String difficulty) {
		return toplist.getList(difficulty);
	}
}
