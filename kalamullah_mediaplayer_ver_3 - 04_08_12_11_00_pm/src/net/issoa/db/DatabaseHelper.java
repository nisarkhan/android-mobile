package net.issoa.db;

import java.util.ArrayList;
import java.util.List;
 

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	//xml stuffs
	
	public static final String KEY_ROOT_START_QURAN = "<quran>";
	public static final String KEY_START_SURAH = "<surah>";
	public static final String KEY_START_ID = "<id>"; 
	public static final String KEY_START_SURAH_ARABIC = "<title_a>"; 
	public static final String KEY_START_SURAH_ENGLISH = "<title_e>"; 
	public static final String KEY_START_AUDIO_URL = "<audio_url>"; 
	public static final String KEY_START_DURATION = "<duration>";
	public static final String KEY_START_THUMB_URL = "<thumb_url>";
	
	public static final String KEY_END_ID = "</id>"; 
	public static final String KEY_END_SURAH_ARABIC = "</title_a>"; 
	public static final String KEY_END_SURAH_ENGLISH = "</title_e>"; 
	public static final String KEY_END_AUDIO_URL = "</audio_url>";
	public static final String KEY_END_DURATION = "</duration>";
	public static final String KEY_END_THUMB_URL = "</thumb_url>";
	
	
	public static final String KEY_END_SURAH = "</surah>";
	public static final String KEY_ROOT_END_QURAN = "</quran>";
	
	
	static final String KEY_DB_NAME = "kalamullah";
	static final String KEY_TABLE_RECITER = "reciter";
	
	static final String KEY_ID = "id"; 
	static final String KEY_ALBUM_ID = "album_id";
	static final String KEY_COLUMN_RECITER_ID = "reciterid";
	static final String KEY_COLUMN_SURAH_NAME_E = "surah_name_e";
	static final String KEY_COLUMN_SURAH_NAME_A = "surah_name_a";
	static final String KEY_COLUMN_AUDIO_URL = "audio_url";
	static final String KEY_COLUMN_THUMB_URL = "thumb_url";
	static final String KEY_COLUMN_DURATION = "duration"; 
	
	static final String KEY_ALBUM_ID_VALUE = "10"; //ENGLISH_ARABIC RECITER
	 
	static final int KEY_DATABASE_VERSION = 1;

	static final String KEY_VIEW_RECITER="view_reciter";
 
 
	    
	public DatabaseHelper(Context context) {
		super(context, KEY_DB_NAME, null, KEY_DATABASE_VERSION); 
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		 //http://stackoverflow.com/questions/4397757/how-can-i-check-to-see-if-my-sqlite-table-has-data-in-it

		db.execSQL("CREATE TABLE " + KEY_TABLE_RECITER + "(" + 
				KEY_ID + " INTEGER PRIMARY KEY, " +
				KEY_ALBUM_ID + " TEXT, " +
				KEY_COLUMN_RECITER_ID + " TEXT, " +
				KEY_COLUMN_SURAH_NAME_A + " TEXT, " + 
				KEY_COLUMN_SURAH_NAME_E + " TEXT, " +
				KEY_COLUMN_AUDIO_URL + " TEXT, " +
				KEY_COLUMN_THUMB_URL + " TEXT, " +
				KEY_COLUMN_DURATION + " TEXT " + 
				");"); 
		//Inserts pre-defined departments 
		if (getReciterCount1() == 0)
		{
			InsertReciters(db);
		}
	}

 
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

		db.execSQL("DROP TABLE IF EXISTS " + KEY_DB_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + KEY_TABLE_RECITER); 

		onCreate(db);
	} 

	/*void AddEmployee(Employee emp)
	{
		SQLiteDatabase db= this.getWritableDatabase();

		ContentValues cv=new ContentValues();
		cv.put(colName, emp.getName());
		cv.put(colAge, emp.getAge());
		cv.put(colDept, emp.getDept());
		//cv.put(colDept,2);
		db.insert(employeeTable, colName, cv);
		db.close();
	}*/

	int getReciterCount()
	{
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor cur= db.rawQuery("Select * from "+ KEY_TABLE_RECITER, null);
		int x= cur.getCount();
		cur.close();
		return x;
	}

	public int getReciterCount1() {	
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cur= db.rawQuery("Select * from "+ KEY_TABLE_RECITER, null);
        int x= cur.getCount();
        cur.close();
        return x;
	}
	
	
	public Cursor getAllReciter(SQLiteDatabase db)
	{
		//SQLiteDatabase db=this.getWritableDatabase(); 

		//Cursor cur= db.rawQuery("Select "+colID+" as _id , "+colName+", "+colAge+" from "+employeeTable, new String [] {});
		Cursor cur= db.rawQuery("SELECT * FROM "+ KEY_TABLE_RECITER, null);
		return cur;
	}
	
	// Getting All Contacts
	public List<ReciterObj> getAllReciterObj() {
		 
		SQLiteDatabase db=this.getWritableDatabase();
		
		List<ReciterObj> rec = new ArrayList<ReciterObj>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + KEY_TABLE_RECITER;

		//  SQLiteDatabase db  = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

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

	Cursor getAllDepts()
	{ 
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur=db.rawQuery("SELECT " + 
				KEY_ID + "," +  
				KEY_ALBUM_ID + "," +   
				KEY_COLUMN_RECITER_ID + "," +  
				KEY_COLUMN_SURAH_NAME_A + "," +    
				KEY_COLUMN_SURAH_NAME_E + "," +   
				KEY_COLUMN_AUDIO_URL + "," +  
				KEY_COLUMN_THUMB_URL + "," +  
				KEY_COLUMN_DURATION + "," +   
				" from "+ KEY_TABLE_RECITER, new String [] {});

		return cur;
	}

	void InsertReciters(SQLiteDatabase db)
	{ 
		ContentValues cv = new ContentValues(); 

		
		cv.put(KEY_ALBUM_ID, KEY_ALBUM_ID_VALUE);
		cv.put(KEY_COLUMN_RECITER_ID, 1);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

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
		
		//SQLiteDatabase db = this.getWritableDatabase();
		db.insert(KEY_TABLE_RECITER, null, cv);
	 
		
		//db.close();
 
	}

	

	/*public String GetDept(int ID)
	{
		SQLiteDatabase db=this.getReadableDatabase();

		String[] params=new String[]{String.valueOf(ID)};
		Cursor c=db.rawQuery("SELECT "+colDeptName+" FROM"+ deptTable+" WHERE "+colDeptID+"=?",params);
		c.moveToFirst();
		int index= c.getColumnIndex(colDeptName);
		return c.getString(index);
	}

	public Cursor getEmpByDept(String Dept)
	{
		SQLiteDatabase db=this.getReadableDatabase();
		String [] columns=new String[]{"_id",colName,colAge,colDeptName};
		Cursor c=db.query(viewEmps, columns, colDeptName+"=?", new String[]{Dept}, null, null, null);
		return c;
	}

	public int GetDeptID(String Dept)
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor c=db.query(deptTable, new String[]{colDeptID+" as _id",colDeptName},colDeptName+"=?", new String[]{Dept}, null, null, null);
		//Cursor c=db.rawQuery("SELECT "+colDeptID+" as _id FROM "+deptTable+" WHERE "+colDeptName+"=?", new String []{Dept});
		c.moveToFirst();
		return c.getInt(c.getColumnIndex("_id"));

	}

	public int UpdateEmp(Employee emp)
	{
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues cv=new ContentValues();
		cv.put(colName, emp.getName());
		cv.put(colAge, emp.getAge());
		cv.put(colDept, emp.getDept());
		return db.update(employeeTable, cv, colID+"=?", new String []{String.valueOf(emp.getID())});

	}

	public void DeleteEmp(Employee emp)
	{
		SQLiteDatabase db=this.getWritableDatabase();
		db.delete(employeeTable,colID+"=?", new String [] {String.valueOf(emp.getID())});
		db.close();
	}*/
}
