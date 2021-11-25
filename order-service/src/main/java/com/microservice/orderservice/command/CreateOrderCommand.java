package com.microservice.orderservice.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.microservice.orderservice.utility.OrderStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateOrderCommand {
	
	//Command identifier
	@TargetAggregateIdentifier
	public final String orderId;
	private final String userId;
	private final String productId;
	private final int quantity;
	private final String addressId;
	private final OrderStatus orderStatus;
}
