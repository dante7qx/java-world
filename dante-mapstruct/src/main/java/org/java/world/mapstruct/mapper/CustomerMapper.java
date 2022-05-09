package org.java.world.mapstruct.mapper;

import org.java.world.mapstruct.model.Customer;
import org.java.world.mapstruct.model.CustomerDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 子对象映射
 * 
 * @author dante
 *
 */
@Mapper(uses = { OrderItemMapper.class })
public interface CustomerMapper {
	
	CustomerMapper MAPPER = Mappers.getMapper(CustomerMapper.class);
	
	@Mapping(source = "name", target = "customerName")
	@Mapping(source = "orderItems", target = "orders")
	CustomerDTO toDto(Customer customer);
	
	@InheritInverseConfiguration
	Customer toCustomer(CustomerDTO dto);
}
