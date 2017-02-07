package main.com.tuanpa.marketing.sms.callback;

import main.com.tuanpa.marketing.sms.log.SMSLogger;

import org.smslib.callback.IMessageSentCallback;
import org.smslib.callback.events.MessageSentCallbackEvent;
import org.smslib.message.OutboundMessage;

public class MessageSentCallback implements IMessageSentCallback {

	@Override
	public boolean process(MessageSentCallbackEvent event) {
		// TODO Auto-generated method stub
		OutboundMessage obMess = event.getMessage();
		String log = "";
		log += " To: " + obMess.getRecipientAddress().getAddress();
		log += " Via: " + obMess.getGatewayId();
		log += " Content: " + obMess.getPayload().getText();
		log += " - " + obMess.getSentStatus();
		log += " - " + obMess.getSentDate();

		switch (obMess.getSentStatus()) {
		case Failed:
			log += " - " + obMess.getFailureCause();
			SMSLogger.getInstance().log("MessageFail", log);
			String failLog = obMess.getRecipientAddress().getAddress();
			failLog += ";" + failLog;
			SMSLogger.getInstance().log("PhoneFail", failLog);
			break;
		case Queued:
			break;
		case Sent:
			SMSLogger.getInstance().log("MessageSent", log);
			SMSLogger.getInstance().log("PhoneSent",
					obMess.getRecipientAddress().getAddress());
			break;
		case Unsent:
			break;
		default:
			break;
		}

		return true;
	}
}
