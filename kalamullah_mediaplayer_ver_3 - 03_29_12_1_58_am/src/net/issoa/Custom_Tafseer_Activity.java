package net.issoa; 
 
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
 
public class Custom_Tafseer_Activity extends Activity { 
	
  private TextView txtDisplay; 
  private TextView tv_title_surah_name; 
  @Override
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.custom_read_desc_surah_layout);
      int default_value = 0;
      
      String escapeStr = "< > \" &";
      
      txtDisplay = (TextView) findViewById(R.id.tv_display_surah_tafseer);   
      tv_title_surah_name = (TextView) findViewById(R.id.tv_surah_name);    
      
       Resources res = getResources();   
       int position = getIntent().getIntExtra("position", default_value); 
        
       String tv_surahName = getIntent().getStringExtra("surah_name_a"); 
       tv_title_surah_name.setText(tv_surahName);
        
       String[] planets = res.getStringArray(R.array.readmore_array);
       //http://stackoverflow.com/questions/1748977/making-textview-scrollable-in-android
       //txtDisplay.setMovementMethod(new ScrollingMovementMethod());
       txtDisplay.setText(planets[position]);  
  } 
} 