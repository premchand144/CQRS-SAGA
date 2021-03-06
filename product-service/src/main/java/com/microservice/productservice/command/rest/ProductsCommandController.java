package com.microservice.productservice.command.rest;

import java.util.UUID;

import javax.validation.Valid;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.productservice.command.CreateProductCommand;

@RestController
@RequestMapping("/products")
public class ProductsCommandController {
	
	@Autowired //Property based injection
	private Environment env;
	private final CommandGateway commandGateway;
	
	@Autowired //Constructor based injection
	public ProductsCommandController(CommandGateway commandGateway) {
		this.commandGateway = commandGateway;
	}

	/**
	 * Valid is used for bean validaion
	 * 
	 * @param product
	 * @return
	 */
	@PostMapping
	public String createProduct(@Valid @RequestBody CreateProductRestModel product) {
		
		CreateProductCommand createProductCommand = CreateProductCommand.builder()
				.price(product.getPrice())
				.title(product.getTitle())
				.quantity(product.getQuantity())
				.productId(UUID.randomUUID().toString())
				.build();
		
		String returnValue;
		
		returnValue = commandGateway.sendAndWait(createProductCommand);
		
		/*try {
			//Send the command to command bus
			returnValue = commandGateway.sendAndWait(createProductCommand);
		}catch(Exception ex) {
			returnValue = ex.getLocalizedMessage();
		}*/
		
		return returnValue;
	}

//	@GetMapping
//	public String getProduct() {
//		return "http GET handled " + env.getProperty("local.server.port");
//	}
//
//	@PutMapping
//	public String updateProduct() {
//		return "http PUT handled";
//	}
//
//	@DeleteMapping
//	public String deleteProduct() {
//		return "http DELETE handled";
//	}

}
