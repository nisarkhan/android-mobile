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
import net.issoa.R.id;
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
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PlayListActivity extends Activity  {
	/** Called when the activity is first created. */

	public ListView list; 
	public Lazy_Adapter_Custom_Listview adapter;
	ProgressDialog pDialog;
	private TextView surahTafseerLink;
	// Songs list
	public ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quran_arabic_english_layout);

		list = (ListView)findViewById(R.id.list);
		//surahTafseerLink = (TextView)findViewById(R.id.txtSurahTafseerLink);
		
		//surahTafseerLink = (TextView)list.findViewById(id.txtSurahTafseerLink);
		
		 
		//TextView tv = (TextView)findViewById(R.id.tv_newfeed);
		//tv.setText(getIntent().getExtras().getString("btn_news_feed"));	
		//Intent i = new Intent(this, Main_Activity_Custom_Listview.class); 
		//startActivity(i); 
		
		
		// Starting a new async task
        new loadSurah_ListView().execute(); 
        
		// Click event for single list row
		list.setOnItemClickListener(new OnItemClickListener() 
		{
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

 
	public class loadSurah_ListView extends AsyncTask<Void, Void, String> 
	{ 
		
		@Override
		protected void onPreExecute() {
			// Showing progress dialog before sending http request
			pDialog = new ProgressDialog(PlayListActivity.this);
			pDialog.setMessage(PlayListActivity.this.getString(R.string.loading_msg_dialog));
			pDialog.setIndeterminate(true);
			pDialog.setCancelable(false);
			pDialog.show();
		}
		
		protected void AppendSongsListArray(String data)
		{
			Document doc =  SongsManager1.getDomElement(data); // getting DOM element

			NodeList nl = doc.getElementsByTagName(SongsManager1.KEY_SURAH);
			// looping through all song nodes <song>
			for (int i = 0; i < nl.getLength(); i++) {
				// creating new HashMap
				HashMap<String, String> map = new HashMap<String, String>();
				Element e = (Element) nl.item(i);
				// adding each child node to HashMap key => value
				map.put(SongsManager1.KEY_ID, SongsManager1.getValue(e, SongsManager1.KEY_ID));
				map.put(SongsManager1.KEY_TITLE_ARABIC, SongsManager1.getValue(e, SongsManager1.KEY_TITLE_ARABIC));
				map.put(SongsManager1.KEY_TITLE_ENGLISH, SongsManager1.getValue(e, SongsManager1.KEY_TITLE_ENGLISH));
				map.put(SongsManager1.KEY_AUDIO_URL, SongsManager1.getValue(e, SongsManager1.KEY_AUDIO_URL));
				map.put(SongsManager1.KEY_DURATION, SongsManager1.getValue(e, SongsManager1.KEY_DURATION));
				map.put(SongsManager1.KEY_THUMB_URL, SongsManager1.getValue(e, SongsManager1.KEY_THUMB_URL));

				// adding HashList to ArrayList
				songsList.add(map);
			}
			//list = (ListView)findViewById(R.id.list);
			// Getting adapter by passing xml data ArrayList
			adapter = new Lazy_Adapter_Custom_Listview(PlayListActivity.this, songsList);        
			list.setAdapter(adapter);
		}
		
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
			
			if(result!= null && result.length() != 0 )
			{ 
				AppendSongsListArray(result);
				//list = (ListView)findViewById(R.id.list);
				//Getting adapter by passing xml data ArrayList
				//adapter = new Lazy_Adapter_Custom_Listview(PlayListActivity.this, songsList);        
				//list.setAdapter(adapter);				
			 
			}
			else
			{
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("999", "Error loading");
				// adding HashList to ArrayList
				songsList.add(map);
				adapter = new Lazy_Adapter_Custom_Listview(PlayListActivity.this, songsList);        
				list.setAdapter(adapter);
			}
			// closing progress dialog
			pDialog.dismiss(); 
        }
		
		@Override
		protected String doInBackground(Void... params) 
		{ 
			String xmlList = null;
			try {
				// defaultHttpClient
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpGet request = new HttpGet(SongsManager1.URL);
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
	} 
}


 