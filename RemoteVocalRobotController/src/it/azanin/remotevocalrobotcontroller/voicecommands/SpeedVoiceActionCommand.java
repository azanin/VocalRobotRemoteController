package it.azanin.remotevocalrobotcontroller.voicecommands;

import it.azanin.remotevocalrobotcontroller.robotcommands.MyRobotCommandFactory;
import it.azanin.remotevocalrobotcontroller.robotproxy.RobotProxy;
import it.azanin.remotevocalrobotcontroller.utils.SysKb;
import it.unibo.iot.models.robotCommands.IRobotCommand;
import android.content.Context;
import android.os.AsyncTask;
import root.gast.speech.text.WordList;
import root.gast.speech.text.match.WordMatcher;
import root.gast.speech.voiceaction.VoiceActionCommand;
import root.gast.speech.voiceaction.VoiceActionExecutor;

public class SpeedVoiceActionCommand implements VoiceActionCommand {
	
	private Context context;
	private VoiceActionExecutor executor;
	private String command;
	private WordMatcher speedValueWordMatcher;
	private RobotProxy robotProxy;


	public SpeedVoiceActionCommand(Context context,	VoiceActionExecutor executor,RobotProxy robotProxy, String robotCommand) {

		this.context = context;
		this.executor = executor;
		this.command = robotCommand;
		this.robotProxy = robotProxy;
		speedValueWordMatcher = new WordMatcher(SysKb.vocalSpeedValueCommand);
		
		
	}

	@Override
	public boolean interpret(WordList heard, float[] confidenceScores) {
		boolean understood = false;
		int matchIndex = speedValueWordMatcher.isInAt(heard.getWords());
		if(matchIndex >= 0)
		{
			String speed = heard.getStringWithout(matchIndex);
			understood = true;

			
			//Construct RobotCommand
			IRobotCommand robotCommand = MyRobotCommandFactory.createRobotCommandFromString(command, speed);
			if(robotCommand!=null)
			{
				//send to robot
				new SendCommand(robotProxy, robotCommand).execute();
				String response = "Comando inviato";
				executor.speak(response);
				 
			}
			 
		}		
		
		return understood;
	}
	
	private class SendCommand extends AsyncTask<Void, Void, Void>
	{
		private RobotProxy robotProxy;
		private IRobotCommand robotCommand;
		
		public SendCommand(RobotProxy robotProxy,IRobotCommand robotCommand) 
		{
			this.robotProxy = robotProxy;
			this.robotCommand = robotCommand;
		}

		@Override
		protected Void doInBackground(Void... params) {			
			robotProxy.execute(robotCommand);
			return null;
		}
	
	

		
	}
	

}
