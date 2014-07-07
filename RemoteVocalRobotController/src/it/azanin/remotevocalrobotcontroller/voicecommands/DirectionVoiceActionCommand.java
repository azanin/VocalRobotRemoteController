package it.azanin.remotevocalrobotcontroller.voicecommands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import it.azanin.remotevocalrobotcontroller.robotproxy.RobotProxy;
import it.azanin.remotevocalrobotcontroller.utils.SysKb;
import it.unibo.iot.models.robotCommands.IRobotCommand;
import it.unibo.iot.models.robotCommands.RobotCommandFactory;
import android.content.Context;
import root.gast.speech.text.WordList;
import root.gast.speech.text.match.SoundsLikeThresholdWordMatcher;
import root.gast.speech.text.match.WordMatcher;
import root.gast.speech.voiceaction.MultiCommandVoiceAction;
import root.gast.speech.voiceaction.VoiceActionCommand;
import root.gast.speech.voiceaction.VoiceActionExecutor;
import root.gast.speech.voiceaction.WhyNotUnderstoodListener;

public class DirectionVoiceActionCommand implements VoiceActionCommand
{
	private static final String TAG = "DirectionVoiceActionCommand";

	private Context context;
	private VoiceActionExecutor executor;
	//	private WordMatcher directionWordMatcher;
	private WordMatcher directionValueWordMatcher;
	private RobotProxy robotProxy;

	public DirectionVoiceActionCommand(Context context, VoiceActionExecutor executor,RobotProxy robotProxy, boolean relaxed) {

		/*	String[] commandWords = SysKb.vocalDirectionCommand;
		if(relaxed)
			directionWordMatcher = new SoundsLikeThresholdWordMatcher(5, commandWords);
		else
			directionWordMatcher = new WordMatcher(commandWords); //match only if users spell correctly words*/
		directionValueWordMatcher = new WordMatcher(SysKb.vocalDirectionValueCommand);
		this.context = context;
		this.executor = executor;
		this.robotProxy = robotProxy;

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
				SpeedVoiceActionCommand speedVoiceActionCommand = new SpeedVoiceActionCommand(context,executor,robotProxy,command);
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
