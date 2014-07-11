package it.azanin.remotevocalrobotcontroller.voicecommands;

import java.util.ArrayList;
import java.util.List;

import it.azanin.remotevocalrobotcontroller.executor.RobotVoiceActionExecutor;
import it.azanin.remotevocalrobotcontroller.utils.SysKb;
import it.azanin.speech.text.WordList;
import it.azanin.speech.text.match.WordMatcher;
import it.azanin.speech.voiceaction.MultiCommandVoiceAction;
import it.azanin.speech.voiceaction.VoiceActionCommand;



public class DirectionVoiceActionCommand implements VoiceActionCommand
{
	@SuppressWarnings("unused")
	private static final String TAG = "DirectionVoiceActionCommand";

	private RobotVoiceActionExecutor executor;
	//	private WordMatcher directionWordMatcher;
	private WordMatcher directionValueWordMatcher;

	public DirectionVoiceActionCommand(RobotVoiceActionExecutor executor, boolean relaxed) {

		/*	String[] commandWords = SysKb.vocalDirectionCommand;
		if(relaxed)
			directionWordMatcher = new SoundsLikeThresholdWordMatcher(5, commandWords);
		else
			directionWordMatcher = new WordMatcher(commandWords); //match only if users spell correctly words*/
		directionValueWordMatcher = new WordMatcher(SysKb.vocalDirectionValueCommand);
		this.executor = executor;

	}

	@Override
	public boolean interpret(WordList heard, float[] confidenceScores) {

		boolean understood = false;
		//match first part: "direction"
		//int matchIndex = directionWordMatcher.isInAt(heard.getWords());
		/*if(matchIndex>= 0)
		{*/
		//match second part: command
		String freeText = heard.getSource();
		if(freeText.length() > 0)
		{
			String command = freeText.trim();		

			if(directionValueWordMatcher.isIn(command))
			{
				//starting build speed voice command
				SpeedVoiceActionCommand speedVoiceActionCommand = new SpeedVoiceActionCommand(executor,command);
				String calPromptFormat = "Quale velocità per %1$s?";
				String calPrompt = String.format(calPromptFormat, command);
				List<VoiceActionCommand> lists = new ArrayList<VoiceActionCommand>();
				lists.add(speedVoiceActionCommand);
				MultiCommandVoiceAction responseAction = new MultiCommandVoiceAction(lists);
				responseAction.setPrompt(calPrompt);
				responseAction.setSpokenPrompt(calPrompt);

				//retry if not understood
				//responseAction.setNotUnderstood(new WhyNotUnderstoodListener(context, executor, true));
				understood = true;
				executor.execute(responseAction);

			}
		}


		return understood;
	}

}
