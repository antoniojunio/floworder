package io.github.levva.floworder.controller;

import io.github.levva.floworder.dto.ExternalOrderDTO;
import io.github.levva.floworder.dto.OrderDTO;
import io.github.levva.floworder.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/external-orders")
public class ExternalOrderController {

    private final OrderService orderService;

    public ExternalOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/external")
    public ResponseEntity<List<OrderDTO>> processExternalOrders(@RequestBody List<ExternalOrderDTO> externalOrders) {
        List<OrderDTO> orders = orderService.processExternalOrders(externalOrders);
        return ResponseEntity.ok(orders);
    }
}