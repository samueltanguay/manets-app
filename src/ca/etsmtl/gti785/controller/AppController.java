package ca.etsmtl.gti785.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.xmlpull.v1.XmlPullParserException;

import ca.etsmtl.gti785.model.Song;


public class AppController 
{
	private static volatile AppController instance = null;
	private HttpController httpController = HttpController.getInstance();
	private XmlParser xmlParser = XmlParser.getInstance();
	
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
	
	public List<Song> getMusic(String serverIP, String serverPort, String serverRootPath)
	{
		List<Song> songs = new ArrayList<Song>();
		
		try 
		{
			String httpData = httpController.execute("http://" + serverIP + ":" + serverPort + serverRootPath + "music/").get();
			songs = xmlParser.getSongs(httpData);
			
			
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		} catch (ExecutionException e) 
		{
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return songs;
	}
	
	
}
