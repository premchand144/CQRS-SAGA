package com.microservice.productservice.query;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.microservice.productservice.core.data.ProductEntity;
import com.microservice.productservice.core.data.ProductRepository;
import com.microservice.productservice.query.rest.ProductRestModel;

@Component
public class ProductsQueryHandler {

	private final ProductRepository productRepository;
	
	public ProductsQueryHandler(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@QueryHandler
	public List<ProductRestModel> findProducts(FindProductsQuery query) {
		List<ProductRestModel> productRestModels = new ArrayList<>();
		
		List<ProductEntity> storedProducts = this.productRepository.findAll();
		
		storedProducts.forEach(products -> {
			ProductRestModel productRestModel = new ProductRestModel();
			BeanUtils.copyProperties(products, productRestModel);
			
			productRestModels.add(productRestModel);
			
		});
		
		return productRestModels;
	}
}
