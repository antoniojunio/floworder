package io.github.levva.floworder.service;

import io.github.levva.floworder.dto.ExternalOrderDTO;
import io.github.levva.floworder.dto.OrderDTO;
import io.github.levva.floworder.model.Order;
import io.github.levva.floworder.model.OrderStatus;
import io.github.levva.floworder.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    public OrderService(OrderRepository orderRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    public Order createOrder(OrderDTO orderDTO) {
        Order order = modelMapper.map(orderDTO, Order.class);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }

    public List<OrderDTO> processExternalOrders(List<ExternalOrderDTO> externalOrders) {
        Set<String> uniqueDescriptions = new HashSet<>();

        List<Order> savedOrders = externalOrders.stream()
                .filter(order -> uniqueDescriptions.add(order.getDescription()))
                .map(dto -> {
                    Order order = new Order();
                    order.setDescription(dto.getDescription());
                    order.setPrice(dto.getPrice());
                    order.setOrderDate(LocalDateTime.now());
                    order.setStatus(OrderStatus.PENDING);
                    return order;
                })
                .map(orderRepository::save)
                .toList();

        return savedOrders.stream()
                .map(this::convertToOrderDTO)
                .collect(Collectors.toList());
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    private OrderDTO convertToOrderDTO(Order order) {
        return modelMapper.map(order, OrderDTO.class);
    }
}