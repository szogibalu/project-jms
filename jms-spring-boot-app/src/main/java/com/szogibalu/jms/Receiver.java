package com.szogibalu.jms;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

	private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

	@JmsListener(destination = "queue/JmsSpringBootQueue")
	public Message<String> receiveMessage(TextMessage message) {
		String text;
		String correlationID;
		try {
			text = message.getText();
			LOGGER.info("Message has been arrived with text: {}", text);
			correlationID = message.getJMSCorrelationID();
			LOGGER.info("Message has been arrived with Correlation Id: {}", correlationID);
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}

		return MessageBuilder.withPayload(text.toUpperCase())
				.setHeader("JMSCorrelationID", correlationID)
				.build();
	}

}
