package main.com.tuanpa.marketing.sms.contact;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import main.com.tuanpa.marketing.sms.log.SMSLogger;
import main.com.tuanpa.marketing.sms.msisdn.Carrier;

public class ContactManager {
	private static String DIRECTORY = "." + File.separator + "contact";
	private static ArrayList<Contact> contacts = new ArrayList<Contact>();
	private static int index = 0;
	private static int noViettel = 0;
	private static int noMobifone = 0;
	private static int noVinaphone = 0;
	private static int noVietnambile = 0;
	private static int noGmobile = 0;
	private static int noUnknow = 0;

	public static void readPhoneNumberFromFile() throws Exception {
		contacts.clear();
		File folder = new File(DIRECTORY);
		// if the directory does not exist, create it
		if (!folder.exists()) {
			folder.mkdir();
		}

		File[] listOfFiles = folder.listFiles();
		for (File file : listOfFiles) {
			if (file.isFile()) {
				String temp = "";
				BufferedReader br = new BufferedReader(new InputStreamReader(
						new FileInputStream(file), StandardCharsets.UTF_8));

				while ((temp = br.readLine()) != null) {
					Contact contact = addToContacts(temp);
					increNoPhoneByCarrier(contact);
					SMSLogger.getInstance().log(
							"ListSend" + contact.getCarrier(),
							contact.getPhone());
				}
				br.close();
				// Save number contact each carrier into log.
				SMSLogger.getInstance().log("Statistic",
						"Viettel : " + noViettel);
				SMSLogger.getInstance().log("Statistic",
						"Mobifone : " + noMobifone);
				SMSLogger.getInstance().log("Statistic",
						"Vinaphone : " + noVinaphone);
				SMSLogger.getInstance().log("Statistic",
						"Vietnamobile : " + noVietnambile);
				SMSLogger.getInstance().log("Statistic",
						"Gmobile : " + noGmobile);
				SMSLogger.getInstance()
						.log("Statistic", "Unknow : " + noUnknow);
			}
		}
		System.out.println("Read Phone Number Complete");
	}

	private static void increNoPhoneByCarrier(Contact contact) {
		Carrier carrier = contact.getCarrier();
		if (carrier == Carrier.Viettel) {
			noViettel++;
		} else if (carrier == Carrier.Mobifone) {
			noMobifone++;
		} else if (carrier == Carrier.Vinaphone) {
			noVinaphone++;
		} else if (carrier == Carrier.Vietnammobile) {
			noVietnambile++;
		} else if (carrier == Carrier.Gmobile) {
			noGmobile++;
		} else
			noUnknow++;
	}

	private static Contact addToContacts(String strContact) {
		String[] info = strContact.split("\\;");
		String name = info[0];
		String phone = info[1];
		Contact contact = new Contact(name, phone);
		contacts.add(contact);
		return contact;
	}

	public static int getNumberContact() {
		return contacts.size();
	}

	public static Contact getContact(int index) {
		return contacts.get(index);
	}

	public static boolean isEnd() {
		return index == contacts.size();
	}

	public static Contact getCurrentContact() {
		return contacts.get(index);
	}

	public static int totalContact() {
		return contacts.size();
	}

	public static void nextContact() {
		if (!isEnd()) {
			index++;
		}
	}

	public static int getCurrentIndex() {
		return index;
	}

	public static void resetIndex() {
		index = 0;
	}
}
