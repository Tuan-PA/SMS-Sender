package main.com.tuanpa.marketing.sms.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Date;
import java.io.File;

public class SMSLogger {
	private static SMSLogger logger;

	public static SMSLogger getInstance() {
		if (logger == null) {
			logger = new SMSLogger();
		}
		return logger;
	}
	
	private static String folderName;

	public SMSLogger() {
		init();
	}
	
	public void init(){
		String time = new Date().toString();
		time = time.replaceAll(" ", "-");
		time = time.replaceAll(":", "-");
		time = time.replaceAll(";", "-");
		folderName = "." + File.separator + "report-" + time;
		File file = new File(folderName);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public synchronized void log(String type, String message) {
		String fileName = folderName + File.separator + type + ".log";
		File file = new File(fileName);
		try {
			FileWriter fileWritter = new FileWriter(file, true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(message + "\n");
			bufferWritter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
