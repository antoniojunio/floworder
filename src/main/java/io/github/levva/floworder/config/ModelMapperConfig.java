package io.github.levva.floworder.config;

import io.github.levva.floworder.dto.ExternalOrderDTO;
import io.github.levva.floworder.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(ExternalOrderDTO.class, Order.class).addMappings(mapper -> {
            mapper.map(ExternalOrderDTO::getDescription, Order::setDescription);
            mapper.map(ExternalOrderDTO::getPrice, Order::setPrice);
        });
        return modelMapper;
    }
}