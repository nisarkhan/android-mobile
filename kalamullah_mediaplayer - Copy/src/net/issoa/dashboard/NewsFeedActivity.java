package net.issoa.dashboard;
import net.issoa.R;
import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
 

import net.issoa.custom_listview.Lazy_Adapter_Custom_Listview;
import net.issoa.custom_listview.Xml_Parser_Custom_Listview;
import net.issoa.player.Player;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class NewsFeedActivity extends Activity {
	/** Called when the activity is first created. */

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

	ListView list;
	Lazy_Adapter_Custom_Listview adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_feed_layout);

		TextView tv = (TextView)findViewById(R.id.tv_newfeed);
		tv.setText(getIntent().getExtras().getString("btn_news_feed"));
		
		
		//Intent i = new Intent(this, Main_Activity_Custom_Listview.class); 
		//startActivity(i); 

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


		list = (ListView)findViewById(R.id.list);

		// Getting adapter by passing xml data ArrayList
		adapter = new Lazy_Adapter_Custom_Listview(this, songsList);        
		list.setAdapter(adapter);


		// Click event for single list row
		list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				//By Index
				//Toast.makeText(getApplicationContext(), "You selected item #: " + position,Toast.LENGTH_SHORT).show(); 
				//By Description
				TextView tx =(TextView)view.findViewById(R.id.title); 
				/*String s = tx.getText().toString(); 
				Log.d("mk", "string : "+); */
				 
				Toast.makeText(getApplicationContext(), "You selected item : " + tx.getText(),Toast.LENGTH_SHORT).show(); 
				// getting listitem index
				int songIndex = position;
				
				// Starting new intent
				Intent in = new Intent(getApplicationContext(), Player.class);
				// Sending songIndex to PlayerActivity
				in.putExtra("songIndex", songIndex);
				setResult(100, in);
				// Closing PlayListView
				finish();
				

			}
		});	

	}
}
