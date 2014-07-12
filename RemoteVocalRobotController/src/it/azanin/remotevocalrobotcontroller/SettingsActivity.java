package it.azanin.remotevocalrobotcontroller;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.widget.Toast;

public class SettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.userpreferences);
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		preferences.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences pref, String key) 
	{
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		String hostname = preferences.getString("PREF_HOST", "localhost");
		String sPort = preferences.getString("PREF_PORT", "8030");
		try
		{
			Integer port = Integer.parseInt(sPort);

		}
		catch(Exception e)
		{
			Toast.makeText(this, "Porta non corretta. Reimpostata al default.", Toast.LENGTH_SHORT).show();
			preferences.edit().putString("PREF_PORT", "8030").commit();			
		}
			
		
		
	}

	

}
