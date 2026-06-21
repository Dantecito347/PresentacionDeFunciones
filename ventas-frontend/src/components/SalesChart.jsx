import {Bar} from "react-chartjs-2";

import {

CategoryScale,
LinearScale,
BarElement,
Title,
Tooltip,
Legend,
Chart

} from "chart.js";

Chart.register(

CategoryScale,
LinearScale,
BarElement,
Title,
Tooltip,
Legend

);

import {useEffect,useState} from "react";

import {obtenerEstadisticas} from "../services/ventaService";

function SalesChart(){

    const [estadisticas,setEstadisticas]=useState([]);

    useEffect(()=>{

        obtenerEstadisticas().then(res=>{

            setEstadisticas(res.data);

        });

    },[]);

    const data={

        labels:estadisticas.map(e=>e.categoria),

        datasets:[{

            label:"Ventas",

            data:estadisticas.map(e=>e.totalVentas)

        }]

    };

    return(

        <div className="card">

            <div className="card-header">

                Ventas por Categoría

            </div>

            <div className="card-body">

                <Bar data={data}/>

            </div>

        </div>

    );

}

export default SalesChart;