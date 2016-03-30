package main.com.tuanpa.marketing.sms.callback;

import main.com.tuanpa.marketing.sms.log.SMSLogger;
import org.smslib.callback.IInboundMessageCallback;
import org.smslib.callback.events.InboundMessageCallbackEvent;

public class InboundMessageCallback implements IInboundMessageCallback {

	@Override
	public boolean process(InboundMessageCallbackEvent event) {
		// TODO Auto-generated method stub
		String log = event.getDate().toString();
		log += " To : " + event.getMessage().getRecipientAddress();
		log += " From:  " + event.getMessage().getOriginatorAddress();
		log += " Content:  " + event.getMessage().getPayload().getText();
		SMSLogger.getInstance().log("InBound", log);
		return true;
	}
}
