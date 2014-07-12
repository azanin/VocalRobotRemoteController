package it.azanin.remotevocalrobotcontroller.executor;

import android.util.Log;
import it.azanin.remotevocalrobotcontroller.robotcommands.MyRobotCommandFactory;
import it.azanin.speech.SpeechRecognizingActivity;
import it.azanin.speech.voiceaction.VoiceActionExecutor;
import it.unibo.iot.models.robotCommands.IRobotCommand;
import it.unibo.iot.robot.IRobot;


public class RobotVoiceActionExecutor extends VoiceActionExecutor {
	private static final String ROBOTVOICEACTIONEXECUTOR="RobotVoiceActionExecutor";
	
	private IRobot robot;

	public RobotVoiceActionExecutor(SpeechRecognizingActivity speech, IRobot robot) {
		super(speech);
		this.robot = robot;
	}
	
	
	public void executeRobotVoiceCommand(String direction, String speed)
	{
		IRobotCommand robotCommand = MyRobotCommandFactory.createRobotCommandFromString(direction, speed);
		new RobotCommandSender(this,robot, robotCommand).start();
		
	
		
	}
	
	private class RobotCommandSender extends Thread
	{
		private IRobotCommand command;
		private IRobot proxy;
		private RobotVoiceActionExecutor executor;
		
		public RobotCommandSender(RobotVoiceActionExecutor executor, IRobot proxy, IRobotCommand command) {
			this.command = command;
			this.proxy = proxy;
			this.executor = executor;
		}
		@Override
		public void run() {
			Log.d(ROBOTVOICEACTIONEXECUTOR, "sending command");
			executor.speak("eseguo il comando");

			proxy.execute(command);
			

		}
	}

}
