package io.github.levva.floworder.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderDTO {
    @NotBlank
    @NotNull(message = "A descrição não pode ser nula")
    private String description;

    @NotNull(message = "O preço não pode ser nulo")
    @Positive(message = "O preço deve ser maior que zero")
    private Double price;

    public OrderDTO() {
    }

    public OrderDTO(String testOrder, double v) {
    }
}