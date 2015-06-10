 package net.issoa.custom_listview;

 
import java.util.ArrayList;
import java.util.HashMap;

import net.issoa.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
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
 
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row_custom_listview, null);
 
        TextView title = (TextView)vi.findViewById(R.id.title); // title
        TextView artist = (TextView)vi.findViewById(R.id.artist); // artist name
        TextView duration = (TextView)vi.findViewById(R.id.duration); // duration
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
 
        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);
 
        // Setting all values in listview
        title.setText(song.get(Main_Activity_Custom_Listview.KEY_TITLE_ARABIC));
        artist.setText(song.get(Main_Activity_Custom_Listview.KEY_TITLE_ENGLISH));
        duration.setText(song.get(Main_Activity_Custom_Listview.KEY_DURATION));
        imageLoader.DisplayImage(song.get(Main_Activity_Custom_Listview.KEY_THUMB_URL), thumb_image);
        return vi;
    }
}