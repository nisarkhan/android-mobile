package net.issoa.helper;

import android.app.ProgressDialog;
import android.widget.ImageView;


public class MyConstants 
{
	// All static variables
		public static final String URL =  "http://issoa.net/api/quran/quran.xml";//"http://api.androidhive.info/music/music.xml";
		// XML node keys
		public static final String KEY_SURAH = "surah"; // parent node
		public static final String KEY_ID = "id";
		public static final String KEY_TITLE_ARABIC = "title_a";
		public static final String KEY_TITLE_ENGLISH = "title_e";
		public static final String KEY_AUDIO_URL = "audio_url";
		public static final String KEY_DURATION = "duration";
		public static final String KEY_THUMB_URL = "thumb_url"; 
		
		//HttpGet error
		public static final String KEY_MalformedURLException = "URL: is a malformed URL";
		public static final String KEY_UnsupportedEncodingException = "URL: UnsupportedEncodingException";
		public static final String KEY_ClientProtocolException = "URL: ClientProtocolException";
		public static final String KEY_SocketTimeoutException = "URL: SocketTimeoutException";
		public static final String KEY_ConnectTimeoutException = "URL: ConnectTimeoutException";
		public static final String KEY_IOException = "URL: IOException";
		
		
		// Progress dialog type (0 - for Horizontal progress bar)
		public static final int progress_bar_type = 0; 
		
	  
}
