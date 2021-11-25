package com.microservice.productservice.command;

import java.math.BigDecimal;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateProductCommand {
	
	//Command identifier
	@TargetAggregateIdentifier
	private String productId;
	
	private final String title;
	private final BigDecimal price;
	private final Integer quantity;

}
