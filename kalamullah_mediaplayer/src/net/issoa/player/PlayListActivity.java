package net.issoa.player;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

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
import org.w3c.dom.NodeList;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
	ProgressDialog pDialog;
	//private TextView surahTafseerLink;
	// Songs list
	final public ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
//string.valueOf();
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quran_arabic_english_layout);

		list = (ListView)findViewById(R.id.list); 
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
			Document doc =  Utilities.getDomElement(data); // getting DOM element

			NodeList nl = doc.getElementsByTagName(MyConstants.KEY_SURAH);
			// looping through all song nodes <song>
			for (int i = 0; i < nl.getLength(); i++) {
				// creating new HashMap
				HashMap<String, String> map = new HashMap<String, String>();
				Element e = (Element) nl.item(i);
				// adding each child node to HashMap key => value
				map.put(MyConstants.KEY_ID, Utilities.getValue(e, MyConstants.KEY_ID));
				map.put(MyConstants.KEY_TITLE_ARABIC, Utilities.getValue(e, MyConstants.KEY_TITLE_ARABIC));
				map.put(MyConstants.KEY_TITLE_ENGLISH, Utilities.getValue(e, MyConstants.KEY_TITLE_ENGLISH));
				map.put(MyConstants.KEY_AUDIO_URL, Utilities.getValue(e, MyConstants.KEY_AUDIO_URL));
				map.put(MyConstants.KEY_DURATION, Utilities.getValue(e, MyConstants.KEY_DURATION));
				map.put(MyConstants.KEY_THUMB_URL, Utilities.getValue(e, MyConstants.KEY_THUMB_URL));

				// adding HashList to ArrayList
				songsList.add(map);
			}
			//list = (ListView)findViewById(R.id.list);
			// Getting adapter by passing xml data ArrayList
			adapter = new Lazy_Adapter_Custom_Listview(PlayListActivity.this, songsList);        
			list.setAdapter(adapter);
		}
		
		@Override
		protected void onPostExecute(String result) {

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
				HttpGet request = new HttpGet(MyConstants.URL);
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


 