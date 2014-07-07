package it.azanin.remotevocalrobotcontroller.robotcommands;

import it.unibo.iot.models.robotCommands.IRobotCommand;
import it.unibo.iot.models.robotCommands.IRobotSpeed;
import it.unibo.iot.models.robotCommands.RobotBackward;
import it.unibo.iot.models.robotCommands.RobotCommandFactory;
import it.unibo.iot.models.robotCommands.RobotForward;
import it.unibo.iot.models.robotCommands.RobotLeft;
import it.unibo.iot.models.robotCommands.RobotRight;
import it.unibo.iot.models.robotCommands.RobotStop;
import it.unibo.iot.models.robotCommands.SysKB;

public class MyRobotCommandFactory extends RobotCommandFactory {
	
	public static IRobotCommand createRobotCommandFromString(
			String robotCommandStringRep, String speed) {
		
		IRobotSpeed robotSpeed = RobotSpeedFactory.createRobotSpeedFromString(speed);
		
		
		robotCommandStringRep = robotCommandStringRep.toLowerCase();
		
		
		if (robotCommandStringRep.contains("avanti")) {
			return new RobotForward(robotSpeed);
		}
		if (robotCommandStringRep.contains("indietro")) {
			return new RobotBackward(robotSpeed);
		}
		if (robotCommandStringRep.contains("sinistra")) {
			return new RobotLeft(robotSpeed);
		}
		if (robotCommandStringRep.contains("destra")) {
			return new RobotRight(robotSpeed);
		}
		if (robotCommandStringRep.contains("stop")) {
			return new RobotStop(robotSpeed);
		}
 		return null;
	}
	
	 

}
