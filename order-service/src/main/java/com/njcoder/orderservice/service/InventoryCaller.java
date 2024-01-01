package com.njcoder.orderservice.service;

import com.njcoder.orderservice.dto.OrderLineItemsDto;
import com.njcoder.orderservice.dto.OrderRequest;
import com.njcoder.orderservice.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InventoryCaller {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Boolean isProductAvailableInStock(OrderRequest orderRequest) {
        List<String> skuCodes = orderRequest.getOrderLineItemsDtos()
                .stream()
                .map(OrderLineItemsDto::getSkuCode)
                .collect(Collectors.toList());

        return webClientBuilder.build().get()
                .uri("/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    }

}
