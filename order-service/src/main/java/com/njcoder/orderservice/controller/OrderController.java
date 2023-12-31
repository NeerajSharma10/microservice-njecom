package com.njcoder.orderservice.controller;

import com.njcoder.orderservice.dto.OrderRequest;
import com.njcoder.orderservice.service.InventoryCaller;
import com.njcoder.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private InventoryCaller inventoryCaller;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private String createOrder(@RequestBody OrderRequest orderRequest) {

        //Before creating the order we need to check whether the skuCode
        //is present in inventory or not
        Boolean productAvailableInStock = inventoryCaller.isProductAvailableInStock(orderRequest);
        if(productAvailableInStock) {
            orderService.createOrder(orderRequest);
            return "Order created successfully";
        }
        return "Order not created successfully";
    }
}
