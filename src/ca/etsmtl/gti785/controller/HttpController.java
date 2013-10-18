package ca.etsmtl.gti785.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;

/**
 *	The HTTP controller handle all the HTTP requests and responses.
 *  It's also the threading class.
 * 	@author Samuel
 */
public class HttpController extends AsyncTask<String, Void, String>
{

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	@Override
	protected String doInBackground(String... params) 
	{
		String response = "";
		if(params[0].equals("listingSongs"))
			response = listingSongs(params[1]);
		else if(params[0].equals("playAMedia"))
			playAMedia(params[2], params[1]);
		else if(params[0].equals("stopThePlayer"))
			stopThePlayer(params[1]);
		else if(params[0].equals("previous") || params[0].equals("next"))
			previousOrNextMedia(params[1], params[0]);
		else if(params[0].equals("addToPlaylist"))
			addToPlaylist(params[2], params[1]);
		else if(params[0].equals("listingPlaylist"))
			response = listingPlaylist(params[1]);
		else if(params[0].equals("removeFromPlaylist"))
			removeFromPlaylist(params[1]);
		else if(params[0].equals("deletePlaylist"))
			deletePlaylist(params[1]);
		else if(params[0].equals("streamSong"))
			streamSong(params[2], params[1], params[3]);
				
        return response;
        
	}
	
	

	/*
	 * CODE EMPRUMTÉ : 
	 * La manière de faire l'envoi des commandes Get et Post sont basées sur le site suivant:
	 * http://www.softwarepassion.com/android-series-get-post-and-multipart-post-requests/
	 */
	
	
	/**
	 * Get the music from the specified URL and return the XML obtained
	 * @param url The URL to send the get request
	 * @return The XML obtain
	 */
	private String listingSongs(String url)
	{
		String result = null;
		DefaultHttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
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
	
	/**
	 * Send a post request with a specific id as a parameter to play the music to the specified URL 
	 * @param id The id to send as a parameter
	 * @param url The URL used for the request
	 */
	private void playAMedia(String id, String url)
	{
		DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        try 
        {
            //http://www.softwarepassion.com/android-series-get-post-and-multipart-post-requests/
        	List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("action", "play"));
            params.add(new BasicNameValuePair("type", "music"));
            params.add(new BasicNameValuePair("id", id));
            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
            post.setEntity(ent);
            client.execute(post);             
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
	}
	

	/**
	 * Send a stop request to the specified URL 
	 * @param url The URL used for the request
	 */
	private void stopThePlayer(String url) 
	{
		DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        try 
        {
            //http://www.softwarepassion.com/android-series-get-post-and-multipart-post-requests/
        	List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("action", "stop"));
            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
            post.setEntity(ent);
            client.execute(post);           
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }		
	}
	
	/**
	 * 
	 * Send a previous or next action to the specified URL and return the XML obtained
	 * @author http://www.softwarepassion.com/android-series-get-post-and-multipart-post-requests/
	 * @param url The URL to send the post request
	 * @param action The action to do (next or previous)
	 * @return The XML obtain
	 */
	private String previousOrNextMedia(String url, String action) 
	{
		String result = "";
		DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        try 
        {
        	List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("action", action));
            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
            post.setEntity(ent);
            HttpResponse responsePOST = client.execute(post);  
            HttpEntity entity = responsePOST.getEntity();
            InputStream inputStream = entity.getContent();
            result= convertStreamToString(inputStream);
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }	
        
        return result;
	}
	
	
	
	/**
	 * Send an add to play list request to the specified URL with a specific id
	 * @param id The id of the song to add
	 * @param url 
	 */
	private void addToPlaylist(String id, String url) 
	{
		DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        try 
        {
        	List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));
            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
            post.setEntity(ent);
            client.execute(post);             
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }		
	}
	
	/**
	 * Send a request to the specified URL to get the songs in the play list in XML
	 * @param url The URL used for the request
	 * @return The response from the server in XML
	 */
	private String listingPlaylist(String url) 
	
	{
		String result = null;
		DefaultHttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
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
	
	/**
	 * Send a remove play list request to the specified URL with a specific id
	 * @param id The id to remove from the play list
	 * @param url The URL used for the request
	 */
	private void removeFromPlaylist(String url) 
	{
		//TODO Faire ça
		DefaultHttpClient client = new DefaultHttpClient();
        HttpDelete delete = new HttpDelete(url);
        try 
        {
            client.execute(delete);             
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
	}
	
	/**
	 * Send a request to the specified URL to delete all the songs in the play list
	 * @param url The URL used for the request
	 */
	private void deletePlaylist(String url) 
	{
		DefaultHttpClient client = new DefaultHttpClient();
        HttpDelete delete = new HttpDelete(url);
        try 
        {
            client.execute(delete);             
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
	}	
	
	/**
	 * Send a request to the specified URL to start song identified by the id
	 * It also starts to stream the music on the server's IP passed as parameter
	 * @param id The id of the song to be broadcasted by the server
	 * @param url The URL used for the request
	 * @param serverIP The server's IP used to stream the IP
	 */
	private void streamSong(String id, String url, String serverIP)
	{
		DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        try 
        {
        	List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("action", "stream"));
            params.add(new BasicNameValuePair("type", "music"));
            params.add(new BasicNameValuePair("id", id));
            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
            post.setEntity(ent);
            client.execute(post); 
            
            

    		String streamUrl = "http://" + serverIP + ":8888";
            MediaPlayer mediaPlayer = new MediaPlayer();            
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
    			mediaPlayer.setDataSource(streamUrl);
    			mediaPlayer.prepare();
    			mediaPlayer.start();
    		} catch (IllegalArgumentException e) {
    			e.printStackTrace();
    		} catch (SecurityException e) {
    			e.printStackTrace();
    		} catch (IllegalStateException e) {
    			e.printStackTrace();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
            
     
            
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
	}
	// FIN DU CODE EMPRUNTÉ
	
	
	/*
	 * 
	 * CODE EMPRUMTÉ : 
	 * les lignes suivantes sont basées sur une classe provenant du site :
	 * http://stackoverflow.com/questions/4457492/simple-http-client-example-in-android
	 */
	
	/**
	 * Read the response from the server and return it in a string format
	 * Début de la source du code
	 * @author http://stackoverflow.com/questions/4457492/simple-http-client-example-in-android
	 * @param is The input stream
	 * @return The response in an string format
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
	// FIN DU CODE EMPRUNTÉ
}
