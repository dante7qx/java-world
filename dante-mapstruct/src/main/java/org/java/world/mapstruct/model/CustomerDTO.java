package org.java.world.mapstruct.model;

import java.util.List;

import lombok.Data;

@Data
public class CustomerDTO {
	public Long id;
    public String customerName;
    public List<OrderItemDTO> orders;
}
