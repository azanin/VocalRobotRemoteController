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
		new RobotCommandSender(robot, robotCommand).start();
	
		
	}
	
	private class RobotCommandSender extends Thread
	{
		private IRobotCommand command;
		private IRobot proxy;
		
		public RobotCommandSender(IRobot proxy, IRobotCommand command) {
			this.command = command;
			this.proxy = proxy;
		}
		@Override
		public void run() {
			Log.d(ROBOTVOICEACTIONEXECUTOR, "sending command");
			proxy.execute(command);

		}
	}

}
