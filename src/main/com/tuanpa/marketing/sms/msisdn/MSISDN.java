package main.com.tuanpa.marketing.sms.msisdn;

import java.util.Random;

public class MSISDN {
	public static String[] Viettel = { "84980200035", "84980200036", "84980200037", "84980200038" };
	public static String[] Mobifone = { "84900000012", "84900000014", "84900000015", "84900000017" };
	public static String[] Vinaphone = { "8491020040", "8491020091", "8491020205", "8491020126" };

	public static String getMsIsDn(Carrier carrier) {
		Random rd = new Random();
		if (carrier == Carrier.Viettel) {
			return Viettel[rd.nextInt(Viettel.length)];
		} else if (carrier == Carrier.Mobifone) {
			return Viettel[rd.nextInt(Mobifone.length)];
		} else if (carrier == Carrier.Vinaphone) {
			return Viettel[rd.nextInt(Vinaphone.length)];
		} else {
			return "";
		}
	}
}
