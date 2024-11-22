package io.github.levva.floworder.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExternalOrderDTO {
    private String externalId;
    private String description;
    private Double price;
    private LocalDateTime orderDate;
}
