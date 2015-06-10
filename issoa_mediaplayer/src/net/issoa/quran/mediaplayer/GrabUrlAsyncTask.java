package net.issoa.quran.mediaplayer;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class GrabUrlAsyncTask  extends AsyncTask<Void, Void, List<Surah>>  
{
	Context mContext; 
	public String directory_listings = "";
	public String[] array_surahs;

	public GrabUrlAsyncTask(Context ct)     
	{      
		mContext= ct;    
	}    
	 		

	@Override
	protected List<Surah> doInBackground(Void... params) {
		// TODO Auto-generated method stub
		//String url1 = "http://ofertaweb.ro/android/sleepandlovemusic/list_files.php";
		String url = "http://issoa.net/surahlist5.htm";
		List<Surah> surahs = new ArrayList<Surah>();   
		try 
		{
			Get_Webpage obj = new Get_Webpage(url);
			directory_listings = obj.get_webpage_source();	

			array_surahs = directory_listings.split(":::");  	

			if(array_surahs.length > 0)
			{ 
				for(int i=0;i<array_surahs.length;i++)
				{
					String[] _surahs = array_surahs[i].split(">>>"); 
					Surah _surah = new Surah(_surahs[0], _surahs[1], _surahs[2], _surahs[3]);					 
					surahs.add(_surah); 
				}
			} 
		} 
		catch (Exception e) 
		{				
			//http://stackoverflow.com/questions/9357513/cant-create-handler-inside-thread-that-has-not-called-looper-prepare-with-asy

			//finish();
			//	m_ProgressDialog.dismiss();				
			Toast.makeText(mContext, "You have to be connected to the internet for this application to work", Toast.LENGTH_LONG).show();
			//showToast("You have to be connected to the internet for this application to work");	
		} 
		return surahs; 
	}


	@Override         
	protected void onCancelled() 
	{             
		super.onCancelled();         
	} 

	protected void onPreExecute(List<Surah> result) 
	{ 
		//progress = ProgressDialog.show( 
				//mContext, null, "Loading ...",true,true); 

		super.onPreExecute(); 
	} 

	@Override 
	protected void onPostExecute(List<Surah> result) 
	{ 			 
		//http://stackoverflow.com/questions/9501815/creating-an-asynctask-class-on-its-own-seperate
		play_audio activity = (play_audio) mContext; 
		//array_surahs.clear();     
		//array_surahs.addAll(result);   
		//m_ProgressDialog.dismiss();
		//surah_adapter.notifyDataSetChanged(); 

	} 

	/*protected void onProgressUpdate(List<Surah> result) { 
		super.onProgressUpdate(result); 
		return null;
	} */ 
} 