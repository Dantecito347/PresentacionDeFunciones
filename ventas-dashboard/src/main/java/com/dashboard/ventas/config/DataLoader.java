package com.dashboard.ventas.config;
import com.dashboard.ventas.entity.Venta;
import com.dashboard.ventas.repository.VentaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner cargarDatos(VentaRepository repository) {
        return args -> {

            if (repository.count() == 0) {

                repository.saveAll(List.of(

                        new Venta(null, "Coca Cola", "Bebidas", 10, 2500.0, LocalDate.of(2026, 6, 1)),

                        new Venta(null, "Hamburguesa", "Comida", 5, 8500.0, LocalDate.of(2026, 6, 2)),

                        new Venta(null, "Auriculares", "Tecnología", 3, 25000.0, LocalDate.of(2026, 6, 3)),

                        new Venta(null, "Agua Mineral", "Bebidas", 20, 1800.0, LocalDate.of(2026, 6, 4)),

                        new Venta(null, "Pizza", "Comida", 8, 12000.0, LocalDate.of(2026, 6, 5)),

                        new Venta(null, "Mouse Gamer", "Tecnología", 4, 18000.0, LocalDate.of(2026, 6, 6))

                ));

                System.out.println("Datos de prueba cargados.");
            }

        };
    }

}
