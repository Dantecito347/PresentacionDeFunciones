import axios from "axios";

const API="http://localhost:8080/ventas";

export const obtenerVentas=()=>axios.get(API);

export const obtenerEstadisticas=()=>axios.get(API+"/estadisticas");