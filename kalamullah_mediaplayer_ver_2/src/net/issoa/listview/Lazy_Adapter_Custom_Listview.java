package net.issoa.listview;


import java.util.ArrayList;
import java.util.HashMap;

import net.issoa.Custom_Tafseer_Activity;
import net.issoa.R;
import net.issoa.helper.MyConstants;
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
	static class ViewHolder 
	{
        public TextView title_a = null; 
        public TextView title_e = null; 
        public TextView duration = null;  
        public ImageView thumbimage = null; 
        public TextView readlinks = null;  
        
        ViewHolder(View row)
        {
        	readlinks = (TextView) row.findViewById(R.id.tv_read_surah_desc_Link);	
        	title_e = (TextView) row.findViewById(R.id.tv_title_e);	
        	title_a = (TextView) row.findViewById(R.id.tv_title_a);	
			duration = (TextView) row.findViewById(R.id.tv_duration);	
			thumbimage = (ImageView) row.findViewById(R.id.iv_list_image);	 
        }
        void populateReadLinks(String r) {
        	readlinks.setText(r);
        }
        void populateEnglish(String r) {
        	title_a.setText(r);
        }
        void populateTitleArabic(String r) {
        	title_e.setText(r);
        }
         
        void populateDuration(String r) {
        	duration.setText(r);
        }
        void populateThumbimage(String r) {
        	//thumbimage.setText(r); 
        	imageLoader.DisplayImage(r, thumbimage);
        }
    }

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
	public static Image_Loader_Custom_Listview imageLoader;  

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
			_holder = new ViewHolder(convertView);  		
			
			// Bind the data efficiently with the holder.
			//http://www.youtube.com/watch?v=xQcUYv51nEc
			convertView.setTag(_holder); 
			
		} else {
			// Get the ViewHolder back to get fast access to the TextView
            // and the ImageView.
			_holder = (ViewHolder) convertView.getTag(); 
		}   
		
		HashMap<String, String> _surahs = new HashMap<String, String>();
		_surahs = data.get(position);

		// Setting all values in listview  
		_holder.populateTitleArabic(_surahs.get(MyConstants.KEY_TITLE_ARABIC));
		_holder.populateEnglish(_surahs.get(MyConstants.KEY_TITLE_ENGLISH));
		_holder.populateDuration(_surahs.get(MyConstants.KEY_DURATION));		
		_holder.populateThumbimage(_surahs.get(MyConstants.KEY_THUMB_URL)); 
	 
		_holder.readlinks.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//int duration = Toast.LENGTH_SHORT; 
				
				String surah_name = _holder.title_e.getText().toString();
				//Log.d("mk", "string : "+s);
				Toast.makeText(v.getContext(), "You selected item #: " + surah_name,Toast.LENGTH_SHORT).show(); 
				
				 
				Intent i = new Intent(v.getContext(), Custom_Tafseer_Activity.class);   
				i.putExtra("position", position); 
				i.putExtra("surah_name", surah_name);  
				v.getContext().startActivity(i);   
			}
		}); 

		return convertView;
	}   
} 