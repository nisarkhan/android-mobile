package net.issoa;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import net.issoa.R;
import net.issoa.helper.MyApplication;
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
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
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

	//ProgressDialog pDialog;

	public ArrayList<HashMap<String, String>> songsList  = new ArrayList<HashMap<String, String>>();


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player1);

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

		// Mediaplayer
		mp = new MediaPlayer(); 
		utils = new Utilities();

		// Listeners
		songProgressBar.setOnSeekBarChangeListener(this); // Important
		mp.setOnCompletionListener(this); // Important 
		
		// Starting a new async task
		new loadSurah_ListView().execute();

		/**
		 * Play button click event
		 * plays a song and changes button to pause image
		 * pauses a song and changes button to play image
		 * */
		btnPlay.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) { 

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
				//}
			}
		});

		/**
		 * Forward button click event
		 * Forwards song specified seconds
		 * */
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

		/**
		 * Backward button click event
		 * Backward song to specified seconds
		 * */
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

		/**
		 * Next button click event
		 * Plays next song by taking currentSongIndex + 1
		 * */
		btnNext.setOnClickListener(new View.OnClickListener() {


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

		/**
		 * Back button click event
		 * Plays previous song by currentSongIndex - 1
		 * */
		btnPrevious.setOnClickListener(new View.OnClickListener() {


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

		/**
		 * Button Click event for Repeat button
		 * Enables repeat flag to true
		 * */
		btnRepeat.setOnClickListener(new View.OnClickListener() {


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

		/**
		 * Button Click event for Shuffle button
		 * Enables shuffle flag to true
		 * */
		btnShuffle.setOnClickListener(new View.OnClickListener() {


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

		/**
		 * Button Click event for Play list click event
		 * Launches list activity which displays list of songs
		 * */
		btnPlaylist.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(), Play_Listing_Activity.class);
				startActivityForResult(i, 100);	
				// Starting a new async task

			}
		});

		mp.setOnPreparedListener(new OnPreparedListener() {

			public void onPrepared(MediaPlayer mp) {
				Log.d("onPrepared", "mp.start()");
				mp.start(); 
			}
		});

	}

	/**
	 * Receiving song index from playlist view
	 * and play the song
	 * */
	@Override
	protected void onActivityResult(int requestCode,
			int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == 100){
			currentSongIndex = data.getExtras().getInt("songIndex");
			// play selected song
			playSong(currentSongIndex);
		}

	}
	
	

	/**
	 * Function to play a song
	 * @param songIndex - index of song
	 * */
	public void  playSong(int songIndex){
		// Play song
		//mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
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
			mp.release();
			e.printStackTrace();
		} catch (IllegalStateException e) {
			mp.release();
			e.printStackTrace();
		} catch (IOException e) {
			mp.release();
			e.printStackTrace();
		}
	}

	/**
	 * Update timer on seekbar
	 * */
	public void updateProgressBar() {
		mHandler.postDelayed(mUpdateTimeTask, 100);        
	}	

	/**
	 * Background Runnable thread
	 * */
	private Runnable mUpdateTimeTask = new Runnable() {
		public void run() {
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

	/**
	 * When user starts moving the progress handler
	 * */

	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch)  { }

	/**
	 * When user starts moving the progress handler
	 * */
	public void onStartTrackingTouch(SeekBar seekBar) {
		// remove message Handler from updating progress bar
		mHandler.removeCallbacks(mUpdateTimeTask);
	}

	/**
	 * When user stops moving the progress hanlder
	 * */

	public void onStopTrackingTouch(SeekBar seekBar) {
		mHandler.removeCallbacks(mUpdateTimeTask);
		int totalDuration = mp.getDuration();
		int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);

		// forward or backward to certain seconds
		mp.seekTo(currentPosition);

		// update timer progress again
		updateProgressBar();
	}

	/**
	 * On Song Playing completed
	 * if repeat is ON play same song again
	 * if shuffle is ON play random song
	 * */
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
		Log.d("destroy", "mp.release");
		
	}

	public class loadSurah_ListView extends AsyncTask<Void, Void, String> { 

		protected ProgressDialog pDialog;
		@Override
		protected void onPreExecute() {
			// Showing progress dialog before sending http request
			/*pDialog = new ProgressDialog(Media_Player_Activity.this);
			pDialog.setMessage(Media_Player_Activity.this.getString(R.string.loading_msg_dialog));
			pDialog.setIndeterminate(true);
			pDialog.setCancelable(false);
			pDialog.show();*/
			pDialog = ProgressDialog.show(Media_Player_Activity.this,  "Loading....", Media_Player_Activity.this.getString(R.string.loading_msg_dialog), true, false);
			
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
					mp.release();
				}
				if (result.contains("UnknownHostException")) {					
					songTitleLabel.setText("You have to be connected to the internet for this application to work.");
					btnPlaylist.setEnabled(false);
					btnPlaylist.setImageResource(R.drawable.img_btn_playlist_disabled);
					songTitleLabel.setTextColor(Color.RED);
					mp.release();
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
				mp.release();
			}
			// closing progress dialog
			pDialog.dismiss(); 
		}

		@Override
		protected String doInBackground(Void... params) 
		{
			String xmlList = null;
			xmlList = ((MyApplication) getApplicationContext()).getSurahListVariable();
			if(xmlList == null || xmlList.length() == 0)  
			{
				Log.d("main_player_activity", "nocache");
				// defaultHttpClient
				DefaultHttpClient httpClient = new DefaultHttpClient();
				try { 
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
					e.printStackTrace();
					xmlList = MyConstants.KEY_UnsupportedEncodingException;
				} catch (ClientProtocolException e) {
					e.printStackTrace();
					xmlList = MyConstants.KEY_ClientProtocolException;
				} catch (SocketTimeoutException  e) {
					e.printStackTrace();
					xmlList = MyConstants.KEY_SocketTimeoutException;
				} catch (ConnectTimeoutException  e) {
					e.printStackTrace();
					xmlList = MyConstants.KEY_ConnectTimeoutException;
				} catch (IOException e) {
					xmlList = e.toString();
					e.printStackTrace();
				} 
				//finally {
				//	httpClient.getConnectionManager().shutdown();
				//}  
				// return XML
				((MyApplication) getApplication()).setSurahListVariable(xmlList); 
			}
			Log.d("main_player_activity", "cache");
			return xmlList;
		}   
	}
}