package com.in28minutes.microservicecs.camelmicroservicea.routes.c;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
@Component
public class RestApiConsumerRouter extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		restConfiguration().host("localhost").port(8081);
		
		from("timer:rest-api-consumer?period=10000")
		.setHeader("from", () -> "EUR")
		.setHeader("to", () -> "INR")
		.log("${body}")
		.to("rest:get:/currency-exchange/from/{from}/to/{to}")
		.log("${body}");
		
	}

}
