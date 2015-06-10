package net.issoa.quran.mediaplayer;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SurahAdapter extends ArrayAdapter<Surah> 
{
	private ArrayList<Surah> items;

	public SurahAdapter(Context context, int textViewResourceId, ArrayList<Surah> items) 
	{
		super(context, textViewResourceId, items);
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View v = convertView;
		if (v == null) {
			//LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			//http://stackoverflow.com/questions/4321343/android-getsystemservice-inside-custom-arrayadapter
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			v = vi.inflate(R.layout.row, null);
		}
		Surah o = items.get(position);
		if (o != null) {
			TextView txtSurahId = (TextView) v.findViewById(R.id.android_surah_id);
			TextView txtSurah_name = (TextView) v.findViewById(R.id.android_surah_name);
			TextView txtSurah_duration = (TextView) v.findViewById(R.id.android_surah_duration);

			if(txtSurahId != null)
			{
				txtSurahId.setText(o.getSurahId().trim()); //duration time.
				txtSurah_duration.setText("02:05"); //time duration
			}
			if(txtSurah_name != null){
				txtSurah_name.setText(o.getSurahName().trim() + " - (" + o.getSurahMeaning().trim() + ")");
			} 
		}
		return v;
	}
}