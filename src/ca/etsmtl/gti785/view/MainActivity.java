package ca.etsmtl.gti785.view;

import com.gti.gti785_manets_app.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 *	This class is starting point of the application.
 *  It gets the server informations from the user.
 * 	@author Samuel
 */
public class MainActivity extends Activity 
{

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button button = (Button) findViewById(R.id.buttonURL);

	    button.setOnClickListener(new OnClickListener()
	    {
	      public void onClick(View v)
	      {
	    	  String serverURL = ((TextView) findViewById(R.id.editTextURL)).getText().toString();
	    	  String serverIP = serverURL.substring(0, serverURL.indexOf(":"));
	    	  String serverPort = serverURL.substring(serverURL.indexOf(":") + 1, serverURL.indexOf("/"));
	    	  String serverRootPath = serverURL.substring(serverURL.indexOf("/"));
	    	  
	    	  setPreferences(serverIP, serverPort, serverRootPath);
	    	  
	    	  
	    	  Intent musicListIntent = new Intent(v.getContext(),MusicListActivity.class );
	          startActivityForResult(musicListIntent,0);
	      }
	    });
	}
	
	/**
	 * Set the preferences from the user input
	 * @param serverIP The server's IP
	 * @param serverPort The server's port
	 * @param serverRootPath The first part of the path required for every URL
	 */
	private void setPreferences(String serverIP, String serverPort, String serverRootPath)
	{
		//http://stackoverflow.com/questions/552070/android-how-do-i-set-a-preference-in-code
		SharedPreferences settings = getSharedPreferences("serverSettings", MODE_PRIVATE);
		Editor editor = settings.edit();
		
		editor.clear();
		editor.putString("pref_IP", serverIP);
		editor.putString("pref_Port", serverPort);
		editor.putString("pref_Root_Path", serverRootPath);
		editor.commit();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
