package io.github.levva.floworder.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    private Double price;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}