function ShareButton(){

    const compartir=()=>{

        const texto="Mira este Dashboard de Ventas desarrollado con Spring Boot y React.";

        if(navigator.share){

            navigator.share({

                title:"Dashboard",

                text:texto

            });

        }else{

            navigator.clipboard.writeText(texto);

            alert("Texto copiado al portapapeles");

        }

    }

    return(

        <div className="text-center mt-4 mb-5">

            <button

                className="btn btn-success"

                onClick={compartir}

            >

                Compartir Reporte

            </button>

        </div>

    );

}

export default ShareButton;