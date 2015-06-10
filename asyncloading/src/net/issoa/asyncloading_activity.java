package net.issoa;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class asyncloading_activity extends Activity  {

	public static final String TAG = "asyncloading_activity";

	public ProgressBar progressBar = null;
	public TextView taskStatus = null;
	public Integer _number = 0;
	public ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

	/*public ArrayList<HashMap<String, String>> getSongList(ArrayList<HashMap<String, String>> r) {
		 String songTitle = songsList.get(0).get("title_a");

				Log.d("onCreate", ""+songTitle);

		return songsList = r;
	}*/


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.main);

		progressBar = (ProgressBar) findViewById(R.id.loaderProgressBar);
		taskStatus = (TextView) findViewById(R.id.taskStatus);
		int numSteps = 5;		 
		
		if (_number >0)
		{
			Log.d("onSuccessfulExecute", ""+_number);
		}
		else
		{
			Log.d("onSuccessfulExecute", "nope empty songs lists");
		}
		
		/*new asynctask().execute(
				new asynctask.Payload(songsList, new Object[] { asyncloading_activity.this, new Integer(numSteps) })); */
		// String songTitle = songsList.get(0).get("title_a");
			/*	if (songsList.size() >0)
				{
					Log.d("onCreate", ""+songsList.size());
				}
				else
				{
					Log.d("onCreate", "nope empty songs lists");
				}*/
		

		((Button) findViewById(R.id.demoButton1)).setOnClickListener(
				new View.OnClickListener() {
					public void onClick(View v) {
						int numSteps = 5;

						progressBar.setMax(numSteps);
						progressBar.setProgress(0);

						taskStatus.setText("Running");

						/* new asynctask().execute(
							 new asynctask.Payload(songsList, new Object[] { asyncloading_activity.this, new Integer(numSteps) }));
						*/
						// String songTitle = songsList.get(0).get("title_a");
						if (songsList.size() >0)
						{
							Log.d("demoButton1xxxxxxxxxxxxxx", ""+songsList.size());
							Toast toast = Toast.makeText(v.getContext(), "Generated number: " + songsList.get(0).get("title_e"), Toast.LENGTH_LONG);
				            toast.show();  
						}
						else
						{
							Log.d("demoButton1", "nope empty songs lists");
						}
						
						//Toast toast = Toast.makeText(v.getContext(), "Generated number: " + String.valueOf(_number), Toast.LENGTH_LONG);
			          //  toast.show();  
			            
						
					}
				}
				);
		
		new asynctask(this).execute(
				new asynctask.Payload(songsList, new Object[] { asyncloading_activity.this, new Integer(numSteps) }));
	
		if (_number >0)
		{
			Log.d("onSuccessfulExecute", ""+_number);
		}
		else
		{
			Log.d("onSuccessfulExecute", "nope empty songs lists");
		}

	}

/*	public int onSuccessfulExecute(int numberOfSongList) {
		// TODO Auto-generated method stub
		_number = numberOfSongList;
		if (numberOfSongList >0)
		{
			Log.d("onSuccessfulExecute", ""+numberOfSongList);
		}
		else
		{
			Log.d("onSuccessfulExecute", "nope empty songs lists");
		}
		return numberOfSongList;
	}
*/


}