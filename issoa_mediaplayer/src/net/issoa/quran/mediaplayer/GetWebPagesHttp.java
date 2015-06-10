package net.issoa.quran.mediaplayer;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class GetWebPagesHttp {
	
	public String parsing_url = "";
	
	public GetWebPagesHttp(String url_2_get){		
		parsing_url = url_2_get;
	}
	
	public String get_webpage_source(){
		
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(parsing_url);
		HttpResponse response = null;
		try {
			response = client.execute(request);
		} catch (ClientProtocolException e) {

		} catch (IOException e) {

		}

		String html = "";
		InputStream in = null;
		try {
			in = response.getEntity().getContent();
		} catch (IllegalStateException e) {

		} catch (IOException e) {

		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder str = new StringBuilder();
		String line = null;
		try {
			while((line = reader.readLine()) != null)
			{
			    str.append(line);
			}
		} catch (IOException e) {

		}
		try {
			in.close();
		} catch (IOException e) {

		}
		html = str.toString();
		
		return html;
	}

}
