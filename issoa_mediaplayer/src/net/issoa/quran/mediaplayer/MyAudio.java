package net.issoa.quran.mediaplayer;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;

public class MyAudio extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    	//http://stackoverflow.com/questions/3535949/how-to-use-mediacontroller-mediaplayercontrol
    	//http://misha.beshkin.lv/android-how-to-play-audio-and-video-from-app/
    	  super.onCreate(savedInstanceState);	
        MyAudioView audio = new MyAudioView(this);
        audio.setMediaController(new MediaController(this));
      
        audio.setAudioURI(Uri.parse("http://www.salafitapes.com/noblequran/12.mp3"));
        audio.setImageResource(R.drawable.favicon);
        setContentView(audio);
    }
}