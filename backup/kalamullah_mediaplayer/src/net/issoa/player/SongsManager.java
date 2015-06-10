package net.issoa.player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import net.issoa.custom_listview.Xml_Parser_Custom_Listview;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.widget.ListView;

public class SongsManager {
	//   Path
	static final String URL = "http://issoa.net/api/quran/quran.xml";
	// XML node keys
	static final String KEY_SURAH = "surah"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_TITLE_ARABIC = "title_a";
	static final String KEY_TITLE_ENGLISH = "title_e";
	static final String KEY_AUDIO_URL = "audio_url";
	static final String KEY_DURATION = "duration";
	static final String KEY_THUMB_URL = "thumb_url";  
	
	ListView list;
	Lazy_Adapter_Custom_Listview adapter;
	
	// Constructor
	public SongsManager(){
		
	}
	
	/**
	 * Function to read all mp3 files from sdcard
	 * and store the details in ArrayList
	 * */
	public ArrayList<HashMap<String, String>> getPlayList(){
		 

		ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

		Xml_Parser_Custom_Listview parser = new Xml_Parser_Custom_Listview();
		String xml = parser.getXmlFromUrl(URL); // getting XML from URL
		Document doc = parser.getDomElement(xml); // getting DOM element
		
		NodeList nl = doc.getElementsByTagName(KEY_SURAH);
		// looping through all song nodes <song>
		for (int i = 0; i < nl.getLength(); i++) {
			// creating new HashMap
			HashMap<String, String> map = new HashMap<String, String>();
			Element e = (Element) nl.item(i);
			// adding each child node to HashMap key => value
			map.put(KEY_ID, parser.getValue(e, KEY_ID));
			map.put(KEY_TITLE_ARABIC, parser.getValue(e, KEY_TITLE_ARABIC));
			map.put(KEY_TITLE_ENGLISH, parser.getValue(e, KEY_TITLE_ENGLISH));
			//map.put(KEY_AUDIO_URL, parser.getValue(e, KEY_AUDIO_URL));
			map.put(KEY_DURATION, parser.getValue(e, KEY_DURATION));
			map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));

			// adding HashList to ArrayList
			songsList.add(map);
		}
		
		/*HashMap<String, String> song = new HashMap<String, String>();
		//song.put("songTitle", get_file_name.substring(0, (get_file_name.length() - 4)));
		//song.put("songPath", get_file_name);
		
		song.put("songTitle", "surah 05");
		song.put("songPath", "http://www.kalamullah.com/Quran/Noble%20Quran/005.mp3"); 
		songsList.add(song);
		 song = new HashMap<String, String>();
		
		song.put("songTitle", "surah 07");
		song.put("songPath", "http://www.kalamullah.com/Quran/Noble%20Quran/007.mp3");
		songsList.add(song);*/
		
		// return songs list array
		return songsList;
	}
	
 
}
