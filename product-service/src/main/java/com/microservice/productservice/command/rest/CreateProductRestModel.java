package com.microservice.productservice.command.rest;

import java.math.BigDecimal;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * Bean Validation
 * Hibernate Validator
	http://hibernate.org/validator/

	Hibernate Validator Documentation
	http://hibernate.org/validator/documentation/
 * 
 * @author premc
 *
 */
@Data
public class CreateProductRestModel {

	@NotBlank(message = "Product title is a required field")
	private String title;
	@Min(value = 1, message = "Price cannot be lower than 1")
	private BigDecimal price;
	@Min(value = 1, message = "Quantity cannot be lower than 1")
	@Max(value = 5, message = "Quantity cannot be larger than 5")
	private Integer quantity;
}
