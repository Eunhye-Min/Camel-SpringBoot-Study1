package com.in28minutes.microservicecs.camelmicroservicea.routes.b;

import java.util.Map;

import org.apache.camel.Body;
import org.apache.camel.ExchangeProperties;
import org.apache.camel.Headers;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//@Component
public class MyFileRouter extends RouteBuilder {
	
	@Autowired
	private DeciderBean deciderBean;
	
	@Override
	public void configure() throws Exception {
		//Pipeline
		
		from("file:files/input")
		//.pipeline()
		.routeId("Files-Input-Route")	//Debugging할때 유용
		.transform().body(String.class)
		.choice()	//Content Based Routig
			.when(simple("${file:ext} ends with 'xml'"))
				.log("XML FILE")
			//.when(simple("${body} contains 'USD'"))
			.when(method(deciderBean))
				.log("NOT and XML FILE BUT contains USD")
			.otherwise()
				.log("Not an XML FILE")
		.end()
		.log("${messageHistory} ${headers.CamelFileAbsolute}")
		.to("file:files/output");
		
		
		
		
	}

}

@Component
class DeciderBean{
	Logger logger = LoggerFactory.getLogger(DeciderBean.class);
	
	public boolean isThisConditionMet(@Body String body,
			@Headers Map<String, String> headers,
			@ExchangeProperties Map<String, String> exchangeProperties){
		logger.info("DeciderBean{} {} {}", body, headers, exchangeProperties);
		return true;
	}
}
