package com.szogibalu.jms;

import org.apache.activemq.broker.BrokerService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BrokerApp {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext annotationConfigApplicationContext = null;
		try {
			annotationConfigApplicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
			annotationConfigApplicationContext.start();
		} catch(Throwable t) {
			closeContext(annotationConfigApplicationContext);
		} 
	}

	private static void closeContext(AnnotationConfigApplicationContext annotationConfigApplicationContext) {
		if (annotationConfigApplicationContext != null) {
			annotationConfigApplicationContext.close();
		}
	}

	@Configuration
	public static class AppConfig {
		@Bean(initMethod = "start", destroyMethod = "stop")
		public BrokerService brokerService() throws Exception {
			BrokerService broker = new BrokerService();
			broker.addConnector("tcp://localhost:61616");
			return broker;
		}
	}

}
