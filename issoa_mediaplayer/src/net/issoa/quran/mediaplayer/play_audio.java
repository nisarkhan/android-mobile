package net.issoa.quran.mediaplayer;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class play_audio extends ListActivity{

	//**************data variables:****************************** 
	public String directory_listings = "";
	public String[] array_surahs;	
	public SurahAdapter surah_adapter;	
	public ArrayList<Surah> arrayListSurahs = null;
	//****************

	public static final String url = "http://issoa.net/arabic_english_surah_list.htm";

	//**************MediaPlayer controls:****************************** 
	private ProgressDialog progressDialog = null; 


	//****************

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);  
		setContentView(R.layout.medialplayer_layout);
		initView();
	} 

	/* This method initialize all the views in project*/
	private void initView() {		
		arrayListSurahs = new ArrayList<Surah>();		
		
		progressDialog = ProgressDialog.show(this, "Please wait...", "Retrieving data ...", true);
		
		new GrabUrlAsyncTask().execute();
		
		this.surah_adapter = new SurahAdapter(this,R.layout.surah_object_layout, arrayListSurahs);
		setListAdapter(this.surah_adapter); 

	}
//http://stackoverflow.com/questions/4602902/how-to-set-text-color-of-textview-by-coding
	//**************GrabUrlAsyncTask:****************************** 
	class GrabUrlAsyncTask extends AsyncTask<Void, Void, List<Surah>> 
	{ 	 
		@Override 
		protected List<Surah> doInBackground(Void... params) 
		{ 			
			List<Surah> list_of_surahs = new ArrayList<Surah>();   
			try 
			{
				if (!isOnline())
				{
					progressDialog.dismiss();						
					Toast.makeText(play_audio.this, "You have to be connected to the internet for this application to work", Toast.LENGTH_LONG).show();
					finish();					
				}
				else
				{
					Get_Webpage obj = new Get_Webpage(url);
					directory_listings = obj.get_webpage_source();
					array_surahs = directory_listings.split(":::");  	

					if(array_surahs.length > 0)
					{ 
						for(int i=0;i<array_surahs.length;i++)
						{
							String[] _surahs = array_surahs[i].split(">>>"); 
							//String id, String name, String meaning, String url
							Surah _surah = new Surah(_surahs[0], _surahs[1], _surahs[2], _surahs[3]);					 
							list_of_surahs.add(_surah); 
						}
						//return list_of_surahs;  
					} 	
				}
			} 
			catch (Exception e) 
			{			
				showToast(e.getStackTrace().toString());
			}
			return  list_of_surahs;

				//http://stackoverflow.com/questions/9357513/cant-create-handler-inside-thread-that-has-not-called-looper-prepare-with-asy
				/*play_audio.this.runOnUiThread(new Runnable() 
				{ 
					public void run() 
					{ 
						m_ProgressDialog.dismiss();						
						Toast.makeText(play_audio.this, "You have to be connected to the internet for this application to work", Toast.LENGTH_LONG).show();
						finish();						
					} 
				}); */				
				//finish();
				//	m_ProgressDialog.dismiss();				
				 
				//showToast("You have to be connected to the internet for this application to work");	
			//} 
			//catch (Exception e) {
				
			//}
			/*catch (UnknownHostException e) {
				//Toast.makeText(play_audio.this, e.printStackTrace(), Toast.LENGTH_LONG).show();;
				showToast(e.getStackTrace().toString());
			} 
			catch (SomeImportantException  e) {
				showToast(e.getStackTrace().toString());
				
			}*/
			
		}

		protected void onProgressUpdate(Void... values)
		{       
			//http://stackoverflow.com/questions/3614663/cant-create-handler-inside-thread-that-has-not-called-looper-prepare-inside-a
			super.onProgressUpdate(values);         
			progressDialog.dismiss();         
			//downloadSpinnerProgressDialog.show();    
		} 		

		@Override 
		protected void onPostExecute(List<Surah> result) 
		{ 			 
			//showToast("You have to be connected to the internet for this application to work");	
			arrayListSurahs.clear();     
			arrayListSurahs.addAll(result);   
			progressDialog.dismiss();
			surah_adapter.notifyDataSetChanged();   
		} 
	}  
	//**********************
	
	//**************isOnline:****************************** 
	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}
	
	//**************showToast:****************************** 
	public void showToast(String message) 
	{
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}  
	//**********************

}
