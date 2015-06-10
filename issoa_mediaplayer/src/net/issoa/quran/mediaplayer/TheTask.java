package net.issoa.quran.mediaplayer;
 

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

//The AsyncTask class
public class TheTask extends AsyncTask<Void, Void, List<String>> {

	Context c;

	public TheTask(Context c) {
		this.c = c;
	}	

	@Override
	protected void onPreExecute() {
		//((TestingAsyncTask) c).findViewById(R.id.the_progress).setVisibility(View.VISIBLE);
	}



	@Override
	protected List<String> doInBackground(Void... params) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<String> res= new ArrayList<String>();
		res.add("a");
		res.add("b");
		res.add("c");
		Log.d("doinbacground", ""+ res.size());
		return res;
	}

	@Override
	protected void onPostExecute(List<String> result) {
		TestingAsyncTask activity = ((TestingAsyncTask) c);
		activity.getData().clear();
		activity.getData().addAll(result);
		((ArrayAdapter)activity.getListAdapter()).notifyDataSetChanged();
		//((TestingAsyncTask) c).findViewById(R.id.the_progress).setVisibility(View.GONE);
	}

}