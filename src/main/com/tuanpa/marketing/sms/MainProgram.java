package main.com.tuanpa.marketing.sms;

import org.smslib.Service;
import main.com.tuanpa.marketing.sms.log.SMSLogger;
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
				SMSMarketing.getInstance(carrier).sendMessage(1.2f);

				while (true) {
					if (Service.getInstance().getAllQueueLoad() <= 0) break;
					Thread.sleep(6000);
				}
				
				Thread.sleep(60000);
				SMSLogger.getInstance().log("Statistic",
						SMSMarketing.getInstance(carrier)
						.getStatistic());
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
