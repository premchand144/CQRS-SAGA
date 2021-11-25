package com.microservice.productservice.core.errorhandling;

import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.EventMessageHandler;
import org.axonframework.eventhandling.ListenerInvocationErrorHandler;

/**
 * It is used to handle exception from event handler and rolls back the changes
 * 
 * @author premc
 *
 */
public class ProductsServiceEventsErrorHandler implements ListenerInvocationErrorHandler{

	@Override
	public void onError(Exception exception, EventMessage<?> event, EventMessageHandler eventHandler) throws Exception {

		throw exception;
	}

}
