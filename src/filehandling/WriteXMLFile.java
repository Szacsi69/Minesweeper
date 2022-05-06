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
	 * Egy xml f�jlba �rja a megadott TopList tartalm�t (a benne tal�lhat� Player-ek adatait).
	 * 
	 * @param filename 	Az �rand� xml f�jl el�r�se
	 * @param t 		A TopList, aminek a tartalm�t f�jlba szeretn�nk �rni
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
	 * Egy Player adataib�l l�trehoz egy xml Element-et.
	 * 
	 * @param name 			Player neve
	 * @param time 			Player ideje
	 * @param difficulty 	Player neh�zs�gi szintje
	 * @return 				A l�trehozott Element
	 */
	private static Element createPlayerXMLElement(String name, long time, String difficulty) {
		Element player = new Element("Player");
		player.addContent(new Element("name").setText(name));
		player.addContent(new Element("time").setText(Long.toString(time)));
		player.addContent(new Element("difficulty").setText(difficulty));
		
		return player;
	}
}