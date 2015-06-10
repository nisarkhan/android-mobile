package net.issoa.player;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.issoa.R;
import net.issoa.custom_listview.Lazy_Adapter_Custom_Listview;
 
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PlayListActivity extends Activity  {
	/** Called when the activity is first created. */

	public ListView list; 
	public Lazy_Adapter_Custom_Listview adapter;
 
	
	// Songs list
	public ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quran_arabic_english_layout);

		list = (ListView)findViewById(R.id.list);
		
		//TextView tv = (TextView)findViewById(R.id.tv_newfeed);
		//tv.setText(getIntent().getExtras().getString("btn_news_feed"));	
		//Intent i = new Intent(this, Main_Activity_Custom_Listview.class); 
		//startActivity(i); 

		//ArrayList<HashMap<String, String>> songsListData = new ArrayList<HashMap<String, String>>();

		//SongsManager1 plm = new SongsManager1();
		// get all songs from sdcard
		//this.songsList = plm.getPlayList();
	 
		

		// looping through playlist
		/*for (int i = 0; i < songsList.size(); i++) {
			// creating new HashMap
			HashMap<String, String> song = songsList.get(i);
			// adding HashList to ArrayList
			songsListData.add(song);
		}*/

		
		
		// Starting a new async task
        new loadMoreListView().execute();

		/*// Getting adapter by passing xml data ArrayList
		adapter = new Lazy_Adapter_Custom_Listview(this, songsList);        
		list.setAdapter(adapter);*/
        
		// Click event for single list row
		list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				/*//By Index
				Toast.makeText(getApplicationContext(), "You selected item #: " + position,Toast.LENGTH_SHORT).show(); 
				//By Description
				TextView tx =(TextView)view.findViewById(R.id.title); 
				String s = tx.getText().toString(); 
				Log.d("mk", "string : "+s);*/

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

/*	public void showSongList(ArrayList<HashMap<String, String>> result) {
		// TODO Auto-generated method stub
		this.songsList = (ArrayList<HashMap<String, String>>) result;     // then do something with the list here 
	}*/
	//
	//
	///
	public class loadMoreListView extends AsyncTask<Void, Void, String> {
		
		static final String URL =  "http://issoa.net/api/quran/quran.xml";//"http://api.androidhive.info/music/music.xml";
		// XML node keys
		static final String KEY_SURAH = "surah"; // parent node
		static final String KEY_ID = "id";
		static final String KEY_TITLE_ARABIC = "title_a";
		static final String KEY_TITLE_ENGLISH = "title_e";
		static final String KEY_AUDIO_URL = "audio_url";
		static final String KEY_DURATION = "duration";
		static final String KEY_THUMB_URL = "thumb_url"; 
		
		protected void onPostExecute(String result) {
            // closing progress dialog
			/*if (songsList.size() >0)
			{
				Log.d("onPostExecute", ""+songsList.size()); 
				Toast toast = Toast.makeText(PlayListActivity.this, "Generated number: " + songsList.get(0).get("title_e"), Toast.LENGTH_LONG);
	            toast.show();
			}
			else
			{
				Log.d("onPostExecute", "nope empty songs lists");
			}*/
			
			String xml = result; //parser.getXmlFromUrl(URL); // getting XML from URL
			Document doc =  getDomElement(xml); // getting DOM element

			NodeList nl = doc.getElementsByTagName(KEY_SURAH);
			// looping through all song nodes <song>
			for (int i = 0; i < nl.getLength(); i++) {
				// creating new HashMap
				HashMap<String, String> map = new HashMap<String, String>();
				Element e = (Element) nl.item(i);
				// adding each child node to HashMap key => value
				map.put(KEY_ID, getValue(e, KEY_ID));
				map.put(KEY_TITLE_ARABIC, getValue(e, KEY_TITLE_ARABIC));
				map.put(KEY_TITLE_ENGLISH, getValue(e, KEY_TITLE_ENGLISH));
				map.put(KEY_AUDIO_URL, getValue(e, KEY_AUDIO_URL));
				map.put(KEY_DURATION, getValue(e, KEY_DURATION));
				map.put(KEY_THUMB_URL, getValue(e, KEY_THUMB_URL));

				// adding HashList to ArrayList
				songsList.add(map);
			}
			//list = (ListView)findViewById(R.id.list);
			// Getting adapter by passing xml data ArrayList
			adapter = new Lazy_Adapter_Custom_Listview(PlayListActivity.this, songsList);        
			list.setAdapter(adapter);
			
			
        }
		@Override
		protected String doInBackground(Void... params) {
		//protected ArrayList<HashMap<String, String>> doInBackground(String... result) {
			 
			String xmlList = null;
			try {
				// defaultHttpClient
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpGet request = new HttpGet(URL);
				HttpResponse response = null;
				response = httpClient.execute(request);
				//HttpResponse httpResponse = httpClient.execute(request);
				HttpEntity httpEntity = response.getEntity();
				xmlList = EntityUtils.toString(httpEntity);

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// return XML
			 
			
			
			return xmlList;
			 
			 
		} 
	
		/**
		 * Getting XML DOM element
		 * @param XML string
		 * */
		public Document getDomElement(String xml){
			Document doc = null;
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			try {

				DocumentBuilder db = dbf.newDocumentBuilder();

				InputSource is = new InputSource();
			        is.setCharacterStream(new StringReader(xml));
			        doc = db.parse(is); 

				} catch (ParserConfigurationException e) {
					Log.e("Error: ", e.getMessage());
					return null;
				} catch (SAXException e) {
					Log.e("Error: ", e.getMessage());
		            return null;
				} catch (IOException e) {
					Log.e("Error: ", e.getMessage());
					return null;
				}

		        return doc;
		}
		
		/** Getting node value
		  * @param elem element
		  */
		 public final String getElementValue( Node elem ) {
		     Node child;
		     if( elem != null){
		         if (elem.hasChildNodes()){
		             for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
		                 if( child.getNodeType() == Node.TEXT_NODE  ){
		                     return child.getNodeValue();
		                 }
		             }
		         }
		     }
		     return "";
		 }
		 
		 /**
		  * Getting node value
		  * @param Element node
		  * @param key string
		  * */
		 public String getValue(Element item, String str) {		
				NodeList n = item.getElementsByTagName(str);		
				return this.getElementValue(n.item(0));
			} 
	
	}

}


 