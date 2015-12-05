package com.szogibalu.jms.service;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSDestinationDefinitions;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TemporaryQueue;
import javax.jms.TextMessage;

@JMSDestinationDefinitions(value = {
		@JMSDestinationDefinition(name = "java:/queue/JmsAppQueue",
				interfaceName = "javax.jms.Queue",
				destinationName = "QueueMDB") })
public class ServiceOne {
	
	private final static Logger LOGGER = Logger.getLogger(ServiceOne.class.toString());

	@Inject
	private JMSContext context;

	@Resource(lookup = "java:/queue/JmsAppQueue")
	private Queue queue;

	public String getMessage(String message) {
		try {
			JMSProducer producer = context.createProducer();			
			
			TextMessage textMessage = context.createTextMessage(message);			
			TemporaryQueue temporaryQueue = context.createTemporaryQueue();
			producer.setJMSReplyTo(temporaryQueue);			
			producer.send(queue, message);
			
			LOGGER.info("Message has been sent to queue: " + textMessage.getText());

			return getResponse(temporaryQueue);
			
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}

	private String getResponse(TemporaryQueue temporaryQueue) throws JMSException {
		JMSConsumer temporaryQueueConsumer = context.createConsumer(temporaryQueue);
		Message receivedMessage = temporaryQueueConsumer.receive();
				
		if (receivedMessage instanceof TextMessage) {
			return ((TextMessage)receivedMessage).getText();
		} else {
			throw new RuntimeException("Message of wrong type: " + receivedMessage.getClass().getName());
		}	
	}

	

}
