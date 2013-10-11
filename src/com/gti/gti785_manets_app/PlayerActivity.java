package com.gti.gti785_manets_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PlayerActivity  extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player);
		
		Button buttonPrevious = (Button) findViewById(R.id.buttonPrevious);

	    buttonPrevious.setOnClickListener(new OnClickListener()
	    {
	      public void onClick(View v)
	      {
	    	  //TODO
	      }
	    });
	    
	    Button buttonPlay = (Button) findViewById(R.id.buttonPlay);

	    buttonPlay.setOnClickListener(new OnClickListener()
	    {
	      public void onClick(View v)
	      {
	    	  //TODO
	      }
	    });
	    
	    Button buttonStream = (Button) findViewById(R.id.buttonStream);

	    buttonStream.setOnClickListener(new OnClickListener()
	    {
	      public void onClick(View v)
	      {
	    	  //TODO
	      }
	    });
	    
	    Button buttonNext = (Button) findViewById(R.id.buttonNext);

	    buttonNext.setOnClickListener(new OnClickListener()
	    {
	      public void onClick(View v)
	      {
	    	  //TODO
	      }
	    });	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.player, menu);
		return true;
	}
	
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
		    
		    case R.id.action_playList:
		    {
		    	Intent musicListIntent = new Intent(getApplicationContext(),MusicListActivity.class );
		        startActivityForResult(musicListIntent,0);
		        return true;
		    }
		    
		    case R.id.action_addPlayList:
		    {
		        return true;
		    }
		    
		    case R.id.action_removePlayList:
		    {
		        return true;
		    }
		    default:
		        return true;
	    }
	}
}
