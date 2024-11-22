package io.github.levva.floworder.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderDTO {
    @NotBlank
    private String description;

    @NotNull
    @Positive
    private Double price;

    public OrderDTO() {
    }
}