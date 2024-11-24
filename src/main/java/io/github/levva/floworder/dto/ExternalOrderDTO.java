package io.github.levva.floworder.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExternalOrderDTO {
    private String externalId;

    @NotNull(message = "A descrição não pode ser nula")
    private String description;

    @NotNull(message = "O preço não pode ser nulo")
    @Positive(message = "O preço deve ser maior que zero")
    private Double price;
    private LocalDateTime orderDate;

    public ExternalOrderDTO(String s, double v) {
    }
}
