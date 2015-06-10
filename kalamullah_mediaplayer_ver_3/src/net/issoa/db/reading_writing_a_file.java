package net.issoa.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Environment;

public class reading_writing_a_file {
	
	/*URL url = null;
	try {
		url = new URL("http://issoa.net/api/quran/quran.xml");
	} catch (MalformedURLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	// Read all of the text returned by the HTTP server
    BufferedReader in = null;
	try {
		in = new BufferedReader(new InputStreamReader(url.openStream()));
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} 
	String line;
	StringBuilder sb = new StringBuilder();

	try {
		while((line=in.readLine())!= null){
		    sb.append(line.trim());
		}
		String x = sb.toString();
		//Log.d("xml", x);
		
		 
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	String filename = "mylongxml.txt";
	File file = new File(Environment.getExternalStorageDirectory(), filename);
	FileOutputStream fos;

	byte[] data = new String(sb.toString()).getBytes();

	try {
	    fos = new FileOutputStream(file);
	    fos.write(data);
	    fos.flush();

	    fos.close();
	} catch (FileNotFoundException e) {
	    // handle exception
	} catch (IOException e) {
	    // handle exception

	}
*/
}
