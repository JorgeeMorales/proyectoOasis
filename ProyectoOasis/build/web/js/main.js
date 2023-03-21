let moduloSoluciones;
let moduloReservacion;
let moduloHabitaciones;
let moduloHabitacionesAdmin;
let moduloIniciarSesion;

//function cargarModuloSesion() {
//    fetch("./index.html")
//            .then(
//                    function (response) {
//                        return response.text();
//                    }
//            );
//}

function cargarModuloReservaciones() {
    fetch("modules/moduloReservaciones/view_Reservaciones.html")
            .then(
                    function (response) {
                        return response.text();
                    }
            )
            .then(
                    function (html) {
                        document.getElementById("contenedorPrincipal").innerHTML = html;
                        import("../modules/moduloReservaciones/controller_Reservaciones.js").then(
                                function (controller) {
                                    moduloReservacion = controller;
                                    moduloReservacion.inicializar();    //Mandamos llamar la función inicializar
                                }
                        );
                    }
            );
}

function cargarModuloHabitaciones() {
    fetch("modules/moduloHabitaciones/view_Habitaciones.html")
            .then(
                    function (response) {
                        return response.text();
                    }
            )
            .then(
                    function (html) {
                        document.getElementById("contenedorPrincipal").innerHTML = html;
                        import("../modules/moduloHabitaciones/controller_Habitaciones.js").then(
                                function (controller) {
                                    moduloHabitaciones = controller;
                                    moduloHabitaciones.inicializar();
                                }
                        );
                    }
            );
}

function cargarModuloHabitacionesAdmin() {
    fetch("modules/moduloHabitacionesAdmin/view_HabitacionesAdmin.html")
            .then(
                    function (response) {
                        return response.text();
                    }
            )
            .then(
                    function (html) {
                        document.getElementById("contenedorPrincipal").innerHTML = html;
                        import("../modules/moduloHabitacionesAdmin/controller_HabitacionesAdmin.js").then(
                                function (controller) {
                                    moduloHabitacionesAdmin = controller;
                                    moduloHabitacionesAdmin.inicializar();
                                }
                        );
                    }
            );
}


//function cargarModuloSoluciones() {
//    fetch("modules/ModuloSoluciones/Vista_Soluciones.html")
//            .then(
//                    function (response) {
//                        return response.text();
//                    }
//            )
//            .then(
//                    function (html) {
//                        document.getElementById("contenedorPrincipal").innerHTML = html;
//                        import("../modules/ModuloSoluciones/controller_Soluciones.js").then(
//                                function (controller) {
//                                    moduloSoluciones = controller;
//                                    moduloSoluciones.inicializar();
//
//                                }
//                        );
//                    }
//            );
//}
//;
//
//function cargarModuloArmazones() {
//    fetch("modules/ModuloArmazones/Vista_Armazones.html")
//            .then(
//                    function (response) {
//                        return response.text();
//                    }
//            )
//            .then(
//                    function (html) {
//                        document.getElementById("contenedorPrincipal").innerHTML = html;
//                        import("../modules/ModuloArmazones/fuctionArmazones.js").then(
//                                function (controller) {
//                                    moduloArmazones = controller;
//                                    moduloArmazones.inicializar();
//                                }
//                        );
//                    }
//            );
//}
//;
//
//function cargarModuloLentes() {
//    fetch("modules/ModuloLentesContacto/view_C.html")
//            .then(
//                    function (response) {
//                        return response.text();
//                    }
//            )
//            .then(
//                    function (html) {
//                        document.getElementById("contenedorPrincipal").innerHTML = html;
//                        import("../modules/ModuloLentesContacto/fuction_LenteContacto.js").then(
//                                function (controller) {
//                                    moduloLentesContacto = controller;
//                                    moduloLentesContacto.inicializar();
//                                }
//                        );
//                    }
//            );
//}
//;
//
//function cargarModuloMateriales() {
//    fetch("modules/moduloMateriales/view_Materiales.html")
//            .then(
//                    function (response) {
//                        return response.text();
//                    }
//            )
//            .then(
//                    function (html) {
//                        document.getElementById("contenedorPrincipal").innerHTML = html;
//                        import("../modules/moduloMateriales/controller_Materiales.js").then(
//                                function (controller) {
//                                    moduloMateriales = controller;
//                                    moduloMateriales.inicializar();
//                                }
//                        );
//                    }
//            );
//}
//;
//
//
