package net.issoa.player;
import java.util.ArrayList;
import java.util.HashMap;

import net.issoa.R;
import net.issoa.custom_listview.Lazy_Adapter_Custom_Listview;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PlayListActivity extends Activity  {
	/** Called when the activity is first created. */

	ListView list;
	Lazy_Adapter_Custom_Listview adapter;

	// Songs list
	public ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
	public ArrayList<HashMap<String, String>> songsList1 = new ArrayList<HashMap<String, String>>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quran_arabic_english_layout);

		//TextView tv = (TextView)findViewById(R.id.tv_newfeed);
		//tv.setText(getIntent().getExtras().getString("btn_news_feed"));	
		//Intent i = new Intent(this, Main_Activity_Custom_Listview.class); 
		//startActivity(i); 

		ArrayList<HashMap<String, String>> songsListData = new ArrayList<HashMap<String, String>>();

		//SongsManager plm = new SongsManager();
		// get all songs from sdcard
		//this.songsList = plm.getPlayList();
		//this.songsList1 = plm.execute();
		//this.songsList1 = new SongsManager().execute();
		//new SongsManager().execute(); 
		 
		

		// looping through playlist
		for (int i = 0; i < songsList.size(); i++) {
			// creating new HashMap
			HashMap<String, String> song = songsList.get(i);
			// adding HashList to ArrayList
			songsListData.add(song);
		}

		list = (ListView)findViewById(R.id.list);

		// Getting adapter by passing xml data ArrayList
		adapter = new Lazy_Adapter_Custom_Listview(this, songsList);        
		list.setAdapter(adapter);

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

	public void showSongList(ArrayList<HashMap<String, String>> result) {
		// TODO Auto-generated method stub
		this.songsList = (ArrayList<HashMap<String, String>>) result;     // then do something with the list here 
	}
}