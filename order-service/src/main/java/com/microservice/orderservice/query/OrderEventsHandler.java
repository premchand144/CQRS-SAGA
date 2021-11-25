package com.microservice.orderservice.query;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.microservice.orderservice.core.data.OrderEntity;
import com.microservice.orderservice.core.data.OrderRepository;
import com.microservice.orderservice.core.events.OrderCreatedEvent;

@Component
@ProcessingGroup("order-group")
public class OrderEventsHandler {

	private OrderRepository orderRepository;

	public OrderEventsHandler(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@EventHandler
	public void on(OrderCreatedEvent orderCreatedEvent) {

		OrderEntity orderEntity = new OrderEntity();

		BeanUtils.copyProperties(orderCreatedEvent, orderEntity);

		try {
			orderRepository.save(orderEntity);
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		}

	}

}
