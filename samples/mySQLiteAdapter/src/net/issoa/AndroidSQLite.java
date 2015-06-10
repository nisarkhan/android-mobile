package net.issoa;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class AndroidSQLite extends Activity {

	private SQLiteAdapter mySQLiteAdapter;
	//http://android-er.blogspot.com/2011/06/simple-example-using-androids-sqlite_02.html
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ListView listContent = (ListView)findViewById(R.id.contentlist);

		/*
		 *  Create/Open a SQLite database
		 *  and fill with dummy content
		 *  and close it
		 */
		mySQLiteAdapter = new SQLiteAdapter(this);
		mySQLiteAdapter.openToWrite();
		mySQLiteAdapter.deleteAll();

		mySQLiteAdapter.insert("A for Apply");
		mySQLiteAdapter.insert("B for Boy");
		mySQLiteAdapter.insert("C for Cat");
		mySQLiteAdapter.insert("D for Dog");
		mySQLiteAdapter.insert("E for Egg");
		mySQLiteAdapter.insert("F for Fish");
		mySQLiteAdapter.insert("G for Girl");
		mySQLiteAdapter.insert("H for Hand");
		mySQLiteAdapter.insert("I for Ice-scream");
		mySQLiteAdapter.insert("J for Jet");
		mySQLiteAdapter.insert("K for Kite");
		mySQLiteAdapter.insert("L for Lamp");
		mySQLiteAdapter.insert("M for Man");
		mySQLiteAdapter.insert("N for Nose");
		mySQLiteAdapter.insert("O for Orange");
		mySQLiteAdapter.insert("P for Pen");
		mySQLiteAdapter.insert("Q for Queen");
		mySQLiteAdapter.insert("R for Rain");
		mySQLiteAdapter.insert("S for Sugar");
		mySQLiteAdapter.insert("T for Tree");
		mySQLiteAdapter.insert("U for Umbrella");
		mySQLiteAdapter.insert("V for Van");
		mySQLiteAdapter.insert("W for Water");
		mySQLiteAdapter.insert("X for X'mas");
		mySQLiteAdapter.insert("Y for Yellow");
		mySQLiteAdapter.insert("Z for Zoo");

		mySQLiteAdapter.close();

		/*
		 *  Open the same SQLite database
		 *  and read all it's content.
		 */
		mySQLiteAdapter = new SQLiteAdapter(this);
		mySQLiteAdapter.openToRead();

		Cursor cursor = mySQLiteAdapter.queueAll();
		startManagingCursor(cursor);

		String[] from = new String[]{SQLiteAdapter.KEY_CONTENT};
		int[] to = new int[]{R.id.text};

		SimpleCursorAdapter cursorAdapter =
				new SimpleCursorAdapter(this, R.layout.row, cursor, from, to);

		listContent.setAdapter(cursorAdapter);

		mySQLiteAdapter.close(); 

	}
}
