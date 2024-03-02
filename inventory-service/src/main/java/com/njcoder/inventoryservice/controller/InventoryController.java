package com.njcoder.inventoryservice.controller;

import com.njcoder.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Boolean iSInStockUtility(@RequestParam List<String> skuCode) {
        return !inventoryService.isInStockValue(skuCode);
    }

}
