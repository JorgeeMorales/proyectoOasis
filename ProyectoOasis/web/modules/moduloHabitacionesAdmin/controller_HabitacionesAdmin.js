let inputImagen = null;
let habitaciones =[];
let indexHabitacionesSeleccionado;

export function inicializar() {
    setDetalleVisible(false);
    inputImagen = document.getElementById("txtFot");
    inputImagen.onchange = function (e) {
    cargarFotografia(inputImagen);
    };
    refrescarTabla();
}

export function save(){
    let datos = null;
    let params = null;
    //creamos objetos sin propiedades
    let habitacion = new Object();
    
    //validamos si la caja de texto tiene algun ID si no es asi se colocan como cero los ID
    if (document.getElementById("txtIdHabitacion").value.trim().length < 1) //para ver si tiene valores la caja de texto de txtIdArmazon
    {
        habitacion.idHabitacion = 0;

    } else
    {
        //si si se tiene un ID se le pasan a el objeto empleado
        habitacion.idHabitacion = parseInt(document.getElementById("txtIdHabitacion").value);
    }
    
    habitacion.nombre = document.getElementById("txtNombre").value;
    habitacion.descripcion = document.getElementById("txtDescripcion").value;
    habitacion.tematica = document.getElementById("txtTematica").value;
    habitacion.precioRenta = document.getElementById("txtPrecioRenta").value;
    habitacion.fotografia = document.getElementById("txtaCodigoImagen").value;
    
    datos = {
        datosHabitacion: JSON.stringify(habitacion) //conviertes un objeto java Script a una cadena JSON
    };
    
        params = new URLSearchParams(datos);
    
    fetch("api/habitacion/save",
            {method: "POST",
                headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}, //cabecera de la peticcion, metodo de como estoy condicionando y como voy a mandar los datos
                body: params
            })
            .then(response => {
                return response.json();
            })
            .then(function (data)
            {
                //let ha=[];
                //ha = data;
                console.log("DATA",data);
                if (data.exception != null)
                {
                    Swal.fire('', "Error interno del servidor. Intente nuevamente más tarde" + data.exception + data, 'error');
                    return;

                }
                if (data.error != null)
                {
                    Swal.fire('', data.error, 'warning')
                    return;
                }
                if (data.errorperm != null)
                {
                    Swal.fire('', "No tiene permiso para realizar esta operación.", 'warning');
                }
                document.getElementById("txtIdHabitacion").value = data.idHabitacion;
                document.getElementById("txtCodigoUnico").value = data.codigoUnico;

                // si no hay errores ahora se le colocan los ID 

                Swal.fire('', 'Datos de la habitación actualizados correctamente', 'success');
                refrescarTabla();
                //clean();
            });
}

function cargarFotografia(objectInputFile) {

    if (objectInputFile.files && objectInputFile.files[0]) {
        let reader = new FileReader();

        reader.onload = function (e) {
            let fotoB64 = e.target.result;
            document.getElementById("imgFotoMos").src = fotoB64;
            document.getElementById("txtaCodigoImagen").value =
                    fotoB64.substring(fotoB64.indexOf(",") + 1, fotoB64.length);
        };
        reader.readAsDataURL(objectInputFile.files[0]);
    }
}

export function refrescarTabla() {
    
    let params = new URLSearchParams({filtro: ""});
  
    fetch("api/habitacion/getAll",
            {method: "POST",
                headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}, //cabecera de la peticcion, metodo de como estoy condicionando y como voy a mandar los datos
                body: params
            })
            .then(response => {
                return response.json();
            })
            .then(function (data)
            {
                if (data.exception != null) {
                    Swal.fire('', 'error interno del servidor.Intete nuevamente mas tarde.', 'error');
                    return;
                }
                if (data.error != null) {
                    Swal.fire('', data.error, 'Warning');
                    return;
                }

                if (data.errorsec) {
                    Swal.fire('', data.errorsec, 'error');
                    window.location.replace('index.html');
                    return;
                }
                loadTabla(data);
            });
}

export function loadTabla(data) {
    let cuerpo = "";
    habitaciones = data;
    habitaciones.forEach(function (habitacion) {
        let registro =
                '<tr onclick="moduloHabitacionesAdmin.selectHabitacion(' + habitaciones.indexOf(habitacion) + ');">' +
                '<td>' + habitacion.nombre + '</td>' +
                '<td>' + habitacion.tematica + '</td>' +
                '<td>' + habitacion.precioRenta + '</td>' +
                '<td>' + habitacion.estatus + '</td>' +'</tr>';
        cuerpo += registro;
    });
    //console.log(cuerpo);
    document.getElementById("tblHabitaciones").innerHTML = cuerpo;

}

export function selectHabitacion(index){
    setDetalleVisible(true);
    document.getElementById("txtIdHabitacion").value = habitaciones[index].idHabitacion;
    document.getElementById("txtCodigoUnico").value = habitaciones[index].codigoUnico;
    document.getElementById("txtNombre").value = habitaciones[index].nombre;
    document.getElementById("txtDescripcion").value = habitaciones[index].descripcion;
    document.getElementById("txtTematica").value = habitaciones[index].tematica;
    document.getElementById("txtPrecioRenta").value = habitaciones[index].precioRenta;
    document.getElementById("txtaCodigoImagen").value = habitaciones[index].fotografia;
    
    document.getElementById("btnDelete").classList.remove("disabled");
    document.getElementById("btnActivar").classList.remove("disabled");
    
    indexHabitacionesSeleccionado = index;
    let fotoB4C = "data:image/png;base64," +document.getElementById("txtaCodigoImagen").value;
   
    document.getElementById("imgFotoMos").src=fotoB4C;
}

export function delet() {
   if(habitaciones[indexHabitacionesSeleccionado].estatus===1){


    let datos = null;
    let params = null;
    //creamos objetos sin propiedades
    let habitacion = new Object();

    habitacion.idHabitacion = parseInt(document.getElementById("txtIdHabitacion").value);

    datos = {
        datosHabitacion: JSON.stringify(habitacion)
    };


    params = new URLSearchParams(datos);

    //como es un POST se coloca la ruta del servicio,despues va el parametro de tipo de metodo ,se establece las cabeceras de la peticion para que el metodo sepa como se esta condicionando y como se le mandaran los datos (estandar HTTP)

    fetch("api/habitacion/delete",
            {method: "POST",
                headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}, //cabecera de la peticcion, metodo de como estoy condicionando y como voy a mandar los datos
                body: params
            })
            .then(response => {
                return response.json();
            })
            .then(function (data)
            {
                if (data.exception != null)
                {
                    Swal.fire('', "Error interno del servidor. Intente nuevamente más tarde" + data.exception, 'error');
                    return;

                }
                if (data.error != null)
                {
                    Swal.fire('', data.error, 'warning');
                    return;
                }
                if (data.errorperm != null)
                {
                    Swal.fire('', "No tiene permiso para realizar esta operación.", 'warning');
                }
                // si no hay errores ahora se le colocan los ID 

                Swal.fire('', 'Datos de la habitación se ha eliminado', 'success');
                refrescarTabla();
                clean();
            });
    }else{Swal.fire('', 'El habitacón ya esta eliminado ', 'success');}
}

export function clean(){
    setDetalleVisible(true);
    document.getElementById("txtIdHabitacion").value = "";
    document.getElementById("txtCodigoUnico").value = "";
    document.getElementById("txtNombre").value = "";
    document.getElementById("txtDescripcion").value = "";
    document.getElementById("txtTematica").value = "";
    document.getElementById("txtPrecioRenta").value = "";
    document.getElementById("txtaCodigoImagen").value = "";
    
    document.getElementById("btnDelete").classList.add("disabled");
    document.getElementById("btnActivar").classList.add("disabled");
}


export function setDetalleVisible(valor) {
    if (valor) {
        document.getElementById("divDetalle").style.display="";
        document.getElementById("divCatalogo").style.display="none";
    }else{
        document.getElementById("divDetalle").style.display="none";
        document.getElementById("divCatalogo").style.display="";
    }
}

