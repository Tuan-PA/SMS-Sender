package main.com.tuanpa.marketing.sms.message;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageManager {
	private static String DIRECTORY = "." + File.separator + "message";
	private static ArrayList<String> messages = new ArrayList<String>();

	public static void readMessageFromFile() throws Exception {
		File folder = new File(DIRECTORY);
		// if the directory does not exist, create it
		if (!folder.exists()) {
		    folder.mkdir();
		}
		File[] listOfFiles = folder.listFiles();
		for (File file : listOfFiles) {
			if (file.isFile()) {
				String msg = "";
				String temp = "";
				BufferedReader br = new BufferedReader(new InputStreamReader(
	                      new FileInputStream(file), "UTF8"));
				while ((temp = br.readLine()) != null) {
					msg += temp;
				}
				messages.add(msg);
				System.out.println("Template: " + msg);
				br.close();
			}
		}
		System.out.println("Read message complete");
	}

	public static String getMessage() {
		if (messages.size() == 0) return null;
		Random rd = new Random();
		String tempMessage = messages.get(rd.nextInt(messages.size()));
		System.out.println("Template: " + tempMessage);
		return buildStringFromTemplate(tempMessage);
	}

	private static String buildStringFromTemplate(String tempMessage){
			String result = new String(tempMessage);
			String regex =  Pattern.quote("{") + "(.*?)" + Pattern.quote("}");
		    Pattern pattern = Pattern.compile(regex);
		    Matcher matcher = pattern.matcher(tempMessage);
		    ArrayList<String> groupText = new ArrayList<String>();
		    while (matcher.find()) {
		    	groupText.add(matcher.group(1));
		    }
		    for (String sText : groupText) {
		    	Random rd =  new Random();
				String[] tmps = sText.split("\\|");
		    	String replaceText = tmps[rd.nextInt(tmps.length)];
		    	int i = result.indexOf("{");
		    	int j = result.indexOf("}", i);
		    	if (i == -1 || j == -1)
		    		break;
		    	
		    	if (j == result.length() - 1) {
		    		result = result.substring(0, i) + replaceText ;
		    	} else if (i == 0 ){
		    		result = replaceText + result.substring(j + 1);
		    	} else {
		    		result = result.substring(0, i) + " " + replaceText + result.substring(j + 1) ;
		    	}
		    }
		return result;
	}
}
