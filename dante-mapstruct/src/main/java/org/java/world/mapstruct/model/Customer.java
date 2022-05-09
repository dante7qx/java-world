package org.java.world.mapstruct.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Customer {
	private Long id;
	private String name;
	private List<OrderItem> orderItems;
	
	public void addOrderItem(OrderItem orderItem) {
		if(orderItems == null) {
			this.orderItems = new ArrayList<>();
		}
		this.orderItems.add(orderItem);
	}
	
}
