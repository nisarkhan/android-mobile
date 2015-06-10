package com.tutorials.customarraylist;

import android.app.Application;
import android.content.Context;

public class CustomAppContext extends Application {

	private static CustomAppContext context;

	@Override
	public void onCreate() {
		super.onCreate();
		
		context = this;
	}
	
	public static CustomAppContext getInstance(){
		return context;
	}
	
	public static Context getContext(){
		return context.getApplicationContext();
	}
	
	/*Add your other methods and global variables here*/
}
