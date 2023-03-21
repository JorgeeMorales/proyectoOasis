
package com.codware.oasis.rest;


import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.utl.oasis.Core.ControllerProducto;
import org.utl.oasis.Producto;

/**
 *
 * @author mazu1
 */
@Path("producto")
public class RESTProducto {
     @POST
    @Path("save")
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@FormParam("datosProducto") @DefaultValue("") String datosProducto){ //QueryParam no sirve para POST(Solo sirve para GET), y tenemos que utilizar FormParam cuando se utiliza POST
        
        String out = null;
        Gson gson = new Gson();
        Producto p = null; //Generamos
         ControllerProducto cp = new ControllerProducto(); //Controler es donde se hace todas las funciones
        
        try{
            p = gson.fromJson(datosProducto, Producto.class);//Ocupamos gson para convertirlo Gson tiene una funcion que permite convertir una variable a una clase
            if (p.getIdProducto()== 0) { // Realizamos un if para saber si existe el empledo, de la clase empleado tomamos el id y lo comparamos con 0
                cp.insert(p); //si es igual a 0 insertamos el empleado
            }
            else
            {
                cp.update(p); //Y si la id no es 0 se actualiza el Empleado
            }
            out = gson.toJson(p); //Devolvemos una respuesta y se compierte a un String
        }
        catch(JsonParseException jpe){
            jpe.printStackTrace();
            
            out = """
                  {"exception": "Formato de Datos Incorrecta."}
                  """;
        }
        catch(Exception e){
            e.printStackTrace();
            out = """
                  {"exception" : "%s"}
                  """;
            out = String.format(out, e.toString());
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    //public Response delete(){}
    
    @POST
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll (@QueryParam("filtro") @DefaultValue("")String filtro){
        String out = null;
        ControllerProducto cp = null;
        List<Producto> productos = null;
        try{
            cp = new ControllerProducto();
            productos = cp.getAll(filtro);
            out = new Gson().toJson(productos);
        }
        catch(Exception e){
            e.printStackTrace();
            out= "{\"exception\":\"Error interno del servidor.\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    
     @POST
    @Path("delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@FormParam("datosProducto") @DefaultValue("") String datosProducto){ //QueryParam no sirve para POST(Solo sirve para GET), y tenemos que utilizar FormParam cuando se utiliza POST
        
        String out = null;
        Gson gson = new Gson();
        Producto producto = null; 
        ControllerProducto cp = new ControllerProducto(); //Controler es donde se hace todas las funciones
        
        try{
          producto = gson.fromJson(datosProducto,Producto.class); //Ocupamos gson para convertirlo Gson tiene una funcion que permite convertir una variable a una clase // Realizamos un if para saber si existe el empledo, de la clase empleado tomamos el id y lo comparamos con 0
                cp.delete(producto.getIdProducto());

            out = gson.toJson(producto); //Debolvemos una respuesta y se compierte a un String
        }
        catch(JsonParseException jpe){
            jpe.printStackTrace();
            //WebCraule
            out = """
                  {"exception": "Formato de Datos Incorrecta."}
                  """;
        }
        catch(Exception e){
            e.printStackTrace();
            out = """
                  {"exception" : "%s"}
                  """;
            out = String.format(out, e.toString());
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
}


   