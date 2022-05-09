package org.java.world.mapstruct;

import java.time.Instant;
import java.util.Date;

import org.java.world.mapstruct.mapper.CarMapper;
import org.java.world.mapstruct.mapper.CustomerMapper;
import org.java.world.mapstruct.model.CarDTO;
import org.java.world.mapstruct.model.CarPO;
import org.java.world.mapstruct.model.Customer;
import org.java.world.mapstruct.model.CustomerDTO;
import org.java.world.mapstruct.model.ManufacturerVO;
import org.java.world.mapstruct.model.OrderItem;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MapstructTests {

	@Test
	public void testBasicConvert() {
		CarPO carPO = new CarPO();
		carPO.setId(11L).setName("奔驰").setFullSign("三角标志").setYear(150).setUpdateDate(Date.from(Instant.now()));

		CarDTO dto = CarMapper.MAPPER.toDTO(carPO);
		log.info("CarDTO: {}", dto);
		Assert.assertEquals(carPO.getName(), dto.getName());

		CarPO po = CarMapper.MAPPER.toPO(dto);
		log.info("CarPO: {}", po);
		Assert.assertNotNull(po.getUpdateDate());
		
	}

	@Test
	public void testAggregateConvert() {
		CarPO carPO = new CarPO();
		carPO.setId(11L).setName("奔驰").setFullSign("三角标志").setYear(150).setUpdateDate(Date.from(Instant.now()));

		ManufacturerVO manuVO = new ManufacturerVO();
		manuVO.setId(200L);
		manuVO.setName("德国大众");

		CarDTO dto = CarMapper.MAPPER.toDTO2(carPO, manuVO);
		log.info("CarDTO: {}", dto);
		Assert.assertEquals(dto.getManufacturer(), manuVO.getName());
	}

	@Test
	public void testSubObjectConvert() {
		OrderItem orderItem1 = new OrderItem("加多宝", 20L);
		OrderItem orderItem2 = new OrderItem("红牛", 10L);
		OrderItem orderItem3 = new OrderItem("健力宝", 50L);

		Customer customer = new Customer();
		customer.setId(1000L);
		customer.setName("王先生");
		customer.addOrderItem(orderItem1);
		customer.addOrderItem(orderItem2);
		customer.addOrderItem(orderItem3);

		CustomerDTO dto = CustomerMapper.MAPPER.toDto(customer);
		log.info("CustomerDTO: {}", dto);
		Assert.assertEquals(dto.getOrders().size(), 3);

		Customer cust = CustomerMapper.MAPPER.toCustomer(dto);
		log.info("Customer: {}", cust);
		Assert.assertEquals(cust.getOrderItems().size(), 3);

	}

}
