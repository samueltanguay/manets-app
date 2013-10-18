package ca.etsmtl.gti785.controller;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.xmlpull.v1.XmlPullParserException;

import ca.etsmtl.gti785.model.Song;
import ca.etsmtl.gti785.model.Songs;

/**
 *	The application controller is a dispatcher. It receives 
 *  the request from the view, forward it to the right
 *  controller and return the result to the view.
 * 	@author Samuel
 */
public class AppController 
{
	
	private static volatile AppController instance = null;
	private XmlParser xmlParser = XmlParser.getInstance();
	
	/**
	 * Implementation of the singleton pattern
	 * Return the instance of the controller
	 * @return The instance
	 */
	public static AppController getInstance() 
	{
		if (instance == null) 
		{
            synchronized (AppController .class)
            {
                if (instance == null)
                {
                	instance = new AppController();
                }
            }
	    }
	    return instance;
	}
	
	/**
	 * Get the music from a specific web server and get all the song on the media server
	 * @param serverIP The server's IP
	 * @param serverPort The server's port
	 * @param serverRootPath The first part of the path required for every URL
	 * @return The songs
	 */
	public Songs getMusic(String serverIP, String serverPort, String serverRootPath)
	{
		Songs songs = null;

		try 
		{
			
			
			String httpData =  new HttpController().execute("listingSongs", "http://" + serverIP + ":" + serverPort + serverRootPath + "music/").get();
			songs = xmlParser.getSongs(httpData);
			
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		} catch (ExecutionException e) 
		{
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return songs;
	}

	/**
	 * Send a previous song request to the HttpController and get the song played
	 * @param serverIP The server's IP
	 * @param serverPort The server's port
	 * @param serverRootPath The first part of the path required for every URL
	 * @return The new song played
	 */
	public void previousSong(String serverIP, String serverPort, String serverRootPath) 
	{
		new HttpController().execute("previous", "http://" + serverIP + ":" + serverPort + serverRootPath + "player/");
	}
	
	/**
	 * Send a play song request to the HttpController
	 * @param song
	 * @param serverIP The server's IP
	 * @param serverPort The server's port
	 * @param serverRootPath The first part of the path required for every URL
	 */
	public void playSong(Song song, String serverIP, String serverPort, String serverRootPath) 
	{
		new HttpController().execute("playAMedia", "http://" + serverIP + ":" + serverPort + serverRootPath + "player/", song.getId());
	}

	/**
	 * Send a stop song request to the HttpController
	 * @param serverIP The server's IP
	 * @param serverPort The server's port
	 * @param serverRootPath The first part of the path required for every URL
	 */
	public void stopSong(String serverIP, String serverPort, String serverRootPath) 
	{
		new HttpController().execute("stopThePlayer", "http://" + serverIP + ":" + serverPort + serverRootPath + "player/");
		
	}

	/**
	 * Send a next song request to the HttpController and get the song played
	 * @param serverIP The server's IP
	 * @param serverPort The server's port
	 * @param serverRootPath The first part of the path required for every URL
	 * @return The new song played
	 */
	public void nextSong(String serverIP, String serverPort, String serverRootPath) 
	{
		new HttpController().execute("next", "http://" + serverIP + ":" + serverPort + serverRootPath + "player/");	
	}

	/**
	 * Send a add to play list request to the HttpController
	 * @param song
	 * @param serverIP The server's IP
	 * @param serverPort The server's port
	 * @param serverRootPath The first part of the path required for every URL
	 */
	public void addToPlaylist(Song song, String serverIP, String serverPort,
			String serverRootPath) 
	{
		new HttpController().execute("addToPlaylist", "http://" + serverIP + ":" + serverPort + serverRootPath + "playlist/", song.getId());
		
	}

	/**
	 * Send request to the HttpController to get the play list and get the music in the play list
	 * @param serverIP The server's IP
	 * @param serverPort The server's port
	 * @param serverRootPath The first part of the path required for every URL
	 * @return The songs in the play list
	 */
	public Songs getPlaylist(String serverIP, String serverPort,
			String serverRootPath) 
	{
		Songs songs = null;

		try 
		{
			
			String httpData =  new HttpController().execute("listingPlaylist", "http://" + serverIP + ":" + serverPort + serverRootPath + "playlist/").get();
			songs = xmlParser.getSongs(httpData);
			
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		} catch (ExecutionException e) 
		{
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return songs;
	}

	/**
	 * Send  request to the HttpController to remove a song from the play list
	 * @param song
	 * @param serverIP The server's IP
	 * @param serverPort The server's port
	 * @param serverRootPath The first part of the path required for every URL
	 */
	public void removeFromPlaylist(Song song, String serverIP,
			String serverPort, String serverRootPath) 
	{
		new HttpController().execute("removeFromPlaylist", "http://" + serverIP + ":" + serverPort + serverRootPath + "playlist/" + song.getId());
	}
	
	/**
	 * Request to the HttpController to delete the play list
	 * @param serverIP The server's IP
	 * @param serverPort The server's port
	 * @param serverRootPath The first part of the path required for every URL
	 */
	public void deletePlaylist(String serverIP,
			String serverPort, String serverRootPath) 
	{
		new HttpController().execute("deletePlaylist", "http://" + serverIP + ":" + serverPort + serverRootPath + "playlist/");
		
	}

	/**
	 * Request to the HttpController to delete the play list
	 * @param serverIP The server's IP
	 * @param serverPort The server's port
	 * @param serverRootPath The first part of the path required for every URL
	 */
	public void streamSong(Song song, String serverIP, String serverPort,
			String serverRootPath) 
	{
		new HttpController().execute("streamSong", "http://" + serverIP + ":" + serverPort + serverRootPath + "player/", song.getId(), serverIP);
	}
	
}
