import {useEffect,useState} from "react";
import {obtenerVentas} from "../services/ventaService";

function SummaryCards(){

    const [ventas,setVentas]=useState([]);

    useEffect(()=>{

        obtenerVentas().then(res=>{

            setVentas(res.data);

        });

    },[]);

    const totalVentas=ventas.length;

    const recaudacion=ventas.reduce((a,v)=>a+v.total,0);

    const promedio=totalVentas?recaudacion/totalVentas:0;

    return(

        <div className="row mb-4">

            <div className="col">

                <div className="card text-center">

                    <div className="card-body">

                        <h5>Total Ventas</h5>

                        <h2>{totalVentas}</h2>

                    </div>

                </div>

            </div>

            <div className="col">

                <div className="card text-center">

                    <div className="card-body">

                        <h5>Recaudación</h5>

                        <h2>${recaudacion}</h2>

                    </div>

                </div>

            </div>

            <div className="col">

                <div className="card text-center">

                    <div className="card-body">

                        <h5>Promedio</h5>

                        <h2>${promedio.toFixed(2)}</h2>

                    </div>

                </div>

            </div>

        </div>

    );

}

export default SummaryCards;