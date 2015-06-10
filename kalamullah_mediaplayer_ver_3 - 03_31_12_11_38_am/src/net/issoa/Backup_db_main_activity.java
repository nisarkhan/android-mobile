package net.issoa;

public class Backup_db_main_activity {
	
	/*package net.issoa;


	import java.io.IOException;
	import java.io.UnsupportedEncodingException;
	import java.net.MalformedURLException;
	import java.net.SocketTimeoutException;
	import java.util.ArrayList;
	import java.util.HashMap;
	import java.util.List;
	import java.util.Random;

	import net.issoa.db.DatabaseHelper;
	import net.issoa.db.ReciterObj;
	import net.issoa.db.SQLiteAdapter;
	import net.issoa.helper.MyConstants;
	import net.issoa.helper.Utilities;

	import org.apache.http.HttpEntity;
	import org.apache.http.HttpResponse;
	import org.apache.http.client.ClientProtocolException;
	import org.apache.http.client.methods.HttpGet;
	import org.apache.http.conn.ConnectTimeoutException;
	import org.apache.http.impl.client.DefaultHttpClient;
	import org.apache.http.util.EntityUtils;
	import org.w3c.dom.Document;
	import org.w3c.dom.Element;
	import org.w3c.dom.NodeList;

	import android.app.Activity;
	import android.app.ProgressDialog;
	import android.content.Intent;
	import android.database.Cursor;
	import android.graphics.Color;
	import android.media.MediaPlayer;
	import android.media.MediaPlayer.OnCompletionListener;
	import android.media.MediaPlayer.OnPreparedListener;
	import android.os.AsyncTask;
	import android.os.Bundle;
	import android.os.Handler;
	import android.util.Log;
	import android.view.View;
	import android.widget.ImageButton;
	import android.widget.SeekBar;
	import android.widget.TextView;
	import android.widget.Toast;

	public class Media_Player_Activity extends Activity implements OnCompletionListener, SeekBar.OnSeekBarChangeListener {

		private ImageButton btnPlay;
		private ImageButton btnForward;
		private ImageButton btnBackward;
		private ImageButton btnNext;
		private ImageButton btnPrevious;
		private ImageButton btnPlaylist;
		private ImageButton btnRepeat;
		private ImageButton btnShuffle;
		private SeekBar songProgressBar;
		private TextView songTitleLabel;
		private TextView songCurrentDurationLabel;
		private TextView songTotalDurationLabel;
		// Media Player
		private  MediaPlayer mp;
		// Handler to update UI timer, progress bar etc,.
		private Handler mHandler = new Handler();;

		private Utilities utils;
		private int seekForwardTime = 5000; // 5000 milliseconds
		private int seekBackwardTime = 5000; // 5000 milliseconds
		private int currentSongIndex = 0; 
		private boolean isShuffle = false;
		private boolean isRepeat = false;


		DatabaseHelper dbHelper;
		ProgressDialog pDialog;
		private SQLiteAdapter mySQLiteAdapter;

		public ArrayList<HashMap<String, String>> songsList  = new ArrayList<HashMap<String, String>>();

		public static String xmlList = "empty"; 

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.player);

			// All player buttons
			btnPlay = (ImageButton) findViewById(R.id.btnPlay);
			btnForward = (ImageButton) findViewById(R.id.btnForward);
			btnBackward = (ImageButton) findViewById(R.id.btnBackward);
			btnNext = (ImageButton) findViewById(R.id.btnNext);
			btnPrevious = (ImageButton) findViewById(R.id.btnPrevious);
			btnPlaylist = (ImageButton) findViewById(R.id.btnPlaylist);
			btnRepeat = (ImageButton) findViewById(R.id.btnRepeat);
			btnShuffle = (ImageButton) findViewById(R.id.btnShuffle);
			songProgressBar = (SeekBar) findViewById(R.id.songProgressBar);
			songTitleLabel = (TextView) findViewById(R.id.songTitle);
			songCurrentDurationLabel = (TextView) findViewById(R.id.songCurrentDurationLabel);
			songTotalDurationLabel = (TextView) findViewById(R.id.songTotalDurationLabel);

			//dbHelper = new DatabaseHelper(this);
			//db = dbHelper.getWritableDatabase();
			// Mediaplayer
			mp = new MediaPlayer(); 
			utils = new Utilities(); 

			// Listeners
			songProgressBar.setOnSeekBarChangeListener(this); // Important
			mp.setOnCompletionListener(this); // Important 

			mySQLiteAdapter = new SQLiteAdapter(this);
			mySQLiteAdapter.openToWrite();
			//mySQLiteAdapter.deleteAll(); 
			//mySQLiteAdapter.InsertReciters();
			mySQLiteAdapter.insert_xml("10","<quran><surah><id>001</id><title_a>Surah al-Fatihah</title_a><title_e>The Opening</title_e><audio_url>http://www.salafitapes.com/noblequran/1.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>002</id><title_a>Surah al-Baqarah</title_a><title_e>The Cow</title_e><audio_url>http://www.salafitapes.com/noblequran/2.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>003</id><title_a>Surah aal-'Imraan</title_a><title_e>The Family of Amran</title_e><audio_url>http://www.salafitapes.com/noblequran/3.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>004</id><title_a>Surah an-Nisaa'</title_a><title_e>The Women</title_e><audio_url>http://www.salafitapes.com/noblequran/4.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>005</id><title_a>Surah al-Maa'idah</title_a><title_e>The Food</title_e><audio_url>http://www.salafitapes.com/noblequran/5.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>006</id><title_a>Surah al-An'aam</title_a><title_e>The Cattle</title_e><audio_url>http://www.salafitapes.com/noblequran/6.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>007</id><title_a>Surah al-A'raaf</title_a><title_e>The Elevated Places</title_e><audio_url>http://www.salafitapes.com/noblequran/7.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>008</id><title_a>Surah al-Anfaal</title_a><title_e>The Voluntary Gifts</title_e><audio_url>http://www.salafitapes.com/noblequran/8.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>009</id><title_a>Surah at-Tawbah</title_a><title_e>The Immunity</title_e><audio_url>http://www.salafitapes.com/noblequran/9.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>010</id><title_a>Surah Yunus</title_a><title_e>The Jonah</title_e><audio_url>http://www.salafitapes.com/noblequran/10.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>011</id><title_a>Surah Hud</title_a><title_e>The Hud</title_e><audio_url>http://www.salafitapes.com/noblequran/11.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>012</id><title_a>Surah Yusuf</title_a><title_e>The Joseph</title_e><audio_url>http://www.salafitapes.com/noblequran/12.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>013</id><title_a>Surah ar-Ra'd</title_a><title_e>The Thunder</title_e><audio_url>http://www.salafitapes.com/noblequran/13.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>014</id><title_a>Surah Ibraheem</title_a><title_e>Abraham</title_e><audio_url>http://www.salafitapes.com/noblequran/14.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>015</id><title_a>Surah al-Hijr</title_a><title_e>Stoneland, Rock City</title_e><audio_url>http://www.salafitapes.com/noblequran/15.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>016</id><title_a>Surah an-Nahl</title_a><title_e>The Bee</title_e><audio_url>http://www.salafitapes.com/noblequran/16.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>017</id><title_a>Surah al-Israa'</title_a><title_e>The Night Journey, Children Of Israel</title_e><audio_url>http://www.salafitapes.com/noblequran/17.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>018</id><title_a>Surah al-Kahf</title_a><title_e>The Cave</title_e><audio_url>http://www.salafitapes.com/noblequran/18.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>019</id><title_a>Surah Maryam</title_a><title_e>Mary</title_e><audio_url>http://www.salafitapes.com/noblequran/19.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>020</id><title_a>Surah Taa-Haa</title_a><title_e>The Ta-ha</title_e><audio_url>http://www.salafitapes.com/noblequran/20.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>021</id><title_a>Surah al-Anbiyaa'</title_a><title_e>The Prophets</title_e><audio_url>http://www.salafitapes.com/noblequran/21.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>022</id><title_a>Surah al-Hajj</title_a><title_e>The Pilgrimage</title_e><audio_url>http://www.salafitapes.com/noblequran/22.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>023</id><title_a>Surah al-Mu'minun</title_a><title_e>The Believers</title_e><audio_url>http://www.salafitapes.com/noblequran/23.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>024</id><title_a>Surah an-Nur</title_a><title_e>Light</title_e><audio_url>http://www.salafitapes.com/noblequran/24.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>025</id><title_a>Surah al-Furqaan</title_a><title_e>The Criterion, The Standard</title_e><audio_url>http://www.salafitapes.com/noblequran/25.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>026</id><title_a>Surah ash-Shu'araa'</title_a><title_e>The Poets</title_e><audio_url>http://www.salafitapes.com/noblequran/26.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>027</id><title_a>Surah an-Naml</title_a><title_e>The Ant, The Ants</title_e><audio_url>http://www.salafitapes.com/noblequran/27.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>028</id><title_a>Surah al-Qasas</title_a><title_e>The Story, Stories</title_e><audio_url>http://www.salafitapes.com/noblequran/28.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>029</id><title_a>Surah al-'Ankabut</title_a><title_e>The Spider</title_e><audio_url>http://www.salafitapes.com/noblequran/29.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>030</id><title_a>Surah ar-Rum</title_a><title_e>The Romans, The Byzantines</title_e><audio_url>http://www.salafitapes.com/noblequran/30.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>031</id><title_a>Surah Luqmaan</title_a><title_e>Luqman</title_e><audio_url>http://www.salafitapes.com/noblequran/31.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>032</id><title_a>Surah as-Sajdah</title_a><title_e>The Prostration, Worship, Adoration</title_e><audio_url>http://www.salafitapes.com/noblequran/32.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>033</id><title_a>Surah al-Ahzaab</title_a><title_e>The Clans, The Coalition, The Combined Forces</title_e><audio_url>http://www.salafitapes.com/noblequran/33.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>034</id><title_a>Surah Saba'</title_a><title_e>Sheba</title_e><audio_url>http://www.salafitapes.com/noblequran/34.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>035</id><title_a>Surah Faatir</title_a><title_e>The Originator</title_e><audio_url>http://www.salafitapes.com/noblequran/35.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>036</id><title_a>Surah Yaa-Seen</title_a><title_e>Ya-sin</title_e><audio_url>http://www.salafitapes.com/noblequran/36.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>037</id><title_a>Surah as-Saaffaat</title_a><title_e>Those Who Set The Ranks, Drawn Up In Ranks</title_e><audio_url>http://www.salafitapes.com/noblequran/37.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>038</id><title_a>Surah Saad</title_a><title_e>Sad, (the Letter) Sad ( S )</title_e><audio_url>http://www.salafitapes.com/noblequran/38.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>039</id><title_a>Surah az-Zumar</title_a><title_e>The Troops, Throngs</title_e><audio_url>http://www.salafitapes.com/noblequran/39.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>040</id><title_a>Surah Ghaafir</title_a><title_e>The Believer, The Forgiver (God)</title_e><audio_url>http://www.salafitapes.com/noblequran/40.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>041</id><title_a>Surah Fussilat</title_a><title_e>(signs) Spelled Out, Ha-mim</title_e><audio_url>http://www.salafitapes.com/noblequran/41.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>042</id><title_a>Surah ash-Shura</title_a><title_e>Councel, Consultation</title_e><audio_url>http://www.salafitapes.com/noblequran/42.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>043</id><title_a>Surah az-Zukhruf</title_a><title_e>Ornaments Of Gold, Luxury</title_e><audio_url>http://www.salafitapes.com/noblequran/43.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>044</id><title_a>Surah ad-Dukhaan</title_a><title_e>Smoke</title_e><audio_url>http://www.salafitapes.com/noblequran/44.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>045</id><title_a>Surah al-Jaathiyah</title_a><title_e>The Kneeling</title_e><audio_url>http://www.salafitapes.com/noblequran/45.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>046</id><title_a>Surah al-Ahqaaf</title_a><title_e>The Wind-curved Sandhills, The Dunes</title_e><audio_url>http://www.salafitapes.com/noblequran/46.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>047</id><title_a>Surah Muhammad</title_a><title_e>Muhammad</title_e><audio_url>http://www.salafitapes.com/noblequran/47.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>048</id><title_a>Surah al-Fath</title_a><title_e>Victory, Conquest</title_e><audio_url>http://www.salafitapes.com/noblequran/48.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>049</id><title_a>Surah al-Hujuraat</title_a><title_e>The Private Apartments, The Inner Apartments</title_e><audio_url>http://www.salafitapes.com/noblequran/49.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>050</id><title_a>Surah Qaaf</title_a><title_e>Qaf, (the Letter), ( Q )</title_e><audio_url>http://www.salafitapes.com/noblequran/50.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>051</id><title_a>Surah adh-Dhaariyaat</title_a><title_e>The Scatterers</title_e><audio_url>http://www.salafitapes.com/noblequran/51.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>052</id><title_a>Surah at-Tur</title_a><title_e>The Mountain</title_e><audio_url>http://www.salafitapes.com/noblequran/52.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>053</id><title_a>Surah an-Najm</title_a><title_e>The Star</title_e><audio_url>http://www.salafitapes.com/noblequran/53.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>054</id><title_a>Surah al-Qamar</title_a><title_e>The Moon</title_e><audio_url>http://www.salafitapes.com/noblequran/54.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>055</id><title_a>Surah ar-Rahmaan</title_a><title_e>The Beneficent, The Mercy Giving</title_e><audio_url>http://www.salafitapes.com/noblequran/55.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>056</id><title_a>Surah al-Waaqi'ah</title_a><title_e>The Event, The Inevitable</title_e><audio_url>http://www.salafitapes.com/noblequran/56.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>057</id><title_a>Surah al-Hadeed</title_a><title_e>Iron</title_e><audio_url>http://www.salafitapes.com/noblequran/57.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>058</id><title_a>Surah al-Mujaadalah</title_a><title_e>The Pleading Woman</title_e><audio_url>http://www.salafitapes.com/noblequran/58.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>059</id><title_a>Surah al-Hashr</title_a><title_e>Exile, Banishment</title_e><audio_url>http://www.salafitapes.com/noblequran/59.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>060</id><title_a>Surah al-Mumtahinah</title_a><title_e>The Woman who is Examined</title_e><audio_url>http://www.salafitapes.com/noblequran/60.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>061</id><title_a>Surah as-Saff</title_a><title_e>The Ranks, Battle Array</title_e><audio_url>http://www.salafitapes.com/noblequran/61.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>062</id><title_a>Surah al-Jumu'ah</title_a><title_e>The Congregation, Friday</title_e><audio_url>http://www.salafitapes.com/noblequran/62.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>063</id><title_a>Surah al-Munaafiqun</title_a><title_e>The Hypocrites</title_e><audio_url>http://www.salafitapes.com/noblequran/63.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>064</id><title_a>Surah at-Taghaabun</title_a><title_e>The Manifestation of Losses</title_e><audio_url>http://www.salafitapes.com/noblequran/64.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>065</id><title_a>Surah at-Talaaq</title_a><title_e>Divorce</title_e><audio_url>http://www.salafitapes.com/noblequran/65.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>066</id><title_a>Surah at-Tahreem</title_a><title_e>The Prohibition</title_e><audio_url>http://www.salafitapes.com/noblequran/66.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>067</id><title_a>Surah al-Mulk</title_a><title_e>The Kingdom</title_e><audio_url>http://www.salafitapes.com/noblequran/67.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>068</id><title_a>Surah al-Qalam</title_a><title_e>The Pen</title_e><audio_url>http://www.salafitapes.com/noblequran/68.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>069</id><title_a>Surah al-Haaqqah</title_a><title_e>The Sure Truth</title_e><audio_url>http://www.salafitapes.com/noblequran/69.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>070</id><title_a>Surah al-Ma'aarij</title_a><title_e>The Ways of Ascent</title_e><audio_url>http://www.salafitapes.com/noblequran/70.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>071</id><title_a>Surah Nuh</title_a><title_e>Noah</title_e><audio_url>http://www.salafitapes.com/noblequran/71.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>072</id><title_a>Surah al-Jinn</title_a><title_e>The Jinn, Sprites</title_e><audio_url>http://www.salafitapes.com/noblequran/72.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>073</id><title_a>Surah al-Muzzammil</title_a><title_e>The One Covering Himself</title_e><audio_url>http://www.salafitapes.com/noblequran/73.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>074</id><title_a>Surah al-Muddaththir</title_a><title_e>The One Wrapping Himself Up</title_e><audio_url>http://www.salafitapes.com/noblequran/74.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>075</id><title_a>Surah al-Qiyaamah</title_a><title_e>The Resurrection</title_e><audio_url>http://www.salafitapes.com/noblequran/75.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>076</id><title_a>Surah al-Insaan</title_a><title_e>The Man</title_e><audio_url>http://www.salafitapes.com/noblequran/76.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>077</id><title_a>Surah al-Mursalaat</title_a><title_e>Those Sent Forth</title_e><audio_url>http://www.salafitapes.com/noblequran/77.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>078</id><title_a>Surah an-Naba'</title_a><title_e>The Announcement</title_e><audio_url>http://www.salafitapes.com/noblequran/78.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>079</id><title_a>Surah al-Naazi'aat</title_a><title_e>Those Who Yearn</title_e><audio_url>http://www.salafitapes.com/noblequran/79.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>080</id><title_a>Surah 'Abasa</title_a><title_e>He Frowned</title_e><audio_url>http://www.salafitapes.com/noblequran/80.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>081</id><title_a>Surah at-Takweer</title_a><title_e>The Folding Up</title_e><audio_url>http://www.salafitapes.com/noblequran/81.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>082</id><title_a>Surah al-Infitaar</title_a><title_e>The Cleaving</title_e><audio_url>http://www.salafitapes.com/noblequran/82.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>083</id><title_a>Surah al-Mutaffifeen</title_a><title_e>Default in Duty</title_e><audio_url>http://www.salafitapes.com/noblequran/83.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>084</id><title_a>Surah Inshiqaaq</title_a><title_e>The Bursting Asunder</title_e><audio_url>http://www.salafitapes.com/noblequran/84.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>085</id><title_a>Surah al-Burooj</title_a><title_e>The Stars</title_e><audio_url>http://www.salafitapes.com/noblequran/85.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>086</id><title_a>Surah at-Taariq</title_a><title_e>The Comer by Night</title_e><audio_url>http://www.salafitapes.com/noblequran/86.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>087</id><title_a>Surah al-A'laa</title_a><title_e>The Most High</title_e><audio_url>http://www.salafitapes.com/noblequran/87.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>088</id><title_a>Surah al-Ghaashiyah</title_a><title_e>The Overwhelming Event</title_e><audio_url>http://www.salafitapes.com/noblequran/88.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>089</id><title_a>Surah al-Fajr</title_a><title_e>The Daybreak</title_e><audio_url>http://www.salafitapes.com/noblequran/89.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>090</id><title_a>Surah al-Balad</title_a><title_e>The City</title_e><audio_url>http://www.salafitapes.com/noblequran/90.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>091</id><title_a>Surah ash-Shams</title_a><title_e>The Sun</title_e><audio_url>http://www.salafitapes.com/noblequran/91.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>092</id><title_a>Surah al-Layl</title_a><title_e>The Night</title_e><audio_url>http://www.salafitapes.com/noblequran/92.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>093</id><title_a>Surah ad-Duha</title_a><title_e>The Brightness of the Day</title_e><audio_url>http://www.salafitapes.com/noblequran/93.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>094</id><title_a>Surah ash-Sharh</title_a><title_e>The Expansion</title_e><audio_url>http://www.salafitapes.com/noblequran/94.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>095</id><title_a>Surah at-Teen</title_a><title_e>The Fig</title_e><audio_url>http://www.salafitapes.com/noblequran/95.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>096</id><title_a>Surah al-'Alaq</title_a><title_e>The Clot</title_e><audio_url>http://www.salafitapes.com/noblequran/96.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>097</id><title_a>Surah al-Qadr</title_a><title_e>The Majesty</title_e><audio_url>http://www.salafitapes.com/noblequran/97.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>098</id><title_a>Surah al-Bayyinah</title_a><title_e>The Clear Evidence</title_e><audio_url>http://www.salafitapes.com/noblequran/98.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>099</id><title_a>Surah az-Zalzaalah</title_a><title_e>The Shaking</title_e><audio_url>http://www.salafitapes.com/noblequran/99.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>100</id><title_a>Surah al-'Aadiyaat</title_a><title_e>The Assaulters</title_e><audio_url>http://www.salafitapes.com/noblequran/100.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>101</id><title_a>Surah al-Qaari'ah</title_a><title_e>The Calamity</title_e><audio_url>http://www.salafitapes.com/noblequran/101.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>102</id><title_a>Surah at-Takaathur</title_a><title_e>The Abundance of Wealth</title_e><audio_url>http://www.salafitapes.com/noblequran/102.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>103</id><title_a>Surah al-'Asr</title_a><title_e>The Time</title_e><audio_url>http://www.salafitapes.com/noblequran/103.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>104</id><title_a>Surah al-Humazah</title_a><title_e>The Slanderer</title_e><audio_url>http://www.salafitapes.com/noblequran/104.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>105</id><title_a>Surah al-Feel</title_a><title_e>The Elephant</title_e><audio_url>http://www.salafitapes.com/noblequran/105.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>106</id><title_a>Surah Quraysh</title_a><title_e>The Quraish</title_e><audio_url>http://www.salafitapes.com/noblequran/106.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>107</id><title_a>Surah al-Maa'un</title_a><title_e>Acts of Kindness</title_e><audio_url>http://www.salafitapes.com/noblequran/107.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>108</id><title_a>Surah al-Kawthar</title_a><title_e>The Abundance of Good</title_e><audio_url>http://www.salafitapes.com/noblequran/108.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>109</id><title_a>Surah al-Kaafirun</title_a><title_e>The Disbelievers</title_e><audio_url>http://www.salafitapes.com/noblequran/109.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>110</id><title_a>Surah an-Nasr</title_a><title_e>The Help</title_e><audio_url>http://www.salafitapes.com/noblequran/110.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>111</id><title_a>Surah al-Masad</title_a><title_e>The Flame</title_e><audio_url>http://www.salafitapes.com/noblequran/111.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>112</id><title_a>Surah al-Ikhlaas</title_a><title_e>The Unity, Sincerity, Oneness Of God</title_e><audio_url>http://www.salafitapes.com/noblequran/112.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>113</id><title_a>Surah al-Falaq</title_a><title_e>The Daybreak, Dawn</title_e><audio_url>http://www.salafitapes.com/noblequran/113.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah><surah><id>114</id><title_a>Surah an-Naas</title_a><title_e>Mankind</title_e><audio_url>http://www.salafitapes.com/noblequran/114.mp3</audio_url><duration>4:47</duration><thumb_url>http://issoa.net/api/quran/images/quran1.png</thumb_url></surah></quran>");
			mySQLiteAdapter.close(); 

			// Starting a new async task
			new loadSurah_ListView().execute(); 


			*//**
			 * Play button click event
			 * plays a song and changes button to pause image
			 * pauses a song and changes button to play image
			 * *//*
			btnPlay.setOnClickListener(new View.OnClickListener() 
			{ 
				public void onClick(View v) 
				{ 
					// check for already playing
					if(mp.isPlaying())
					{
						if(mp!=null)
						{
							mp.pause();
							// Changing button image to play button
							btnPlay.setImageResource(R.drawable.btn_play);
						}
					}
					else
					{
						// Resume song
						if(mp!=null)
						{
							mp.start();
							// Changing button image to pause button
							btnPlay.setImageResource(R.drawable.btn_pause);
						}
					}	 
				}
			});

			*//**
			 * Forward button click event
			 * Forwards song specified seconds
			 * *//*
			btnForward.setOnClickListener(new View.OnClickListener() {  
				public void onClick(View arg0) {
					// get current song position				
					int currentPosition = mp.getCurrentPosition();
					// check if seekForward time is lesser than song duration
					if(currentPosition + seekForwardTime <= mp.getDuration()){
						// forward song
						mp.seekTo(currentPosition + seekForwardTime);
					}else{
						// forward to end position
						mp.seekTo(mp.getDuration());
					}
				}
			});

			*//**
			 * Backward button click event
			 * Backward song to specified seconds
			 * *//*
			btnBackward.setOnClickListener(new View.OnClickListener() { 
				public void onClick(View arg0) {
					// get current song position				
					int currentPosition = mp.getCurrentPosition();
					// check if seekBackward time is greater than 0 sec
					if(currentPosition - seekBackwardTime >= 0){
						// forward song
						mp.seekTo(currentPosition - seekBackwardTime);
					}else{
						// backward to starting position
						mp.seekTo(0);
					} 
				}
			});

			*//**
			 * Next button click event
			 * Plays next song by taking currentSongIndex + 1
			 * *//*
			btnNext.setOnClickListener(new View.OnClickListener() 
			{
				public void onClick(View arg0) {
					// check if next song is there or not
					if(currentSongIndex < (songsList.size() - 1)){
						playSong(currentSongIndex + 1);
						currentSongIndex = currentSongIndex + 1;
					}else{
						// play first song
						playSong(0);
						currentSongIndex = 0;
					}
				}
			});

			*//**
			 * Back button click event
			 * Plays previous song by currentSongIndex - 1
			 * *//*
			btnPrevious.setOnClickListener(new View.OnClickListener() 
			{
				public void onClick(View arg0) {
					if(currentSongIndex > 0){
						playSong(currentSongIndex - 1);
						currentSongIndex = currentSongIndex - 1;
					}else{
						// play last song
						playSong(songsList.size() - 1);
						currentSongIndex = songsList.size() - 1;
					}
				}
			});

			*//**
			 * Button Click event for Repeat button
			 * Enables repeat flag to true
			 * *//*
			btnRepeat.setOnClickListener(new View.OnClickListener() 
			{
				public void onClick(View arg0) {
					if(isRepeat){
						isRepeat = false;
						Toast.makeText(getApplicationContext(), "Repeat is OFF", Toast.LENGTH_SHORT).show();
						btnRepeat.setImageResource(R.drawable.btn_repeat);
					}else{
						// make repeat to true
						isRepeat = true;
						Toast.makeText(getApplicationContext(), "Repeat is ON", Toast.LENGTH_SHORT).show();
						// make shuffle to false
						isShuffle = false;
						btnRepeat.setImageResource(R.drawable.btn_repeat_focused);
						btnShuffle.setImageResource(R.drawable.btn_shuffle);
					}	
				}
			});

			*//**
			 * Button Click event for Shuffle button
			 * Enables shuffle flag to true
			 * *//*
			btnShuffle.setOnClickListener(new View.OnClickListener() 
			{
				public void onClick(View arg0) {
					if(isShuffle){
						isShuffle = false;
						Toast.makeText(getApplicationContext(), "Shuffle is OFF", Toast.LENGTH_SHORT).show();
						btnShuffle.setImageResource(R.drawable.btn_shuffle);
					}else{
						// make repeat to true
						isShuffle= true;
						Toast.makeText(getApplicationContext(), "Shuffle is ON", Toast.LENGTH_SHORT).show();
						// make shuffle to false
						isRepeat = false;
						btnShuffle.setImageResource(R.drawable.btn_shuffle_focused);
						btnRepeat.setImageResource(R.drawable.btn_repeat);
					}	
				}
			});

			*//**
			 * Button Click event for Play list click event
			 * Launches list activity which displays list of songs
			 * *//*
			btnPlaylist.setOnClickListener(new View.OnClickListener() 
			{
				public void onClick(View arg0) {
					Intent i = new Intent(getApplicationContext(), Play_Listing_Activity.class);
					startActivityForResult(i, 100);	 
				}
			});

			mp.setOnPreparedListener(new OnPreparedListener() 
			{
				public void onPrepared(MediaPlayer mp) 
				{
					Log.d("onPrepared", "mp.start()");
					mp.start(); 
				}
			});

		}

		*//**
		 * Receiving song index from playlist view
		 * and play the song
		 * *//*
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) 
		{
			super.onActivityResult(requestCode, resultCode, data);
			if(resultCode == 100)
			{
				currentSongIndex = data.getExtras().getInt("songIndex");
				// play selected song
				playSong(currentSongIndex);
			}
		}

		*//**
		 * Function to play a song
		 * @param songIndex - index of song
		 * *//*
		public void  playSong(int songIndex)
		{
			// Play song 
			try 
			{
				if(songsList!= null && songsList.size() != 0 )
				{ 
					//Log.d("playSong", ""+ songsList.size());
					mp.reset();
					mp.setDataSource(songsList.get(songIndex).get(MyConstants.KEY_AUDIO_URL));
					Log.d("playSong", ""+ songsList.get(songIndex).get(MyConstants.KEY_AUDIO_URL));
					mp.prepare();
					//mp.start();
					// Displaying Song title
					String songTitle = songsList.get(songIndex).get(MyConstants.KEY_TITLE_ARABIC);
					songTitleLabel.setText(songTitle);

					// Changing Button Image to pause image
					btnPlay.setImageResource(R.drawable.btn_pause);

					// set Progress bar values
					songProgressBar.setProgress(0);
					songProgressBar.setMax(100);

					// Updating progress bar
					updateProgressBar();	
				}
				else
				{
					String songTitle = Media_Player_Activity.this.getString(R.string.invalid_url_unable_to_read);
					songTitleLabel.setText(songTitle);
				} 
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				mp.release();
			}
		}

		*//**
		 * Update timer on seekbar
		 * *//*
		public void updateProgressBar() 
		{
			mHandler.postDelayed(mUpdateTimeTask, 100);        
		}	

		*//**
		 * Background Runnable thread
		 * *//*
		private Runnable mUpdateTimeTask = new Runnable() 
		{
			public void run() 
			{
				long totalDuration = mp.getDuration();
				long currentDuration = mp.getCurrentPosition();

				// Displaying Total Duration time
				songTotalDurationLabel.setText(""+utils.milliSecondsToTimer(totalDuration));
				// Displaying time completed playing
				songCurrentDurationLabel.setText(""+utils.milliSecondsToTimer(currentDuration));

				// Updating progress bar
				int progress = (utils.getProgressPercentage(currentDuration, totalDuration));
				//Log.d("Progress", ""+progress);
				songProgressBar.setProgress(progress);

				// Running this thread after 100 milliseconds
				mHandler.postDelayed(this, 100);
			}
		}; 

		*//**
		 * When user starts moving the progress handler
		 * *//*

		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch)  { }

		*//**
		 * When user starts moving the progress handler
		 * *//*
		public void onStartTrackingTouch(SeekBar seekBar) {
			// remove message Handler from updating progress bar
			mHandler.removeCallbacks(mUpdateTimeTask);
		}

		*//**
		 * When user stops moving the progress hanlder
		 * *//*

		public void onStopTrackingTouch(SeekBar seekBar) {
			mHandler.removeCallbacks(mUpdateTimeTask);
			int totalDuration = mp.getDuration();
			int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);

			// forward or backward to certain seconds
			mp.seekTo(currentPosition);

			// update timer progress again
			updateProgressBar();
		}

		*//**
		 * On Song Playing completed
		 * if repeat is ON play same song again
		 * if shuffle is ON play random song
		 * *//*
		public void onCompletion(MediaPlayer arg0) {
			Log.d("onCompletion", ""+currentSongIndex);
			// check for repeat is ON or OFF
			if(isRepeat){
				// repeat is on play same song again
				playSong(currentSongIndex);
			} else if(isShuffle){
				// shuffle is on - play a random song
				Random rand = new Random();
				currentSongIndex = rand.nextInt((songsList.size() - 1) - 0 + 1) + 0;
				playSong(currentSongIndex);
			} else{
				// no repeat or shuffle ON - play next song
				if(currentSongIndex < (songsList.size() - 1)){
					playSong(currentSongIndex + 1);
					currentSongIndex = currentSongIndex + 1; 
				}else{
					// play first song
					playSong(0);
					currentSongIndex = 0;
				}
			}
		}

		@Override
		public void onDestroy(){
			super.onDestroy();
			mHandler.removeCallbacks(mUpdateTimeTask);
			mp.release();

			if (null != db && db.isOpen() && !db.isDbLockedByOtherThreads())
			{
			   db.close();
			}
		}


		public class loadSurah_ListView extends AsyncTask<Void, Void, String> { 

			@Override
			protected void onPreExecute() {
				// Showing progress dialog before sending http request
				pDialog = new ProgressDialog(Media_Player_Activity.this);
				pDialog.setMessage(Media_Player_Activity.this.getString(R.string.loading_msg_dialog));
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
			}

			@Override
			protected void onPostExecute(String result) {  

				if(result!= null && result.length() != 0 )
				{ 
					if (result.startsWith("<!DOCTYPE html PUBLIC")) {					
						songTitleLabel.setText("404 - File or directory not found.");
						btnPlaylist.setEnabled(false);
						btnPlaylist.setImageResource(R.drawable.img_btn_playlist_disabled);
						songTitleLabel.setTextColor(Color.RED);
					}
					if (result.contains("UnknownHostException")) {					
						songTitleLabel.setText("You have to be connected to the internet for this application to work.");
						btnPlaylist.setEnabled(false);
						btnPlaylist.setImageResource(R.drawable.img_btn_playlist_disabled);
						songTitleLabel.setTextColor(Color.RED);
					}
					else {
						AppendSongsListArray(result); 			
						playSong(0);
						btnPlaylist.setEnabled(true);
						btnPlaylist.setImageResource(R.drawable.img_btn_playlist);
					}
				}
				else
				{
					String songTitle = "No Surah found in the list!";
					songTitleLabel.setText(songTitle);				
				}
				// closing progress dialog
				pDialog.dismiss(); 
			}

			@Override
			protected String doInBackground(Void... params) 
			{
				if (xmlList  == "empty") 
				 {
					 Log.d("inside xmllist empty line", xmlList);
					Cursor cursor = null;
					mySQLiteAdapter = new SQLiteAdapter(Media_Player_Activity.this);
					mySQLiteAdapter.openToRead();
					cursor = mySQLiteAdapter.queueAll_xml();
					startManagingCursor(cursor);
					cursor.moveToFirst();
					xmlList = cursor.getString(2);
					if (cursor.moveToFirst()) {
						do { 
							xmlList = cursor.getString(2);
						} while (cursor.moveToNext());
					}
					mySQLiteAdapter.close();
				 }
				 Log.d("outside xmllist empty line", xmlList);
				return xmlList;
				String xmlList = null;
				DefaultHttpClient httpClient = new DefaultHttpClient();
				try {
					// defaultHttpClient				
					HttpGet request = new HttpGet(MyConstants.URL);
					HttpResponse response = null;
					response = httpClient.execute(request);
					//HttpResponse httpResponse = httpClient.execute(request);
					HttpEntity httpEntity = response.getEntity();
					xmlList = EntityUtils.toString(httpEntity);
				}
				catch (MalformedURLException e) {
					xmlList = MyConstants.KEY_MalformedURLException;
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					xmlList = MyConstants.KEY_UnsupportedEncodingException;
					e.printStackTrace();				
				} catch (ClientProtocolException e) {
					xmlList = MyConstants.KEY_ClientProtocolException;
					e.printStackTrace();				
				} catch (SocketTimeoutException  e) {
					xmlList = MyConstants.KEY_SocketTimeoutException;
					e.printStackTrace();				
				} catch (ConnectTimeoutException  e) {
					xmlList = MyConstants.KEY_ConnectTimeoutException;
					e.printStackTrace();
				} catch (IOException e) {
					xmlList = e.toString();
					e.printStackTrace();
				} 
				finally {
					httpClient.getConnectionManager().shutdown();
					mp.release();
				}   
				return xmlList; 
			}   
		}
	}*/

}
