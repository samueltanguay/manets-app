package ca.etsmtl.gti785.controller;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import ca.etsmtl.gti785.model.Song;
import ca.etsmtl.gti785.model.Songs;

/**
 *	The XML parser receives the XML responses from the web server
 *  and convert in a Songs object.
 * 	@author Samuel
 */
public class XmlParser 
{
	private static volatile XmlParser instance = null;
	
	
	/**
	 * Implementation of the singleton pattern
	 * Return the instance of the parser
	 * @return The instance
	 */
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
	
	/**
	 * Build the list of the songs from the XML response of the server
	 * @param httpData The response from the server in XML
	 * @return The songs obtained
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	public Songs getSongs(String httpData) throws XmlPullParserException, IOException
	{
		ArrayList<Song> songs = new ArrayList<Song>();
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
        		song.setId(xpp.getText());
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
        
		return new Songs(songs);
	}

}
