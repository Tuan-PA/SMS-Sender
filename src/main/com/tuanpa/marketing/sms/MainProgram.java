package main.com.tuanpa.marketing.sms;

import javax.mail.Message;
import javax.swing.text.html.parser.ContentModel;

import main.com.tuanpa.marketing.sms.contact.ContactManager;
import main.com.tuanpa.marketing.sms.log.SMSLogger;
import main.com.tuanpa.marketing.sms.message.MessageManager;
import main.com.tuanpa.marketing.sms.msisdn.Carrier;

/**
 * 
 * @author AnhTuan-PC Application main file Architecture: Server <-> Client <->
 *         GSM Modem via Com - Port.
 */
public class MainProgram {
	public static Carrier carrier = Carrier.Viettel;

	public static void main(String[] args) {
		// Connect and send
		try {
			boolean isInited = SMSMarketing.getInstance(carrier).init();
			if (isInited) {
				SMSMarketing.getInstance(carrier).addCallback();
				SMSMarketing.getInstance(carrier).registerGateway();
				SMSMarketing.getInstance(carrier).sendMessage(1);

				int count = 0;
				while (count <= 10) {
					if (count  == 5)
						SMSLogger.getInstance().log("Statistic",
								SMSMarketing.getInstance(carrier)
										.getStatistic());
					count++;
					Thread.sleep(60000);
				}

			} else {
				System.out.println("Check input data");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				SMSMarketing.getInstance(carrier).unregisterGateway();
				SMSMarketing.getInstance(carrier).terminate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
