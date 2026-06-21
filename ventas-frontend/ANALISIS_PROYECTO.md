# ANÁLISIS DEL PROYECTO: Dashboard de Ventas (Ventas-Frontend)

## 📋 Descripción General
Este es un proyecto de **Dashboard de Ventas** desarrollado con **React** y **Vite**. Se trata de una aplicación frontend que visualiza datos de ventas obtenidos desde una API backend (Spring Boot) en el puerto 8080. La aplicación es responsive, utiliza Bootstrap para estilos y Chart.js para gráficas.

---

## 📂 ESTRUCTURA DE ARCHIVOS Y SUS FUNCIONES

### **🔧 ARCHIVOS DE CONFIGURACIÓN**

#### 1. **package.json**
- **Propósito**: Archivo de configuración principal de Node.js/npm
- **Contiene**:
  - Metadatos del proyecto (nombre, versión)
  - Scripts de desarrollo y compilación
  - Dependencias necesarias para el proyecto
  
- **Dependencias Principales**:
  - `react` y `react-dom`: Framework frontend
  - `vite`: Empaquetador y servidor de desarrollo ultrarrápido
  - `axios`: Cliente HTTP para hacer peticiones a la API
  - `bootstrap`: Framework CSS para estilos responsive
  - `chart.js` y `react-chartjs-2`: Para gráficas interactivas
  
- **Scripts Disponibles**:
  - `npm run dev`: Inicia servidor de desarrollo en localhost:5173
  - `npm run build`: Empaqueta la aplicación para producción
  - `npm run lint`: Verifica la calidad del código
  - `npm run preview`: Previsualiza la compilación de producción

---

#### 2. **vite.config.js**
- **Propósito**: Configuración de Vite (empaquetador)
- Define cómo se construye y sirve la aplicación
- Incluye plugin de React para transformar JSX

#### 3. **eslint.config.js**
- **Propósito**: Configuración de ESLint (linter)
- Define reglas de calidad y estilo de código
- Valida que el código siga estándares

#### 4. **index.html**
- **Propósito**: Archivo HTML principal
- Punto de entrada de la aplicación
- Contiene un `<div id="root">` donde React renderiza la aplicación
- Vinculado con `main.jsx`

---

### **⚛️ ARCHIVOS PRINCIPALES DE LA APLICACIÓN**

#### 5. **src/main.jsx**
- **Propósito**: Punto de entrada de React
- **Responsabilidades**:
  - Importa React y ReactDOM
  - Carga estilos globales (Bootstrap y CSS personalizado)
  - Renderiza el componente `<App/>` en el DOM
  
```javascript
// Importa Bootstrap para estilos
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

// Renderiza la aplicación
ReactDOM.createRoot(document.getElementById("root")).render(
    <React.StrictMode>
        <App />
    </React.StrictMode>
);
```

---

#### 6. **src/App.jsx**
- **Propósito**: Componente raíz de la aplicación
- Enruta el contenido principal al componente `Dashboard`
- Estructura simple: solo devuelve el Dashboard
- Podría expandirse para incluir routing múltiple

```javascript
import Dashboard from "./pages/Dashboard";

function App() {
    return <Dashboard />;
}
```

---

### **📄 PÁGINAS (Pages)**

#### 7. **src/pages/Dashboard.jsx**
- **Propósito**: Página principal que orquesta todos los componentes
- **Rol**: Contenedor principal que estructura el layout
- **Componentes que Incluye**:
  1. `<Navbar/>` - Encabezado de navegación
  2. `<SummaryCards/>` - Tarjetas de resumen
  3. `<SalesChart/>` - Gráfica de ventas por categoría ⭐
  4. `<SalesTable/>` - Tabla de ventas detalladas
  5. `<ShareButton/>` - Botón para compartir reporte ⭐

```javascript
return (
    <>
        <Navbar/>
        <div className="container">
            <SummaryCards/>
            <SalesChart/>         {/* GRÁFICA 1 */}
            <SalesTable/>
            <ShareButton/>        {/* FUNCIÓN DE COMPARTIR */}
        </div>
    </>
);
```

---

### **🧩 COMPONENTES (Components)**

#### 8. **src/components/Navbar.jsx**
- **Propósito**: Barra de navegación superior
- **Funcionalidad**: Muestra el título "Dashboard de Ventas"
- **Estilos**: Usa Bootstrap (`navbar navbar-dark bg-dark`)
- **Tipo**: Componente presentacional (sin lógica)

```javascript
<nav className="navbar navbar-dark bg-dark mb-4">
    <div className="container">
        <span className="navbar-brand">
            Dashboard de Ventas
        </span>
    </div>
</nav>
```

---

#### 9. **src/components/SummaryCards.jsx** 
- **Propósito**: Mostrar 3 tarjetas con métricas resumidas
- **Datos que Calcula**:
  1. **Total Ventas**: Cantidad de registros de ventas
  2. **Recaudación**: Suma total de todas las ventas
  3. **Promedio**: Promedio de venta por registro

- **Obtención de Datos**: 
  - Llama a `obtenerVentas()` al montar el componente
  - Usa `useEffect` para hacer la petición HTTP

```javascript
const totalVentas = ventas.length;
const recaudacion = ventas.reduce((a,v) => a + v.total, 0);
const promedio = totalVentas ? recaudacion / totalVentas : 0;
```

- **Renderizado**: Tres columnas de Bootstrap con cards

---

#### 10. **src/components/SalesChart.jsx** ⭐ **[GRÁFICA 1]**
- **Propósito**: Mostrar gráfica de barras con ventas por categoría
- **Tipo de Gráfica**: Gráfica de Barras (Bar Chart)
- **Librerías Utilizadas**:
  - `react-chartjs-2`: Componente React para Chart.js
  - `chart.js`: Librería de gráficas

- **Funcionamiento**:
  - Obtiene datos de estadísticas desde la API
  - Agrupa ventas por categoría
  - Suma las ventas totales por cada categoría

**📍 CÓDIGO PRINCIPAL - Registrando elementos de Chart.js:**
```javascript
import { Bar } from "react-chartjs-2";
import {
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
    Tooltip,
    Legend,
    Chart
} from "chart.js";

// LÍNEA IMPORTANTE: Registra los elementos para que Chart.js funcione
Chart.register(
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
    Tooltip,
    Legend
);
```

**📍 ESTRUCTURA DE DATOS:**
```javascript
const data = {
    labels: estadisticas.map(e => e.categoria),  // Eje X: Categorías
    datasets: [{
        label: "Ventas",
        data: estadisticas.map(e => e.totalVentas)  // Eje Y: Totales de ventas
    }]
};

// Renderizado
<Bar data={data} />  // Componente de react-chartjs-2
```

**📊 Ejemplo de salida esperada:**
- Eje X (Categorías): "Electrónica", "Ropa", "Alimentos", etc.
- Eje Y (Ventas): 5000, 3200, 8900, etc.

---

#### 11. **src/components/SalesTable.jsx**
- **Propósito**: Mostrar tabla detallada de todas las ventas
- **Datos Mostrados**:
  - Producto
  - Categoría
  - Cantidad
  - Precio Unitario
  - Total

- **Obtención de Datos**: 
  - Llama a `obtenerVentas()` desde la API
  - Usa `useEffect` para cargar datos al montar

- **Renderizado**: Tabla HTML con Bootstrap (`table-striped`)

```javascript
<table className="table table-striped">
    <thead>
        <tr>
            <th>Producto</th>
            <th>Categoría</th>
            <th>Cantidad</th>
            <th>Precio</th>
            <th>Total</th>
        </tr>
    </thead>
    <tbody>
        {ventas.map(v => (
            <tr key={v.id}>
                <td>{v.producto}</td>
                <td>{v.categoria}</td>
                <td>{v.cantidad}</td>
                <td>${v.precioUnitario}</td>
                <td>${v.total}</td>
            </tr>
        ))}
    </tbody>
</table>
```

---

#### 12. **src/components/ShareButton.jsx** ⭐ **[FUNCIÓN DE COMPARTIR]**
- **Propósito**: Permitir compartir el reporte de ventas con otros usuarios
- **Métodos de Compartición**:
  1. **Si el navegador soporta Web Share API**: Abre el selector nativo de compartición
  2. **Si no soporta**: Copia el texto al portapapeles

**📍 CÓDIGO PRINCIPAL - Método de Compartir:**
```javascript
const compartir = () => {
    const texto = "Mira este Dashboard de Ventas desarrollado con Spring Boot y React.";
    
    // LÍNEA IMPORTANTE: Verifica si el navegador soporta Web Share API
    if (navigator.share) {
        // Abre el selector nativo (WhatsApp, Email, etc.)
        navigator.share({
            title: "Dashboard",
            text: texto
        });
    } else {
        // LÍNEA IMPORTANTE: Si no soporta, copia al portapapeles
        navigator.clipboard.writeText(texto);
        alert("Texto copiado al portapapeles");
    }
};
```

**🎯 Comportamiento:**
- **Navegadores modernos (Chrome, Edge)**: Muestra menú de compartición nativa
- **Navegadores antiguos o sin soporte**: Copia el mensaje al portapapeles

**Botón de Activación:**
```javascript
<button className="btn btn-success" onClick={compartir}>
    Compartir Reporte
</button>
```

---

### **🔌 SERVICIOS (Services)**

#### 13. **src/services/ventaService.jsx**
- **Propósito**: Capa de comunicación con la API backend
- **URL Base de API**: `http://localhost:8080/ventas`
- **Funciones Exportadas**:

```javascript
import axios from "axios";

const API = "http://localhost:8080/ventas";

// Obtiene todas las ventas
export const obtenerVentas = () => axios.get(API);

// Obtiene estadísticas agrupadas por categoría
export const obtenerEstadisticas = () => axios.get(API + "/estadisticas");
```

**Endpoints utilizados:**
1. `GET /ventas` - Retorna lista de todas las ventas
2. `GET /ventas/estadisticas` - Retorna ventas agrupadas por categoría

---

### **🎨 ESTILOS**

#### 14. **src/App.css**
- **Propósito**: Estilos personalizados para la aplicación
- Complementa los estilos de Bootstrap
- Define la apariencia específica del dashboard

#### 15. **src/index.css**
- **Propósito**: Estilos globales
- Define tipografía, colores base y estilos generales

---

## 🔄 FLUJO DE DATOS

```
[API Backend: http://localhost:8080]
           ↓
  [ventaService.jsx] → axios.get()
           ↓
  [Componentes React]
    ├─ Navbar (estático)
    ├─ SummaryCards (obtenerVentas → calcula métricas)
    ├─ SalesChart (obtenerEstadisticas → gráfica de barras) ⭐
    ├─ SalesTable (obtenerVentas → tabla)
    └─ ShareButton (comparte contenido) ⭐
           ↓
  [Navegador] → Renderiza HTML
```

---

## 📊 TABLA RESUMEN

| Archivo | Tipo | Función Principal | Dependencias |
|---------|------|------------------|--------------|
| main.jsx | Inicializador | Punto de entrada | React, Bootstrap, CSS |
| App.jsx | Componente | Raíz de la app | Dashboard |
| Dashboard.jsx | Página | Orquesta componentes | Todos los componentes |
| Navbar.jsx | Componente | Encabezado | Bootstrap |
| SummaryCards.jsx | Componente | Métricas resumidas | Axios, ventaService |
| **SalesChart.jsx** | Componente | **Gráfica de barras** ⭐ | Chart.js, react-chartjs-2 |
| SalesTable.jsx | Componente | Tabla de datos | Axios, ventaService |
| **ShareButton.jsx** | Componente | **Compartir reporte** ⭐ | Navigator API |
| ventaService.jsx | Servicio | Comunicación API | Axios |
| App.css / index.css | Estilos | Apariencia visual | - |

---

## ✨ CARACTERÍSTICAS DESTACADAS

### 1️⃣ **Gráfica Interactiva (SalesChart.jsx)**
- Utiliza **Chart.js** con **react-chartjs-2**
- Muestra ventas por categoría en formato de barras
- Datos obtenidos dinámicamente desde la API
- Totalmente responsive

### 2️⃣ **Función de Compartición (ShareButton.jsx)**
- Implementa **Web Share API** moderna
- Fallback a **clipboard** para navegadores antiguos
- Permite compartir en redes sociales directamente desde el navegador
- Interfaz amigable con botón Bootstrap

---

## 🚀 CÓMO EJECUTAR

1. **Instalar dependencias**:
   ```bash
   npm install
   ```

2. **Iniciar servidor de desarrollo**:
   ```bash
   npm run dev
   ```
   La app abrirá en `http://localhost:5173`

3. **Asegurarse que la API backend está activa**:
   - Debe estar corriendo en `http://localhost:8080`

4. **Compilar para producción**:
   ```bash
   npm run build
   ```

---

## 🛠️ TECNOLOGÍAS UTILIZADAS

| Tecnología | Versión | Propósito |
|-----------|---------|----------|
| React | 19.2.6 | Framework frontend |
| Vite | 8.0.12 | Empaquetador y dev server |
| Bootstrap | 5.3.8 | Framework CSS |
| Chart.js | 4.5.1 | Gráficas |
| react-chartjs-2 | 5.3.1 | Wrapper React para Chart.js |
| Axios | 1.18.0 | Cliente HTTP |
| ESLint | 10.3.0 | Linter de código |

---

## 📝 NOTAS IMPORTANTES

- **CORS**: La API backend debe permitir peticiones desde `http://localhost:5173`
- **API Endpoints**: El servicio espera dos endpoints en la API:
  - `GET /ventas` - Lista de ventas
  - `GET /ventas/estadisticas` - Estadísticas por categoría
- **Datos Esperados**: Cada venta debe incluir: `id`, `producto`, `categoria`, `cantidad`, `precioUnitario`, `total`
- **Compatibilidad**: La función de compartir funciona mejor en navegadores modernos (Chrome, Edge, Firefox)

---

**Documento generado para análisis del proyecto Ventas-Frontend**
**Fecha: 21 de Junio de 2026**
