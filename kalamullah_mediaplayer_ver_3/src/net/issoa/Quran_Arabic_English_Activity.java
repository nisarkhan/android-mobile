package net.issoa;
 
import java.util.ArrayList;
import java.util.HashMap;

import net.issoa.helper.HttpGet_Xml_Parser;
import net.issoa.helper.MyConstants;
import net.issoa.listview.Lazy_Adapter_Custom_Listview;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Quran_Arabic_English_Activity extends Activity {
	 
 
	ListView list;
	Lazy_Adapter_Custom_Listview adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quran_arabic_english_layout);

		TextView tv = (TextView)findViewById(R.id.tv_newfeed);
		tv.setText(getIntent().getExtras().getString("btn_quran_arabic_english"));
		
		
		//Intent i = new Intent(this, Main_Activity_Custom_Listview.class); 
		//startActivity(i); 

		ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

		HttpGet_Xml_Parser parser = new HttpGet_Xml_Parser();
		String xml = parser.getXmlFromUrl(MyConstants.URL); // getting XML from URL
		Document doc = parser.getDomElement(xml); // getting DOM element

		NodeList nl = doc.getElementsByTagName(MyConstants.KEY_SURAH);
		// looping through all song nodes <song>
		for (int i = 0; i < nl.getLength(); i++) {
			// creating new HashMap
			HashMap<String, String> map = new HashMap<String, String>();
			Element e = (Element) nl.item(i);
			// adding each child node to HashMap key => value
			map.put(MyConstants.KEY_ID, parser.getValue(e, MyConstants.KEY_ID));
			map.put(MyConstants.KEY_TITLE_ARABIC, parser.getValue(e, MyConstants.KEY_TITLE_ARABIC));
			map.put(MyConstants.KEY_TITLE_ENGLISH, parser.getValue(e, MyConstants.KEY_TITLE_ENGLISH));
			//map.put(KEY_AUDIO_URL, parser.getValue(e, KEY_AUDIO_URL));
			map.put(MyConstants.KEY_DURATION, parser.getValue(e, MyConstants.KEY_DURATION));
			map.put(MyConstants.KEY_THUMB_URL, parser.getValue(e, MyConstants.KEY_THUMB_URL));

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
				TextView tx =(TextView)view.findViewById(R.id.tv_title_e); 
				/*String s = tx.getText().toString(); 
				Log.d("mk", "string : "+); */
				 
				Toast.makeText(getApplicationContext(), "You selected item : " + tx.getText(),Toast.LENGTH_SHORT).show(); 
				// getting listitem index
				int songIndex = position;
				
				// Starting new intent
				Intent in = new Intent(getApplicationContext(), Media_Player_Activity.class);
				// Sending songIndex to PlayerActivity
				in.putExtra("songIndex", songIndex);
				setResult(100, in);
				// Closing PlayListView
				finish();
				

			}
		});	

	}
}
