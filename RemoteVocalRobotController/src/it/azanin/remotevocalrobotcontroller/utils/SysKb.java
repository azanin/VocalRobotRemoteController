package it.azanin.remotevocalrobotcontroller.utils;
import it.unibo.supports.FactoryProtocol;


public class SysKb {	
	public static final String protocol = FactoryProtocol.TCP;
	public static final int port = 8070;
	public static final String hostName = "192.168.1.139";//192.168.43.219

	public static  String[] vocalDirectionValueCommand = {"avanti","dritto","indietro","sinistra","destra","stop"};
	public static  String[] vocalSpeedValueCommand = {"alta","veloce","bassa","lenta","media"};

}
