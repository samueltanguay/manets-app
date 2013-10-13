package ca.etsmtl.gti785.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

public class HttpController extends AsyncTask<String, Void, String>
{
	private static volatile HttpController instance = null;
	
	public static HttpController getInstance() 
	{
		if (instance == null) 
		{
            synchronized (HttpController .class)
            {
                if (instance == null)
                {
                	instance = new HttpController();
                }
            }
	    }
	    return instance;
	}

	@Override
	protected String doInBackground(String... params) 
	{
		String result = null;
		DefaultHttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(params[0]);
        try 
        {
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            InputStream inputStream = entity.getContent();
            
            
            result= convertStreamToString(inputStream);
            
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        
        return result;
        
	}
	
	
	
	/*
	 * Début source du code
	 * http://stackoverflow.com/questions/4457492/simple-http-client-example-in-android
	 *
	 */
	private static String convertStreamToString(InputStream is) {
	    /*
	     * To convert the InputStream to String we use the BufferedReader.readLine()
	     * method. We iterate until the BufferedReader return null which means
	     * there's no more data to read. Each line will appended to a StringBuilder
	     * and returned as String.
	     */
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();

	    String line = null;
	    try {
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            is.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return sb.toString();
	}
	
	/*
	 * Fin du source code
	 *
	 */
}
