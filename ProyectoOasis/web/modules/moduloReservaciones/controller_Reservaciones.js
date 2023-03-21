let indexMaterialSeleccionado;
let materiales = [];



export function inicializar() {
    configureTableFilter(document.getElementById("txtBusquedaMaterial"),
            document.getElementById("tm"));
    refrescarTabla();
}

export function save() {  //funcino para salvar Materiales
    //alert("Hola desde Jva");
    let datos = null;
    let params = null;

    let material = new Object(); //Se crea material de tipo Objecto de tipo Base

    if (document.getElementById("txtCodigoMaterial").value.trim().length < 1) { //Se verifica si tiene datos la caja de texto y si no tiene se le coloca la ids y los pone en 0
        material.idMaterial = 0;
        
    } else { // Y si tiene ID's se recogen sus ID'S de sus cajas de texto
        material.idMaterial = parseInt(document.getElementById("txtCodigoMaterial").value);
        
    }//Se empieza a llenar los datos de la interfaz
    material.nombre = document.getElementById("txtNombreMaterial").value;
    material.precioCompra = parseFloat(document.getElementById("txtPrecioC").value);
    material.precioVenta = document.getElementById("txtPrecioV").value;
    
    datos = {//Se crea un JSON ponemos la variable  que se va a enviar al servicio
        datosMaterial: JSON.stringify(material) //Y lo convertimos de tipo String
    };

    params = new URLSearchParams(datos); //Se cre una URL para poder enviarla al REST

    fetch("api/material/save", //Se pone la ruta del servicio
            {method: "POST", //el tipo de metodo que tenemos que definir que es POST si no se pone nada por default se Pone en GET
                headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}, // Es para que vea como le estoy enviando los datos el servicio (Cabezara del servico)
                body: params
            })
            .then(response => {
                return response.json();
            }) //Es respuesta Cruda
            .then(function (data) {
                if (data.exception != null) {
                    Swal.fire('', 'Error interno del servidor. Intente nuevamente mas tarde.', 'error');
                    return;
                }
                if (data.error != null) {
                    Swal.fire('', data.error, 'warning');
                    return;
                }
                if (data.errorperm != null) {
                    Swal.fire('', 'No tiene permiso para realizae esta operacion.', 'warning');
                    return;
                }
                //Guardamos las ID'S en las cajas de texto
                document.getElementById("txtCodigoMaterial").value = data.idMaterial;
                //Swal.fire('', 'Datos del empleado actualizados correctamente.', 'Success');
                refrescarTabla();
                cleanMateriales();
            });
}


export function eliminar(){  //funcion para eliminar materiales (no funciona)
    
    //alert("la funcion si funciona");
   
        let datos = null;
        let params = null;
        
        let material = new Object();
        material.idMaterial = parseInt(document.getElementById("txtCodigoMaterial").value);
        datos = {
            datosMaterial: JSON.stringify(material)
        };
        params = new URLSearchParams(datos);
            fetch("api/material/delete", //Se pone la ruta del servicio
            {method: "POST", //el tipo de metodo que tenemos que definir que es POST si no se pone nada por default se Pone en GET
                headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}, // Es para que vea como le estoy enviando los datos el servicio (Cabezara del servico)
                body: params
            })
            .then(response => {
                return response.json();
            }) //Es respuesta Cruda
            .then(function (data) {
                if (data.exception != null) {
                   // Swal.fire('', 'Error interno del servidor. Intente nuevamente mas tarde.', 'error');
                    return;
                }
                if (data.error != null) {
                    Swal.fire('', data.error, 'warning');
                    return;
                }
                if (data.errorperm != null) {
                    Swal.fire('', 'No tiene permiso para realizae esta operacion.', 'warning');
                    return;
                }
                //Estamos guarda las ID'S en las cajas de texto
//                document.getElementById("txtCodigoPersona").value = data.persona.idPersona;
//                document.getElementById("txtNumeroUnico").value = data.numeroUnico;
                //Swal.fire('', 'Datos del empleado actualizados correctamente.', 'Success');
                    refrescarTabla();
                clean();
            });


}

export function refrescarTabla() {  //funcion para refrescar la tabla y este actualizada
    //let url = "..api/empleado/getAll?token=" + currentUser.usuario.lastToken;
    let url = 'api/material/getAll';
    fetch(url)
            .then(response => {
                return response.json();
            })
            .then(function (data) {
                if (data.exception != null) {
                    Swal.fire('', 'Error interno del servidor. Intente nuevamente mas tarde.', 'error');
                    return;
                }

                if (data.error != null) {
                    Swal.fire('', data.error, 'warning');
                    return;
                }
                if (data.errorsec != null) {
                    Swal.fire('', data.errorsec, 'error');
                    window.location.replace('index.html');
                    return;
                }
                cargarTabla(data);
            });
}

export function cargarTabla(data) {   //funcion para cargar la tabla 
    materiales = data;
    let cuerpo = "";

    //let resultadoEstatus = materiales.filter(element => element.estatus === "Activo");

    materiales.forEach(function (material) {
        let registro =
                '<tr onclick="moduloMateriales.selectMaterial(' + materiales.indexOf(material) + ');">' +
                '<td>' + material.nombre + '</td>' +
                '<td>' + material.precioVenta + '</td>' +
                '<td>' + material.precioCompra + '</td>' +
                '<td>' + material.Estatus + '</td></tr>';
        cuerpo += registro;
    });
    
    document.getElementById("tblMateriales").innerHTML = cuerpo;
}

/*export function addMaterial() {
    let     nombre,
            precioV,
            precioC;
            
    nombre      = document.getElementById("txtNombreMaterial").value;
    precioV     = document.getElementById("txtPrecioV").value;
    precioC     = document.getElementById("txtPrecioC").value;
    
    if (nombre === "" || precioV === "" || precioC === ""){
         swal('campos obligatorios vacios!', '', 'warning');
        }else{
            let material ={};
            material.nombre     = nombre;
            material.precioV    = precioV;
            material.precioC    = precioC;
            material.estatus    = "Activo";

            materiales.push(material);
            cleanMateriales();
            loadTablaMateriales();
            swal("¡Guardado!", "Material registrado con Exito", "success");
    }
}*/

export function loadTablaMateriales() {
     let select = document.getElementById("select");
    let filtrito = select.options[select.selectedIndex].text;
    if (filtrito === "Activo"){
        
        activo();
        
    } if(filtrito === "Inactivo"){
        
        inactivo();
    }
    
}

export function cleanMateriales() {
    document.getElementById("txtCodigoMaterial").value="";
    document.getElementById("txtNombreMaterial").value="";
    document.getElementById("txtPrecioV").value="";
    document.getElementById("txtPrecioC").value="";
    
    //document.getElementById("btnUpdate").classList.add("disabled");
    
    //document.getElementById("btnDelete").classList.add("disabled");
    //document.getElementById("btnReactivar").classList.add("disabled");
    //document.getElementById("btnAdd").classList.remove("disabled");
}

export function deleteMaterial() {
    if(materiales[indexMaterialSeleccionado].estatus === "Activo"){
        swal({
        title: "¿Estas Seguro?",
        text: "Una vez eliminado, ¡no podrá salvar datos!",
        icon: "warning",
        buttons: true,
        dangerMode: true
    }).then((willDelete) => {
            if (willDelete) {
        materiales[indexMaterialSeleccionado].estatus = "Inactivo";
        cleanMateriales();
        loadTablaMateriales();
        swal("Poof! Su registro ha sido eliminado", {
                            icon: "success"
                        }
                        );
                    } else {
                        swal("Eliminacion Cancelada");
                    }
                });
    }else{
        swal("Ups, El material ya está eliminado",'','warning');
    }
}



export function selectMaterial(index) {
    document.getElementById("txtNombreMaterial").value=materiales[index].nombre;
    document.getElementById("txtPrecioV").value=materiales[index].precioVenta;
    document.getElementById("txtPrecioC").value=materiales[index].precioCompra;
    document.getElementById("txtCodigoMaterial").value = materiales[index].idMaterial;
    
    document.getElementById("btnDelete").classList.remove("disabled");
    document.getElementById("btnAdd").classList.remove("disabled");
    indexMaterialSeleccionado=index;
}

export function updateMaterial() {
    if(materiales[indexMaterialSeleccionado].estatus === "Inactivo"){
        swal("Acción no permitida", 'No puedes modificar un material eliminado', 'warning');
    }else{
        swal({
        title: "¿Estas Seguro?",
        text: "Una vez modificado, ¡no podrá salvar datos!",
        icon: "warning",
        buttons: true,
        dangerMode: true
    }).then((willDelete) => {
                if (willDelete) {
        materiales[indexMaterialSeleccionado].nombre = document.getElementById("txtNombreMaterial").value;
        materiales[indexMaterialSeleccionado].precioV = document.getElementById("txtPrecioV").value;
        materiales[indexMaterialSeleccionado].precioC = document.getElementById("txtPrecioC").value;
        cleanMateriales();
        loadTablaMateriales();
        swal("Poof! Su registro ha sido modificado", {
            icon: "success"
            });
            } else {
                swal("Modificacion Cancelada");
            }
        });
    }
}


export function searchMaterial(){
    let filtro = document.getElementById("txtBusquedaMaterial").value;
    let filtroMinuscula = filtro.toLowerCase();
    if(filtro ===  ""){
        
        loadTablaMateriales();
        
    }else{
    
    
    
    let resultadoNombre = materiales.filter(element => element.nombre.toLowerCase() === filtroMinuscula);
    let resultadoPV= materiales.filter(element => element.precioV.toLowerCase() === filtroMinuscula);
    let resultadoPC= materiales.filter(element => element.precioC.toLowerCase() === filtroMinuscula);
    let resultadoE= materiales.filter(element => element.estatus.toLowerCase() === filtroMinuscula);
    let cuerpo = "";
    resultadoNombre.forEach(function (material){
        let registro =  
                '<tr onclick="moduloMateriales.selectMaterial('+materiales.indexOf(material)+');">'+
                '<td>'  + material.nombre  +            '</td>' +
                '<td>'  + material.precioV +            '</td>' +
                '<td>'  + material.precioC +            '</td>' +
                '<td>'  + material.estatus +            '</td></tr>';
        cuerpo += registro;
    });
    
    resultadoPV.forEach(function (material){
        let registro =  
                '<tr onclick="moduloMateriales.selectMaterial('+materiales.indexOf(material)+');">'+
                '<td>'  + material.nombre  +            '</td>' +
                '<td>'  + material.precioV +            '</td>' +
                '<td>'  + material.precioC +            '</td>' +
                '<td>'  + material.estatus +            '</td></tr>';
        cuerpo += registro;
    });
    
    resultadoPC.forEach(function (material){
        let registro =  
                '<tr onclick="moduloMateriales.selectMaterial('+materiales.indexOf(material)+');">'+
                '<td>'  + material.nombre  +            '</td>' +
                '<td>'  + material.precioV +            '</td>' +
                '<td>'  + material.precioC +            '</td>' +
                '<td>'  + material.estatus +            '</td></tr>';
        cuerpo += registro;
    });
    
    resultadoE.forEach(function (material){
        let registro =  
                '<tr onclick="moduloMateriales.selectMaterial('+materiales.indexOf(material)+');">'+
                '<td>'  + material.nombre  +            '</td>' +
                '<td>'  + material.precioV +            '</td>' +
                '<td>'  + material.precioC +            '</td>' +
                '<td>'  + material.estatus +            '</td></tr>';
        cuerpo += registro;
    });
    
    console.log(cuerpo);
    document.getElementById("tblMateriales").innerHTML = cuerpo;
    }
}

export function reactivar(){
   if (materiales[indexMaterialSeleccionado].estatus === "Inactivo") {
        swal({ 
            title: "¿Estas Seguro?",
            text: "Una vez activado, ¡no podrá salvar datos!",
            icon: "warning",
            buttons: true,
            dangerMode: true
        })
                .then((willDelete) => {
                    if (willDelete) {
                        materiales[indexMaterialSeleccionado].estatus = "Activo";
                        cleanMateriales();
                        loadTablaMateriales();
                        swal("La solucion ha sido Activado", {
                            icon: "success"
                        }
                        );
                    } else {
                        swal("Activación Cancelada");
                    }
                });
    } else {
        swal("El material ya está activado", '', 'warning');
    }
}


export function activo(){
    
     
     let cuerpo = "";
        let resultadoEstatus = materiales.filter(element => element.estatus === "Activo");
        
         resultadoEstatus.forEach(function (material){
        let registro =  
                '<tr onclick="moduloMateriales.selectMaterial('+materiales.indexOf(material)+');">'+
                '<td>'  + material.nombre  +            '</td>' +
                '<td>'  + material.precioV +            '</td>' +
                '<td>'  + material.precioC +            '</td>' +
                '<td>'  + material.estatus +            '</td></tr>';
        cuerpo += registro;
    });
     document.getElementById("tblMateriales").innerHTML = cuerpo;
}


export function inactivo(){
    
     
     let cuerpo = "";
        let resultadoEstatus = materiales.filter(element => element.estatus === "Inactivo");
        
          resultadoEstatus.forEach(function (material){
        let registro =  
                '<tr onclick="moduloMateriales.selectMaterial('+materiales.indexOf(material)+');">'+
                '<td>'  + material.nombre  +            '</td>' +
                '<td>'  + material.precioV +            '</td>' +
                '<td>'  + material.precioC +            '</td>' +
                '<td>'  + material.estatus +            '</td></tr>';
        cuerpo += registro;
    });
     document.getElementById("tblMateriales").innerHTML = cuerpo;
}