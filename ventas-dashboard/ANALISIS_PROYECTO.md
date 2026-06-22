# Análisis del Proyecto: Ventas Dashboard

## 📋 Descripción General
**Ventas Dashboard** es una aplicación web backend desarrollada con **Spring Boot** que permite gestionar y visualizar estadísticas de ventas. La aplicación sigue una arquitectura de capas (MVC mejorada) y utiliza una base de datos H2 para almacenar información de productos vendidos.

---

## 🛠️ Tecnologías y Dependencias

### Stack Tecnológico:
- **Java**: Versión 21
- **Spring Boot**: 4.1.0 (última versión disponible)
- **Base de Datos**: H2 (base de datos embebida)
- **ORM**: JPA/Hibernate
- **Utilidad**: Lombok (para reducir código boilerplate)
- **API**: REST

### Dependencias principales en `pom.xml`:
1. **spring-boot-starter-webmvc**: Framework web para crear APIs REST
2. **spring-boot-starter-data-jpa**: Acceso a datos con JPA/Hibernate
3. **spring-boot-h2console**: Consola web para gestionar la BD H2
4. **spring-boot-starter-validation**: Validaciones de datos
5. **lombok**: Anotaciones para generar getters, setters, constructores
6. **h2**: Base de datos embebida (runtime)

---

## 📁 Estructura de Carpetas y Archivos

```
src/main/java/com/dashboard/ventas/
├── VentasDashboardApplication.java     (Punto de entrada)
├── config/
│   └── DataLoader.java                 (Carga datos iniciales)
├── controller/
│   └── VentaController.java            (Endpoints REST)
├── dto/
│   ├── VentaDTO.java                   (Transferencia de datos de ventas)
│   └── EstadisticaDTO.java             (Transferencia de datos estadísticos)
├── entity/
│   └── Venta.java                      (Modelo de datos persistente)
├── repository/
│   └── VentaRepository.java            (Acceso a datos)
└── service/
    ├── VentaService.java               (Interfaz de servicios)
    └── VentaServiceImpl.java            (Implementación de servicios)

src/main/resources/
└── application.properties               (Configuración de la aplicación)
```

---

## 📄 Descripción Detallada de Cada Archivo

### 1. **VentasDashboardApplication.java**
**Ubicación**: `src/main/java/com/dashboard/ventas/`

**Propósito**: Es el punto de entrada (main) de la aplicación Spring Boot.

**Contenido**:
```java
@SpringBootApplication
public class VentasDashboardApplication {
    public static void main(String[] args) {
        SpringApplication.run(VentasDashboardApplication.class, args);
    }
}
```

**Funciones**:
- La anotación `@SpringBootApplication` activa la autoconfiguración de Spring Boot
- El método `main()` inicia la aplicación
- Cuando ejecutas `mvn spring-boot:run`, este archivo es lo primero que se ejecuta

---

### 2. **application.properties**
**Ubicación**: `src/main/resources/`

**Propósito**: Archivo de configuración central de la aplicación.

**Contenido**:
```properties
spring.application.name=ventas-dashboard
spring.jpa.hibernate.ddl-auto=create-drop
```

**Explicación de propiedades**:
- `spring.application.name`: Nombre de la aplicación (usada en logs y gestión)
- `spring.jpa.hibernate.ddl-auto=create-drop`: 
  - `create-drop`: La BD se crea al iniciar la app y se elimina al cerrar
  - Útil para desarrollo y pruebas
  - **⚠️ No usar en producción (perderías los datos)**

---

### 3. **Venta.java** (Entity)
**Ubicación**: `src/main/java/com/dashboard/ventas/entity/`

**Propósito**: Entidad JPA que representa una venta en la base de datos.

**Anotaciones**:
- `@Entity`: Indica que es una entidad persistente
- `@Table(name = "ventas")`: Mapea a la tabla "ventas" en la BD
- `@Getter @Setter`: Anotaciones de Lombok que generan getters/setters automáticamente
- `@NoArgsConstructor`: Genera constructor sin parámetros
- `@AllArgsConstructor`: Genera constructor con todos los parámetros
- `@Builder`: Genera patrón Builder para crear objetos

**Atributos**:
```java
private Long id;                      // ID único, generado automáticamente
private String producto;              // Nombre del producto (ej: "Coca Cola")
private String categoria;             // Categoría (ej: "Bebidas")
private Integer cantidad;             // Cantidad vendida
private Double precioUnitario;        // Precio por unidad
private LocalDate fecha;              // Fecha de la venta
```

**Relación con BD**:
- Se crea una tabla llamada "ventas" con estos campos como columnas
- El id es la clave primaria con auto-incremento

---

### 4. **VentaDTO.java** (Data Transfer Object)
**Ubicación**: `src/main/java/com/dashboard/ventas/dto/`

**Propósito**: Objeto de transferencia de datos entre el servidor y cliente (JSON).

**Diferencia con Entity**:
- `Venta` es la entidad persistente (BD)
- `VentaDTO` es lo que se envía/recibe en las APIs REST

**Atributos adicionales**:
```java
private Double total;  // Campo calculado: cantidad × precioUnitario
```

**Flujo**:
```
Cliente → JSON → VentaDTO → Venta (Entity) → BD
BD → Venta (Entity) → VentaDTO → JSON → Cliente
```

---

### 5. **EstadisticaDTO.java** (Data Transfer Object)
**Ubicación**: `src/main/java/com/dashboard/ventas/dto/`

**Propósito**: Objeto para transferir estadísticas de ventas por categoría.

**Contenido**:
```java
private String categoria;           // Nombre de la categoría
private Double totalVentas;         // Total de ventas en esa categoría
```

**Ejemplo de respuesta**:
```json
[
  { "categoria": "Bebidas", "totalVentas": 4300.0 },
  { "categoria": "Comida", "totalVentas": 20500.0 },
  { "categoria": "Tecnología", "totalVentas": 43000.0 }
]
```

---

### 6. **VentaRepository.java** (Repository/DAO)
**Ubicación**: `src/main/java/com/dashboard/ventas/repository/`

**Propósito**: Interfaz para acceso a datos de ventas (Data Access Object).

**Contenido**:
```java
public interface VentaRepository extends JpaRepository<Venta, Long> {
}
```

**Funcionalidad**:
- Hereda de `JpaRepository` que proporciona métodos CRUD automáticos:
  - `save()`: Guardar una venta
  - `findAll()`: Obtener todas las ventas
  - `findById()`: Obtener una venta por ID
  - `delete()`: Eliminar una venta
  - `count()`: Contar ventas
  - etc.

**Parámetros**:
- `<Venta, Long>`: Trabajamos con entidades `Venta` y sus IDs son `Long`

---

### 7. **VentaService.java** (Interfaz de Servicio)
**Ubicación**: `src/main/java/com/dashboard/ventas/service/`

**Propósito**: Define el contrato (métodos) que debe cumplir el servicio de ventas.

**Métodos definidos**:
```java
public VentaDTO guardar(VentaDTO ventaDTO)           // Crear/guardar venta
public List<VentaDTO> listar()                       // Listar todas las ventas
public List<EstadisticaDTO> obtenerEstadisticas()    // Obtener estadísticas
```

**Nota**: En este proyecto, los métodos retornan `null`. La implementación real está en `VentaServiceImpl`.

---

### 8. **VentaServiceImpl.java** (Implementación de Servicio)
**Ubicación**: `src/main/java/com/dashboard/ventas/service/`

**Propósito**: Implementa la lógica de negocio para gestionar ventas.

**Anotaciones**:
- `@Service`: Indica que es un componente de servicio de Spring
- `@RequiredArgsConstructor`: Genera constructor con las dependencias anotadas como `final`

**Métodos**:

#### A) `guardar(VentaDTO dto)`
```java
// Convierte DTO a Entity
Venta venta = Venta.builder()
    .producto(dto.getProducto())
    .categoria(dto.getCategoria())
    .cantidad(dto.getCantidad())
    .precioUnitario(dto.getPrecioUnitario())
    .fecha(dto.getFecha())
    .build();

// Guarda en BD
Venta guardada = repository.save(venta);

// Convierte Entity a DTO y retorna
return VentaDTO.builder()
    // ... mapea todos los campos
    .total(guardada.getCantidad() * guardada.getPrecioUnitario())
    .build();
```

**Flujo**: DTO → Entity → BD → Entity → DTO

#### B) `listar()`
```java
return repository.findAll()  // Obtiene todas las ventas
    .stream()                // Convierte a stream
    .map(v -> VentaDTO...)   // Convierte cada Venta a VentaDTO
    .collect(Collectors.toList());  // Recolecta en lista
```

**Resultado**: Lista de todas las ventas con sus totales calculados

#### C) `obtenerEstadisticas()`
```java
// Agrupa ventas por categoría y suma totales
Map<String, Double> mapa = new HashMap<>();

for (Venta venta : repository.findAll()) {
    double total = venta.getCantidad() * venta.getPrecioUnitario();
    mapa.put(
        venta.getCategoria(),
        mapa.getOrDefault(venta.getCategoria(), 0.0) + total
    );
}

// Convierte mapa a lista de EstadisticaDTO
return mapa.entrySet()
    .stream()
    .map(e -> new EstadisticaDTO(e.getKey(), e.getValue()))
    .collect(Collectors.toList());
```

**Ejemplo de resultado**:
```
Bebidas: $4,300
Comida: $20,500
Tecnología: $43,000
```

---

### 9. **VentaController.java** (Controlador REST)
**Ubicación**: `src/main/java/com/dashboard/ventas/controller/`

**Propósito**: Expone endpoints REST para acceder a los datos de ventas.

**Anotaciones**:
- `@RestController`: Indica que es un controlador REST (retorna JSON, no HTML)
- `@RequestMapping("/ventas")`: Base URL de todos los endpoints
- `@RequiredArgsConstructor`: Inyecta automáticamente `VentaService`
- `@CrossOrigin(origins = "*")`: Permite solicitudes desde cualquier origen (CORS)

**Endpoints**:

#### 1) **POST /ventas** (Crear venta)
```java
@PostMapping
public VentaDTO guardar(@RequestBody VentaDTO ventaDTO) {
    return service.guardar(ventaDTO);
}
```

**Ejemplo de solicitud**:
```json
POST http://localhost:8080/ventas
{
  "producto": "Café",
  "categoria": "Bebidas",
  "cantidad": 5,
  "precioUnitario": 2500,
  "fecha": "2026-06-21"
}
```

**Respuesta**:
```json
{
  "id": 7,
  "producto": "Café",
  "categoria": "Bebidas",
  "cantidad": 5,
  "precioUnitario": 2500,
  "fecha": "2026-06-21",
  "total": 12500
}
```

#### 2) **GET /ventas** (Listar todas las ventas)
```java
@GetMapping
public List<VentaDTO> listar() {
    return service.listar();
}
```

**Respuesta**:
```json
[
  {
    "id": 1,
    "producto": "Coca Cola",
    "categoria": "Bebidas",
    "cantidad": 10,
    "precioUnitario": 2500,
    "fecha": "2026-06-01",
    "total": 25000
  },
  // ... más ventas
]
```

#### 3) **GET /ventas/estadisticas** (Obtener estadísticas)
```java
@GetMapping("/estadisticas")
public List<EstadisticaDTO> estadisticas() {
    return service.obtenerEstadisticas();
}
```

**Respuesta**:
```json
[
  {
    "categoria": "Bebidas",
    "totalVentas": 4300
  },
  {
    "categoria": "Comida",
    "totalVentas": 20500
  },
  {
    "categoria": "Tecnología",
    "totalVentas": 43000
  }
]
```

---

### 10. **DataLoader.java** (Configuración/Cargador de Datos)
**Ubicación**: `src/main/java/com/dashboard/ventas/config/`

**Propósito**: Carga datos de prueba automáticamente al iniciar la aplicación.

**Anotaciones**:
- `@Configuration`: Indica que es una clase de configuración
- `@Bean`: Define un bean (componente) de Spring

**Lógica**:
```java
@Bean
CommandLineRunner cargarDatos(VentaRepository repository) {
    return args -> {
        if (repository.count() == 0) {  // Solo si no hay datos
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
```

**Datos iniciales**:
- 6 ventas de prueba en 3 categorías: Bebidas, Comida, Tecnología
- Se cargan solo si la tabla está vacía (`count() == 0`)
- Imprime un mensaje en la consola

---

## 🏗️ Arquitectura de Capas

```
┌─────────────────────────────────────────┐
│      Cliente (Frontend / Apps)          │
│      (envía/recibe JSON)                │
└────────────────┬────────────────────────┘
                 │
                 ↓
        ┌────────────────────┐
        │   VentaController   │  ← Capa de Presentación/API
        │   (REST Endpoints)  │
        └────────────┬────────┘
                     │
                     ↓
        ┌────────────────────┐
        │   VentaService      │  ← Capa de Lógica de Negocio
        │   (Reglas, cálculos)│
        └────────────┬────────┘
                     │
                     ↓
        ┌────────────────────┐
        │  VentaRepository    │  ← Capa de Acceso a Datos (DAO)
        │  (JpaRepository)    │
        └────────────┬────────┘
                     │
                     ↓
        ┌────────────────────┐
        │   Base de Datos H2   │  ← Almacenamiento
        │   (Tabla: ventas)    │
        └────────────────────┘
```

---

## 🔄 Flujo de una Solicitud

### Ejemplo: Crear una nueva venta

```
1. Cliente envía POST a /ventas con JSON:
   {
     "producto": "Café",
     "categoria": "Bebidas",
     "cantidad": 5,
     "precioUnitario": 2500,
     "fecha": "2026-06-21"
   }

2. VentaController.guardar() recibe el VentaDTO

3. Llama a VentaService.guardar(ventaDTO)

4. VentaServiceImpl convierte VentaDTO a Venta (Entity)

5. Guarda la Venta en la BD mediante VentaRepository.save()

6. La BD devuelve la Venta con ID generado

7. VentaServiceImpl convierte de vuelta a VentaDTO (con ID e total)

8. VentaController devuelve el VentaDTO como JSON

9. Cliente recibe respuesta con la venta creada
```

---

## 📊 Diagrama de Clases (Simplicado)

```
Venta (Entity)
├─ id: Long
├─ producto: String
├─ categoria: String
├─ cantidad: Integer
├─ precioUnitario: Double
└─ fecha: LocalDate

VentaDTO (Data Transfer)
├─ id: Long
├─ producto: String
├─ categoria: String
├─ cantidad: Integer
├─ precioUnitario: Double
├─ fecha: LocalDate
└─ total: Double (calculado)

EstadisticaDTO (Data Transfer)
├─ categoria: String
└─ totalVentas: Double
```

---

## 🚀 Cómo Ejecutar el Proyecto

### Opción 1: Con Maven (línea de comandos)
```bash
cd c:\Users\DANTE01\Desktop\ventas-dashboard
./mvnw spring-boot:run
```

### Opción 2: Con Eclipse/IntelliJ
1. Click derecho en `VentasDashboardApplication.java`
2. Seleccionar "Run As" → "Java Application"

### Acceder a la aplicación:
- **API Base**: `http://localhost:8080`
- **Listar ventas**: `http://localhost:8080/ventas`
- **Ver estadísticas**: `http://localhost:8080/ventas/estadisticas`
- **Consola H2**: `http://localhost:8080/h2-console`

---

## 📝 Resumen de Dependencias y su Rol

| Dependencia | Versión | Función |
|---|---|---|
| spring-boot-starter-webmvc | 4.1.0 | Framework REST |
| spring-boot-starter-data-jpa | 4.1.0 | Acceso a datos/ORM |
| h2database | - | Base de datos embebida |
| spring-boot-h2console | - | Interfaz web para H2 |
| lombok | - | Reduce código boilerplate |
| spring-boot-starter-validation | 4.1.0 | Validaciones de datos |

---

## ⚠️ Puntos Importantes

1. **Base de datos temporal**: Con `create-drop`, la BD se recrean cada vez que se inicia la app
   - ✅ Útil para desarrollo
   - ❌ No usar en producción

2. **CORS habilitado**: `@CrossOrigin(origins = "*")` permite cualquier origen
   - ✅ Desarrollo fácil
   - ❌ Riesgo de seguridad en producción

3. **Sin autenticación**: No hay seguridad actualmente
   - Cualquiera puede acceder a los endpoints

4. **Lombok**: Reduce código, pero asegúrate de que esté instalado en tu IDE

---

## 🔍 Próximos Pasos Sugeridos

1. **Validaciones**: Agregar `@NotNull`, `@NotBlank`, etc. en DTOs
2. **Manejo de errores**: Crear `@ControllerAdvice` para excepciones
3. **Autenticación**: Implementar Spring Security
4. **Persistencia**: Cambiar de H2 a una BD real (PostgreSQL, MySQL)
5. **Pruebas**: Agregar tests unitarios e integración
6. **Frontend**: Crear interfaz web o consumir desde otra aplicación
7. **Documentación API**: Agregar Swagger/SpringDoc OpenAPI

---

**Documento generado automáticamente - Análisis completo del proyecto Ventas Dashboard**
