package com.microservice.productservice;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventhandling.PropagatingErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;

import com.microservice.productservice.command.interceptor.CreateProductCommandInterceptor;
import com.microservice.productservice.core.errorhandling.ProductsServiceEventsErrorHandler;

@SpringBootApplication
@EnableEurekaClient
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}
	
	/**
	 * Register create product command interceptor to command bus
	 * 
	 * @param context
	 * @param commandBus
	 */
	@Autowired
	public void registerCreateProductCommandInterceptor(ApplicationContext context, CommandBus commandBus) {
		
		commandBus.registerDispatchInterceptor(context.getBean(CreateProductCommandInterceptor.class));
	}
	
	@Autowired
	public void configure(EventProcessingConfigurer config) {

		config.registerListenerInvocationErrorHandler("product-group", 
				conf -> new ProductsServiceEventsErrorHandler());
		
		//default provided by axon framework
//		config.registerListenerInvocationErrorHandler("product-group", 
//				conf -> PropagatingErrorHandler.instance());
	}

}
 