//Crea una variable llamada index
let index;

//Esta funcion manda a llamar el formulario para que el administrador inicie sesion
function cargarformularioLoginAdmin(){
    //Busca el archivo que ingresamos
    fetch("formularioLoginAdmin.html")
            .then(
                function(response){
                    return response.text();
                }
            )
    //Despues mete al contenedor la vista y se le asigna su javascript el cual es el mismo
            .then(
                function(html){
                    document.getElementById("contenedorLoginAdmin").innerHTML=html;
                    import("../js/principal.js")
                            .then(
                                function(controller){
                                    moduloLoginAdmin=controller;
                                }
                            );
                }
            );
}

//Esta funcion manda a llamar la vista principal desde el formulario del administrador
function regresarPantallaAnterior(){
    //Busca el archivo que ingresamos
    fetch("index.html")
            .then(
                function(response){
                    return response.text();
                }
            )
    //Despues mete al contenedor la vista y se le asigna su javascript
            .then(
                function(html){
                    document.getElementById("contenedorPrincipal").innerHTML=html;
                    import("../js/principal.js")
                            .then(
                                function(controller){
                                    index=controller;
                                }
                            );
                }
            );
}

//Funcion para encriptar que funciona a sincrona de la funcion que la usa
async function encriptarContraseña(texto){
    const encoder=new TextEncoder();
    const data=encoder.encode(texto);
    const hash=await crypto.subtle.digest("SHA-256", data);
    const hashArray=Array.from(new Uint8Array(hash));
    const hashHex=hashArray.map((b)=>b.toString(16).padStart(2,"0")).join("");
    return hashHex;
}

//Funcion que toma los datos que le ingresamos al formulario, encripta la contraseña y convierte a json para validacion
//Una vez que valida se regresa el json y lo asigna al current user y nos manda a la pagina main del admin
function ingreso(){
    let usuario=document.getElementById("nombreDeUsuario").value;
    let contrasenia=document.getElementById("contrasenia").value;
    encriptarContraseña(contrasenia)
            .then((contraseñaEncriptada) => {
                alert(contraseñaEncriptada.toString());
                let datos=JSON.stringify({nombreDeUsuario:usuario, contrasenia:contraseñaEncriptada.toString()});
                console.log(datos);
                alert(datos.toString());
                params=new URLSearchParams({datos:datos});
                console.log(params);
                fetch("api/log/in",
                    {method:"POST",
                     headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'},
                     body:params
                })
                .then(response => {
                    return response.json();
                })
                .then(data => {
                    if(data.error){
                        alert("Error al iniciar sesión");
                    } else{
                        alert("Bienvenido "+usuario.toString());
                        console.log(data);
                        localStorage.setItem("currentUser", JSON.stringify(datos));
                        if(usuario.toLowerCase() !=="cliente"){
                            window.location.replace("modules/moduloAdministrador/moduloMain/vista_main.html");
                        } else{
                            window.location.replace("modules/moduloCliente/moduloMain/vista_main.html");
                        }
                    }
                });
    });
}
