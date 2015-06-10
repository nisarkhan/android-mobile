package net.issoa.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.issoa.custom_listview.HttpGetXmlParser_Listview;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class SongsManager1 extends AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>    
{
	// All static variables
	static final String URL =  "http://issoa.net/api/quran/quran.xml";//"http://api.androidhive.info/music/music.xml";
	// XML node keys
	static final String KEY_SURAH = "surah"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_TITLE_ARABIC = "title_a";
	static final String KEY_TITLE_ENGLISH = "title_e";
	static final String KEY_AUDIO_URL = "audio_url";
	static final String KEY_DURATION = "duration";
	static final String KEY_THUMB_URL = "thumb_url"; 

	public ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
	 
	/**
	 * Function to read all mp3 files from sdcard
	 * and store the details in ArrayList
	 * */ 
	@Override
	protected ArrayList<HashMap<String, String>>  doInBackground(Void... params) 
	{		 
		HttpGetXmlParser_Listview parser = new HttpGetXmlParser_Listview();
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
			map.put(KEY_AUDIO_URL, parser.getValue(e, KEY_AUDIO_URL));
			map.put(KEY_DURATION, parser.getValue(e, KEY_DURATION));
			map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));

			// adding HashList to ArrayList
			songsList.add(map);
		}
		Log.d("doInBackground", ""+ songsList.size());
		return songsList;
	}

	@Override
	protected void onPostExecute(ArrayList<HashMap<String, String>> result) 
	{
		Log.d("onPostExecute", ""+ result.size());
	} 
}