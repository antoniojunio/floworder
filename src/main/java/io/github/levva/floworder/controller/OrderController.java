package io.github.levva.floworder.controller;

import io.github.levva.floworder.dto.ExternalOrderDTO;
import io.github.levva.floworder.dto.OrderDTO;
import io.github.levva.floworder.model.Order;
import io.github.levva.floworder.service.OrderService;
import io.github.levva.floworder.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrderDTO>> createOrder(@RequestBody OrderDTO orderDto) {
        Order createdOrder = orderService.createOrder(orderDto);
        ApiResponse<OrderDTO> response = ApiResponse.success("Pedido criado com sucesso", new OrderDTO(createdOrder.getDescription(), createdOrder.getPrice()));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/external-orders/external")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> processExternalOrders(@RequestBody List<ExternalOrderDTO> externalOrders) {
        List<OrderDTO> processedOrders = orderService.processExternalOrders(externalOrders);
        return ResponseEntity.ok(ApiResponse.success("Pedidos processados com sucesso", (OrderDTO) processedOrders));
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
}