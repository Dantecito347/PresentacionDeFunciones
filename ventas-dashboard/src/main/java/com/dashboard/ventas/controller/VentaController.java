package com.dashboard.ventas.controller;

import com.dashboard.ventas.dto.EstadisticaDTO;
import com.dashboard.ventas.dto.VentaDTO;
import com.dashboard.ventas.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ventas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VentaController {

    private final VentaService service;

    @PostMapping
    public VentaDTO guardar(@RequestBody VentaDTO ventaDTO) {
        return service.guardar(ventaDTO);
    }

    @GetMapping
    public List<VentaDTO> listar() {
        return service.listar();
    }

    @GetMapping("/estadisticas")
    public List<EstadisticaDTO> estadisticas() {
        return service.obtenerEstadisticas();
    }
}