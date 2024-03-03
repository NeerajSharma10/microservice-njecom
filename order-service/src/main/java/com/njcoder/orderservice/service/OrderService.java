package com.njcoder.orderservice.service;

import com.njcoder.orderservice.event.OrderEventPlaced;
import com.njcoder.orderservice.repository.OrderRepository;
import com.njcoder.orderservice.dto.*;
import com.njcoder.orderservice.model.Order;
import com.njcoder.orderservice.model.OrderLineItems;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final InventoryCaller inventoryCaller;
    private final KafkaTemplate<String, OrderEventPlaced> kafkaTemplate;

    public Boolean createOrder(OrderRequest orderRequest) {
        //Before creating the order we need to check whether the skuCode
        //is present in inventory or not
        Boolean productAvailableInStock = inventoryCaller.isProductAvailableInStock(orderRequest);
        if(!productAvailableInStock) return false;

        Order order = Order.builder()
                .orderNumber(String.valueOf(UUID.randomUUID()))
                .orderLineItemsList(methodForConvertingOrderLineItemsDtoToOrderLineItems(orderRequest.getOrderLineItemsDtos()))
                .build();
        orderRepository.save(order);
        kafkaTemplate.send("notification-topic", OrderEventPlaced.builder().orderNumber(order.getOrderNumber()).build());
        return true;
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
