

package net.issoa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class asynctask extends AsyncTask<asynctask.Payload, Object, asynctask.Payload>
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
	ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

	/*MyAsyncListener mal;
	
	public asynctask(MyAsyncListener listener) {
        this.mal = listener;
   }*/
	private final Context _context;
	private final String TAG = "TestTask";
	//private final Random _rnd
	
 

	public asynctask(Context context){
	    _context = context;
	    //_rnd = new Random();
	}


	@Override
	public void onProgressUpdate(Object... value)
	{
		// int type = ((Integer) value[0]).intValue();

		//switch(type) {

		//case APPTASK_1:
		asyncloading_activity app_activity_name = (asyncloading_activity) value[0];
		int progress = ((Integer) value[1]).intValue();
		app_activity_name.progressBar.setProgress(progress);
		// break;

		//case APPTASK_2:
		// break;
		//}

	}

	/*
	 * Runs on background thread
	 */
	@Override
	public asynctask.Payload doInBackground(asynctask.Payload... params)
	{
		asynctask.Payload payload = params[0];

		try {

			// extract the parameters of the task from payload
			asyncloading_activity app = (asyncloading_activity) payload.data[0];
			int numSteps = ((Integer) payload.data[1]).intValue();

			if(numSteps < 0) throw new AppException("Invalid input");

			int progress = 0;


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
	public void onPostExecute(asynctask.Payload payload)
	{ 
		
		asyncloading_activity app_activity = (asyncloading_activity) payload.data[0];

		asyncloading_activity test = (asyncloading_activity) _context;
	     
	    
		if(payload.result != null) {

			// Present the result on success
			int answer = ((Integer) payload.result).intValue();
			app_activity.taskStatus.setText("Success: answer = "+payload.mysongsList.size());
			//ArrayList<HashMap<String, String>> _songs = ((ArrayList<HashMap<String, String>>) payload.

			app_activity.songsList = payload.mysongsList;
			test.songsList = payload.mysongsList;
			//mal.onSuccessfulExecute(answer);

		} else {
			// Report the exception on failure
			String msg = (payload.exception !=null) ?
					payload.exception.toString() : "";
					app_activity.taskStatus.setText("Failure: error ="+msg);
		} 

		if (songsList.size() >0)
		{
			Log.d("onPostExecute", ""+songsList.size());
		}
		else
		{
			Log.d("onPostExecute", "nope empty songs lists");
		}
	}


	public static class Payload
	{
		public int taskType;
		public Object[] data;
		public Object result;
		public ArrayList<HashMap<String, String>> mysongsList;
		public Exception exception;

		public Payload(ArrayList<HashMap<String, String>> list, Object[] data) 
		{
			//this.taskType = taskType;
			this.mysongsList = list;
			this.data = data;


		}
	}
}
