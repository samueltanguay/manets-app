package ca.etsmtl.gti785.controller;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import ca.etsmtl.gti785.model.Song;

public class XmlParser 
{
	private static volatile XmlParser instance = null;
	
	
	public static XmlParser getInstance()
	{
		if (instance == null) 
		{
            synchronized (XmlParser .class)
            {
                if (instance == null)
                {
                	instance = new XmlParser();
                }
            }
	    }
	    return instance;
	}
	
	public List<Song> getSongs(String httpData) throws XmlPullParserException, IOException
	{
		List<Song> songs = new ArrayList<Song>();
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        httpData = httpData.replaceAll("\n", "");
        xpp.setInput( new StringReader(httpData));
        int eventType = xpp.getEventType();
        
        for(int i = 0; i < 4; i++)
        {
        	xpp.next();
        }
        
        Song song = new Song();
        String tagName = "";
        while(eventType != XmlPullParser.END_DOCUMENT)
        {
        	if(eventType == XmlPullParser.START_TAG && xpp.getName().equals("song"))
	        	song = new Song();
        	if(eventType == XmlPullParser.START_TAG && xpp.getName().equals("id"))
        		tagName = xpp.getName();        	
        	if(eventType == XmlPullParser.START_TAG && xpp.getName().equals("artist"))
        		tagName = xpp.getName();
        	if(eventType == XmlPullParser.START_TAG && xpp.getName().equals("title"))
        		tagName = xpp.getName();
        	if(eventType == XmlPullParser.START_TAG && xpp.getName().equals("path"))
        		tagName = xpp.getName();
        	if(eventType == XmlPullParser.TEXT && tagName.equals("id") && xpp.getText().trim().equals("") == false)
        		song.setId(Integer.parseInt(xpp.getText()));
        	if(eventType == XmlPullParser.TEXT && tagName.equals("artist") && xpp.getText().trim().equals("") == false)
        		song.setArtist(xpp.getText());
        	if(eventType == XmlPullParser.TEXT && tagName.equals("title") && xpp.getText().trim().equals("") == false)
        		song.setTitle(xpp.getText());
        	if(eventType == XmlPullParser.TEXT && tagName.equals("path") && xpp.getText().trim().equals("") == false)
        		song.setPath(xpp.getText());
        	if(eventType == XmlPullParser.END_TAG && xpp.getName().equals("song"))
	        	songs.add(song);
        	
        	eventType = xpp.next();
        }
        
		return songs;
	}

}
