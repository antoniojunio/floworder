package io.github.levva.floworder.controller;

import io.github.levva.floworder.dto.ExternalOrderDTO;
import io.github.levva.floworder.model.Order;
import io.github.levva.floworder.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class ExternalOrderController {

    private final OrderService orderService;

    public ExternalOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/external")
    public ResponseEntity<List<Order>> processExternalOrders(@RequestBody List<ExternalOrderDTO> externalOrders) {
        List<Order> orders = orderService.processExternalOrders(externalOrders);
        return ResponseEntity.ok(orders);
    }
}