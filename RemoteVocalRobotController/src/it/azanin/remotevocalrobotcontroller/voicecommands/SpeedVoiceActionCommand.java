package it.azanin.remotevocalrobotcontroller.voicecommands;

import it.azanin.remotevocalrobotcontroller.executor.RobotVoiceActionExecutor;
import it.azanin.remotevocalrobotcontroller.utils.SysKb;
import it.azanin.speech.text.WordList;
import it.azanin.speech.text.match.WordMatcher;
import it.azanin.speech.voiceaction.VoiceActionCommand;



public class SpeedVoiceActionCommand implements VoiceActionCommand {
	
	private RobotVoiceActionExecutor executor;
	private String direction;
	private WordMatcher speedValueWordMatcher;


	public SpeedVoiceActionCommand(	RobotVoiceActionExecutor executor, String direction) {

		this.executor = executor;
		this.direction = direction;
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
			executor.executeRobotVoiceCommand(direction, speed);			 
		}		
		
		return understood;
	}
	

}
