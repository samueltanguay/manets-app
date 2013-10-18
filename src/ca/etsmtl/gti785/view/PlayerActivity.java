package ca.etsmtl.gti785.view;

import ca.etsmtl.gti785.controller.AppController;
import ca.etsmtl.gti785.model.Songs;

import com.gti.gti785_manets_app.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


/**
 *	This class is a music player view. 
 *  It displays information about the song to the user.
 *  It also catch the user input to do the actions wanted.
 * 	@author Samuel
 */
public class PlayerActivity  extends Activity
{
	private Songs songs = null;
	private int position = 0;
	private AppController appController = AppController.getInstance();
	private boolean showNextPrevious = false;
	String serverIP ="";
	String serverPort= "";
	String serverRootPath = "";
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player);
		
		//Get the data from the previous view
		songs = (Songs) getIntent().getSerializableExtra("songs");
		position = getIntent().getIntExtra("position", 0);
		showNextPrevious = getIntent().getBooleanExtra("showNextPrevious", false);
		
		//Get preferences
		SharedPreferences settings = getSharedPreferences("serverSettings", MODE_PRIVATE);
		serverIP = settings.getString("pref_IP", "127.0.0.1");
		serverPort = settings.getString("pref_Port", "8080");
		serverRootPath = settings.getString("pref_Root_Path", "/gti785-manets-server/");
		
		//Set the information about the song played
		((TextView) findViewById(R.id.textViewArtist)).setText(songs.getSongs().get(position).getArtist());
		((TextView) findViewById(R.id.textViewTitle)).setText(songs.getSongs().get(position).getTitle());
		
		//Check if the previous view was the play list view. If it is, the next and previous buttons are visible
		if(showNextPrevious == false)
		{
			((Button) findViewById(R.id.buttonPrevious)).setVisibility(View.INVISIBLE);
			((Button) findViewById(R.id.buttonNext)).setVisibility(View.INVISIBLE);
		}
		else
		{
			((Button) findViewById(R.id.buttonPrevious)).setVisibility(View.VISIBLE);
			((Button) findViewById(R.id.buttonNext)).setVisibility(View.VISIBLE);
		}
		
		//Play the song
		playSong();
		
		//Handle the buttons action
		Button buttonPrevious = (Button) findViewById(R.id.buttonPrevious);
	    buttonPrevious.setOnClickListener(new OnClickListener()
	    {
	      public void onClick(View v)
	      {
	    	  previousSong();
	      }
	    });
	    
	    Button buttonPlay = (Button) findViewById(R.id.buttonPlay);

	    buttonPlay.setOnClickListener(new OnClickListener()
	    {
	      public void onClick(View v)
	      {
	    	if(((Button) findViewById(R.id.buttonPlay)).getText().equals("Play"))
	    	{
	    		playSong();
	    		((Button) findViewById(R.id.buttonPlay)).setText("Stop");
	    	}
	    	else
	    	{
	    		stopSong();
	    		((Button) findViewById(R.id.buttonPlay)).setText("Play");
	    	}
	      }
	    });
	    
	    Button buttonStream = (Button) findViewById(R.id.buttonStream);

	    buttonStream.setOnClickListener(new OnClickListener()
	    {
	      public void onClick(View v)
	      {
	    	  streamSong();
	      }
	    });
	    
	    Button buttonNext = (Button) findViewById(R.id.buttonNext);

	    buttonNext.setOnClickListener(new OnClickListener()
	    {
	      public void onClick(View v)
	      {
	    	  nextSong();
	      }
	    });	    
	}

	/**
	 *	Call the application controller to play the previous song
	 *	Update the text view regarding the new played song
	 */
	private void previousSong()
	{
		appController.previousSong(serverIP, serverPort, serverRootPath);
		//((TextView) findViewById(R.id.textViewArtist)).setText(song.getArtist());
		//((TextView) findViewById(R.id.textViewTitle)).setText(song.getTitle());		
	}
	
	/**
	 * 		Call the application controller to play the song selected
	 */
	private void playSong()
	{
		appController.playSong(songs.getSongs().get(position), serverIP, serverPort, serverRootPath);
	}
	
	/**
	 * 	Call the application controller to stop the song selected
	 */
	private void stopSong()
	{
		appController.stopSong(serverIP, serverPort, serverRootPath);
	}
	
	/**
	 * 	Call the application controller to stream the song selected
	 */
	private void streamSong()
	{
		//TODO
		appController.streamSong(songs.getSongs().get(position), serverIP, serverPort, serverRootPath);
	}
	
	/**
	 * 	Call the application controller to play the next song
	 *	Update the text view regarding the new played song
	 */
	private void nextSong()
	{
		appController.nextSong(serverIP, serverPort, serverRootPath);
		//((TextView) findViewById(R.id.textViewArtist)).setText(song.getArtist());
		//((TextView) findViewById(R.id.textViewTitle)).setText(song.getTitle());	
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.player, menu);
		return true;
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
	    switch (item.getItemId()) 
	    {
		    case R.id.action_settings:
		    {
		    	Intent mainIntent = new Intent(getApplicationContext(),MainActivity.class );
		        startActivityForResult(mainIntent,0);
		        return true;
		    }
		    
		    case R.id.action_music:
		    {
		    	Intent musicListIntent = new Intent(getApplicationContext(),MusicListActivity.class );
		    	musicListIntent.putExtra("showPlaylist", false);
		        startActivityForResult(musicListIntent,0);
		        return true;
		    }
		    
		    case R.id.action_playList:
		    {
		    	Intent musicListIntent = new Intent(getApplicationContext(),MusicListActivity.class );
		    	musicListIntent.putExtra("showPlaylist", true);
		        startActivityForResult(musicListIntent,0);
		        return true;
		    }
		    
		    case R.id.action_addPlayList:
		    {
		    	appController.addToPlaylist(songs.getSongs().get(position), serverIP, serverPort, serverRootPath);
		        return true;
		    }
		    
		    case R.id.action_removePlayList:
		    {
		    	//TODO Enlever juste une tune et pas toute
		    	appController.removeFromPlaylist(songs.getSongs().get(position), serverIP, serverPort, serverRootPath);
		        return true;
		    }
		    default:
		        return true;
	    }
	}
}
