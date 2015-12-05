package com.szogibalu.jms.mdb;

import java.util.function.Supplier;
import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(name = "QueueMDB", activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/JmsAppQueue"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
public class QueueMDB implements MessageListener {

	@Inject
	private JMSContext context;

	private final static Logger LOGGER = Logger.getLogger(QueueMDB.class.toString());

	public void onMessage(Message receivedMessage) {
		try {
			if (receivedMessage instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) receivedMessage;
				LOGGER.info("Received Message from queue: " + textMessage.getText());
				String text = textMessage.getText();
				
				TextMessage replyMessage = getReplyMessage(() -> text.toUpperCase());
				
				replyMessage.setJMSCorrelationID(textMessage.getJMSCorrelationID());

				reply(replyMessage, textMessage.getJMSReplyTo());
			} else {
				throw new RuntimeException("Message of wrong type: " + receivedMessage.getClass().getName());
			}
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}

	private TextMessage getReplyMessage(Supplier<String> supplier) {
		return context.createTextMessage(supplier.get());
	}

	private void reply(TextMessage replyMessage, Destination replyTo) {
		context.createProducer().send(replyTo, replyMessage);
	}

}
