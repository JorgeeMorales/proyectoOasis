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
import org.utl.oasis.Cliente;
import org.utl.oasis.Core.ControllerCliente;
/**
 *
 * @author pasit
 */
@Path("cliente")
public class RESTCliente {
    @POST
    @Path("save")
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@FormParam("datosCliente") @DefaultValue("") String datosCliente){ //QueryParam no sirve para POST(Solo sirve para GET), y tenemos que utilizar FormParam cuando se utiliza POST
        
        String out = null;
        Gson gson = new Gson();
        Cliente cli = null; //Generamos
        ControllerCliente cc = new ControllerCliente(); //Controler es donde se hace todas las funciones
        
        try{
          cli = gson.fromJson(datosCliente, Cliente.class); //Ocupamos gson para convertirlo Gson tiene una funcion que permite convertir una variable a una clase
            if (cli.getIdCliente() == 0) { // Realizamos un if para saber si existe el cliente, de la clase cliente tomamos el id y lo comparamos con 0
                cc.insert(cli); //si es igual a 0 insertamos el cliente
            }
            else
            {
                cc.update(cli); //Y si la id no es 0 se actualiza el Cliente
            }
            out = gson.toJson(cli); //Debolvemos una respuesta y se compierte a un String
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
    
    //public Response delete(){}
    
    @POST
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll (@QueryParam("filtro") @DefaultValue("")String filtro){
        String out = null;
        ControllerCliente ce = null;
        List<Cliente> cliente = null;
        try{
            ce = new ControllerCliente();
            cliente = ce.getAll(filtro);
            out = new Gson().toJson(cliente);
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
    public Response delete(@FormParam("datosCliente") @DefaultValue("") String datosCliente){
        String out = null;
        Gson gson = new Gson();
        Cliente cli = null; 
        ControllerCliente cc = new ControllerCliente(); 
        try{
          cli = gson.fromJson(datosCliente, Cliente.class); 
                cc.delete(cli.getIdCliente()); 
            out = gson.toJson(cli); 
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
    
    @POST
    @Path("activate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response active(@FormParam("datosCliente") @DefaultValue("") String datosCliente){
        String out = null;
        Gson gson = new Gson();
        Cliente cli = null; 
        ControllerCliente cc = new ControllerCliente(); 
        try{
          cli = gson.fromJson(datosCliente, Cliente.class); 
                cc.activate(cli.getIdCliente()); 
            out = gson.toJson(cli); 
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
    
}
