package com.microservice.orderservice.core.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.microservice.orderservice.utility.OrderStatus;

import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class OrderEntity {
	@Id
	@Column(unique = true)
	public String orderId;
	private String userId;
	private String productId;
	private int quantity;
	private String addressId;
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
}
