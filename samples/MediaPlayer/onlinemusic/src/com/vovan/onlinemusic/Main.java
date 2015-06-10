package com.vovan.onlinemusic;

import java.io.IOException;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import com.vovan.onlinemusic.R;

public class Main extends Activity {

	public ImageButton btn_play;
	public Button btn_alarm, btn_close;
	public boolean pause = false;
	public String url;
	public MediaPlayer mediaPlayer;
	public SeekBar seekbar2;
	public AudioManager audioManager;
	public Spinner spinner1;
	public RadioButton rb0, rb1, rb2;
	public int counter = 1;
	public String[] songs_array;	
	public String directory_listings = "";
	public String directory_listings1 = "";
	public AlertDialog.Builder builder;
	public AlertDialog alert;
	public int remining_time;	   
    protected   int     timeTickDown        = 10;
    public TextView txt_countdown;
    
    public boolean timer_canceled = false;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		btn_play = (ImageButton) findViewById(R.id.imageButton_play);

		btn_close = (Button) findViewById(R.id.button_close);
		seekbar2 = (SeekBar) findViewById(R.id.seekBar2);
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		rb0 = (RadioButton) findViewById(R.id.radio0);
		rb1 = (RadioButton) findViewById(R.id.radio1);
		rb2 = (RadioButton) findViewById(R.id.radio2);


		try {
			 Get_Webpage obj = new Get_Webpage("http://ofertaweb.ro/android/sleepandlovemusic/list_files.php");
			 
			 directory_listings = obj.get_webpage_source();
		} catch (Exception e) {
			Toast.makeText(
					this,
					"You have to be connected to the internet for this application to work",
					Toast.LENGTH_LONG).show();
			finish();
		}

		//Log.d("director listing", directory_listings);

		songs_array = directory_listings.split(":::");

		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, songs_array);
		spinnerArrayAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(spinnerArrayAdapter);

		audioManager = (AudioManager) getSystemService(Main.AUDIO_SERVICE);
		int maxVolume = audioManager
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

		seekbar2.setMax(maxVolume);	
		seekbar2.setProgress(curVolume);


		mediaPlayer = new MediaPlayer();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

		btn_play.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				if (pause == false) {
					btn_play.setImageResource(android.R.drawable.ic_media_pause);
					pause = true;

					try {
						url = "http://ofertaweb.ro/android/sleepandlovemusic/" + songs_array[counter] + ".mp3";
						//url = "http://www.salafitapes.com/noblequran/100.mp3";
						mediaPlayer.setDataSource(url);//"http://www.kalamullah.com/Quran/Noble%20Quran/001.mp3");
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						mediaPlayer.prepare();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} // might take long! (for buffering, etc)
					mediaPlayer.start();

				} else {
					btn_play.setImageResource(android.R.drawable.ic_media_play);
					pause = false;
					mediaPlayer.pause();

				}
			}
		});

		seekbar2.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
						progress, 0);

			}
		});

		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			public void onCompletion(MediaPlayer mp) {

				if (rb0.isChecked()) {
					//Log.v("repeat", "REPEAT ALL CHECKED");

					counter = counter + 1;
					if (counter > 24)
						counter = 1;

					Toast.makeText(getApplicationContext(),
							"Playing: " + songs_array[counter],
							Toast.LENGTH_LONG).show();

					//Log.v("set on completion now counter is",
					//		String.valueOf(counter));

					btn_play.setImageResource(android.R.drawable.ic_media_pause);
					pause = false;

					mediaPlayer.stop();
					mediaPlayer.reset();

					url = "http://ofertaweb.ro/android/sleepandlovemusic/" + songs_array[counter] + ".mp3";
					//url = "http://www.salafitapes.com/noblequran/" + songs_array[counter] + ".mp3";
					try {
						mediaPlayer.setDataSource(url);
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

					url = "http://ofertaweb.ro/android/sleepandlovemusic/" + songs_array[counter] + ".mp3";
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

				if (rb1.isChecked()) {
					//Log.v("repeat", "REPEAT 1");

					Toast.makeText(getApplicationContext(),
							"Playing: " + songs_array[counter],
							Toast.LENGTH_LONG).show();

					mediaPlayer.seekTo(0);

					mediaPlayer.start();

				}
				if (rb2.isChecked()) {
				//	Log.v("repeat", "REPEAT shuffle");

					int cur_counter = counter;

					Random rand = new Random();
					counter = rand.nextInt(24);

					if (cur_counter == counter)
						counter = rand.nextInt(24);
					counter = rand.nextInt(24);

					if (cur_counter == counter)
						counter = rand.nextInt(11);
					counter = rand.nextInt(11);
					

					Toast.makeText(getApplicationContext(),
							"Playing: " + songs_array[counter],
							Toast.LENGTH_LONG).show();

					//Log.v("set on completion now counter is",
					//		String.valueOf(counter));

					btn_play.setImageResource(android.R.drawable.ic_media_pause);
					pause = false;

					mediaPlayer.stop();
					mediaPlayer.reset();

					url = "http://ofertaweb.ro/android/sleepandlovemusic/" + songs_array[counter] + ".mp3";
					//url = "http://www.salafitapes.com/noblequran/" + songs_array[counter] + ".mp3";
					try {
						mediaPlayer.setDataSource(url);
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

					url = "http://ofertaweb.ro/android/sleepandlovemusic/" + songs_array[counter] + ".mp3";
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
		});

		spinner1.setOnItemSelectedListener(new MyOnItemSelectedListener());

			

		btn_close.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mediaPlayer.release();
			finish();
			}
		});
	}

	public class MyOnItemSelectedListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {

			if (mediaPlayer.isPlaying())
				mediaPlayer.stop();
			mediaPlayer.reset();

			counter = pos;

			url = "http://ofertaweb.ro/android/sleepandlovemusic/"
					+ songs_array[counter] + ".mp3";

			//Log.v("now counter is", String.valueOf(counter));

			btn_play.setImageResource(android.R.drawable.ic_media_play);
			pause = false;

		}

		public void onNothingSelected(AdapterView parent) {
			// Do nothing.
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.exit:
			finish();
			break;

		case R.id.info:

			Intent i = new Intent(Main.this, About.class);
			startActivity(i);
			break;

		}
		return true;
	}

	
	
}