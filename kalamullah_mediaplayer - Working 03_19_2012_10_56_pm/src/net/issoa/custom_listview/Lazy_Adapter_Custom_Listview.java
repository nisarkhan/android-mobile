 package net.issoa.custom_listview;
 
 
import java.util.ArrayList;
import java.util.HashMap; 

import net.issoa.R;
import net.issoa.player.MediaPlayer_Activity;
import net.issoa.player.PlayListActivity;
import net.issoa.player.Tafseer_01_Activity;
import net.issoa.player.Tafseer_02_Activity;
import net.issoa.player.Tafseer_03_Activity;
import net.issoa.player.Tafseer_04_Activity;
import net.issoa.player.Tafseer_05_Activity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
 
public class Lazy_Adapter_Custom_Listview extends BaseAdapter {
 
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
        View vi = convertView;
        
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row_custom_listview, null);
 
        //View rowView = inflater.inflate(R.layout.list_row_custom_listview, null, true);
        
        final TextView title = (TextView)vi.findViewById(R.id.title); // title
        final TextView artist = (TextView)vi.findViewById(R.id.artist); // artist name
        final TextView duration = (TextView)vi.findViewById(R.id.duration); // duration
        final ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
        final TextView surahTafseerLink = (TextView)vi.findViewById(R.id.txtSurahTafseerLink);
 
        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);
 
        // Setting all values in listview
        title.setText(song.get(Main_Activity_Custom_Listview.KEY_TITLE_ARABIC));
        artist.setText(song.get(Main_Activity_Custom_Listview.KEY_TITLE_ENGLISH));
        duration.setText(song.get(Main_Activity_Custom_Listview.KEY_DURATION));
        imageLoader.DisplayImage(song.get(Main_Activity_Custom_Listview.KEY_THUMB_URL), thumb_image);
        
        
        
        surahTafseerLink.setOnClickListener(new View.OnClickListener() 
        {
        	 public void onClick(View v) 
        	 {
				int duration = Toast.LENGTH_SHORT;
				//View rowView = inflater.inflate(R.layout.list_row_custom_listview, null, true);
				/*TextView tx =(TextView)v.findViewById(R.id.txtSurahTafseerLink); 
				TextView artist = (TextView)v.findViewById(R.id.artist); // artist name*/
				Log.v("surahTafseerLink>>", "onItemClick at position" + position);
				Log.v("link>>", "onItemClick at position" + artist.getText());
				//log.d("getView", tx.getText());
				//Toast toast = Toast.makeText(net.issoa.player.PlayListActivity.this, tx.getText(), duration);
				//toast.show();	
				//Toast toast = Toast.makeText(getApplicationContext().this, "You clicked on a comment by "+artist.getText(), duration);
				//toast.show();
				if (position == 0)
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
				}
			}
		}); 
        
        return vi;
    }
}