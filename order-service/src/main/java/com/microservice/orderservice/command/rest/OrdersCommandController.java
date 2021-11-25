package com.microservice.orderservice.command.rest;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.orderservice.command.CreateOrderCommand;
import com.microservice.orderservice.utility.OrderStatus;

@RestController
@RequestMapping("/orders")
public class OrdersCommandController {
	
	private CommandGateway commandGateway;
	
	public OrdersCommandController(CommandGateway commandGateway) {
		this.commandGateway = commandGateway;
	}
	
	
	@PostMapping
	public String saveOrder(@RequestBody CreateOrderRestModel createOrderRestModel) {
		
		CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
				.orderId(UUID.randomUUID().toString())
				.userId("27b95829-4f3f-4ddf-8983-151ba010e35b")
				.productId(createOrderRestModel.getProductId())
				.quantity(createOrderRestModel.getQuantity())
				.addressId(createOrderRestModel.getAddressId())
				.orderStatus(OrderStatus.CREATED).build();
		
		String returnValue;
		
		returnValue = commandGateway.sendAndWait(createOrderCommand);
		
		return returnValue;
		
	}

}
