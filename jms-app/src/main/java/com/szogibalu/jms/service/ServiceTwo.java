package com.szogibalu.jms.service;

import java.util.Random;
import java.util.logging.Logger;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class ServiceTwo {

	private static final Logger LOGGER = Logger.getLogger(ServiceTwo.class.getName());

	public String getMessage(String text) {

		ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");

		try (Connection connection = factory.createConnection()) {
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Queue queue = session.createQueue("queue/JmsSpringBootQueue");
			Queue responseQueue = session.createQueue("queue/JmsQueue");

			MessageProducer producer = session.createProducer(queue);
			Message msg = session.createTextMessage(text);
			String randomCorrelationId = randomCorrelationId();
			msg.setJMSCorrelationID(randomCorrelationId);
			msg.setJMSReplyTo(responseQueue);

			producer.send(msg);

			LOGGER.info("Message has been sent with Text:" + text);
			LOGGER.info("Message has been sent with Correlation ID:" + randomCorrelationId);

			MessageConsumer consumer = session.createConsumer(responseQueue,
					"JMSCorrelationID='" + randomCorrelationId + "'");

			Message receivedMessage = consumer.receive();

			LOGGER.info("Response has been arrived with Text:" + text);
			LOGGER.info("Response has been arrived with Correlation ID:" + randomCorrelationId);

			if (receivedMessage instanceof TextMessage) {
				return ((TextMessage) receivedMessage).getText();
			} else {
				throw new RuntimeException("Message of wrong type: " + receivedMessage.getClass().getName());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String randomCorrelationId() {
		return Long.toHexString(new Random(System.currentTimeMillis()).nextLong());
	}

}
