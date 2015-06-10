package com.marcodinacci.book.acb;

import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.MediaController;
import android.widget.MediaController.MediaPlayerControl;

public class PlayAudioActivity extends Activity implements MediaPlayerControl {
    private static final String PLAY_AUDIO = "PLAY_AUDIO";
	private MediaController mMediaController;
	private MediaPlayer mMediaPlayer;
	private Handler mHandler = new Handler();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mMediaPlayer = new MediaPlayer();
        mMediaController = new MediaController(this);
        mMediaController.setMediaPlayer(PlayAudioActivity.this);
        mMediaController.setAnchorView(findViewById(R.id.audioView));
        
        String audioFile = "http://www.salafitapes.com/noblequran/12.mp3" ; 
        try {
        	mMediaPlayer.setDataSource(audioFile);
        	mMediaPlayer.prepare();
        } catch (IOException e) {
        	Log.e(PLAY_AUDIO, "Could not open file " + audioFile + " for playback.", e);
		}
        
        mMediaPlayer.setOnPreparedListener(new OnPreparedListener() {
        	public void onPrepared(MediaPlayer mp) {
        		mHandler.post(new Runnable() {
        			public void run() {
        				mMediaController.show(10000);
        				mMediaPlayer.start();
        			}
        		});
        	}
        });
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	mMediaPlayer.stop();
    	mMediaPlayer.release();
    }
    
	public boolean canPause() {
		return true;
	}

	public boolean canSeekBackward() {
		return false;
	}

	 
	public boolean canSeekForward() {
		return false;
	}
	
	 
	public int getBufferPercentage() {
		int percentage = (mMediaPlayer.getCurrentPosition() * 100) / mMediaPlayer.getDuration();
		
		return percentage;
	}

	 
	public int getCurrentPosition() {
		return mMediaPlayer.getCurrentPosition();
	}

	 
	public int getDuration() {
		return mMediaPlayer.getDuration();
	}

	 
	public boolean isPlaying() {
		return mMediaPlayer.isPlaying();
	}

	 
	public void pause() {
		if(mMediaPlayer.isPlaying())
			mMediaPlayer.pause();
	}

	 
	public void seekTo(int pos) {
		mMediaPlayer.seekTo(pos);
	}

	 
	public void start() {
		mMediaPlayer.start();
	}
	
	 
	public boolean onTouchEvent(MotionEvent event) {
		mMediaController.show();
		
		return false;
	}
}