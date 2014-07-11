package it.azanin.remotevocalrobotcontroller.voicecommands;

import it.azanin.remotevocalrobotcontroller.executor.RobotVoiceActionExecutor;
import it.azanin.remotevocalrobotcontroller.utils.SysKb;
import it.azanin.speech.text.WordList;
import it.azanin.speech.text.match.WordMatcher;
import it.azanin.speech.voiceaction.VoiceActionCommand;

public class RobotVoiceCommand implements VoiceActionCommand{

	private RobotVoiceActionExecutor executor;
	//	private WordMatcher directionWordMatcher;
	private WordMatcher speedValueWordMatcher;
	private WordMatcher directionValueWordMatcher;


	public RobotVoiceCommand(RobotVoiceActionExecutor executor, boolean relaxed) {
		this.executor = executor;
		this.speedValueWordMatcher = new WordMatcher(SysKb.vocalSpeedValueCommand);
		this.directionValueWordMatcher = new WordMatcher(SysKb.vocalDirectionValueCommand);
	}

	@Override
	public boolean interpret(WordList heard, float[] confidenceScores) {

		boolean understood = false;


		if(directionValueWordMatcher.isIn(heard.getWords()))
		{
			String direction = heard.getWords()[0].trim();


			String freeText = heard.getStringAfter(directionValueWordMatcher.isInAt(heard.getWords()));
			if(speedValueWordMatcher.isIn(freeText.trim()))
			{
				String speed = freeText;
				understood = true;
				executor.executeRobotVoiceCommand(direction, speed);
			}			

		}


		return understood;
	}

}
