package com.microservice.orderservice.saga;

import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.microservice.orderservice.core.events.OrderCreatedEvent;
import com.microservice.sagamodule.commands.ReserveProductCommand;
import com.microservice.sagamodule.events.ProductReservedEvent;

@Saga
public class OrderSaga {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderSaga.class);

	@Autowired
	private transient CommandGateway commandGateway;

	@StartSaga
	@SagaEventHandler(associationProperty = "orderId")
	public void handle(OrderCreatedEvent orderCreatedEvent) {

		ReserveProductCommand reserveProductCommand = ReserveProductCommand.builder()
				.orderId(orderCreatedEvent.getOrderId())
				.productId(orderCreatedEvent.getProductId())
				.quantity(orderCreatedEvent.getQuantity())
				.userId(orderCreatedEvent.getUserId())
				.build();
		
		LOGGER.info("OrderCreatedEvent is handled for orderId: " + reserveProductCommand.getOrderId() + "and productId: "
				+ reserveProductCommand.getProductId());

		commandGateway.send(reserveProductCommand, new CommandCallback<ReserveProductCommand, Object>() {

			@Override
			public void onResult(CommandMessage<? extends ReserveProductCommand> commandMessage,
					CommandResultMessage<? extends Object> commandResultMessage) {
				
				if(commandResultMessage.isExceptional()) {
					//Start a compensation transaction
				}

			}

		});

	}
	
	@SagaEventHandler(associationProperty = "orderId")
	public void handle(ProductReservedEvent productReservedEvent) {
		//Process user payment
		LOGGER.info("ProductReservedEvent called for orderId: " + productReservedEvent.getOrderId() + "and productId: "
				+ productReservedEvent.getProductId());
	}

}
