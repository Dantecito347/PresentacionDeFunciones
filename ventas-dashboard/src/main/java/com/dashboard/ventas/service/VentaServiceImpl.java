package com.dashboard.ventas.service;
import com.dashboard.ventas.dto.EstadisticaDTO;
import com.dashboard.ventas.dto.VentaDTO;
import com.dashboard.ventas.entity.Venta;
import com.dashboard.ventas.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VentaServiceImpl extends VentaService {
    private final VentaRepository repository;

    public VentaDTO guardar(VentaDTO dto) {

        Venta venta = Venta.builder()
                .producto(dto.getProducto())
                .categoria(dto.getCategoria())
                .cantidad(dto.getCantidad())
                .precioUnitario(dto.getPrecioUnitario())
                .fecha(dto.getFecha())
                .build();

        Venta guardada = repository.save(venta);

        return VentaDTO.builder()
                .id(guardada.getId())
                .producto(guardada.getProducto())
                .categoria(guardada.getCategoria())
                .cantidad(guardada.getCantidad())
                .precioUnitario(guardada.getPrecioUnitario())
                .fecha(guardada.getFecha())
                .total(guardada.getCantidad() * guardada.getPrecioUnitario())
                .build();
    }

    public List<VentaDTO> listar() {

        return repository.findAll()
                .stream()
                .map(v -> VentaDTO.builder()
                        .id(v.getId())
                        .producto(v.getProducto())
                        .categoria(v.getCategoria())
                        .cantidad(v.getCantidad())
                        .precioUnitario(v.getPrecioUnitario())
                        .fecha(v.getFecha())
                        .total(v.getCantidad() * v.getPrecioUnitario())
                        .build())
                .collect(Collectors.toList());
    }

    public List<EstadisticaDTO> obtenerEstadisticas() {

        Map<String, Double> mapa = new HashMap<>();

        for (Venta venta : repository.findAll()) {

            double total = venta.getCantidad() * venta.getPrecioUnitario();

            mapa.put(
                    venta.getCategoria(),
                    mapa.getOrDefault(venta.getCategoria(), 0.0) + total
            );
        }

        return mapa.entrySet()
                .stream()
                .map(e -> new EstadisticaDTO(
                        e.getKey(),
                        e.getValue()))
                .collect(Collectors.toList());
    }
}
