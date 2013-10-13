package ca.etsmtl.gti785.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ca.etsmtl.gti785.controller.AppController;
import ca.etsmtl.gti785.controller.HttpController;
import ca.etsmtl.gti785.model.Song;

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

public class MusicListActivity extends Activity
{
	private AppController appController = AppController.getInstance();
	private List<Song> songs = new ArrayList<Song>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.music_list);
		
		SharedPreferences settings = getSharedPreferences("serverSettings", MODE_PRIVATE);
		
		String serverIP = settings.getString("pref_IP", "127.0.0.1");
  	  	String serverPort = settings.getString("pref_Port", "8080");
  	  	String serverRootPath = settings.getString("pref_Root_Path", "/gti785-manets-server/");
			
		songs = appController.getMusic(serverIP, serverPort, serverRootPath);
		
		List<String> songsListView = new ArrayList<String>();
		for(int i = 0; i < songs.size(); i++)
		{
			
			songsListView.add(((Song)songs.get(i)).getArtist() + " - " + ((Song)songs.get(i)).getTitle());
		}
		
		ListView musicListview = (ListView) findViewById(R.id.listViewMusic);

	    ArrayList<String> musicList = new ArrayList<String>();
	    for (int i = 0; i < songsListView.size(); ++i) 
	    {
	      musicList.add(songsListView.get(i));
	    }
	    
	    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	        android.R.layout.simple_list_item_1, musicList);
	    musicListview.setAdapter(adapter);
	    musicListview.setOnItemClickListener(new AdapterView.OnItemClickListener() 
	    {
	        public void onItemClick(AdapterView<?> parent, final View view,
	            int position, long id) 
	        {
	          String item = (String) parent.getItemAtPosition(position);

	          Intent playerIntent = new Intent(parent.getContext(),PlayerActivity.class );
	          startActivityForResult(playerIntent,0);
	        }

	      });	    
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.music, menu);  
		return true;
	}
	
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
	    
	    	case R.id.action_playListMusic:
		    {
		        return true;
		    }
		    default:
		        return true;
	    }
	}
}