package net.issoa.player;

import android.app.Activity;
import android.os.Bundle;

public class CreatingNewThread extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		//here is how you will create for new thread
		
		new Thread() {
			
			@Override
			public void run() {
				//your code here...
				
			}
		}.start();
		
	}
}
