package main.com.tuanpa.marketing.sms.msisdn;

public enum Carrier {
	Viettel, Vinaphone, Mobifone, Vietnammobile, Gmobile, Unknown;
	
	public static Carrier getCarrier (String phone){
		String viettel = "-8496-8497-8498-84162-84163-84164-84165-84166-84167-84168-84169-";
		String vina = "-8491-8494-84123-84124-84125-84127-84129-";
		String mobifone = "-8490-8493-84120-84121-84122-84126-84128-";
		String vietnammobile = "-8492-84188-84186-";
		String gmobile = "-8499-84199-";
		phone = formatPhoneToCarrier(phone);
		String preFix = ""; 
		if (phone.substring(0, 3).equals("841")){
			preFix = "-" + phone.substring(0, 5) + "-";
		} else {
			preFix = "-" + phone.substring(0, 4) + "-";
		}
		
		if (viettel.contains(preFix)){
			return Viettel;
		} else if (vina.contains(preFix)){
			return Vinaphone;
		} else if (mobifone.contains(preFix)){
			return Mobifone;
		} else if (vietnammobile.contains(preFix)) {
			return Vietnammobile;
		} else if (gmobile.contains(preFix)) {
			return Gmobile;
		} else 
			return Unknown;
	}
	
	public static boolean checkPhoneNumberFormart(String phoneNumber){
		return phoneNumber != null && phoneNumber.length() >= 9 && phoneNumber.length() <= 15;
	}
		
		public static String formatPhoneToCarrier(String phoneNumber){
			if (checkPhoneNumberFormart(phoneNumber)
					&& phoneNumber.charAt(0) == '0'){
				return phoneNumber.replaceFirst("0", "84");
			} else {
				return phoneNumber;
			}
		}
}