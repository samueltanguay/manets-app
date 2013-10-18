package ca.etsmtl.gti785.view;

import java.util.ArrayList;
import java.util.List;

import ca.etsmtl.gti785.controller.AppController;
import ca.etsmtl.gti785.model.Song;
import ca.etsmtl.gti785.model.Songs;

import com.gti.gti785_manets_app.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 *	This view shows a list of music.
 *  It handles the user input to get the song selected.
 * 	@author Samuel
 */
public class MusicListActivity extends Activity
{
	private AppController appController = AppController.getInstance();
	private boolean showPlayList = false;
	private Songs songs = null;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.music_list);
		
		//Get preferences
		SharedPreferences settings = getSharedPreferences("serverSettings", MODE_PRIVATE);
		String serverIP = settings.getString("pref_IP", "127.0.0.1");
  	  	String serverPort = settings.getString("pref_Port", "8080");
  	  	String serverRootPath = settings.getString("pref_Root_Path", "/gti785-manets-server/");
  	  	
  	  	//Check if we must show the list of all the music available or the play list
  	  	showPlayList = getIntent().getBooleanExtra("showPlaylist", false);
  	  	if(showPlayList == false)
  	  	{
  	  		//Get all the songs from the Web server
  			songs = appController.getMusic(serverIP, serverPort, serverRootPath);
  	  	}
  	  	else
  	  	{
  	  		// Get the playlist from the Web server
  	  		songs = appController.getPlaylist(serverIP, serverPort, serverRootPath);
  	  	}
  	  	
		//Show the songs in the ListView
		List<String> songsListView = new ArrayList<String>();
		for(int i = 0; i < songs.getSongs().size(); i++)
		{
			
			songsListView.add(((Song)songs.getSongs().get(i)).getArtist() + " - " + ((Song)songs.getSongs().get(i)).getTitle());
		}
		ListView musicListview = (ListView) findViewById(R.id.listViewMusic);
	    ArrayList<String> musicList = new ArrayList<String>();
	    for (int i = 0; i < songsListView.size(); ++i) 
	    {
	      musicList.add(songsListView.get(i));
	    }
	    
	    
	    //Catch the selected song and to the player
	    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	        android.R.layout.simple_list_item_1, musicList);
	    musicListview.setAdapter(adapter);
	    musicListview.setOnItemClickListener(new AdapterView.OnItemClickListener() 
	    {
	        public void onItemClick(AdapterView<?> parent, final View view,
	            int position, long id) 
	        {
	        	
	          //Send the songs to the player and go to the player view
	          Intent playerIntent = new Intent(parent.getContext(),PlayerActivity.class );
	          playerIntent.putExtra("position", position);
	          playerIntent.putExtra("songs", songs);
	          playerIntent.putExtra("showNextPrevious", showPlayList);
	          startActivityForResult(playerIntent,0);
	        }

	      });	    
	    
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{

		getMenuInflater().inflate(R.menu.music, menu);  
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
		    case R.id.action_settingsMusic:
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
		    case R.id.action_delete_playlist:
		    {
		    	Intent musicListIntent = new Intent(getApplicationContext(),MusicListActivity.class );
		    	
		    	//TODO Delete the playlist
		    	SharedPreferences settings = getSharedPreferences("serverSettings", MODE_PRIVATE);
				String serverIP = settings.getString("pref_IP", "127.0.0.1");
		  	  	String serverPort = settings.getString("pref_Port", "8080");
		  	  	String serverRootPath = settings.getString("pref_Root_Path", "/gti785-manets-server/");
		    	appController.deletePlaylist(serverIP, serverPort, serverRootPath);
		    	
		    	musicListIntent.putExtra("showPlaylist", true);
		        startActivityForResult(musicListIntent,0);
		        return true;
		    }
		    default:
		        return true;
	    }
	}
}