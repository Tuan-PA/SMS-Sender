package main.com.tuanpa.marketing.sms.plugandplay;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;

import main.com.tuanpa.marketing.sms.log.SMSLogger;

import org.smslib.gateway.modem.driver.serial.CommPortIdentifier;
import org.smslib.gateway.modem.driver.serial.SerialPort;

public class ComPortDetect {
//	private static int bauds[] = { 9600, 19200, 14400, 28800, 33600, 38400,
//			56000, 57600, 115200 };
	public static final String MAINCLASS = "org.smslib.Service";
	public static ArrayList<CommPortIdentifier> lPort;

	public ComPortDetect() throws Exception {
		Class.forName(MAINCLASS);
	}

	public static void autoDetectDevices() {
		if (lPort != null) {
			lPort.clear();
		} else {
			lPort = new ArrayList<CommPortIdentifier>();
		}
		int bauds[] = { 9600, 14400, 19200, 28800, 33600, 38400, 56000, 57600, 115200 };
		Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
		while (portList.hasMoreElements())
		{
			CommPortIdentifier portId = portList.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL)
			{
				System.out.println(String.format("====== Found port: %-5s", portId.getName()));
				for (int i = 0; i < 1; i++)
				{
					SerialPort serialPort = null;
					InputStream inStream = null;
					OutputStream outStream = null;
					try
					{
						int c;
						String response;
						serialPort = portId.open("SMSLibCommTester", 5000);
						serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN);
						serialPort.setSerialPortParams(bauds[i], SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
						inStream = serialPort.getInputStream();
						outStream = serialPort.getOutputStream();
						serialPort.enableReceiveTimeout(1000);
						c = inStream.read();
						while (c != -1)
							c = inStream.read();
						outStream.write('A');
						outStream.write('T');
						outStream.write('\r');
						Thread.sleep(1000);
						response = "";
						StringBuilder sb = new StringBuilder();
						c = inStream.read();
						while (c != -1)
						{
							sb.append((char) c);
							c = inStream.read();
						}
						response = sb.toString();
						if (response.indexOf("OK") >= 0)
						{
							try
							{
								System.out.print("  Getting Info...");
								outStream.write('A');
								outStream.write('T');
								outStream.write('+');
								outStream.write('C');
								outStream.write('G');
								outStream.write('M');
								outStream.write('M');
								outStream.write('\r');
								response = "";
								c = inStream.read();
								while (c != -1)
								{
									response += (char) c;
									c = inStream.read();
								}
								lPort.add(portId);
								System.out.println(" Found: " + response.replaceAll("\\s+OK\\s+", "").replaceAll("\n", "").replaceAll("\r", ""));
								break;
							}
							catch (Exception e)
							{
							}
						}
						else
						{
						}
					}
					catch (Exception e)
					{
						Throwable cause = e;
						while (cause.getCause() != null)
							cause = cause.getCause();
					}
					finally
					{ 
						try {
							if (inStream != null) inStream.close();
							if (outStream != null) outStream.close();
							if (serialPort != null) serialPort.close();
						} catch (Exception e ) {
							e.printStackTrace();
						}	
					}
				}
			}
		}
		System.out.println("\nTest complete.");
		String log = "";
		log += "Total Device: " + lPort.size() + "\n";
		for (CommPortIdentifier pid : lPort){
			log += pid.getName() + "\n";
		}
		SMSLogger.getInstance().log("DEVICE", log);
	}

}
