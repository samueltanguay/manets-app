package ca.etsmtl.gti785.model;

import java.io.Serializable;

/**
 *	This class represents an song. It is serializable
 *  to be able to transfer songs between views.
 * 	@author Samuel
 */
public class Song implements Serializable
{
	
	private static final long serialVersionUID = -5479476406048382448L;
	private String id;
	private String artist;
	private String title;
	private String path;
	
	/**
	 * Get the ID of the song
	 * @return The ID
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Set the id of the song
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Get the artist's name of the song
	 * @return The artist's name
	 */
	public String getArtist() {
		return artist;
	}

	/**
	 * Set the artist'name of the song
	 * @param artist The artist's name
	 */
	public void setArtist(String artist) {
		this.artist = artist;
	}

	/**
	 * Get the title of the song
	 * @return The title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set the title of the song
	 * @param title the title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Get the path of the song
	 * @return The path of the song
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * Set the path of the song
	 * @param path The path
	 */
	public void setPath(String path) {
		this.path = path;
	}
	
}
