package filehandling;
import toplist.Player;
import toplist.TopList;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class WriteXMLFile {
	
	/**
	 * Egy xml fájlba írja a megadott TopList tartalmát (a benne található Player-ek adatait).
	 * 
	 * @param filename 	Az írandó xml fájl elérése
	 * @param t 		A TopList, aminek a tartalmát fájlba szeretnénk írni
	*/
	public static void writeFile(String filename, TopList t) {
		try {
			ArrayList<Player> players = t.getPlayers();
			Document doc = new Document();
			doc.setRootElement(new Element("Players"));
			
			for(Player p : players) {
			doc.getRootElement().addContent(createPlayerXMLElement(p.getName(), p.getSeconds(), p.getDifficulty()));
			}
			
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			FileWriter fw = new FileWriter(filename);
			xmlOutput.output(doc, fw);
			fw.close();
		} catch (IOException e) {System.out.println(e.getMessage());}
	}
	
	/**
	 * Egy Player adataiból létrehoz egy xml Element-et.
	 * 
	 * @param name 			Player neve
	 * @param time 			Player ideje
	 * @param difficulty 	Player nehézségi szintje
	 * @return 				A létrehozott Element
	 */
	private static Element createPlayerXMLElement(String name, long time, String difficulty) {
		Element player = new Element("Player");
		player.addContent(new Element("name").setText(name));
		player.addContent(new Element("time").setText(Long.toString(time)));
		player.addContent(new Element("difficulty").setText(difficulty));
		
		return player;
	}
}