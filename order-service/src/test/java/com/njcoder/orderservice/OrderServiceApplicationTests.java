package com.njcoder.orderservice;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.njcoder.orderservice.dto.OrderLineItemsDto;
import com.njcoder.orderservice.dto.OrderRequest;
import com.njcoder.orderservice.model.Order;
import com.njcoder.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class OrderServiceApplicationTests {

	// Define a MySQL container
	@Container
	 static MySQLContainer mySQLContainer = new MySQLContainer("mysql:latest");

	 @DynamicPropertySource
	 static void configureProperty(DynamicPropertyRegistry dynamicPropertyRegistry) {
		 dynamicPropertyRegistry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
		 dynamicPropertyRegistry.add("spring.datasource.username", mySQLContainer::getUsername);
		 dynamicPropertyRegistry.add("spring.datasource.password", mySQLContainer::getPassword);
	 }

	 @Autowired
	 private OrderRepository orderRepository;


	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testCreateResource() throws Exception {
		OrderRequest orderRequest = new OrderRequest();
		List<OrderLineItemsDto> orderLineItemsDtoList = new ArrayList<>();
		orderLineItemsDtoList.add(OrderLineItemsDto.builder()
				.price(new BigDecimal(143.00))
				.skuCode("Iphone moto 15g")
				.quantity(1)
				.build()
		);
		orderRequest.setOrderLineItemsDtos(orderLineItemsDtoList);
		// Perform a POST request to your REST API endpoint

		ObjectMapper objectMapper = new ObjectMapper();
		String requestJson = objectMapper.writeValueAsString(orderRequest);
		mockMvc.perform(post("/api/order")
						.contentType("application/json")
						.content(requestJson))
				.andExpect(status().isCreated());
		List<Order> orders = orderRepository.findAll();
		List<Order> orderLineItemsList = orderRepository.findAll();

		Assertions.assertEquals(1, orders.size());
		Assertions.assertEquals(1, orderLineItemsList.size());
		// Add additional assertions or verifications based on your use case
	}
}

