package com.microservice.productservice.command.interceptor;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;

import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.microservice.productservice.command.CreateProductCommand;
import com.microservice.productservice.core.data.ProductLookupEntity;
import com.microservice.productservice.core.data.ProductLookupRepository;

@Component
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>>{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CreateProductCommandInterceptor.class);
	
	private ProductLookupRepository productLookupRepository;
	
	public CreateProductCommandInterceptor(ProductLookupRepository productLookupRepository) {
		this.productLookupRepository = productLookupRepository;
	}

	@Override
	public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(
			List<? extends CommandMessage<?>> messages) {
		
		return (index, command) -> {
			
			LOGGER.info("Interceptor command: " + command.getPayloadType());
			
			if(CreateProductCommand.class.equals(command.getPayloadType())) {
				
				CreateProductCommand createProductCommand = (CreateProductCommand)command.getPayload();
				//validate Create Product Command
				/*if(createProductCommand.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
					throw new IllegalArgumentException("Price cannot be less than or equal to zero.");
				}
				
				if(createProductCommand.getTitle() == null || createProductCommand.getTitle().isBlank()) {
					throw new IllegalArgumentException("Title cannot be empty.");
				}*/
				
				ProductLookupEntity productLookupEntity = productLookupRepository.findByProductIdOrTitle(createProductCommand.getProductId(), 
						createProductCommand.getTitle());
				
				if(productLookupEntity != null)
					throw new IllegalStateException(String.format("Product with ProductId %s or title %s already exists", 
							createProductCommand.getProductId(), createProductCommand.getTitle()));
			}
			
			return command;
		};
	}

}
