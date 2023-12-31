package com.njcoder.inventoryservice.service;

import com.njcoder.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public Boolean isInStockValue(List<String> params) {
        return params.stream().allMatch(skuCode -> inventoryRepository.findBySkuCode(skuCode).isEmpty());
    }
}
