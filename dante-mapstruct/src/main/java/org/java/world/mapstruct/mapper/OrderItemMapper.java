package org.java.world.mapstruct.mapper;

import org.java.world.mapstruct.model.OrderItem;
import org.java.world.mapstruct.model.OrderItemDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderItemMapper {
	OrderItemMapper MAPPER = Mappers.getMapper(OrderItemMapper.class);
	
	OrderItemDTO toDTO(OrderItem orderItem);
	
	@InheritInverseConfiguration
	OrderItem toOrderItem(OrderItemDTO dto);
}
