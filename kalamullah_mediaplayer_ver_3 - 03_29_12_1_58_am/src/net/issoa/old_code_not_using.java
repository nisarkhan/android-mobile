package net.issoa;

import android.widget.TextView;

public class old_code_not_using {
	
	/* mySQLiteAdapter = new SQLiteAdapter(this);
	mySQLiteAdapter.openToRead();

	Cursor cursor = mySQLiteAdapter.queueAll();
	startManagingCursor(cursor);
	List<ReciterObj> rec = new ArrayList<ReciterObj>();
	
	if (cursor.moveToFirst()) {
		do {
			ReciterObj r = new ReciterObj();
			r.setID(Integer.parseInt(cursor.getString(0)));
			r.setAlbumID(cursor.getString(1));
			r.setReciterID(cursor.getString(2));
			r.setSurahNameA(cursor.getString(3));
			r.setSurahNameE(cursor.getString(4));
			r.setAudioURL(cursor.getString(5));
			r.setThumbURL(cursor.getString(6));
			r.setThumbURL(cursor.getString(7)); 
			// Adding contact to list
			rec.add(r);
		} while (cursor.moveToNext());
	} 
	mySQLiteAdapter.close(); 

		StringBuilder str = new StringBuilder();
		str.append(DatabaseHelper.KEY_ROOT_START_QURAN);
		for (ReciterObj ro : rec) 
		{
			str.append(DatabaseHelper.KEY_START_SURAH);

			str.append(DatabaseHelper.KEY_START_ID);
			str.append(ro.getID());
			str.append(DatabaseHelper.KEY_END_ID);					

			str.append(DatabaseHelper.KEY_START_SURAH_ARABIC);
			str.append(ro.getSurahNameA());
			str.append(DatabaseHelper.KEY_END_SURAH_ARABIC);					

			str.append(DatabaseHelper.KEY_START_SURAH_ENGLISH);
			str.append(ro.getSurahNameE());
			str.append(DatabaseHelper.KEY_END_SURAH_ENGLISH);

			str.append(DatabaseHelper.KEY_START_AUDIO_URL);
			str.append(ro.getAudioURL());
			str.append(DatabaseHelper.KEY_END_AUDIO_URL);

			str.append(DatabaseHelper.KEY_START_DURATION);
			str.append(ro.getDuration());
			str.append(DatabaseHelper.KEY_END_DURATION);

			str.append(DatabaseHelper.KEY_START_THUMB_URL);
			str.append(ro.getThumbURL());
			str.append(DatabaseHelper.KEY_END_THUMB_URL);

			str.append(DatabaseHelper.KEY_END_SURAH); 
		}
		str.append(DatabaseHelper.KEY_ROOT_END_QURAN);

		 
		onPostExecute(str.toString());
	
	mySQLiteAdapter.close();  */
	
 
	
	
	
	
	/*
	 *  Open the same SQLite database
	 *  and read all it's content.
	 */
	/*mySQLiteAdapter = new SQLiteAdapter(this);
	mySQLiteAdapter.openToRead();

	Cursor cursor = mySQLiteAdapter.queueAll();
	startManagingCursor(cursor);

	//String[] from = new String[]{SQLiteAdapter.KEY_ALBUM_ID};
	
	List<ReciterObj> r = mySQLiteAdapter.getAllReciterObj(); 
	
	  
	for (ReciterObj cn : r) {
        String log = "Id: "+cn.getID()+" ,getAlbumID: " + cn.getAlbumID() + " ,getSurahNameA: " + cn.getSurahNameA() + " ,getSurahNameE: " + cn.getSurahNameE() + " ,getAudioURL: " + cn.getAudioURL() + " ,getDuration: " + cn.getDuration();
            
        Log.d("Name: ", log);
	}

	mySQLiteAdapter.close(); */
	
	
	/*//@Override
	protected void doInBackground() //Void... params)
	{
		String xmlList = null;  
		 
	 mySQLiteAdapter = new SQLiteAdapter(this);
		 mySQLiteAdapter.openToWrite(); 
		//List<ReciterObj> r = dbHelper.getAllReciterObj();
		
		Cursor cursor = mySQLiteAdapter.queueAll();
		startManagingCursor(cursor);
		List<ReciterObj> rec = new ArrayList<ReciterObj>();
		
		if (cursor.moveToFirst()) {
			do {
				ReciterObj r = new ReciterObj();
				r.setID(Integer.parseInt(cursor.getString(0)));
				r.setAlbumID(cursor.getString(1));
				r.setReciterID(cursor.getString(2));
				r.setSurahNameA(cursor.getString(3));
				r.setSurahNameE(cursor.getString(4));
				r.setAudioURL(cursor.getString(5));
				r.setThumbURL(cursor.getString(6));
				r.setThumbURL(cursor.getString(7)); 
				// Adding contact to list
				rec.add(r);
			} while (cursor.moveToNext());
		} 
		mySQLiteAdapter.close(); 

			StringBuilder str = new StringBuilder();
			str.append(DatabaseHelper.KEY_ROOT_START_QURAN);
			for (ReciterObj ro : rec) 
			{
				str.append(DatabaseHelper.KEY_START_SURAH);

				str.append(DatabaseHelper.KEY_START_ID);
				str.append(ro.getID());
				str.append(DatabaseHelper.KEY_END_ID);					

				str.append(DatabaseHelper.KEY_START_SURAH_ARABIC);
				str.append(ro.getSurahNameA());
				str.append(DatabaseHelper.KEY_END_SURAH_ARABIC);					

				str.append(DatabaseHelper.KEY_START_SURAH_ENGLISH);
				str.append(ro.getSurahNameE());
				str.append(DatabaseHelper.KEY_END_SURAH_ENGLISH);

				str.append(DatabaseHelper.KEY_START_AUDIO_URL);
				str.append(ro.getAudioURL());
				str.append(DatabaseHelper.KEY_END_AUDIO_URL);

				str.append(DatabaseHelper.KEY_START_DURATION);
				str.append(ro.getDuration());
				str.append(DatabaseHelper.KEY_END_DURATION);

				str.append(DatabaseHelper.KEY_START_THUMB_URL);
				str.append(ro.getThumbURL());
				str.append(DatabaseHelper.KEY_END_THUMB_URL);

				str.append(DatabaseHelper.KEY_END_SURAH); 
			}
			str.append(DatabaseHelper.KEY_ROOT_END_QURAN);

			xmlList = str.toString();
			onPostExecute(xmlList);
		//return xmlList;
		 * 
		 void CatchError(String Exception)
	{
		Dialog diag=new Dialog(this);
		diag.setTitle("Errrrr....");
		TextView txt=new TextView(this);
		txt.setText(Exception);
		diag.setContentView(txt);
		diag.show();
	} 
	}   */

}
