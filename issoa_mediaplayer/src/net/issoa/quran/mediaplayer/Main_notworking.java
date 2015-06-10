package net.issoa.quran.mediaplayer;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Main_notworking extends ListActivity{
	//http://www.softwarepassion.com/android-series-custom-listview-items-and-adapters/

	private ArrayList<Surah> arrayListSurahs = null;
	private SurahAdapter surah_adapter;
	//private Runnable viewOrders;

	//accessing web site variables:
	private String directory_listings = "";
	private String[] array_surahs;	
	private TextView tv_selelctedFile = null;
	private TextView tv_mediaFileLengthInMilliseconds  = null;

	private static final int UPDATE_FREQUENCY = 500;
	private static final int STEP_VALUE = 4000;

	//String url1 = "http://ofertaweb.ro/android/sleepandlovemusic/list_files.php";
	public static final String url = "http://issoa.net/surah99.htm";
	public static final String url_loop = "http://www.salafitapes.com/noblequran/";


	private SeekBar progressControlSeekbar = null;
	private SeekBar volControlSeekbar = null;

	public RadioButton rbt_repeat_all, rbt_repeat_one, rbt_shuffle;
	public int counter = 1;

	private MediaPlayer mediaPlayer = null;
	private ImageButton playButton = null;
	private ImageButton prevButton = null;
	private ImageButton nextButton = null;
	private final Handler handler = new Handler();

	private boolean isStarted = true;
	private String playUrl = "";
	private boolean isMoveingSeekBar = false;
	private ProgressDialog m_ProgressDialog = null; 

	private AudioManager audio;
	long durationInMillis = -1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);  
		setContentView(R.layout.main);
		initView();
	} 

	
/*	private void setPlayer(){

        getWindow().setFormat(PixelFormat.UNKNOWN);
        mediaPlayer = new MediaPlayer();    

        mediaPlayer.setOnBufferingUpdateListener(new OnBufferingUpdateListener() {

            public void onBufferingUpdate(MediaPlayer mp, int percent) {
            	progressControlSeekbar.setSecondaryProgress((progressControlSeekbar.getMax()/100)*percent);

            }
        });
        mediaPlayer.setOnPreparedListener(new OnPreparedListener() {

            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
                //isStop=false;
                durationInMillis = mediaPlayer.getDuration();
                //LTDAudioPlayer.this.setProgressControl();
            }
        });
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }*/
	
	/** This method initialise all the views in project*/
	private void initView() {

		arrayListSurahs = new ArrayList<Surah>();		 

		tv_selelctedFile = (TextView)findViewById(R.id.selectedfile);
		progressControlSeekbar = (SeekBar)findViewById(R.id.seekbar);
		//seekbar.setMax(99); // It means 100% .0-99

		volControlSeekbar = (SeekBar)findViewById(R.id.volumebar);
		playButton = (ImageButton)findViewById(R.id.play);
		prevButton = (ImageButton)findViewById(R.id.prev);
		nextButton = (ImageButton)findViewById(R.id.next);

		rbt_repeat_all = (RadioButton) findViewById(R.id.rpt_all);
		rbt_repeat_one = (RadioButton) findViewById(R.id.rpt_one);
		rbt_shuffle = (RadioButton) findViewById(R.id.shuffle);

		mediaPlayer = new MediaPlayer();

		mediaPlayer.setOnCompletionListener(onCompletion);
		mediaPlayer.setOnErrorListener(onError);
		//progressControlSeekbar.setOnSeekBarChangeListener(progressControlSeekbarChanged);
		//volumebar.setOnSeekBarChangeListener(volumeBarChanged);

		playButton.setOnClickListener(onButtonClick);
		nextButton.setOnClickListener(onButtonClick);
		prevButton.setOnClickListener(onButtonClick);

		//audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		//AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
		//audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 40, AudioManager.FLAG_PLAY_SOUND);

		audio = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
		int maxVolume = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		int curVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);

		volControlSeekbar.setMax(maxVolume);
		volControlSeekbar.setProgress(curVolume);

		volControlSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() 
		{
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
			}
			//
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
			}
			//
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
				audio.setStreamVolume(AudioManager.STREAM_MUSIC, arg1, 0);
			}
		}); 

		m_ProgressDialog = ProgressDialog.show(this, "Please wait...", "Retrieving data ...", true);
		//http://groups.google.com/group/android-developers/browse_thread/thread/a5057f1d9c00e9e3/e0f98737a4043166?lnk=gst&q=+publishProgress#e0f98737a4043166

		new GrabUrlAsyncTask().execute(); 
		//GrabUrlAsyncTask graburls = new GrabUrlAsyncTask(this);
		//new GrabUrlAsyncTask(this).execute();

		this.surah_adapter = new SurahAdapter(this, R.layout.row, arrayListSurahs);
		setListAdapter(this.surah_adapter); 
		
		//this.setPlayer();
		
	}
	/*private OnSeekBarChangeListener VolumeChange = new 	OnSeekBarChangeListener()
	{ 
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
		{ 
            int index = seekbar.getProgress(); 
            audio.setStreamVolume(AudioManager.STREAM_MUSIC, index, 1); 
            //volume_label.setText(String.valueOf(index)); 
        } 
        public void onStartTrackingTouch(SeekBar seekBar) 
        {
        	//
        } 

        public void onStopTrackingTouch(SeekBar seekBar) 
        {
        	//
        } 
	};*/

	private MediaPlayer.OnCompletionListener onCompletion = new MediaPlayer.OnCompletionListener() 
	{		 
		public void onCompletion(MediaPlayer mp) {
			//stopPlay();

			//http://stackoverflow.com/questions/6125426/playing-audio-files-one-after-another-in-android
			/*if(i < numberOfFiles) {
                i++;
                playAudio("/sdcard/MyAudio/RecAudio"+i+".3gp");
            } else i = 0;*/

			if (rbt_repeat_all.isChecked()) {
				//Log.v("repeat", "REPEAT ALL CHECKED");

				counter = counter + 1;
				if (counter > 114)
					counter = 1;

				//Toast.makeText(getApplicationContext(),	"Playing: " + songs_array[counter],	Toast.LENGTH_LONG).show();

				//Log.v("set on completion now counter is", 
				//String.valueOf(counter));

				playButton.setImageResource(android.R.drawable.ic_media_pause);
				//pauseButton = false;

				mediaPlayer.stop();
				mediaPlayer.reset();

				//url = "http://ofertaweb.ro/android/sleepandlovemusic/" + songs_array[counter] + ".mp3";
				String newURL = "";// url_loop + songs_array[counter];//+ ".mp3";

				if(array_surahs.length > 0)
				{ 
					//for(int i=0;i<songs_array.length;i++)
					//{
					String[] _surahs = array_surahs[counter].split(">>>"); 
					//Surah _surah = new Surah(_surahs[0], _surahs[1], _surahs[2], _surahs[3]);
					newURL = _surahs[3];	

					tv_selelctedFile = (TextView)findViewById(R.id.selectedfile);		
					tv_selelctedFile.setText(_surahs[0]);
					//}
				} 				

				try {
					mediaPlayer.setDataSource(newURL);
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SecurityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalStateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				//url = "http://ofertaweb.ro/android/sleepandlovemusic/" + songs_array[counter] + ".mp3";
				//url = "http://www.salafitapes.com/noblequran/" + songs_array[counter] + ".mp3";
				try {
					mediaPlayer.prepare();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mediaPlayer.start();

			}
		}

	};

	private MediaPlayer.OnErrorListener onError = new MediaPlayer.OnErrorListener() {

		public boolean onError(MediaPlayer mp, int what, int extra) {
			// returning false will call the OnCompletionListener
			return false;
		}
	};

	private class GrabUrlAsyncTask extends AsyncTask<Void, Void, List<Surah>> 
	{ 	 
		@Override 
		protected List<Surah> doInBackground(Void... params) 
		{ 			
			List<Surah> surahs = new ArrayList<Surah>();   
			try 
			{
				Get_Webpage obj = new Get_Webpage(url);
				directory_listings = obj.get_webpage_source();
				array_surahs = directory_listings.split(":::");  	

				if(array_surahs.length > 0)
				{ 
					for(int i=0;i<array_surahs.length;i++)
					{
						String[] _surahs = array_surahs[i].split(">>>"); 
						//String id, String name, String meaning, String url
						Surah _surah = new Surah(_surahs[0], _surahs[1], _surahs[2], _surahs[3]);					 
						surahs.add(_surah); 
					}
				} 				
			} 
			catch (Exception e) 
			{				
				//http://stackoverflow.com/questions/9357513/cant-create-handler-inside-thread-that-has-not-called-looper-prepare-with-asy
				Main_notworking.this.runOnUiThread(new Runnable() 
				{ 
					public void run() 
					{ 
						m_ProgressDialog.dismiss();						
						Toast.makeText(Main_notworking.this, "You have to be connected to the internet for this application to work", Toast.LENGTH_LONG).show();
						finish();						
					} 
				}); 				
				//finish();
				//	m_ProgressDialog.dismiss();				
				//Toast.makeText(Main.this, "You have to be connected to the internet for this application to work", Toast.LENGTH_LONG).show();
				//showToast("You have to be connected to the internet for this application to work");	
			}
			return surahs;  
		} 

		protected void onProgressUpdate(Void... values)
		{       
			//http://stackoverflow.com/questions/3614663/cant-create-handler-inside-thread-that-has-not-called-looper-prepare-inside-a
			super.onProgressUpdate(values);         
			m_ProgressDialog.dismiss();         
			//downloadSpinnerProgressDialog.show();    
		} 		

		@Override 
		protected void onPostExecute(List<Surah> result) 
		{ 			 
			//showToast("You have to be connected to the internet for this application to work");	
			arrayListSurahs.clear();     
			arrayListSurahs.addAll(result);   
			m_ProgressDialog.dismiss();
			surah_adapter.notifyDataSetChanged();   
		} 
	}  

	private View.OnClickListener onButtonClick = new View.OnClickListener() 
	{
		public void onClick(View v) {
			switch(v.getId())
			{
			case R.id.play:
			{
				if(mediaPlayer.isPlaying())
				{
					handler.removeCallbacks(updatePositionRunnable);
					mediaPlayer.pause();
					playButton.setImageResource(android.R.drawable.ic_media_play);
				}
				else
				{
					if(isStarted)
					{
						mediaPlayer.start();
						playButton.setImageResource(android.R.drawable.ic_media_pause);

						updatePosition();
					}
					else
					{
						startPlay(playUrl, "");
					}
				}
				break;
			}
			case R.id.next:
			{
				int seekto = mediaPlayer.getCurrentPosition() + STEP_VALUE;

				if(seekto > mediaPlayer.getDuration())
					seekto = mediaPlayer.getDuration();

				mediaPlayer.pause();
				mediaPlayer.seekTo(seekto);
				mediaPlayer.start();

				break;
			}
			case R.id.prev:
			{
				int seekto = mediaPlayer.getCurrentPosition() - STEP_VALUE;

				if(seekto < 0)
					seekto = 0;

				mediaPlayer.pause();
				mediaPlayer.seekTo(seekto);
				mediaPlayer.start();

				break;
			}
			}
			//primarySeekBarProgressUpdater();
		}
	};	



	/*private SeekBar.OnSeekBarChangeListener volumeBarChanged = new SeekBar.OnSeekBarChangeListener() 
	{		 
		public void onStopTrackingTouch(SeekBar seekBar) 
		{
			isMoveingSeekBar = false;
		}

		public void onStartTrackingTouch(SeekBar seekBar) 
		{
			isMoveingSeekBar = true;
		}

		public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
			if(isMoveingSeekBar)
			{
				audio.setStreamVolume(AudioManager.STREAM_MUSIC, progress, AudioManager.FLAG_SHOW_UI);
				Log.i("OnSeekBarChangeListener","onProgressChanged");
			}
		}
	};*/

	/*private SeekBar.OnSeekBarChangeListener progressControlSeekbarChanged = new SeekBar.OnSeekBarChangeListener() {

		public void onStopTrackingTouch(SeekBar seekBar) 
		{
			isMoveingSeekBar = false;
		}

		public void onStartTrackingTouch(SeekBar seekBar) 
		{
			isMoveingSeekBar = true;
		}

		public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) 
		{
			if(isMoveingSeekBar)
			{
				mediaPlayer.seekTo(progress);

				Log.i("OnSeekBarChangeListener","onProgressChanged");
			}
		}
	};*/

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		//Object o = this.getListAdapter().getItem(position);
		//String keyword = o.toString();
		//String currentFile = (String) v.getTag();
		tv_selelctedFile = (TextView)findViewById(R.id.selectedfile);		
		tv_selelctedFile.setText(arrayListSurahs.get(position).getSurahName());



		playUrl = arrayListSurahs.get(position).getSurahUrl();//(String) v.getTag();
		startPlay(playUrl, tv_selelctedFile.getText().toString());		

		//Toast.makeText(this, "you have selected " + keyword, Toast.LENGTH_LONG).show();
		showToast("You are playing : " + arrayListSurahs.get(position).getSurahName());
		//+ " - " + m_orders.get(position).getOrderStatus() + " - " + m_orders.get(position).getQuantity());


		//tv_mediaFileLengthInMilliseconds = (TextView)findViewById(R.id.duration);	
		//tv_mediaFileLengthInMilliseconds.setText(player.getDuration());


	}

	private void updatePosition()
	{
		handler.removeCallbacks(updatePositionRunnable);

		progressControlSeekbar.setProgress(mediaPlayer.getCurrentPosition());

		handler.postDelayed(updatePositionRunnable, UPDATE_FREQUENCY);
	}

	private final Runnable updatePositionRunnable = new Runnable() 
	{
		public void run() {
			updatePosition();
		}
	};	

	private void startPlay(String file, String name) 
	{
		Log.i("Selected: ", file);

		tv_selelctedFile.setText(name);
		
		//progressControlSeekbar.setProgress(0);
		
		int maxVolume = mediaPlayer.getDuration();
        int curVolume = mediaPlayer.getCurrentPosition();

        progressControlSeekbar.setMax(maxVolume);
        progressControlSeekbar.setProgress(curVolume);
        progressControlSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

        	public void onStopTrackingTouch(SeekBar seekBar) 
    		{
    			isMoveingSeekBar = false;
    		}
    		public void onStartTrackingTouch(SeekBar seekBar) 
    		{
    			isMoveingSeekBar = true;
    		}
    		public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) 
    		{
    			if(isMoveingSeekBar)
    			{
    				mediaPlayer.seekTo(progress);
    				Log.i("OnSeekBarChangeListener","onProgressChanged");
    			}
    		}
        });     

		mediaPlayer.stop();
		mediaPlayer.reset();

		try {
			mediaPlayer.setDataSource(file);
			mediaPlayer.prepare();
			mediaPlayer.start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		progressControlSeekbar.setMax(mediaPlayer.getDuration());
		playButton.setImageResource(android.R.drawable.ic_media_pause);

		updatePosition();

		isStarted = true;
	}

	 
    
	private void stopPlay() 
	{
		mediaPlayer.stop();
		mediaPlayer.reset();
		playButton.setImageResource(android.R.drawable.ic_media_play);
		handler.removeCallbacks(updatePositionRunnable);
		progressControlSeekbar.setProgress(0);

		isStarted = false;
	}

	private void showToast(String message) 
	{
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}  

	private class SurahAdapter extends ArrayAdapter<Surah> 
	{
		private ArrayList<Surah> items;

		public SurahAdapter(Context context, int textViewResourceId, ArrayList<Surah> items) 
		{
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) 
		{
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.row, null);
			}
			Surah o = items.get(position);
			if (o != null) {
				TextView txtSurahId = (TextView) v.findViewById(R.id.android_surah_id);
				TextView txtSurah_name = (TextView) v.findViewById(R.id.android_surah_name);
				TextView txtSurah_duration = (TextView) v.findViewById(R.id.android_surah_duration);

				if(txtSurahId != null)
				{
					txtSurahId.setText(o.getSurahId().trim()); //duration time.
					txtSurah_duration.setText("02:05"); //time duration
				}
				if(txtSurah_name != null){
					txtSurah_name.setText(o.getSurahName().trim() + " - (" + o.getSurahMeaning().trim() + ")");
				} 
			}
			return v;
		}
	}
}