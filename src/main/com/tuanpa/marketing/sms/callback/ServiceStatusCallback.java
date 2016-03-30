package main.com.tuanpa.marketing.sms.callback;


import main.com.tuanpa.marketing.sms.log.SMSLogger;

import org.smslib.callback.IServiceStatusCallback;
import org.smslib.callback.events.ServiceStatusCallbackEvent;

public class ServiceStatusCallback implements IServiceStatusCallback
{

	@Override
	public boolean process(ServiceStatusCallbackEvent event) {
		// TODO Auto-generated method stub
		String log = event.getDate().toString();
		log += " From : " + event.getOldStatus();
		log += " To : " + event.getNewStatus();
		SMSLogger.getInstance().log("Service", log);
		return true;
	}

}
