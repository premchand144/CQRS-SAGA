package com.microservice.productservice.command;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import com.microservice.productservice.core.data.ProductLookupEntity;
import com.microservice.productservice.core.data.ProductLookupRepository;
import com.microservice.productservice.core.events.ProductCreatedEvent;

@Component
@ProcessingGroup(value = "product-group")
public class ProductLookupEventsHandler {

	private ProductLookupRepository productLookupRepository;

	public ProductLookupEventsHandler(ProductLookupRepository productLookupRepository) {
		this.productLookupRepository = productLookupRepository;
	}

	@EventHandler
	public void on(ProductCreatedEvent event) {

		ProductLookupEntity productLookupEntity = new ProductLookupEntity(event.getProductId(), event.getTitle());
		this.productLookupRepository.save(productLookupEntity);
	}

}
