package com.microservice.productservice.command;

import java.math.BigDecimal;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.microservice.productservice.core.events.ProductCreatedEvent;
import com.microservice.sagamodule.commands.ReserveProductCommand;
import com.microservice.sagamodule.events.ProductReservedEvent;

@Aggregate
public class ProductAggregate {
	
	@AggregateIdentifier
	private String productId;
	
	private String title;
	private BigDecimal price;
	private Integer quantity;

	//It is used by axon framework to create the aggregate object
	public ProductAggregate() {
		
	}
	
	//It will be invoked to create aggregate object when CreateProductCommand is dispatched
	@CommandHandler
	public ProductAggregate(CreateProductCommand createProductCommand) throws Exception{
		//validate Create Product Command
		if(createProductCommand.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Price cannot be less than or equal to zero.");
		}
		
		if(createProductCommand.getTitle() == null || createProductCommand.getTitle().isBlank()) {
			throw new IllegalArgumentException("Title cannot be empty.");
		}
		
		ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();
		
		BeanUtils.copyProperties(createProductCommand, productCreatedEvent);
		
		AggregateLifecycle.apply(productCreatedEvent);
		
		//if(true) // axon framework wraps this exception into command execution exception (handled in centralized exception handler)
			//throw new Exception("An error took place in ProductAggregate @CommandHandler method");
	}
	
	@CommandHandler
	public void handle(ReserveProductCommand reserveProductCommand) {
		
		System.out.println("Within serverproduct command");
		//No need to write separate query to fetch the details
		//Axon framework automatically fetch the details from event store and re publish the product aggregate 
		if(quantity < reserveProductCommand.getQuantity()) {
			throw new IllegalArgumentException("Insufficient number of items in stock");
		}
		
		ProductReservedEvent productReservedEvent = ProductReservedEvent.builder()
				.orderId(reserveProductCommand.getOrderId())
				.productId(reserveProductCommand.getProductId())
				.quantity(reserveProductCommand.getQuantity())
				.userId(reserveProductCommand.getUserId())
				.build();
		
		AggregateLifecycle.apply(productReservedEvent);
	}
	
	@EventSourcingHandler
	public void on(ProductCreatedEvent productCreatedEvent) {
		this.productId = productCreatedEvent.getProductId();
		this.title = productCreatedEvent.getTitle();
		this.price = productCreatedEvent.getPrice();
		this.quantity = productCreatedEvent.getQuantity();
	}
	
	@EventSourcingHandler
	public void on(ProductReservedEvent productReservedEvent) {
		this.quantity -= productReservedEvent.getQuantity(); // will be updated in event store
	}
	
}
