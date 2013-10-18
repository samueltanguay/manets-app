package ca.etsmtl.gti785.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 *	This class represents a list of songs. It is serializable
 *  to be able to transfer the list between views.
 * 	@author Samuel
 */
public class Songs implements Serializable
{

	private static final long serialVersionUID = 6996761621644984754L;
	
	List<Song> songs = new ArrayList<Song>();
	
	/**
	 * Create a new list of songs from a Array list of Song
	 * @param musics The array list of song
	 */
	public Songs(ArrayList<Song> musics) {
		songs = musics;
	}
	
	/**
	 * Get the list of songs
	 * @return The list of songs
	 */
	public List<Song> getSongs()
	{
		return songs;
	}
	
	/**
	 * Add a song to the list
	 * @param song The song to add
	 */
	public void addSong(Song song)
	{
		songs.add(song);
	}
	
	/**
	 * Remove a song from the list
	 * @param song The song to remove
	 */
	public void removeSong(Song song)
	{
		songs.remove(song);
	}

}
