package it.azanin.remotevocalrobotcontroller;

import java.util.Arrays;
import java.util.List;

import it.azanin.remotevocalrobotcontroller.executor.RobotVoiceActionExecutor;
import it.azanin.remotevocalrobotcontroller.robotproxy.RobotProxy;
import it.azanin.remotevocalrobotcontroller.voicecommands.DirectionVoiceActionCommand;
import it.azanin.remotevocalrobotcontroller.voicecommands.RobotVoiceCommand;
import it.azanin.speech.SpeechRecognizingAndSpeakingActivity;
import it.azanin.speech.voiceaction.MultiCommandVoiceAction;
import it.azanin.speech.voiceaction.VoiceAction;
import it.azanin.speech.voiceaction.VoiceActionCommand;
import it.azanin.speech.voiceaction.WhyNotUnderstoodListener;
import android.speech.tts.TextToSpeech;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends SpeechRecognizingAndSpeakingActivity {

	private static final String TAG = "SpeechRecognizerActivity";

	private RobotVoiceActionExecutor executor;
	private VoiceAction sendCommandVoiceAction;
	private RobotProxy proxy;




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		hookButtons();


	}

	private void initDialog()
	{
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		String hostname = preferences.getString("PREF_HOST", "localhost");
		Integer port = Integer.parseInt(preferences.getString("PREF_PORT", "8030"));
		Log.d("MainActivity", hostname + " "+port);
		
		proxy = new RobotProxy(hostname,port);
		if (executor == null)
			executor = new RobotVoiceActionExecutor(this, proxy);
		sendCommandVoiceAction = makeSendCommandVoiceAction();
	}


	private void hookButtons() {
		Button sendCommand = (Button) findViewById(R.id.sendCommandButton);
		sendCommand.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				if(proxy != null)
					executor.execute(sendCommandVoiceAction);
			}
		});
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		//initDialog();

	}
	
	@Override
	protected void onResume() {
		super.onResume();
		//SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		//String hostname = preferences.getString("PREF_HOST", "localhost");
		//Integer port = Integer.parseInt(preferences.getString("PREF_PORT", "8030"));
		//proxy = new RobotProxy(hostname,port);
		initDialog();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		proxy = null;
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		proxy = null;
	}

	
	
	private VoiceAction makeSendCommandVoiceAction() {
		VoiceActionCommand robotVoiceCommand = new RobotVoiceCommand(executor,false);
		VoiceActionCommand robotVoiceCommandRelaxed = new RobotVoiceCommand(executor,true);

		VoiceAction sendVoiceAction = new MultiCommandVoiceAction(Arrays.asList(robotVoiceCommand,robotVoiceCommandRelaxed));
		sendVoiceAction.setNotUnderstood(new WhyNotUnderstoodListener(this, executor, false));
		sendVoiceAction.setPrompt("Dimmi il comando");
		return sendVoiceAction;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			startActivity(new Intent(this,SettingsActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onSuccessfulInit(TextToSpeech tts) {
		super.onSuccessfulInit(tts);
		Log.d(TAG, "activate ui, set tts");
		executor.setTts(getTts());
	}


	@Override
	protected void receiveWhatWasHeard(List<String> heard,float[] confidenceScores) {
		Log.d(TAG, "received " + heard.size());
		executor.handleReceiveWhatWasHeard(heard, confidenceScores);
	}

	
    
    

}
