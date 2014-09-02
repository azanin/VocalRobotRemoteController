package it.azanin.remotevocalrobotcontroller.robotproxy;
import android.util.Log;
import it.azanin.remotevocalrobotcontroller.utils.SysKb;
import it.unibo.is.interfaces.IBasicEnvAwt;
import it.unibo.is.interfaces.protocols.IConnInteraction;
import it.unibo.iot.models.robotCommands.IRobotCommand;
import it.unibo.iot.models.robotCommands.RobotCommandFactory;
import it.unibo.iot.robot.IRobot;
import it.unibo.supports.FactoryProtocol;
import it.unibo.system.SituatedPlainObject;

public class RobotProxy extends SituatedPlainObject implements IRobot {
	
	private final static String ROBOTPROXY = "RobotProxy";
	
	protected IConnInteraction conn;
	protected static IBasicEnvAwt dummy = null; //TRUCCO
	
	private String hostName;
	private int port;


	public RobotProxy() {
		super(dummy);
		hostName =SysKb.hostName;
		port = SysKb.port;
	}

	public RobotProxy(IBasicEnvAwt env) {
		super(env);
		hostName =SysKb.hostName;
		port = SysKb.port;
	}
	
	public RobotProxy(String hostName, int port) {
		super(dummy);
		this.hostName = hostName;
		this.port = port;
	}
	
	public void execute(String cmd) {
		IRobotCommand robotCommand = RobotCommandFactory.createRobotCommandFromString(cmd);
 		 execute(robotCommand);		
	}

	
	public void connectToRobot() throws Exception {
		FactoryProtocol factoryP = new FactoryProtocol(outView,
				SysKb.protocol, "RobotProxy");
		//conn = factoryP.createClientProtocolSupport(hostName, port);
		Log.d(ROBOTPROXY,"Connecting to Robot");
		conn=factoryP.createClientProtocolSupport(hostName, port);
		Log.d(ROBOTPROXY, "Connected to " + hostName + " port: " + port);
	}

	
	public void terminate() {
		try {
			println("Connection closed ");

			if (conn != null)
				conn.closeConnection();
			conn=null;
		} catch (Exception e) {
			println("ERROR +  " + e.getMessage());
		}
	}

	@Override
	public void execute(IRobotCommand command) {
		try {
			terminate();
			if (conn == null)
			{
				connectToRobot();
			}
			String msg = command.getStringRep();
			println("SEND to the remote robot THE COMMAND " + msg);
			conn.sendALine(msg);
			Thread.sleep(2000);
		} 
		catch (Exception e) 
		{
			conn=null;
			println("ERROR   " + e.getMessage());
		}
		
	}
	
	

	

}
