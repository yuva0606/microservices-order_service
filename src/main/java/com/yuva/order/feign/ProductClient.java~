package com.yuva.order.feign;

import com.yuva.order.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service",url = "http://localhost:8082")
public interface ProductClient {
    @GetMapping("products/get/id")
    public ProductDto getProduct(@RequestParam String id);

}
