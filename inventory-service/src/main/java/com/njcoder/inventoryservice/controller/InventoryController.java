package com.njcoder.inventoryservice.controller;

import com.njcoder.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{sku-code}")
    @ResponseStatus(HttpStatus.OK)
    public String iSInStockUtility(@PathVariable("sku-code") String skuCode) {
        Boolean isInStock = inventoryService.isInStockValue(skuCode);
        return isInStock ? "Product is available in stock" : "Product is not available in stock";
    }
}
