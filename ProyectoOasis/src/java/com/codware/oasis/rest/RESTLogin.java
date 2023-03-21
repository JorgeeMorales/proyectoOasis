package com.codware.oasis.rest;


import org.utl.oasis.Core.ControllerLogin;
import org.utl.oasis.Empleado;
import org.utl.oasis.Usuario;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author rocha
 */
//Ponemos de nombre log a la clase para que en la url acceda a la clase
@Path("log")
public class RESTLogin{
    //Ponemos de nombre al metodo para que una vez que entre a la clase encuentre el metodo
    @Path("in")
    //Indica que el metodo anotado devuelve una respuesta en formato JSON
    @Produces(MediaType.APPLICATION_JSON)
    //Indica que se van a enviar datos
    @POST
    /*
        Metodo que devuelve una respuesta positiva o negativa dependiendo en si hay usuario o no 
    */
    public Response login(@FormParam("datos") @DefaultValue("") String datos) throws Exception{
        //Creamos un objeto Gson con el cual vamos a hacer o deshacer jsons
        Gson gson=new Gson();
        //Creamos una variable de tipo empleado
        Empleado emp=null;
        //Creamos una variable de tipo usuario que deshace un json llamado datos y los datos los manda a los atributos  de usuario 
        Usuario usuario=gson.fromJson(datos, Usuario.class);
        //Preparamos la variable con la que vamos a mandar un mensaje como respuesta
        String out="";
        //Llamaos al controllador
        ControllerLogin cl=new ControllerLogin();
        
        //Usa el metodo del controllador para ver si existe en la base de datos un registro con ese usuario y contraseña
        emp=cl.login(usuario.getNombreDeUsuario(), usuario.getContrasenia());
        
        //Intenta
        try{
            //Si el empleado regresa con registros
            if(emp!=null){
                //Al objeto emp se ejecuta el metodo settoken donde crea el token
                emp.getUsuario().setLastToken();
                //Mandamos de regreso el emp convirtienolo en json
                out=new Gson().toJson(emp);
                //Usamos el metodo generar token del controller para asignarle el token al usuario en la base de datos
                cl.generarToken(emp.getUsuario().getIdUsuario(), emp.getUsuario().getLastToken());
                //Si el empleado regresa sin registros
            } else{
                //Manda mensaje de salida que inidca que las credenciales son incorrectas
                out="""
                    {"error":"Datos de Acceso Incorrectos"}
                    """;
            }
            //Si el intento falla cae en la excepcion
        } catch(Exception e){
            //Imprime la traza de pila en la consola
            e.printStackTrace();
            //Manda un mensaje de error que indica que hubo error no por usuario y contraseña si no de sintaxis o servidor
            out="""
                {"exception":"?"}
                """;
            //Une el mensaje de error y la excepction
            out=String.format(out, e.toString());
        }
        //Regresa la respuesta 
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    //Ponemos de nombre al metodo para que una vez que entre a la clase encuentre el metodo
    @Path("out")
    //Indica que el metodo anotado devuelve una respuesta en formato JSON
    @Produces(MediaType.APPLICATION_JSON)
    //Indica que se van a enviar datos
    @POST
    /*
        Metodo que devuelve una respuesta positiva o negativa dependiendo en si pudo serrar sesion o no
    */
    public Response logout(@FormParam("empleado") @DefaultValue("") String empleado) throws Exception{
        //Preparamos la cadena que enviaremos de salida
        String out=null;
        //Creamos una variable de tipo empleado
        Empleado emp=null;
        //Mandamos a llamar el controllador
        ControllerLogin cl=new ControllerLogin();
        //Creamos una variable gson para hacer y deshacer jsons
        Gson gson=new Gson();
        //Le pasamos a emp el json deshecho con los datos del empleado en los setters y getters listos
        emp=gson.fromJson(empleado, Empleado.class);
        
        //Intenta
        try{
            //Si el metodo eliminar token del controlador es true
            if(cl.eliminarToken(emp)){
                //Envia el texto exitoso
                out="""
                    {"ok":"Eliminacion de Token se Realizo con Exito"}
                    """;
                //Si lo manda false
            } else{
                //Asigna y manda el mensaje de error
                out="""
                    {"error":"Error al Eliminar el Token"}
                    """;
            }
            //Si el intento falla cae en la excepcion
        } catch(JsonParseException jpe){
            //Manda un mensaje de error que indica que hubo error no por eliminar token si no de sintaxis o servidor
            out="""
                {"error":"No se pudo Eliminar el Token"}
                """;
            //Imprime la traza de pila en la consola
            jpe.printStackTrace();
        }
        //Regresa la respuesta 
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
