let indexEmpleadoSeleccionado;
let empleados = [];

export function inicializar() {
    
        configureTableFilter(document.getElementById("txtBusquedaEmpleado"),
            document.getElementById("te"));
    refrescarTabla();
}
export function save() {
    alert("Hola desde Java");
    let datos = null;
    let params = null;

    let empleado = new Object(); //Se crea empleado de tipo Objecto de tipo Base
    empleado.usuario = new Object(); // Se crea un subObjecto dentro de empleado
    empleado.persona = new Object(); // Se crea un subObjecto dentro de empleado

    if (document.getElementById("txtCodigoEmpleado").value.trim().length < 1) { //Se verifica si tiene datos la caja de texto y si no tiene se le coloca la ids y los pone en 0
        empleado.idEmpleado = 0;
        empleado.persona.idPersona = 0;
        empleado.usuario.IdUsuario = 0;
    } else { // Y si tiene ID's se recogen sus ID'S de sus cajas de texto
        empleado.idEmpleado = parseInt(document.getElementById("txtCodigoEmpleado").value);
        empleado.persona.idPersona = parseInt(document.getElementById("txtCodigoPersona").value);
        empleado.usuario.idUsuario = parseInt(document.getElementById("txtCodigoUsuario").value);
    }//Se empieza a llenar los datos de la interfaz
    empleado.persona.nombre = document.getElementById("txtNombre").value;
    empleado.persona.apellidoPaterno = document.getElementById("txtApePaterno").value;
    empleado.persona.apellidoMaterno = document.getElementById("txtApeMaterno").value;
    empleado.persona.genero = document.getElementById("txtGenero").value;
    empleado.persona.fechaNacimiento = document.getElementById("txtFechaN").value;
    empleado.persona.calle = document.getElementById("txtCalle").value;
    empleado.persona.numero = document.getElementById("txtNumeroE").value;
    empleado.persona.colonia = document.getElementById("txtColonia").value;
    empleado.persona.cp = document.getElementById("txtCP").value;
    empleado.persona.ciudad = document.getElementById("txtCiudad").value;
    empleado.persona.estado = document.getElementById("txtEstado").value;
    empleado.persona.telCasa = document.getElementById("txtTelefono").value;
    empleado.persona.telMovil = document.getElementById("txtMovil").value;
    //empleado.persona.rfc = document.getElementById("txtRFC").value;
    empleado.persona.email = document.getElementById("txtCorreo").value;
    empleado.usuario.nombre = document.getElementById("txtUsuario").value;
    empleado.usuario.contrasenia = document.getElementById("txtContrasenia").value;
    empleado.usuario.rol = document.getElementById("txtRol").value;
    empleado.rol = document.getElementById("txtRol").value;
    empleado.numeroUnico = document.getElementById("txtNumeroUnico").value;

    datos = {//Se crea un JSON ponemos la variable  que se va a enviar al servicio
        datosEmpleado: JSON.stringify(empleado) //Y lo convertimos de tipo String
    };

    params = new URLSearchParams(datos); //Se cre una URL para poder enviarla al REST

    fetch("api/empleado/save", //Se pone la ruta del servicio
            {method: "POST", //el tipo de metodo que tenemos que definir que es POST si no se pone nada por default se Pone en GET
                headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}, // Es para que vea como le estoy enviando los datos el servicio (Cabezara del servico)
                body: params
            })
            .then(response => {
                return response.json();
            }) //Es respuesta Cruda
            .then(function (data) {
                if (data.exception != null) {
                    swal.fire('', 'Error interno del servidor. Intente nuevamente mas tarde.', 'error');
                    return;
                }
                if (data.error != null) {
                    swal.fire('', data.error, 'warning');
                    return;
                }
                if (data.errorperm != null) {
                    swal.fire('', 'No tiene permiso para realizae esta operacion.', 'warning');
                    return;
                }
                //Estamos aguarda las ID'S en las cajas de texto
                document.getElementById("txtCodigoEmpleado").value = data.idEmpleado;
                document.getElementById("txtCodigoUsuario").value = data.usuario.idUsuario;
                document.getElementById("txtCodigoPersona").value = data.persona.idPersona;
                document.getElementById("txtNumeroUnico").value = data.numeroUnico;
                //swal.fire('', 'Datos del empleado actualizados correctamente.', 'Success');
                refrescarTabla();
            });
}

export function addEmpleado() {
    let numero_unico_empleado,
            nombre,
            apellido_paterno,
            apellido_materno,
            genero,
            rfc,
            telefono,
            telefono_movil,
            correo_electronico;

    let valor = document.getElementById("txtCorreo").value;
    var exp = /^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$/;
    var esValido = exp.test(valor);

    numero_unico_empleado = document.getElementById("txtNumUnico").value;
    nombre = document.getElementById("txtNombre").value;
    apellido_paterno = document.getElementById("txtApePaterno").value;
    apellido_materno = document.getElementById("txtApeMaterno").value;
    telefono = document.getElementById("txtTelefono").value;
    telefono_movil = document.getElementById("txtMovil").value;
    correo_electronico = document.getElementById("txtCorreo").value;
    ///rfc = document.getElementById("txtRfc").value;

    if (nombre === "" || apellido_paterno === "" || telefono_movil === "" || correo_electronico === "") {

        swal('campos obligatorios vacios!', '', 'warning');
    } else {
        if (esValido === true) {
            let letras;

            if (apellido_materno === "") {
                letras = "X";
            } else {
                letras = apellido_materno.charAt(0);
            }
            console.log(letras);

            let letra1 = apellido_paterno.charAt(0);
            let letra2 = apellido_paterno.charAt(1);

            var today = new Date();
            let timestamp = today.toLocaleString();

            genero = document.getElementById("txtGenero").value;
            ;

            let empleado = {};
            empleado.numero_unico_empleado = letra1 + letra2 + letras + timestamp;
            empleado.nombre = nombre;
            empleado.apellido_paterno = apellido_paterno;
            empleado.apellido_materno = apellido_materno;
            empleado.telefono = telefono;
            empleado.telefono_movil = telefono_movil;
            empleado.correo_electronico = correo_electronico;
            empleado.rfc = rfc;
            empleado.genero = genero;
            empleado.estatus = "Activo";

            empleados.push(empleado);
            clean();
            loadTabla();
            swal("¡Guardado!", "Empleado", "success");

        } else {
               swal("La dirección de email no es válida.", "", "warning");
          }
    }
}

export function refrescarTabla() {
    //let url = "..api/empleado/getAll?token=" + currentUser.usuario.lastToken;
    let url = 'api/empleado/getAll';
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
                loadTabla(data);
            });
}

export function loadTabla(data) {
    empleados = data;
    let cuerpo = "";

    let resultadoEstatus = empleados.filter(element => element.estatus === "Activo");

    empleados.forEach(function (empleado) {
        let registro =
                '<tr onclick="moduloEmpleados.selectEmpleado(' + empleados.indexOf(empleado) + ');">' +
                '<td>' + empleado.persona.nombre + '</td>' +
                '<td>' + empleado.persona.apellidoPaterno + ' ' + empleado.persona.apellidoMaterno + '</td>' +
                '<td>' + empleado.persona.genero + '</td>' +
                '<td>' + empleado.persona.telCasa + '</td>' +
                '<td>' + empleado.persona.telMovil + '</td>' +
                '<td>' + empleado.estatus + '</td></tr>';
        cuerpo += registro;
    });
    document.getElementById("tblEmpleados").innerHTML = cuerpo;
}

export function selectEmpleado(index) {
    document.getElementById("txtNumeroUnico").value = empleados[index].numeroUnico;
    document.getElementById("txtCodigoEmpleado").value = empleados[index].idEmpleado;
    document.getElementById("txtCodigoPersona").value = empleados[index].persona.idPersona;
    document.getElementById("txtCodigoUsuario").value = empleados[index].usuario.idUsuario;
    
    
    
    document.getElementById("txtNombre").value = empleados[index].persona.nombre;
    document.getElementById("txtApePaterno").value = empleados[index].persona.apellidoPaterno;
    document.getElementById("txtApeMaterno").value = empleados[index].persona.apellidoMaterno;
    document.getElementById("txtGenero").value = empleados[index].persona.genero;
    document.getElementById("txtFechaN").value = empleados[index].persona.fechaNacimiento;
    document.getElementById("txtCalle").value = empleados[index].persona.calle;
    document.getElementById("txtNumeroE").value = empleados[index].persona.numero;
    document.getElementById("txtColonia").value = empleados[index].persona.colonia;
    document.getElementById("txtCP").value = empleados[index].persona.cp;
    document.getElementById("txtCiudad").value = empleados[index].persona.ciudad;
    document.getElementById("txtEstado").value = empleados[index].persona.estado;
    document.getElementById("txtTelefono").value = empleados[index].persona.telCasa;
    document.getElementById("txtMovil").value = empleados[index].persona.telMovil;
    document.getElementById("txtCorreo").value = empleados[index].persona.email;
    
    document.getElementById("txtUsuario").value = empleados[index].usuario.nombre;
    document.getElementById("txtContrasenia").value = empleados[index].usuario.contrasenia;
    document.getElementById("txtRol").value = empleados[index].usuario.rol;

    //
    //document.getElementById("btnDelete").classList.remove("disabled");
    //document.getElementById("btnAdd").classList.add("disabled");
    //document.getElementById("btnReactivar").classList.remove("disabled");
    indexEmpleadoSeleccionado = index;
}

export function clean() {
    document.getElementById("txtNumUnico").value = "";
    document.getElementById("txtNombre").value = "";
    document.getElementById("txtApePaterno").value = "";
    document.getElementById("txtApeMaterno").value = "";
    document.getElementById("txtTelefono").value = "";
    document.getElementById("txtMovil").value = "";
    document.getElementById("txtCorreo").value = "";
    document.getElementById("txtRfc").value = "";

    document.getElementById("txtNombre").focus();
    document.getElementById("btnUpdate").classList.add("disabled");
    document.getElementById("btnDelete").classList.add("disabled");
    document.getElementById("btnReactivar").classList.add("disabled");
    document.getElementById("btnAdd").classList.remove("disabled");
    indexEmpleadoSeleccionado = 0;
}

export function deleteEmpleadoo() {
       if (empleados[indexEmpleadoSeleccionado].estatus === 1){
        let datos = null;
        let params = null;
        
        let empleado = new Object();
        empleado.idEmpleado = parseInt(document.getElementById("txtCodigoEmpleado").value);
        datos = {
            datosEmpleado : JSON.stringify(empleado)
        };
        params = new URLSearchParams(datos);
            fetch("api/empleado/delete", //Se pone la ruta del servicio
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
                //Estamos aguarda las ID'S en las cajas de texto
//                document.getElementById("txtCodigoPersona").value = data.persona.idPersona;
//                document.getElementById("txtNumeroUnico").value = data.numeroUnico;
                //Swal.fire('', 'Datos del empleado actualizados correctamente.', 'Success');
                refrescarTabla();
                clean();
            });
}
}

export function updateEmpleado() {
    if (empleados[indexEmpleadoSeleccionado].estatus === "Inactivo") {
        swal("Acción no permitida", 'No puedes modificar un empleado eliminado', 'warning');
    } else {
        swal({
            title: "¿Estas Seguro?",
            text: "Una vez modificado, ¡no podrá salvar datos!",
            icon: "warning",
            buttons: true,
            dangerMode: true
        }).then((willDelete) => {
            if (willDelete) {
                empleados[indexEmpleadoSeleccionado].numero_unico_empleado = document.getElementById("txtNumUnico").value;
                empleados[indexEmpleadoSeleccionado].nombre = document.getElementById("txtNombre").value;
                empleados[indexEmpleadoSeleccionado].apellido_paterno = document.getElementById("txtApePaterno").value;
                empleados[indexEmpleadoSeleccionado].apellido_materno = document.getElementById("txtApeMaterno").value;
                empleados[indexEmpleadoSeleccionado].telefono = document.getElementById("txtTelefono").value;
                empleados[indexEmpleadoSeleccionado].telefono_movil = document.getElementById("txtMovil").value;
                empleados[indexEmpleadoSeleccionado].correo_electronico = document.getElementById("txtCorreo").value;
                empleados[indexEmpleadoSeleccionado].rfc = document.getElementById("txtRfc").value;
                empleados[indexEmpleadoSeleccionado].genero = document.getElementById("txtGenero").value;

                loadTabla();
                swal("Poof! Su archivo ha sido modificado", {
                    icon: "success"
                });
            } else {
                swal("Modificacion Cancelada");
            }
        });
    }
}

export function deleteEmpleado() {
    if (empleados[indexEmpleadoSeleccionado].estatus === "Activo") {
        swal({
            title: "¿Estas Seguro?",
            text: "Una vez eliminado, ¡no podrá salvar datos!",
            icon: "warning",
            buttons: true,
            dangerMode: true
        })
                .then((willDelete) => {
                    if (willDelete) {
                        empleados[indexEmpleadoSeleccionado].estatus = "Inactivo";
                        clean();
                        loadTabla();
                        swal("Poof! Su registro ha sido eliminado", {
                            icon: "success"
                        }
                        );
                    } else {
                        swal("Eliminacion Cancelada");
                    }
                });
    } else {
        swal("Ups, El empleado ya está eliminado", '', 'warning');
    }
}


//        fetch("modules/moduloEmpleados/data_Empleados.json")
//                .then(function (response) {
//                    return response.json();
//                })
//                .then(function (jsondata) {
//                    empleados = jsondata;
//                    console.log(empleados);
//                    loadTabla();
//                }
//                );


export function searchEmpleado() {
    let filtro = document.getElementById("txtBusquedaEmpleado").value;
    let filtroMinuscula = filtro.toLowerCase();

    if (filtro === "") {

        loadTabla();

    } else {



        let resultados = empleados.filter(element => element.nombre.toLowerCase() === filtroMinuscula);
        let resultados2 = empleados.filter(element => element.apellido_paterno.toLowerCase() === filtroMinuscula);
        let resultados3 = empleados.filter(element => element.apellido_materno.toLowerCase() === filtroMinuscula);
        let resultados4 = empleados.filter(element => element.genero.toLowerCase() === filtroMinuscula);
        let resultados5 = empleados.filter(element => element.telefono_movil === filtro);
        let resultados6 = empleados.filter(element => element.telefono === filtro);
        let resultados7 = empleados.filter(element => element.correo_electronico.toLowerCase() === filtroMinuscula);
        let resultados8 = empleados.filter(element => element.rfc.toLowerCase() === filtroMinuscula);
        let resultados9 = empleados.filter(element => element.numero_unico_empleado === filtro);
        let resultados10 = empleados.filter(element => element.estatus.toLowerCase() === filtroMinuscula);

        let cuerpo = "";
        resultados.forEach(function (empleado) {
            let registro =
                    '<tr onclick="moduloEmpleado.selectEmpleado(' + empleados.indexOf(empleado) + ');">' +
                    '<td>' + empleado.nombre + '</td>' +
                    '<td>' + empleado.apellido_paterno + ' ' + empleado.apellido_materno + '</td>' +
                    '<td>' + empleado.genero + '</td>' +
                    '<td>' + empleado.telefono + '</td>' +
                    '<td>' + empleado.telefono_movil + '</td>' +
                    '<td>' + empleado.estatus + '</td></tr>';
            cuerpo += registro;
        });


        resultados2.forEach(function (empleado) {
            let registro =
                    '<tr onclick="moduloEmpleado.selectEmpleado(' + empleados.indexOf(empleado) + ');">' +
                    '<td>' + empleado.nombre + '</td>' +
                    '<td>' + empleado.apellido_paterno + ' ' + empleado.apellido_materno + '</td>' +
                    '<td>' + empleado.genero + '</td>' +
                    '<td>' + empleado.telefono + '</td>' +
                    '<td>' + empleado.telefono_movil + '</td>' +
                    '<td>' + empleado.estatus + '</td></tr>';
            cuerpo += registro;
        });


        resultados3.forEach(function (empleado) {
            let registro =
                    '<tr onclick="moduloEmpleado.selectEmpleado(' + empleados.indexOf(empleado) + ');">' +
                    '<td>' + empleado.nombre + '</td>' +
                    '<td>' + empleado.apellido_paterno + ' ' + empleado.apellido_materno + '</td>' +
                    '<td>' + empleado.genero + '</td>' +
                    '<td>' + empleado.telefono + '</td>' +
                    '<td>' + empleado.telefono_movil + '</td>' +
                    '<td>' + empleado.estatus + '</td></tr>';
            cuerpo += registro;
        });


        resultados4.forEach(function (empleado) {
            let registro =
                    '<tr onclick="moduloEmpleado.selectEmpleado(' + empleados.indexOf(empleado) + ');">' +
                    '<td>' + empleado.nombre + '</td>' +
                    '<td>' + empleado.apellido_paterno + ' ' + empleado.apellido_materno + '</td>' +
                    '<td>' + empleado.genero + '</td>' +
                    '<td>' + empleado.telefono + '</td>' +
                    '<td>' + empleado.telefono_movil + '</td>' +
                    '<td>' + empleado.estatus + '</td></tr>';
            cuerpo += registro;
        });


        resultados5.forEach(function (empleado) {
            let registro =
                    '<tr onclick="moduloEmpleado.selectEmpleado(' + empleados.indexOf(empleado) + ');">' +
                    '<td>' + empleado.nombre + '</td>' +
                    '<td>' + empleado.apellido_paterno + ' ' + empleado.apellido_materno + '</td>' +
                    '<td>' + empleado.genero + '</td>' +
                    '<td>' + empleado.telefono + '</td>' +
                    '<td>' + empleado.telefono_movil + '</td>' +
                    '<td>' + empleado.estatus + '</td></tr>';
            cuerpo += registro;
        });


        resultados6.forEach(function (empleado) {
            let registro =
                    '<tr onclick="moduloEmpleado.selectEmpleado(' + empleados.indexOf(empleado) + ');">' +
                    '<td>' + empleado.nombre + '</td>' +
                    '<td>' + empleado.apellido_paterno + ' ' + empleado.apellido_materno + '</td>' +
                    '<td>' + empleado.genero + '</td>' +
                    '<td>' + empleado.telefono + '</td>' +
                    '<td>' + empleado.telefono_movil + '</td>' +
                    '<td>' + empleado.estatus + '</td></tr>';
            cuerpo += registro;
        });


        resultados7.forEach(function (empleado) {
            let registro =
                    '<tr onclick="moduloEmpleado.selectEmpleado(' + empleados.indexOf(empleado) + ');">' +
                    '<td>' + empleado.nombre + '</td>' +
                    '<td>' + empleado.apellido_paterno + ' ' + empleado.apellido_materno + '</td>' +
                    '<td>' + empleado.genero + '</td>' +
                    '<td>' + empleado.telefono + '</td>' +
                    '<td>' + empleado.telefono_movil + '</td>' +
                    '<td>' + empleado.estatus + '</td></tr>';
            cuerpo += registro;
        });


        resultados8.forEach(function (empleado) {
            let registro =
                    '<tr onclick="moduloEmpleado.selectEmpleado(' + empleados.indexOf(empleado) + ');">' +
                    '<td>' + empleado.nombre + '</td>' +
                    '<td>' + empleado.apellido_paterno + ' ' + empleado.apellido_materno + '</td>' +
                    '<td>' + empleado.genero + '</td>' +
                    '<td>' + empleado.telefono + '</td>' +
                    '<td>' + empleado.telefono_movil + '</td>' +
                    '<td>' + empleado.estatus + '</td></tr>';
            cuerpo += registro;
        });


        resultados9.forEach(function (empleado) {
            let registro =
                    '<tr onclick="moduloEmpleado.selectEmpleado(' + empleados.indexOf(empleado) + ');">' +
                    '<td>' + empleado.nombre + '</td>' +
                    '<td>' + empleado.apellido_paterno + ' ' + empleado.apellido_materno + '</td>' +
                    '<td>' + empleado.genero + '</td>' +
                    '<td>' + empleado.telefono + '</td>' +
                    '<td>' + empleado.telefono_movil + '</td>' +
                    '<td>' + empleado.estatus + '</td></tr>';
            cuerpo += registro;
        });

        resultados10.forEach(function (empleado) {
            let registro =
                    '<tr onclick="moduloEmpleado.selectEmpleado(' + empleados.indexOf(empleado) + ');">' +
                    '<td>' + empleado.nombre + '</td>' +
                    '<td>' + empleado.apellido_paterno + ' ' + empleado.apellido_materno + '</td>' +
                    '<td>' + empleado.genero + '</td>' +
                    '<td>' + empleado.telefono + '</td>' +
                    '<td>' + empleado.telefono_movil + '</td>' +
                    '<td>' + empleado.estatus + '</td></tr>';
            cuerpo += registro;
        });

        console.log(cuerpo);
        document.getElementById("tblEmpleados").innerHTML = cuerpo;
    }
}

export function reactivar() {
    if (empleados[indexEmpleadoSeleccionado].estatus === "Inactivo") {
        swal({
            title: "¿Estas Seguro?",
            text: "Una vez activado, ¡no podrá salvar datos!",
            icon: "warning",
            buttons: true,
            dangerMode: true
        })
                .then((willDelete) => {
                    if (willDelete) {
                        empleados[indexEmpleadoSeleccionado].estatus = "Activo";
                        clean();
                        loadTabla();
                        swal("El empleado ha sido Activado", {
                            icon: "success"
                        }
                        );
                    } else {
                        swal("Activación Cancelada");
                    }
                });
    } else {
        swal("El empleado ya está activado", '', 'warning');
    }
}

export function activo() {


    let cuerpo = "";
    let resultadoEstatus = empleados.filter(element => element.estatus === "Activo");

    resultadoEstatus.forEach(function (empleado) {
        let registro =
                '<tr onclick="moduloEmpleados.selectEmpleado(' + empleados.indexOf(empleado) + ');">' +
                '<td>' + empleado.nombre + '</td>' +
                '<td>' + empleado.apellido_paterno + ' ' + empleado.apellido_materno + '</td>' +
                '<td>' + empleado.genero + '</td>' +
                '<td>' + empleado.telefono + '</td>' +
                '<td>' + empleado.telefono_movil + '</td>' +
                '<td>' + empleado.estatus + '</td></tr>';
        cuerpo += registro;
    });
    document.getElementById("tblEmpleados").innerHTML = cuerpo;
}


export function inactivo() {


    let cuerpo = "";
    let resultadoEstatus = empleados.filter(element => element.estatus === "Inactivo");

    resultadoEstatus.forEach(function (empleado) {
        let registro =
                '<tr onclick="moduloEmpleados.selectEmpleado(' + empleados.indexOf(empleado) + ');">' +
                '<td>' + empleado.nombre + '</td>' +
                '<td>' + empleado.apellido_paterno + ' ' + empleado.apellido_materno + '</td>' +
                '<td>' + empleado.genero + '</td>' +
                '<td>' + empleado.telefono + '</td>' +
                '<td>' + empleado.telefono_movil + '</td>' +
                '<td>' + empleado.estatus + '</td></tr>';
        cuerpo += registro;
    });
    document.getElementById("tblEmpleados").innerHTML = cuerpo;
}


