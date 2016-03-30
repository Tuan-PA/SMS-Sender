package main.com.tuanpa.marketing.sms.callback;

import main.com.tuanpa.marketing.sms.log.SMSLogger;

import org.smslib.callback.IGatewayStatusCallback;
import org.smslib.callback.events.GatewayStatusCallbackEvent;

public class GatewayStatusCallback implements IGatewayStatusCallback
{

	@Override
	public boolean process(GatewayStatusCallbackEvent event) {
		// TODO Auto-generated method stub
		String log = event.getGateway().getGatewayId();
		log += " Time: " + event.getDate().toString();
		log += " From : " + event.getOldStatus();
		log += " To : " + event.getNewStatus();
		SMSLogger.getInstance().log("GateWay", log);
		return true;
	}
}
