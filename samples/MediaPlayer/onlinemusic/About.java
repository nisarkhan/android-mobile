package com.vovan.onlinemusic;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.vovan.onlinemusic.R;

public class About extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);

		Button btn_exit = (Button) findViewById(R.id.exit);
		
		
		Button btn_rate = (Button) findViewById(R.id.button1);
		
		btn_rate.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {

				Toast.makeText(getApplicationContext(), "Thank you very much", 3000).show();
				Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse( "https://market.android.com/details?id=com.danandrei.relaxlovesleepsounds" ) );
  			    startActivity( browse );
 
				
			}
		});
		
		/*
		Button btn_visit = (Button) findViewById(R.id.button_visit_site);
		
		btn_visit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {							
				Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse( "https://m.demotivation.us" ) );
  			    startActivity( browse );
				
			}
		});
		*/

		btn_exit.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				finish();
			}
		});

	}

}
