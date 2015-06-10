package com.tutorials.customarraylist;

import java.util.ArrayList;

import com.tutorials.customarraylist.adapter.CommentArrayAdapter;
import com.tutorials.customarraylist.datastructure.CommentObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CommentListActivity extends Activity {
    
	private ArrayList<CommentObject> commentList = null;
	private ListView commentListView = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_list);
        
        //Prepare the Dataset http://www.shubhayu.com/
        prepareDataList();
        
        commentListView = (ListView)findViewById(R.id.listComment);
        TextView emptyList = (TextView)findViewById(R.id.txtEmptyList);
        emptyList.setText("No comments to display!");
        commentListView.setEmptyView(emptyList);
        CommentArrayAdapter adapter = new CommentArrayAdapter(this, R.id.txtLoadingList, commentList);
        commentListView.setAdapter(adapter);
        
        //Manage the onItemClick method
        commentListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				CommentObject currComment = (CommentObject)parent.getItemAtPosition(position);
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(CommentListActivity.this, 
						"You clicked on a comment by "+currComment.senderName, duration);
				toast.show();
				
			}
		});
        
    }
    
    private void prepareDataList(){
    	commentList = new ArrayList<CommentObject>();
    	
    	CommentObject comment = new CommentObject();
    	comment.senderName = "Calvin";
    	comment.commentText = "I am a man of few words.";
    	comment.senderPhoto = "calvin.png";
    	comment.link = "http://www.iclickedoncalvin.com";    	
    	commentList.add(comment);
    	
    	comment = new CommentObject();
    	comment.senderName = "Hobbes";
    	comment.commentText = "Well, maybe if you read more, your vocabulary would increase";
    	comment.senderPhoto = "hobbes.png";
    	comment.link = "http://www.iclickedonhobbes.com";    	
    	commentList.add(comment);
    	
    	comment = new CommentObject();
    	comment.senderName = "Calvin";
    	comment.commentText = "I'll make you pay for that!";
    	comment.senderPhoto = "calvin.png";
    	comment.link = "http://www.iclickedoncalvin.com";    	
    	commentList.add(comment);
    	
    	comment = new CommentObject();
    	comment.senderName = "Hobbes";
    	comment.commentText = "You have to catch me first :D";
    	comment.senderPhoto = "hobbes.png";
    	comment.link = "http://www.iclickedonhobbes.com";    	
    	commentList.add(comment);
    	
    	comment = new CommentObject();
    	comment.senderName = "Susie";
    	comment.commentText = "The boy is nuts";
    	comment.senderPhoto = "susie.png";
    	comment.link = "http://www.iclickedonsusie.com";    	
    	commentList.add(comment);
    }
}