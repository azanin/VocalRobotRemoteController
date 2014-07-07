package it.azanin.remotevocalrobotcontroller.robotcommands;

import it.unibo.iot.models.robotCommands.IRobotSpeed;
import it.unibo.iot.models.robotCommands.SysKB;

public class RobotSpeedFactory {
	
	public static IRobotSpeed createRobotSpeedFromString(
			String robotCommandStringRep) {
		
		robotCommandStringRep = robotCommandStringRep.toLowerCase();
		
		if (robotCommandStringRep.contains("bassa")) {
			return SysKB.ROBOT_SPEED_LOW;
		}
		if (robotCommandStringRep.contains("alta")) {
			return SysKB.ROBOT_SPEED_HIGH;
		}
		if(robotCommandStringRep.contains("media"))
		{
			return SysKB.ROBOT_SPEED_MEDIUM;

		}
 		return SysKB.ROBOT_SPEED_LOW;
	}

}
