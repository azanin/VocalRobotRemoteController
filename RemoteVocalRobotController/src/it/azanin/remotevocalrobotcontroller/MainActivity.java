package it.azanin.remotevocalrobotcontroller;

import java.util.Arrays;
import java.util.List;

import it.azanin.remotevocalrobotcontroller.executor.RobotVoiceActionExecutor;
import it.azanin.remotevocalrobotcontroller.robotproxy.RobotProxy;
import it.azanin.remotevocalrobotcontroller.voicecommands.DirectionVoiceActionCommand;
import it.azanin.speech.SpeechRecognizingAndSpeakingActivity;
import it.azanin.speech.voiceaction.MultiCommandVoiceAction;
import it.azanin.speech.voiceaction.VoiceAction;
import it.azanin.speech.voiceaction.VoiceActionCommand;
import it.azanin.speech.voiceaction.WhyNotUnderstoodListener;
import android.speech.tts.TextToSpeech;
import android.os.Bundle;
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
		initDialog();


	}

	private void initDialog()
	{
		proxy = new RobotProxy();
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

	
	
	private VoiceAction makeSendCommandVoiceAction() {
		VoiceActionCommand directionCommand = new DirectionVoiceActionCommand(executor,false);
		VoiceActionCommand directionCommandRelaxed = new DirectionVoiceActionCommand(executor,true);

		VoiceAction sendVoiceAction = new MultiCommandVoiceAction(Arrays.asList(directionCommand,directionCommandRelaxed));
		sendVoiceAction.setNotUnderstood(new WhyNotUnderstoodListener(this, executor, false));
		sendVoiceAction.setPrompt("Dimmi la direzione");
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
