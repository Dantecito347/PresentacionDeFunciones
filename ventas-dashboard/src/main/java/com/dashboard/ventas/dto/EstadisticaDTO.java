package com.dashboard.ventas.dto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadisticaDTO {
    private String categoria;
    private Double totalVentas;
}