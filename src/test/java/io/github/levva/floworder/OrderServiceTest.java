package io.github.levva.floworder;

import io.github.levva.floworder.dto.OrderDTO;
import io.github.levva.floworder.model.Order;
import io.github.levva.floworder.repository.OrderRepository;
import io.github.levva.floworder.service.OrderService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

class OrderServiceTest {

    @Test
    void createOrderTest() {
        OrderRepository repository = Mockito.mock(OrderRepository.class);


        ModelMapper mapper = new ModelMapper();

        OrderService service = new OrderService(repository, mapper);

        OrderDTO dto = new OrderDTO();
        dto.setDescription("Test Order");
        dto.setPrice(100.0);


        Mockito.when(repository.save(any(Order.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            order.setId(1L);
            order.setOrderDate(java.time.LocalDateTime.now());
            return order;
        });

        Order result = service.createOrder(dto);

        assertNotNull(result.getId(), "O ID do pedido não deveria ser nulo");
        assertNotNull(result.getOrderDate(), "A data do pedido não deveria ser nula");
    }
}