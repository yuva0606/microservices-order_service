package com.yuva.order.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;// Will match the user's ID in User Service

    @Column(nullable = false,unique = false)
    private String productId; // Product ID from Product Service

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private int quantity; // Quantity ordered by user

    @Column(nullable = false)
    private String status; // e.g., "PENDING", "CONFIRMED", "SHIPPED"

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
