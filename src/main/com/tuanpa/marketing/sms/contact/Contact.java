package main.com.tuanpa.marketing.sms.contact;

import java.util.ArrayList;

import main.com.tuanpa.marketing.sms.msisdn.Carrier;

public class Contact {
	private ArrayList<String> infos; //name - phone - other field
	
	public Contact() {
		// TODO Auto-generated constructor stub
		infos = new ArrayList<String>();
		for (int i = 0; i <= 5; i++){
			infos.add("");
		}
	}
	
	public Contact(String name, String phone) {
		// TODO Auto-generated constructor stub
		infos = new ArrayList<String>();
		for (int i = 0; i <= 5; i++){
			infos.add("");
		}
		setName(name);
		setPhone(phone);
	}
	
	public void setName(String name){
		infos.set(0, name);	
	}
	
	public String getName(){
		return infos.get(0);
	}
	
	public void setPhone(String phone){
		infos.set(1, phone);
	}
	public Carrier getCarrier(){
		return Carrier.getCarrier(getPhone());
	}
	public String getPhone(){
		return infos.get(1);
	}
	
	public void setOtherFields(int index, String value){
	
		infos.set(index, value);
	}
	
	public String getOrtherField(int index){
		return infos.get(index);
	}
}
