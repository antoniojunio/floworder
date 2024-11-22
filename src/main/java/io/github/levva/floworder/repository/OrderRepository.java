package io.github.levva.floworder.repository;

import io.github.levva.floworder.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    boolean existsByDescription(String description);
}