package com.szogibalu.jms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class JmsSpringBootApplication {  

	public static void main(String[] args) {
		SpringApplication.run(JmsSpringBootApplication.class, args);
	}

}
