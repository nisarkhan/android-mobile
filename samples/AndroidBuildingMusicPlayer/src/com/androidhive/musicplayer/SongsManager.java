package com.androidhive.musicplayer;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

public class SongsManager {
	// SDCard Path
	final String MEDIA_PATH = new String("/sdcard/");
	private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
	
	// Constructor
	public SongsManager(){
		
	}
	
	/**
	 * Function to read all mp3 files from sdcard
	 * and store the details in ArrayList
	 * */
	public ArrayList<HashMap<String, String>> getPlayList(){
		File home = new File(MEDIA_PATH);

		//if (home.listFiles(new FileExtensionFilter()).length > 0) {
			//for (File file : home.listFiles(new FileExtensionFilter())) {
				//HashMap<String, String> song = new HashMap<String, String>();
				//song.put("songTitle", file.getName().substring(0, (file.getName().length() - 4)));
				//song.put("songPath", file.getPath());
				
				// Adding each song to SongList
				//songsList.add(song);
			//}
		//}
		
		//String get_file_name = new File("http://www.salafitapes.com/noblequran/12.mp3").getName();
		//String get_file_length = new File("http://www.salafitapes.com/noblequran/12.mp3")get_file_name.length();
		
		
		/*String url = "http://www.salafitapes.com/noblequran/12.mp3";
		String file = url.substring(url.lastIndexOf('/')+1, url.lastIndexOf('.'));*/
		
		HashMap<String, String> song = new HashMap<String, String>();
		//song.put("songTitle", get_file_name.substring(0, (get_file_name.length() - 4)));
		//song.put("songPath", get_file_name);
		
		song.put("songTitle", "surah 05");
		song.put("songPath", "http://www.kalamullah.com/Quran/Noble%20Quran/005.mp3"); 
		songsList.add(song);
		 song = new HashMap<String, String>();
		
		song.put("songTitle", "surah 07");
		song.put("songPath", "http://www.kalamullah.com/Quran/Noble%20Quran/007.mp3");
		songsList.add(song);
		
		// return songs list array
		return songsList;
	}
	
	/**
	 * Class to filter files which are having .mp3 extension
	 * */
	class FileExtensionFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			return (name.endsWith(".mp3") || name.endsWith(".MP3"));
		}
	}
}
