package net.issoa.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	static final String KEY_DB_NAME = "kalamullah.db";
	static final String KEY_TABLE_RECITER = "reciter";
	static final String KEY_COLUMN_RECITER_ID = "reciterid";
	static final String KEY_COLUMN_SURAH_NAME_E = "surah_name_e";
	static final String KEY_COLUMN_SURAH_NAME_A = "surah_name_a";
	static final String KEY_COLUMN_AUDIO_URL = "audio_url";
	static final String KEY_COLUMN_THUMB_URL = "thum_url";
	static final String KEY_COLUMN_DURATION = "duration"; 


	static final int KEY_DATABASE_VERSION = 1;

	static final String KEY_VIEW_RECITER="view_reciter";


	public DatabaseHelper(Context context) {
		super(context, KEY_DB_NAME, null, KEY_DATABASE_VERSION);

		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub 

		db.execSQL("CREATE TABLE " + KEY_TABLE_RECITER + " " +
				"(" 
				+ 
				KEY_COLUMN_RECITER_ID + " INTEGER PRIMARY KEY, " +
				KEY_COLUMN_SURAH_NAME_A + " TEXT, " + 
				KEY_COLUMN_SURAH_NAME_E + " TEXT, " +
				KEY_COLUMN_AUDIO_URL + " TEXT, " +
				KEY_COLUMN_THUMB_URL + " TEXT, " +
				KEY_COLUMN_DURATION + " TEXT, " + 
				"));"); 

		//Inserts pre-defined departments
		InsertReciters(db);

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

	int getEmployeeCount()
	{
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor cur= db.rawQuery("Select * from "+ KEY_TABLE_RECITER, null);
		int x= cur.getCount();
		cur.close();
		return x;
	}

	Cursor getAllEmployees()
	{
		SQLiteDatabase db=this.getWritableDatabase(); 

		//Cursor cur= db.rawQuery("Select "+colID+" as _id , "+colName+", "+colAge+" from "+employeeTable, new String [] {});
		Cursor cur= db.rawQuery("SELECT * FROM "+ KEY_TABLE_RECITER, null);
		return cur;
	}

	Cursor getAllDepts()
	{ 
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur=db.rawQuery("SELECT " + 
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

		cv.put(KEY_COLUMN_RECITER_ID, 1);
		cv.put(KEY_COLUMN_SURAH_NAME_A, "The Opening"); 
		cv.put(KEY_COLUMN_SURAH_NAME_E, "Surah al-Fatihah"); 
		cv.put(KEY_COLUMN_AUDIO_URL, "http://www.salafitapes.com/noblequran/1.mp3");
		cv.put(KEY_COLUMN_THUMB_URL, "http://issoa.net/api/quran/images/quran1.png");
		cv.put(KEY_COLUMN_DURATION, "4:47");

		db.insert(KEY_TABLE_RECITER, KEY_COLUMN_RECITER_ID, cv); 
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
