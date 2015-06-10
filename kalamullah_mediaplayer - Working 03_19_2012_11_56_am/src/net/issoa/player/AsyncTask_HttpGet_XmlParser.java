package net.issoa.player;
  

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

public class AsyncTask_HttpGet_XmlParser extends AsyncTask<String, Void, String> {

	// constructor
	public AsyncTask_HttpGet_XmlParser() {

	}

	/**
	 * Getting XML from URL making HTTP request
	 * @param url string //getxmlfromurl
	 * */
	@Override
	protected String doInBackground(String... url) {
		String xml = null;
		try {
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet request = new HttpGet(url[0]);
			HttpResponse response = null;
			response = httpClient.execute(request);
			//HttpResponse httpResponse = httpClient.execute(request);
			HttpEntity httpEntity = response.getEntity();
			xml = EntityUtils.toString(httpEntity);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// return XML
		return xml;
	}
	
	
}
