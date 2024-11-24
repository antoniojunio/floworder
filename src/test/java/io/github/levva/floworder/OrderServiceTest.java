package io.github.levva.floworder;

import io.github.levva.floworder.dto.ExternalOrderDTO;
import io.github.levva.floworder.dto.OrderDTO;
import io.github.levva.floworder.model.Order;
import io.github.levva.floworder.repository.OrderRepository;
import io.github.levva.floworder.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private OrderService orderService;

    @Test
    void processExternalOrdersTest() {
        List<ExternalOrderDTO> externalOrders = List.of(
                new ExternalOrderDTO("External Order 1", 150.0),
                new ExternalOrderDTO("External Order 2", 200.0)
        );

        Mockito.when(orderRepository.save(Mockito.any(Order.class)))
                .thenAnswer(invocation -> {
                    Order order = invocation.getArgument(0);
                    order.setId(1L);
                    order.setPrice(order.getPrice() != null ? order.getPrice() : 0.0);
                    return order;
                });

        Mockito.when(orderRepository.existsByDescription("Duplicate Order")).thenReturn(false);

        Mockito.when(modelMapper.map(Mockito.any(Order.class), Mockito.eq(OrderDTO.class)))
                .thenAnswer(invocation -> {
                    Order order = invocation.getArgument(0);
                    return new OrderDTO(order.getDescription(), order.getPrice());
                });

        List<OrderDTO> result = orderService.processExternalOrders(externalOrders);

        assertEquals(2, result.size());
        assertEquals("External Order 1", result.get(0).getDescription());
        assertEquals(150.0, result.get(0).getPrice());
        assertEquals("External Order 2", result.get(1).getDescription());
        assertEquals(200.0, result.get(1).getPrice());
    }

    @Test
    void processExternalOrdersTest_withDuplicates() {
        List<ExternalOrderDTO> externalOrders = List.of(
                new ExternalOrderDTO("Duplicate Order", 150.0),
                new ExternalOrderDTO("Duplicate Order", 200.0)
        );

        Mockito.when(orderRepository.existsByDescription("Duplicate Order")).thenReturn(true);

        List<OrderDTO> result = orderService.processExternalOrders(externalOrders);

        assertNotNull(result, "A lista de pedidos não deveria ser nula");
        assertEquals(0, result.size(), "Nenhum pedido deveria ser criado para descrições duplicadas");
    }
}