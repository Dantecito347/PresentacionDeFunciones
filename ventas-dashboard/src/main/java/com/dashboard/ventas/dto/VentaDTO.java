package com.dashboard.ventas.dto;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaDTO {
    private Long id;
    private String producto;
    private String categoria;
    private Integer cantidad;
    private Double precioUnitario;
    private LocalDate fecha;
    private Double total;
}