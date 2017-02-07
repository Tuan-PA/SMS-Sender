package main.com.tuanpa.marketing.sms;

import java.util.HashMap;

import org.smslib.Service;
import org.smslib.core.Settings;
import org.smslib.gateway.AbstractGateway;
import org.smslib.gateway.modem.Modem;
import org.smslib.gateway.modem.driver.serial.CommPortIdentifier;
import org.smslib.message.MsIsdn;
import org.smslib.message.OutboundMessage;
import org.smslib.message.AbstractMessage.Encoding;

import main.com.tuanpa.marketing.sms.callback.DeliveryReportCallback;
import main.com.tuanpa.marketing.sms.callback.InboundMessageCallback;
import main.com.tuanpa.marketing.sms.callback.MessageSentCallback;
import main.com.tuanpa.marketing.sms.contact.Contact;
import main.com.tuanpa.marketing.sms.contact.ContactManager;
import main.com.tuanpa.marketing.sms.log.SMSLogger;
import main.com.tuanpa.marketing.sms.message.MessageManager;
import main.com.tuanpa.marketing.sms.msisdn.Carrier;
import main.com.tuanpa.marketing.sms.msisdn.MSISDN;
import main.com.tuanpa.marketing.sms.plugandplay.ComPortDetect;

/**
 * Main File: Process Logic, Sequence....
 * 
 * @author AnhTuan-PC
 *
 */
public class SMSMarketing {
	private static SMSMarketing instance;

	public static SMSMarketing getInstance(Carrier carrier) {
		if (instance == null) {
			instance = new SMSMarketing(carrier);
		}
		return instance;
	}

	private final Carrier carrier;

	public SMSMarketing(Carrier carrier) {
		this.carrier = carrier;
	}

	public boolean init() throws Exception {
		boolean isInited = readContactFromFile() && readMessageFromFile()
				&& autoDetectDevice();
		Service.getInstance().start();
		return isInited;
	}

	public boolean autoDetectDevice() {
		ComPortDetect.autoDetectDevices();
		return ComPortDetect.lPort.size() > 0;
	}

	public boolean readMessageFromFile() throws Exception {
		MessageManager.readMessageFromFile();
		return MessageManager.getMessage() != null;
	}

	public boolean readContactFromFile() throws Exception {
		ContactManager.readPhoneNumberFromFile();
		return ContactManager.getNumberContact() > 0;
	}

	public void addCallback() {
		Service.getInstance().setMessageSentCallback(new MessageSentCallback());
		Service.getInstance().setInboundMessageCallback(
				new InboundMessageCallback());
		Service.getInstance().setDeliveryReportCallback(
				new DeliveryReportCallback());
	}

	public void registerGateway() throws InterruptedException {
		MsIsdn msisdn = new MsIsdn(MSISDN.getMsIsDn(carrier));
		HashMap<CommPortIdentifier, Integer> lPort = ComPortDetect.lPort;
		for (CommPortIdentifier cpi : lPort.keySet()) {
			Modem modem = new Modem(cpi.getName(), cpi.getName(),
					lPort.get(cpi), "0000", "0000", msisdn, "");
			Service.getInstance().registerGateway(modem);
			Thread.sleep(5000);
		}

		SMSLogger.getInstance().log(
				"DEVICE",
				"No Device Can Send: "
						+ Service.getInstance().getGatewayIDs().size());
	}

	public void unregisterGateway() throws InterruptedException {
		for (String gatewayId : Service.getInstance().getGatewayIDs()) {
			AbstractGateway gate = Service.getInstance().getGatewayById(
					gatewayId);
			Service.getInstance().unregisterGateway(gate);
			Thread.sleep(2000);
		}
	}

	/**
	 * 
	 * @param spm
	 *            Number sms will send per minute
	 * @throws Exception
	 */
	public void sendMessage(float spm) throws Exception {
		int sleepTime = (int) (60000 / (Service.getInstance().getGatewayIDs()
				.size() * spm));
		Settings.gatewayDispatcherYield = sleepTime;
		Settings.loadSettings();
		while (!ContactManager.isEnd()) {
			Contact contact = ContactManager.getCurrentContact();
			if (contact.getCarrier() == carrier) {
				String msg = MessageManager.getMessage();
				OutboundMessage obMsg = new OutboundMessage(contact.getPhone(),
						msg);
				obMsg.setEncoding(Encoding.EncUcs2);
				Service.getInstance().queue(obMsg);

				Thread.sleep(1000);
				ContactManager.nextContact();
			} else {
				ContactManager.nextContact();
			}
		}
	}

	public void terminate() {
		Service.getInstance().stop();
		Service.getInstance().terminate();
	}

	public String getStatistic() {
		String statistic = "\n";
		statistic += "\n Time Start: "
				+ Service.getInstance().getStatistics().getStartTime()
						.toString();
		statistic += "\n Total Failed: "
				+ Service.getInstance().getStatistics().getTotalFailed();
		statistic += "\n Total Received: "
				+ Service.getInstance().getStatistics().getTotalReceived();
		statistic += "\n Total Sent: "
				+ Service.getInstance().getStatistics().getTotalSent();
		return statistic;
	}
}
