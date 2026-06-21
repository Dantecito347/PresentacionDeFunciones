# 🎯 EJEMPLOS DE CÓDIGO - FUNCIONES CLAVE

## ⭐ FUNCIÓN 1: GRÁFICA INTERACTIVA (SalesChart.jsx)

### Descripción
Una gráfica de barras que muestra las ventas totales por categoría. Usa la librería **Chart.js** con el wrapper **react-chartjs-2** para integración perfecta con React.

### Código Completo Anotado

```javascript
// ========== IMPORTACIONES ==========
import { Bar } from "react-chartjs-2";

// Importar elementos de Chart.js que se van a usar
import {
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
    Tooltip,
    Legend,
    Chart
} from "chart.js";

// Hook para manejar estado y efectos
import { useEffect, useState } from "react";

// Servicio para obtener datos de la API
import { obtenerEstadisticas } from "../services/ventaService";

// ========== COMPONENTE PRINCIPAL ==========
function SalesChart(){

    // 📍 LÍNEA CLAVE #1: Estado para almacenar estadísticas
    const [estadisticas, setEstadisticas] = useState([]);

    // 📍 LÍNEA CLAVE #2: Registrar elementos de Chart.js
    // Esto es OBLIGATORIO para que Chart.js sepa qué elementos usar
    Chart.register(
        CategoryScale,      // Escala de categorías (eje X)
        LinearScale,        // Escala lineal (eje Y)
        BarElement,         // Elementos de barras
        Title,              // Título de la gráfica
        Tooltip,            // Información al pasar el mouse
        Legend              // Leyenda de la gráfica
    );

    // 📍 LÍNEA CLAVE #3: Obtener datos al montar el componente
    useEffect(() => {
        obtenerEstadisticas().then(res => {
            setEstadisticas(res.data);
        });
    }, []);  // Array vacío = se ejecuta solo una vez al montar

    // 📍 LÍNEA CLAVE #4: Estructurar datos para Chart.js
    const data = {
        // labels = Eje X de la gráfica (las categorías)
        labels: estadisticas.map(e => e.categoria),
        
        // datasets = Datos a mostrar
        datasets: [{
            label: "Ventas",  // Etiqueta que aparece en la leyenda
            data: estadisticas.map(e => e.totalVentas)  // Eje Y (valores)
        }]
    };

    // 📍 LÍNEA CLAVE #5: Renderizar gráfica
    return(
        <div className="card">
            <div className="card-header">
                Ventas por Categoría
            </div>
            <div className="card-body">
                {/* El componente <Bar/> de react-chartjs-2 */}
                <Bar data={data} />
            </div>
        </div>
    );
}

export default SalesChart;
```

### Ejemplo de Datos que Recibe

**Respuesta de la API** (`GET /ventas/estadisticas`):
```json
[
    { "categoria": "Electrónica", "totalVentas": 15000 },
    { "categoria": "Ropa", "totalVentas": 8500 },
    { "categoria": "Alimentos", "totalVentas": 12300 },
    { "categoria": "Libros", "totalVentas": 5200 }
]
```

**Gráfica generada:**
```
    15000 |     ██
          |     ██
    12300 |     ██        ██
          |     ██        ██
     8500 |     ██  ██    ██
          |     ██  ██    ██
     5200 |     ██  ██    ██  ██
          |_____|___|____|___|___|
              E   R   A    L
```

---

## ⭐ FUNCIÓN 2: MÉTODO DE COMPARTIR CONTENIDO (ShareButton.jsx)

### Descripción
Un botón que permite compartir el reporte de ventas. Usa la **Web Share API** moderna si está disponible, o copia al portapapeles como alternativa.

### Código Completo Anotado

```javascript
// ========== COMPONENTE PRINCIPAL ==========
function ShareButton(){

    // 📍 LÍNEA CLAVE #1: Función que maneja el compartir
    const compartir = () => {
        // Mensaje a compartir
        const texto = "Mira este Dashboard de Ventas desarrollado con Spring Boot y React.";

        // 📍 LÍNEA CLAVE #2: Verificar si el navegador soporta Web Share API
        if (navigator.share) {
            // Si soporta: abre el menú nativo de compartición
            // (WhatsApp, Email, Telegram, etc.)
            navigator.share({
                title: "Dashboard",  // Título del compartición
                text: texto          // Texto a compartir
            });
        } else {
            // Si NO soporta: copia al portapapeles
            // 📍 LÍNEA CLAVE #3: Usar Clipboard API
            navigator.clipboard.writeText(texto);
            alert("Texto copiado al portapapeles");
        }
    };

    // Renderizar el botón
    return(
        <div className="text-center mt-4 mb-5">
            {/* 📍 LÍNEA CLAVE #4: Botón que dispara la función */}
            <button
                className="btn btn-success"  // Estilos de Bootstrap
                onClick={compartir}          // Al hacer clic, ejecuta compartir()
            >
                Compartir Reporte
            </button>
        </div>
    );
}

export default ShareButton;
```

### Ejemplo de Funcionamiento

#### Escenario 1: Navegador Moderno (Chrome, Edge)
```
Usuario hace clic en "Compartir Reporte"
            ↓
navigator.share detecta soporte
            ↓
Se abre menú nativo:
┌─────────────────────────┐
│ Compartir               │
├─────────────────────────┤
│ 📱 WhatsApp            │
│ 📧 Gmail               │
│ 📱 Telegram            │
│ 📋 Copiar enlace       │
│ 🖨️ Imprimir            │
└─────────────────────────┘
```

#### Escenario 2: Navegador Antiguo (o sin soporte)
```
Usuario hace clic en "Compartir Reporte"
            ↓
navigator.share NO existe
            ↓
Se copia al portapapeles:
"Mira este Dashboard de Ventas desarrollado con Spring Boot y React."
            ↓
Aparece alert: "Texto copiado al portapapeles"
            ↓
Usuario puede pegar en cualquier lado (Ctrl+V)
```

---

## ⭐ FUNCIÓN 3: SEGUNDA GRÁFICA (BONUS - Estructura similar)

Si quisieras agregar una segunda gráfica, la estructura sería idéntica:

```javascript
import { Pie } from "react-chartjs-2";  // Cambiar tipo de gráfica
import { Pie as PieElement } from "chart.js";  // Importar elemento Pie

// Registrar elemento
Chart.register(PieElement, ...);

// Estructura de datos
const data = {
    labels: ["Categoría A", "Categoría B", "Categoría C"],
    datasets: [{
        label: "Distribución de Ventas",
        data: [30, 50, 20]  // Porcentajes
    }]
};

return <Pie data={data} />;
```

**Otros tipos de gráficas disponibles:**
- `Line` - Gráfica de líneas
- `Doughnut` - Gráfica de rosquilla
- `Radar` - Gráfica de radar
- `Scatter` - Gráfica de dispersión
- `Bubble` - Gráfica de burbujas

---

## 🔗 INTEGRACIÓN CON LA API

### Endpoint para SalesChart
```
GET http://localhost:8080/ventas/estadisticas

Respuesta esperada:
[
    { "categoria": "string", "totalVentas": number }
]
```

### Endpoint para SalesTable y SummaryCards
```
GET http://localhost:8080/ventas

Respuesta esperada:
[
    {
        "id": number,
        "producto": "string",
        "categoria": "string",
        "cantidad": number,
        "precioUnitario": number,
        "total": number
    }
]
```

---

## 🧪 CÓMO PROBAR ESTAS FUNCIONES

### Probar SalesChart
1. Asegúrate que la API está activa en `http://localhost:8080`
2. El endpoint `/estadisticas` debe retornar datos
3. En el navegador, abre DevTools (F12)
4. En la pestaña "Network", verás la petición GET a `/estadisticas`
5. La gráfica debe renderizarse automáticamente

### Probar ShareButton
1. Haz clic en el botón "Compartir Reporte"
2. **En navegador moderno**: Se abre el menú nativo
3. **En navegador antiguo**: Aparece un alert de "Texto copiado"
4. Intenta pegar (Ctrl+V) en cualquier texto para verificar

---

## 🐛 POSIBLES ERRORES Y SOLUCIONES

### Error: "Chart is not defined"
**Causa**: No registraste los elementos de Chart.js
**Solución**: Asegúrate de incluir:
```javascript
Chart.register(CategoryScale, LinearScale, BarElement, ...);
```

### Error: "Cannot read property 'category'"
**Causa**: La API retorna datos con estructura diferente
**Solución**: Verifica que los datos tengan las propiedades: `categoria` y `totalVentas`

### ShareButton no funciona
**Causa**: Navegador sin soporte de Web Share API
**Solución**: El fallback (clipboard) siempre funciona

### Gráfica no aparece
**Causa**: 
- API no retorna datos
- Datos están vacíos
**Solución**: 
- Verifica en DevTools que la API retorna datos
- Añade console.log para debuggear:
```javascript
useEffect(() => {
    obtenerEstadisticas().then(res => {
        console.log("Datos recibidos:", res.data);  // Ver qué recibe
        setEstadisticas(res.data);
    });
}, []);
```

---

**Documento de ejemplos de código generado el 21 de Junio de 2026**
