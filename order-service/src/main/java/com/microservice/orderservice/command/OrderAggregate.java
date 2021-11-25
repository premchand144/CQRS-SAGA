package com.microservice.orderservice.command;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.microservice.orderservice.core.events.OrderCreatedEvent;
import com.microservice.orderservice.utility.OrderStatus;

@Aggregate
public class OrderAggregate {

	@AggregateIdentifier
	public String orderId;
	private String userId;
	private String productId;
	private int quantity;
	private String addressId;
	private OrderStatus orderStatus;

	// it is used by the axon framework to create the aggregate object
	public OrderAggregate() {

	}

	// it will be invoked to create aggregate object when CreateOrderCommand is
	// dispatched
	@CommandHandler
	public OrderAggregate(CreateOrderCommand createOrderCommand) {

		if (createOrderCommand.getQuantity() <= 0) {
			throw new IllegalArgumentException("Quantity cannot be less than or equal to zero");
		}

		OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();

		BeanUtils.copyProperties(createOrderCommand, orderCreatedEvent);

		// It apply the orderCreatedEvent and then EventSourcingHandler will be called
		AggregateLifecycle.apply(orderCreatedEvent);

	}

	@EventSourcingHandler
	public void on(OrderCreatedEvent orderCreatedEvent) {
		this.orderId = orderCreatedEvent.getOrderId();
		this.userId = orderCreatedEvent.getUserId();
		this.productId = orderCreatedEvent.getProductId();
		this.quantity = orderCreatedEvent.getQuantity();
		this.addressId = orderCreatedEvent.getAddressId();
		this.orderStatus = orderCreatedEvent.getOrderStatus();
	}

}
