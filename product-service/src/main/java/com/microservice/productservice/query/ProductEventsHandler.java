package com.microservice.productservice.query;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.microservice.productservice.core.data.ProductEntity;
import com.microservice.productservice.core.data.ProductRepository;
import com.microservice.productservice.core.events.ProductCreatedEvent;
import com.microservice.sagamodule.events.ProductReservedEvent;

/**
 * It is generally called as Projection
 * 
 * @author premc
 *
 */
@Component
@ProcessingGroup(value = "product-group")
public class ProductEventsHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductEventsHandler.class);

	private final ProductRepository productRepository;

	// @Autowired
	public ProductEventsHandler(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	/**
	 * Handles exception using axon @ExceptionHandler
	 * 
	 * @param ex
	 */
	@ExceptionHandler(resultType = IllegalArgumentException.class)
	public void handle(IllegalArgumentException ex) {
		// log error messages
		throw ex;
	}
	
	@ExceptionHandler(resultType = Exception.class)
	public void handle(Exception ex) throws Exception {
		// log error messages
		throw ex;
	}

	@EventHandler
	public void on(ProductCreatedEvent event) throws Exception {
		ProductEntity productEntity = new ProductEntity();
		BeanUtils.copyProperties(event, productEntity);

		try {
			this.productRepository.save(productEntity);
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		}
		
		//if(true) // axon framework wraps this exception into command execution exception (handled in centralized exception handler)
		  //throw new Exception("An error took place in ProductEventsHandler @EventHandler method");
	}
	
	
	@EventHandler
	public void on(ProductReservedEvent productReservedEvent) throws Exception {
		ProductEntity productEntity = this.productRepository.findByProductId(productReservedEvent.getProductId());
		//Update the quantity value
		productEntity.setQuantity(productEntity.getQuantity() - productReservedEvent.getQuantity());

		try {
			this.productRepository.save(productEntity);
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		}
		
		LOGGER.info("ProductReservedEvent called for orderId: " + productReservedEvent.getOrderId() + "and productId: "
				+ productReservedEvent.getProductId());
	}

}
