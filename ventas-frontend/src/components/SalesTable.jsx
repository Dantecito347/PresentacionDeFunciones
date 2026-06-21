import {useEffect,useState} from "react";
import {obtenerVentas} from "../services/ventaService";

function SalesTable(){

    const [ventas,setVentas]=useState([]);

    useEffect(()=>{

        obtenerVentas().then(res=>{

            setVentas(res.data);

        });

    },[]);

    return(

        <div className="card mt-4">

            <div className="card-header">

                Ventas Registradas

            </div>

            <div className="card-body">

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

                    {

                        ventas.map(v=>(

                            <tr key={v.id}>

                                <td>{v.producto}</td>

                                <td>{v.categoria}</td>

                                <td>{v.cantidad}</td>

                                <td>${v.precioUnitario}</td>

                                <td>${v.total}</td>

                            </tr>

                        ))

                    }

                    </tbody>

                </table>

            </div>

        </div>

    );

}

export default SalesTable;