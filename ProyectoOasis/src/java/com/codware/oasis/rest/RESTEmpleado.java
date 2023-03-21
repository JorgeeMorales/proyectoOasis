/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import org.utl.oasis.Core.ControllerEmpleado;
import org.utl.oasis.Empleado;

/**
 *
 * @author pasit
 */
@Path("empleado")
public class RESTEmpleado {
    
    @POST
    @Path("save")
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@FormParam("datosEmpleado") @DefaultValue("") String datosEmpleado){ //QueryParam no sirve para POST(Solo sirve para GET), y tenemos que utilizar FormParam cuando se utiliza POST
        
        String out = null; //Generamos un variable String
        Gson gson = new Gson(); //Generamos una varibale de tipo Gson
        Empleado emp = null; //Generamos una variable de tipo Empleado
        ControllerEmpleado ce = new ControllerEmpleado(); //Controler es donde se hace todas las funciones
        
        try{
          emp = gson.fromJson(datosEmpleado, Empleado.class); //Ocupamos gson para convertirlo Gson tiene una funcion que permite convertir una variable a una clase
            if (emp.getIdEmpleado() == 0) { // Realizamos un if para saber si existe el empledo, de la clase empleado tomamos el id y lo comparamos con 0
                ce.insert(emp); //si es igual a 0 insertamos el empleado
            }
            else
            {
                ce.update(emp); //Y si la id no es 0 se actualiza el Empleado
            }
            out = gson.toJson(emp); //Debolvemos una respuesta y se compierte a un String
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
    
    @POST
    @Path("delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@FormParam("datosEmpleado") @DefaultValue("") String datosEmpleado){ //QueryParam no sirve para POST(Solo sirve para GET), y tenemos que utilizar FormParam cuando se utiliza POST
        
        String out = null;
        Gson gson = new Gson();
        Empleado emp = null; //Generamos
        ControllerEmpleado cs = new ControllerEmpleado(); //Controler es donde se hace todas las funciones
        
        try{
          emp = gson.fromJson(datosEmpleado,Empleado.class); //Ocupamos gson para convertirlo Gson tiene una funcion que permite convertir una variable a una clase // Realizamos un if para saber si existe el empledo, de la clase empleado tomamos el id y lo comparamos con 0
                cs.delete(emp.getIdEmpleado()); //si es igual a 0 insertamos el empleado

            out = gson.toJson(emp); //Debolvemos una respuesta y se compierte a un String
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
    
    @GET
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll (@QueryParam("filtro") @DefaultValue("")String filtro){
        String out = null;
        ControllerEmpleado ce = null;
        List<Empleado> empleado = null;
        try{
            ce = new ControllerEmpleado();
            empleado = ce.getAll(filtro);
            out = new Gson().toJson(empleado);
        }
        catch(Exception e){
            e.printStackTrace();
            out= "{\"exception\":\"Error interno del servidor.\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
