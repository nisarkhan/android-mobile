package net.issoa.dashboard;
import net.issoa.R;
import net.issoa.player.Player;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
 

public class Main_Dashboard_Activity extends Activity {
    
	//Button btn_quran_arabic_english = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.dashboard_layout); 
        
        
        /**
         * Creating all buttons instances
         * */
        // Dashboard News feed button
      	Button btn_quran_arabic_english = (Button) findViewById(R.id.btn_quran_arabic_english);
        
        // Dashboard Friends button
        Button btn_shaykh_ali_jaber = (Button) findViewById(R.id.btn_shaykh_ali_jaber);
        
        // Dashboard Messages button
        Button btn_saud_ash_shuraim = (Button) findViewById(R.id.btn_saud_ash_shuraim);
        
        // Dashboard Places button
        Button btn_meshary_afasy = (Button) findViewById(R.id.btn_meshary_afasy);
        
        // Dashboard Events button
        Button btn_hani_rifai = (Button) findViewById(R.id.btn_hani_rifai);
        
        // Dashboard Photos button
        Button btn_ghamidi = (Button) findViewById(R.id.btn_ghamidi);
        
        /**
         * Handling all button click events
         * */
        
        // Listening to News Feed button click
        btn_quran_arabic_english.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View view) {
				// Launching News Feed Screen
				//pass some data to the extra's in the intent
				//
				/*String newsFeedButtonText = btn_newsfeed.getText().toString();
				Intent i = new Intent(getApplicationContext(), NewsFeedActivity.class);
				
				//if (newsFeedButtonText != null && newsFeedButtonText.toString().equals(""))
				//{
					
				//}
				i.putExtra("btn_quran_arabic_english", newsFeedButtonText.toString());
				startActivity(i);*/
				
				//showing the player here...
				//String newsFeedButtonText = btn_newsfeed.getText().toString();
				//Intent i = new Intent(getApplicationContext(), PlayListActivity.class);
				//startActivity(i);
				
				Intent i = new Intent(getApplicationContext(), Player.class);
				startActivityForResult(i, 100);	
				
				
				
			}
		});
        
       // Listening Friends button click
        btn_shaykh_ali_jaber.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View view) {
				// Launching News Feed Screen
				Intent i = new Intent(getApplicationContext(), Shaykh_Ali_Jaber_Activity.class);
				startActivity(i);
			}
		});
        
        // Listening Messages button click
        btn_saud_ash_shuraim.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View view) {
				// Launching News Feed Screen
				Intent i = new Intent(getApplicationContext(), Saud_Ash_Shuraim_Activity.class);
				startActivity(i);
			}
		});
        
        // Listening to Places button click
        btn_meshary_afasy.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View view) {
				// Launching News Feed Screen
				Intent i = new Intent(getApplicationContext(), Meshary_Afasy_Activity.class);
				startActivity(i);
			}
		});
        
        // Listening to Events button click
        btn_hani_rifai.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View view) {
				// Launching News Feed Screen
				Intent i = new Intent(getApplicationContext(), Hani_Rifai_Activity.class);
				startActivity(i);
			}
		});
        
        // Listening to Photos button click
        btn_ghamidi.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View view) {
				// Launching News Feed Screen
				Intent i = new Intent(getApplicationContext(), Ghamidi_Activity.class);
				startActivity(i);
			}
		});
    }
}