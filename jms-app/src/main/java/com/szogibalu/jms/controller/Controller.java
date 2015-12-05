package com.szogibalu.jms.controller;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.szogibalu.jms.service.ServiceOne;
import com.szogibalu.jms.service.ServiceTwo;

@Path("/")
public class Controller {

	@Inject
	private ServiceOne service1;
	
	@Inject
	private ServiceTwo service2;

	@GET
	@Path("/service1")
	@Produces({ "application/json" })
	public String getServiceOneMessage(@QueryParam("text") String text) {
		return "{\"message\":\"" + service1.getMessage(text) + "\"}";
	}
	
	@GET
	@Path("/service2")
	@Produces({ "application/json" })
	public String getServiceTwoMessage(@QueryParam("text") String text) {
		return "{\"message\":\"" + service2.getMessage(text) + "\"}";
	}


}
