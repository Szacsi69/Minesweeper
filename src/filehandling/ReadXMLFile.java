package filehandling;
import toplist.Player;
import toplist.TopList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class ReadXMLFile {
	
	/**
	 * Beolvas egy xml fájlból adatokat, amiket egy Player ArrayList adatszerkezetbe rendez,
	 * és létrehoz belõle egy TopList objektumot, majd visszatér vele.
	 * 
	 * @param filename 	Az olvasandó xml fájl elérése
	 * @return 			A beolvasott adatokból létrehozott TopList
	 */
	public static TopList readFile(String filename) {
		ArrayList<Player> players = new ArrayList<Player>();
		try {
			SAXBuilder builder = new SAXBuilder();
			File file = new File(filename);
			Document doc = (Document) builder.build(file);
			Element root = doc.getRootElement();
			List<Element> listOfPlayers = root.getChildren("Player");
			
			for(Element e : listOfPlayers) {
				Player p = new Player();
				p.setName(e.getChildText("name"));
				p.setSeconds(Long.parseLong(e.getChildText("time")));
				p.setDifficulty(e.getChildText("difficulty"));
				players.add(p);
			}
		} catch (Exception e) {e.printStackTrace();}
		TopList t = new TopList(players);
		return t;
	}
}