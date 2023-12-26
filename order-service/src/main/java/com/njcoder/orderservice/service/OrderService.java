package com.njcoder.orderservice.service;

import com.njcoder.orderservice.repository.OrderRepository;
import com.njcoder.orderservice.dto.*;
import com.njcoder.orderservice.model.Order;
import com.njcoder.orderservice.model.OrderLineItems;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    
    private final OrderRepository orderRepository; 

    public void createOrder(OrderRequest orderRequest) {
        Order order = Order.builder()
                .orderNumber(String.valueOf(UUID.randomUUID()))
                .orderLineItemsList(methodForConvertingOrderLineItemsDtoToOrderLineItems(orderRequest.getOrderLineItemsDtos()))
                .build();
        orderRepository.save(order);
    }

    private List<OrderLineItems> methodForConvertingOrderLineItemsDtoToOrderLineItems(List<OrderLineItemsDto> orderLineItemsDtos) {
        return orderLineItemsDtos
                .stream()
                .map(this::mapToClass)
                .toList();
    }

    private OrderLineItems mapToClass(OrderLineItemsDto orderLineItemsDto) {
        return OrderLineItems.builder()
                .price(orderLineItemsDto.getPrice())
                .quantity(orderLineItemsDto.getQuantity())
                .skuCode(orderLineItemsDto.getSkuCode())
                .build();
    }
}
