package net.issoa.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils.InsertHelper;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class SQLiteAdapter {

	/*public static final String KEY_END_ID = "</id>"; 
	public static final String KEY_END_SURAH_ARABIC = "</title_a>"; 
	public static final String KEY_END_SURAH_ENGLISH = "</title_e>"; 
	public static final String KEY_END_AUDIO_URL = "</audio_url>";
	public static final String KEY_END_DURATION = "</duration>";
	public static final String KEY_END_THUMB_URL = "</thumb_url>";


	public static final String KEY_END_SURAH = "</surah>";
	public static final String KEY_ROOT_END_QURAN = "</quran>";*/


	public static final String KEY_DB_NAME = "kalamullah";
	public static final String KEY_TABLE_RECITER = "reciter";

	public static final String KEY_ID = "_id";
	public static final String KEY_ALBUM_ID = "album_id";
	public static final String KEY_COLUMN_RECITER_ID = "reciterid";
	public static final String KEY_COLUMN_SURAH_NAME_E = "surah_name_e";
	public static final String KEY_COLUMN_SURAH_NAME_A = "surah_name_a";
	public static final String KEY_COLUMN_AUDIO_URL = "audio_url";
	public static final String KEY_COLUMN_THUMB_URL = "thumb_url";
	public static final String KEY_COLUMN_DURATION = "duration"; 

	public static final String KEY_ALBUM_ID_VALUE = "10"; //ENGLISH_ARABIC RECITER

	public static final int KEY_DATABASE_VERSION = 1;

	public static final String KEY_VIEW_RECITER="view_reciter";

	public static final String KEY_TABLE_XML = "reciterxml";
	public static final String KEY_XML_DATA = "xml_data";
	
	//create table MY_DATABASE (ID integer primary key, Content text not null);
	private static final String SCRIPT_CREATE_DATABASE =
			"CREATE TABLE " + KEY_TABLE_RECITER + "(" + 
					KEY_ID + " INTEGER PRIMARY KEY, " +
					KEY_ALBUM_ID + " TEXT, " +
					KEY_COLUMN_RECITER_ID + " TEXT, " +
					KEY_COLUMN_SURAH_NAME_A + " TEXT, " + 
					KEY_COLUMN_SURAH_NAME_E + " TEXT, " +
					KEY_COLUMN_AUDIO_URL + " TEXT, " +
					KEY_COLUMN_THUMB_URL + " TEXT, " +
					KEY_COLUMN_DURATION + " TEXT " + 
					");"; 
	
	private static final String SCRIPT_CREATE_TABLE_XML =
			"CREATE TABLE " + KEY_TABLE_XML + "(" + 
					KEY_ID + " INTEGER PRIMARY KEY, " +
					KEY_ALBUM_ID + " TEXT, " +
					KEY_XML_DATA + " TEXT " + 
					");"; 
	

	private SQLiteHelper sqLiteHelper;
	private SQLiteDatabase sqLiteDatabase;

	private Context context;

	public SQLiteAdapter(Context c){
		context = c;
	}

	public SQLiteAdapter openToRead() throws android.database.SQLException {
		sqLiteHelper = new SQLiteHelper(context, KEY_DB_NAME, null, KEY_DATABASE_VERSION);
		sqLiteDatabase = sqLiteHelper.getReadableDatabase();
		return this;
	}

	public SQLiteAdapter openToWrite() throws android.database.SQLException {
		sqLiteHelper = new SQLiteHelper(context, KEY_DB_NAME, null, KEY_DATABASE_VERSION);
		sqLiteDatabase = sqLiteHelper.getWritableDatabase();
		return this;
	}

	public void close(){
		sqLiteHelper.close();
	}

	public long insert(String album_id,
			String reciterid,
			String surah_name_e,
			String surah_name_a,
			String audio_url,
			String thumb_url,
			String duration){

		ContentValues contentValues = new ContentValues();
		contentValues.put(KEY_ALBUM_ID, album_id);
		contentValues.put(KEY_COLUMN_RECITER_ID, reciterid);
		contentValues.put(KEY_COLUMN_SURAH_NAME_E, surah_name_e);
		contentValues.put(KEY_COLUMN_SURAH_NAME_A, surah_name_a);
		contentValues.put(KEY_COLUMN_AUDIO_URL, audio_url);
		contentValues.put(KEY_COLUMN_THUMB_URL, thumb_url);
		contentValues.put(KEY_COLUMN_DURATION, duration);  
		return sqLiteDatabase.insert(KEY_TABLE_RECITER, null, contentValues); 
	}
	
	public long insert_xml(String album_id,	String xml_data){

		ContentValues contentValues = new ContentValues();
		contentValues.put(KEY_ALBUM_ID, album_id);
		contentValues.put(KEY_XML_DATA, xml_data); 
		return sqLiteDatabase.insert(KEY_TABLE_XML, null, contentValues); 
	}



	public int deleteAll(){
		return sqLiteDatabase.delete(KEY_TABLE_RECITER, null, null);
	}

	public Cursor queueAll(){
		String[] columns = new String[]{KEY_ID, KEY_ALBUM_ID, KEY_COLUMN_RECITER_ID, KEY_COLUMN_SURAH_NAME_E,KEY_COLUMN_SURAH_NAME_A, KEY_COLUMN_AUDIO_URL,KEY_COLUMN_THUMB_URL,KEY_COLUMN_DURATION};
		Cursor cursor = sqLiteDatabase.query(KEY_TABLE_RECITER, columns,
				null, null, null, null, null); 
		return cursor;
	}

	public Cursor queueAll_xml(){
		String[] columns = new String[]{KEY_ID, KEY_ALBUM_ID, KEY_XML_DATA};
		Cursor cursor = sqLiteDatabase.query(KEY_TABLE_XML, columns,
				null, null, null, null, null); 
		return cursor;
	}

	
	public long getReciterCount()
	{ 
		Cursor cur= sqLiteDatabase.rawQuery("Select * from "+ KEY_TABLE_RECITER, null);
		int x= cur.getCount();
		cur.close();
		return x;
	}
	
	
	 
	
	

	// Getting All Contacts
	public List<ReciterObj> getAllReciterObj() {

		List<ReciterObj> rec = new ArrayList<ReciterObj>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + KEY_TABLE_RECITER;

		//  SQLiteDatabase db  = this.getWritableDatabase();
		Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				ReciterObj r = new ReciterObj();
				r.setID(Integer.parseInt(cursor.getString(0)));
				r.setAlbumID(cursor.getString(1));
				r.setSurahNameA(cursor.getString(2));
				r.setSurahNameE(cursor.getString(2));
				r.setAudioURL(cursor.getString(2));
				r.setDuration(cursor.getString(2));
				r.setThumbURL(cursor.getString(2));
				// Adding contact to list
				rec.add(r);
			} while (cursor.moveToNext());
		}

		// return contact list
		return rec;
	}

	public void InsertReciters()
	{ 
		/*try
		{*/
			//sqLiteDatabase.beginTransaction();
			this.insert("10","1","The Opening","Surah al-Fatihah","http://www.salafitapes.com/noblequran/1.mp3","http://issoa.net/api/quran/images/quran1.png","4:47");
			this.insert("10","2","The Cow","The Opening","http://www.salafitapes.com/noblequran/2.mp3","http://issoa.net/api/quran/images/quran1.png","5:47");
			
		/*} 
		catch (SQLException e) 
		{
			//
		} 
		finally 
		{
			sqLiteDatabase.endTransaction();
		}*/
		/*try
		{
			InsertHelper iHelp = new InsertHelper(sqLiteDatabase, KEY_TABLE_RECITER);
			sqLiteDatabase.beginTransaction();
		   for(int i=0 ; i<114 ; ++i)
		   {
			 //need to tell the helper you are inserting (rather than replacing)
		       iHelp.prepareForInsert();
		   
		       iHelp.bind(first_index, thing_1);
		       iHelp.bind(last_index, thing_2);
		       
		     //the db.insert() equilvalent
		       iHelp.execute();
		   }
		}
		finally
		{
			sqLiteDatabase.endTransaction();
		}
		sqLiteDatabase.close();*/

		/*cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 2);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");
		
		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 3);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");
		
		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 4);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");
		
		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 5);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");
		
		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 6);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");
		
		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 7);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");
		
		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 8);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 9);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 1);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 10);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 1);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 11);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 12);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 13);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 14);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 15);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 1);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 16);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 17);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 1);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 18);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 19);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 20);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 21);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 22);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 23);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 24);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 25);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 26);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 27);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 28);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 29);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 30);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 31);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 32);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 33);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");
		
		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 34);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 35);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 36);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 37);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 38);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 39);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 40);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 41);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 42);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 43);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 44);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 45);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 46);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 47);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 48);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 49);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 50);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");
		
		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 51);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 52);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 53);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 54);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 55);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 56);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 57);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 58);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 59);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 60);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 61);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 62);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 63);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 64);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 65);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 66);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 67);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 68);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 69);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 70);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 71);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");
		
		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 72);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 73);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 74);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 75);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 76);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 77);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 78);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 79);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 80);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 81);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 82);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 83);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 84);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 85);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 86);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 87);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 88);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 89);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 90);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 91);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 92);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 93);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 94);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 95);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 96);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 97);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 98);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 99);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 100);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 101);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 102);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 103);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 104);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 105);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 106);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 107);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 108);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 109);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 110);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 111);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 112);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 113);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 114);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");
		*/
		
		
	 
	}
	//new class
	public class SQLiteHelper extends SQLiteOpenHelper {

		public SQLiteHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(SCRIPT_CREATE_DATABASE);
			db.execSQL(SCRIPT_CREATE_TABLE_XML); 
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub 
		}

	}

}