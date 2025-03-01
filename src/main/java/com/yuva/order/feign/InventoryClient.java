package com.yuva.order.feign;

import com.yuva.order.dto.StockRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-service", url = "http://inventory-service:8084")
public interface InventoryClient {
    @GetMapping("inventory/{productId}")
    public Integer getStock(@PathVariable String productId);

    @PutMapping("inventory/reduceStock")
    public Integer reduceStock(@RequestBody StockRequest stockRequest);
}
