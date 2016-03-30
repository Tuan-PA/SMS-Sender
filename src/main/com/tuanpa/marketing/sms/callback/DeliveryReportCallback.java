package main.com.tuanpa.marketing.sms.callback;

import main.com.tuanpa.marketing.sms.log.SMSLogger;

import org.smslib.callback.IDeliveryReportCallback;
import org.smslib.callback.events.DeliveryReportCallbackEvent;

public class DeliveryReportCallback implements IDeliveryReportCallback{

	@Override
	public boolean process(DeliveryReportCallbackEvent event) {
		// TODO Auto-generated method stub
		String log = event.getDate().toString();
		log += " Date: " + event.getMessage().getDeliveryStatus();
		log += " Recipient: " + event.getMessage().getRecipientAddress();
		log += " Message: " + event.getMessage().getSentDate().toString();
		log += " Content: " + event.getMessage().getPayload().getText();
		
		SMSLogger.getInstance().log("Delivery", log);
		return true;
	}

}
