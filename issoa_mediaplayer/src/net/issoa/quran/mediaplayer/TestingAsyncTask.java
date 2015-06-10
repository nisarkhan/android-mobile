package net.issoa.quran.mediaplayer;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

//The class that starts the task
public class TestingAsyncTask extends ListActivity {

	private ProgressBar bar = null;	
	private ArrayList<String> data = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thetask);
	//	bar = (ProgressBar) findViewById(R.id.);
		data.add("1");
		data.add("2");
		data.add("3");

		Log.d("create", ""+ data.size());
		
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data));
		new TheTask(this).execute((Void) null);		
	}

	public ArrayList<String> getData() {
		return data;
	} 
}

