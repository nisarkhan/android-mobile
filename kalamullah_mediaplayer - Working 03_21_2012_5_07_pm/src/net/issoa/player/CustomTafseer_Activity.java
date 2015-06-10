package net.issoa.player;

import net.issoa.R;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;
 
public class CustomTafseer_Activity extends Activity { 
	
  private TextView txtDisplay; 
  @Override
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.tafseer_layout_01);
      int default_value = 0;
      txtDisplay = (TextView) findViewById(R.id.textView1);     

       Resources res = getResources();   
       int position = getIntent().getIntExtra("position", default_value); 
        
        
       String[] planets = res.getStringArray(R.array.readmore_array);
       txtDisplay.setText(planets[position]);
       
      /* switch (position) 
       { 
       	case 0: 
       		txtDisplay.setText(R.string.tafseer_0); 
       		break; 
       	case 1: 
       		txtDisplay.setText(R.string.tafseer_1); 
       		break; 
       	case 1: txtDisplay.setText(R.string.tafseer_2); break; 
      
       	} */
       
      
      /* int id = res.getIdentifier("tafseer_" + String.valueOf(position), "string", getPackageName()); 
       String str = res.getString(id); 
       txtDisplay.setText(str);*/
       
     /* if (position > 0)
      {  
    	  String _format = "tafseer_" + String.valueOf(position);
    	  String text = String.format(res.getString(R.string.tafseer_), _format);
    	  //txtDisplay.setText(text); 
    	  txtDisplay.setText(_format); 
      } else {
    	  
    	  txtDisplay.setText(R.string.tafseer_0); 
      } */
      
  }
  
   

}

