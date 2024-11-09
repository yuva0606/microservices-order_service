package com.yuva.order.service;

import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuva.order.dto.OrderRequest;
import com.yuva.order.dto.OrderResponse;
import com.yuva.order.dto.ProductDto;
import com.yuva.order.dto.StockRequest;
import com.yuva.order.event.ReduceStockEvent;
import com.yuva.order.feign.InventoryClient;
import com.yuva.order.feign.ProductClient;
import com.yuva.order.model.Order;
import com.yuva.order.model.OrderStatus;
import com.yuva.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final InventoryClient inventoryClient;
    private final ServiceBusSenderClient serviceBusSenderClient;
    private final ObjectMapper objectMapper;

    public ResponseEntity<?> createOrder(String productId, int quantity, String username) throws JsonProcessingException {
        if (quantity <= 0) {
            return ResponseEntity.badRequest().body("Quantity must be greater than 0");
        }
        Order order = new Order();
        order.setUsername(username);
        order.setProductId(productId);
        ProductDto productDto = productClient.getProduct(productId);
        if (productDto != null) {
            order.setProductName(productDto.name());
            Integer quantityInStock = inventoryClient.getStock(productId);
            if (quantityInStock >= quantity) {
                order.setQuantity(quantity);
                order.setStatus(OrderStatus.CREATED.name());
                String message = objectMapper.writeValueAsString(new StockRequest(productId, quantity));
                serviceBusSenderClient.sendMessage(new ServiceBusMessage(message));
                System.out.println("ReduceStockEvent sent: " + message);
                Order savedOrder = orderRepository.save(order);
//                kafkaTemplate.send("reduce-quantity", new ReduceStockEvent(orderRequest.productId(), orderRequest.quantity()));
                return ResponseEntity.status(HttpStatus.OK).body(new OrderResponse(savedOrder.getId(), savedOrder.getUsername(), savedOrder.getProductId(),
                        savedOrder.getProductName(), savedOrder.getQuantity(), savedOrder.getStatus()));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient stock");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        Order updatedOrder = orderRepository.save(order);
        return ResponseEntity.ok(new OrderResponse(updatedOrder.getId(), updatedOrder.getUsername(), updatedOrder.getProductId(),
                updatedOrder.getProductName(), updatedOrder.getQuantity(), updatedOrder.getStatus()));
    }
}
