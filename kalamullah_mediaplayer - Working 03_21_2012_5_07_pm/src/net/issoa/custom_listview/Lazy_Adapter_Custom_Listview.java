package net.issoa.custom_listview;


import java.util.ArrayList;
import java.util.HashMap;

import net.issoa.R;
import net.issoa.player.CustomTafseer_Activity;
import net.issoa.player.MyConstants;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Lazy_Adapter_Custom_Listview extends BaseAdapter {
	
	/*The ViewHolder pattern consists in storing a data structure in the tag of the view returned by
	 * getView(). This data structures contains references to the views we want to bind data to, thus
	 * avoiding calls to findViewById() every time getView() is invoked.*/
	static class ViewHolder {
        TextView title; 
        TextView artist ; 
        TextView duration;  
        ImageView thumbimage;
        TextView readlinks; 
    }

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater=null;
	public Image_Loader_Custom_Listview imageLoader;  

	public Lazy_Adapter_Custom_Listview(Activity a, ArrayList<HashMap<String, String>> d) {
		activity = a;
		data=d;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader=new Image_Loader_Custom_Listview(activity.getApplicationContext());
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}



	public View getView(final int position, View convertView, ViewGroup parent) 
	{
		//http://developer.android.com/resources/samples/ApiDemos/src/com/example/android/apis/view/List14.html		 
		
		// A ViewHolder keeps references to children views to avoid unneccessary calls
        // to findViewById() on each row.
		final ViewHolder _holder; 
		
		// When convertView is not null, we can reuse it directly, there is no need
        // to reinflate it. We only inflate a new View when the convertView supplied
        // by ListView is null.
		if(convertView == null) 
		{
			convertView = inflater.inflate(R.layout.list_row_custom_listview, null);
			
			// Creates a ViewHolder and store references to the two children views
            // we want to bind data to.
			_holder = new ViewHolder(); 
			_holder.readlinks = (TextView) convertView.findViewById(R.id.txtSurahTafseerLink);	
			_holder.artist = (TextView) convertView.findViewById(R.id.artist);	
			_holder.title = (TextView) convertView.findViewById(R.id.title);	
			_holder.duration = (TextView) convertView.findViewById(R.id.duration);	
			_holder.thumbimage = (ImageView) convertView.findViewById(R.id.list_image);	
			
			
			// Bind the data efficiently with the holder.
			convertView.setTag(_holder); 
			
		} else {
			// Get the ViewHolder back to get fast access to the TextView
            // and the ImageView.
			_holder = (ViewHolder) convertView.getTag(); 
		}   
		
		HashMap<String, String> _surahs = new HashMap<String, String>();
		_surahs = data.get(position);

		// Setting all values in listview 
		_holder.title.setText(_surahs.get(MyConstants.KEY_TITLE_ARABIC));
		_holder.artist.setText(_surahs.get(MyConstants.KEY_TITLE_ENGLISH));
		_holder.duration.setText(_surahs.get(MyConstants.KEY_DURATION));
		imageLoader.DisplayImage(_surahs.get(MyConstants.KEY_THUMB_URL), _holder.thumbimage);
		
	 
		_holder.readlinks.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int duration = Toast.LENGTH_SHORT;
				//View rowView = inflater.inflate(R.layout.list_row_custom_listview, null, true);
				/*TextView tx =(TextView)v.findViewById(R.id.txtSurahTafseerLink);*/ 
				//TextView artist = (TextView)v.findViewById(R.id.artist); // artist name
				//Log.v("surahTafseerLink> readlinks>", "onItemClick at position" + tx.getText()position);
				
				//TextView tx =(TextView)v.findViewById(R.id.title); 
				
				//String s = _holder.artist.getText().toString();
				//Log.d("mk", "string : "+s);
				//Toast.makeText(v.getContext(), "You selected item #: " + s,Toast.LENGTH_SHORT).show(); 
				
				 
				Intent i = new Intent(v.getContext(), CustomTafseer_Activity.class);   
				i.putExtra("position", position);   
				v.getContext().startActivity(i);  
				 
				 
				//Intent i = new Intent(v.getContext(), activityList1[position]);     
				//v.getContext().startActivity(i); 
				
				
				/*if (position == 0)
				{
					Intent intent_01 = new Intent(v.getContext(), Tafseer_01_Activity.class);	 
					v.getContext().startActivity(intent_01); 
				}
				if (position == 1) 
				{
					Intent intent_03 = new Intent(v.getContext(), Tafseer_02_Activity.class);	 
					v.getContext().startActivity(intent_03); 
				}
				if (position == 2) 
				{
					Intent intent_04 = new Intent(v.getContext(), Tafseer_03_Activity.class);	 
					v.getContext().startActivity(intent_04); 
				}
				if (position == 3) 
				{
					Intent intent_05 = new Intent(v.getContext(), Tafseer_04_Activity.class);	 
					v.getContext().startActivity(intent_05); 
				}
				if (position == 4) 
				{
					Intent intent_05 = new Intent(v.getContext(), Tafseer_05_Activity.class);	 
					v.getContext().startActivity(intent_05); 
				} */
			}
		}); 

		return convertView;
	}  
	

}


