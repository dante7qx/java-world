package org.java.world.mapstruct.mapper;

import org.java.world.mapstruct.model.CarDTO;
import org.java.world.mapstruct.model.CarPO;
import org.java.world.mapstruct.model.ManufacturerVO;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CarMapper {
	
	CarMapper MAPPER = Mappers.getMapper(CarMapper.class);
	
	@Mapping(target = "manufacturer", ignore = true)
	@Mapping(source = "fullSign", target = "sign")
	@Mapping(target = "updateDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
	CarDTO toDTO(CarPO carPO);
	
	/**
	 * 多个类中的值聚合为一个DTO
	 * 
	 * @param carPO
	 * @param manuVO
	 * @return
	 */
	@Mapping(source = "carPO.id", target = "id")
	@Mapping(source = "carPO.name", target = "name")
	@Mapping(source = "carPO.fullSign", target = "sign")
	@Mapping(source = "manuVO.name", target = "manufacturer")
	@Mapping(target = "updateDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
	CarDTO toDTO2(CarPO carPO, ManufacturerVO manuVO);
	
	@InheritConfiguration
	@Mapping(target = "year", ignore = true)
	@Mapping(source = "sign", target = "fullSign")
	@Mapping(target = "updateDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
	CarPO toPO(CarDTO carDTO);
	
	
}
