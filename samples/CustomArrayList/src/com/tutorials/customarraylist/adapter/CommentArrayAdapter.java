package com.tutorials.customarraylist.adapter;

import java.util.ArrayList;
import java.util.List;

import com.tutorials.customarraylist.R;
import com.tutorials.customarraylist.datastructure.CommentObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CommentArrayAdapter extends ArrayAdapter<CommentObject> {

	private Context m_context = null;
	private ArrayList<CommentObject> m_comments = null;
	private LayoutInflater m_inflater = null;
	
	public CommentArrayAdapter(Context context, int textViewResourceId,
			List<CommentObject> objects) {
		super(context, textViewResourceId, objects);
		
		m_context = context;
		m_comments = (ArrayList<CommentObject>)objects;
		m_inflater = LayoutInflater.from(m_context);
	}

	@Override
	public int getCount() {
		if( m_comments != null ){
			return m_comments.size();
		}
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		CommentViewHolder holder = null;
		
		//form the view
		if( convertView == null ){
			holder = new CommentViewHolder();
			convertView = m_inflater.inflate(R.layout.comment_list_row, parent, false);
			holder.senderImage = (ImageView)convertView.findViewById(R.id.imgUserImage);
			holder.senderName = (TextView)convertView.findViewById(R.id.txtUserName);
			holder.commentText = (TextView)convertView.findViewById(R.id.txtUserComment);
			holder.link = (TextView)convertView.findViewById(R.id.txtClickableLink);
			
			convertView.setTag(holder);
			
		}else{
			
			holder = (CommentViewHolder)convertView.getTag();
		}
		
		//bind the data to the view
		CommentObject comment = m_comments.get(position);
		if( comment.senderPhoto.contentEquals("calvin.png") )
			holder.senderImage.setImageResource(R.drawable.calvin);
		else if( comment.senderPhoto.contentEquals("hobbes.png") )
			holder.senderImage.setImageResource(R.drawable.hobbes);
		else if( comment.senderPhoto.contentEquals("susie.png") )
			holder.senderImage.setImageResource(R.drawable.susie);
		
		holder.senderName.setText(comment.senderName);
		holder.commentText.setText(comment.commentText);
		final String clickableText = comment.link;
		holder.link.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(m_context, clickableText, duration);
				toast.show();				
			}
		});
		
		return convertView;
	}

	private class CommentViewHolder{
		ImageView senderImage;
		TextView senderName;
		TextView commentText;
		TextView link;
	}
	
}
