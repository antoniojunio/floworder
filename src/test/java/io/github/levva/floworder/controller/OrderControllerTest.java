package io.github.levva.floworder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.levva.floworder.dto.ExternalOrderDTO;
import io.github.levva.floworder.dto.OrderDTO;
import io.github.levva.floworder.model.Order;
import io.github.levva.floworder.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        Mockito.when(orderService.createOrder(Mockito.any(OrderDTO.class)));
        Mockito.when(orderService.createOrder(Mockito.any(OrderDTO.class)))
                .thenReturn(new Order("Pedido Teste", 120.0));

        Mockito.when(orderService.processExternalOrders(Mockito.anyList()))
                .thenReturn(List.of(
                        new OrderDTO("External Order 1", 150.0),
                        new OrderDTO("External Order 2", 200.0)
                ));
    }

    @Test
    void createOrderIntegrationTest() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setDescription("Pedido Teste");
        orderDTO.setPrice(120.0);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Pedido criado com sucesso"))
                .andExpect(jsonPath("$.data.description").value("Pedido Teste"))
                .andExpect(jsonPath("$.data.price").value(120.0));
    }

    @Test
    void processExternalOrdersTest() throws Exception {
        List<ExternalOrderDTO> externalOrders = List.of(
                new ExternalOrderDTO("External Order 1", 150.0),
                new ExternalOrderDTO("External Order 2", 200.0)
        );

        Mockito.when(orderService.processExternalOrders(Mockito.anyList()))
                .thenReturn(List.of(
                        new OrderDTO("External Order 1", 150.0),
                        new OrderDTO("External Order 2", 200.0)
                ));
    }
}