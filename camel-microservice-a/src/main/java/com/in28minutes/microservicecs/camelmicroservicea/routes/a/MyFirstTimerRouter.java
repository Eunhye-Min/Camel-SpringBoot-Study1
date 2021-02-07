package com.in28minutes.microservicecs.camelmicroservicea.routes.a;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class MyFirstTimerRouter extends RouteBuilder{


	@Autowired
	private GetCurrentTimeBean getCurrentTimeBean;
	
	@Autowired
	private SimpleLoggingProcessingComponent loggingComponent;
	
	@Override
	public void configure() throws Exception {
		// timer
		// transformation
		// database
		from("timer:first-timer")	//null
		.log("${body}")
		.transform().constant("My Constant Message")
		.log("${body}")
		//.transform().constant("Time now is " + LocalDateTime.now())
		//.bean("getCurrentTimeBean")
		//.bean(getCurrentTimeBean, "getCurrentTime")
		//Processing - message의 body에 change X
		//Transformation
		
		.bean(getCurrentTimeBean)	//이것도 transformation
		.log("${body}")

		.bean(loggingComponent)
		.log("${body}")
		.process(new SimpleLoggingProcessor())
		.to("log:first-timer");		//database
		
	}

}

@Component
class GetCurrentTimeBean{
	public String getCurrentTime() {
		return "Time now is " + LocalDateTime.now();	//body changed
	}
}

@Component
class SimpleLoggingProcessingComponent{
	
	private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);

	public void process(String message) {
		logger.info("SimpleLoggingProcessingComponent {}", message);
	}
}

class SimpleLoggingProcessor implements Processor {
	private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);
	
	@Override
	public void process(Exchange exchange) throws Exception {
		logger.info("SimpleLoggingProcessor {}", exchange);

	}

}
			