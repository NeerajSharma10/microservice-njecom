package com.njcoder.orderservice.controller;

import com.njcoder.orderservice.dto.OrderRequest;
import com.njcoder.orderservice.service.InventoryCaller;
import com.njcoder.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private String createOrder(@RequestBody OrderRequest orderRequest) {
        log.info("****************Welcome to OrderService**********************");
        Boolean isProductAvailableInStock = orderService.createOrder(orderRequest);
        if (isProductAvailableInStock){
            log.info("****************End to OrderService**********************");
            return "Order created successfully";
        }
        return "Order not created successfully";
    }
}
