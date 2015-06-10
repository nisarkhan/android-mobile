package net.issoa.player;

import java.util.ArrayList;
import java.util.HashMap;

import net.issoa.custom_listview.HttpGetXmlParser_Listview;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.os.AsyncTask;

public class SongsManager extends AsyncTask<SongsManager.AsyncTaskObject, Object, SongsManager.AsyncTaskObject>  
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
	 
	private final Context _context;
	
	public SongsManager(Context context){
	    _context = context; 
	}
	
	/**
	 * Function to read all mp3 files from sdcard
	 * and store the details in ArrayList
	 * */ 
	@Override
	public SongsManager.AsyncTaskObject doInBackground(SongsManager.AsyncTaskObject... params)
	{
		SongsManager.AsyncTaskObject payload = params[0];

		try {

			// extract the parameters of the task from payload
			Player app = (Player) payload.data[0];
			int numSteps = ((Integer) payload.data[1]).intValue();

			if(numSteps < 0) throw new AppException("Invalid input");

			int progress = 0;


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
			payload.result = new Integer(42);
			//payload.mysongsList = new ArrayList<HashMap<String, String>>(songsList);
			payload.mysongsList = songsList;



		} catch(AppException ape) {
			payload.exception = ape;
			payload.result = null;
		}

		return payload;
	}

	@Override
	protected void onPostExecute(SongsManager.AsyncTaskObject asynctaskObject) 
	{
		Player app_activity = (Player) asynctaskObject.data[0];
		Player test = (Player)_context;
		
		if(asynctaskObject.result != null) {

			// Present the result on success
			int answer = ((Integer) asynctaskObject.result).intValue();
			//app_activity.taskStatus.setText("Success: answer = "+asynctaskObject.mysongsList.size());
			//ArrayList<HashMap<String, String>> _songs = ((ArrayList<HashMap<String, String>>) payload.

			app_activity.songsList = asynctaskObject.mysongsList;
			test.songsList = asynctaskObject.mysongsList;
			//mal.onSuccessfulExecute(answer);

		} else {
			// Report the exception on failure
			String msg = (asynctaskObject.exception !=null) ?
					asynctaskObject.exception.toString() : "";
					//app_activity.taskStatus.setText("Failure: error ="+msg);
		} 
		
	} 
	
	public static class AsyncTaskObject
	{
		public int taskType;
		public Object[] data;
		public Object result;
		public ArrayList<HashMap<String, String>> mysongsList;
		public Exception exception;

		public AsyncTaskObject(ArrayList<HashMap<String, String>> list, Object[] data) 
		{
			//this.taskType = taskType;
			this.mysongsList = list;
			this.data = data;
		}
	}
	
}