package com.gti.gti785_manets_app;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

public class HttpTreatment extends AsyncTask<String, Void, String>
{

	@Override
	protected String doInBackground(String... params) 
	{
		DefaultHttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(params[0]);
        try 
        {
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            InputStream inputStream = entity.getContent();
            
            StatusLine statusLine = response.getStatusLine();
            
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
		
		return "";
	}
}
