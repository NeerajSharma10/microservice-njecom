package com.njcoder.orderservice.controller;

import com.njcoder.orderservice.dto.OrderRequest;
import com.njcoder.orderservice.service.InventoryCaller;
import com.njcoder.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private String createOrder(@RequestBody OrderRequest orderRequest) {
        Boolean isProductAvailableInStock = orderService.createOrder(orderRequest);
        if (isProductAvailableInStock){
            return "Order created successfully";
        }
        return "Order not created successfully";
    }
}
