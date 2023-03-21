/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utl.oasis.Core;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.utl.oasis.Cliente;
import org.utl.oasis.ConexionBD.ConexionBD;
import org.utl.oasis.Habitacion;
import org.utl.oasis.Persona;

/**
 *
 * @author pasit
 */
public class ControllerCliente {
    public int insert(Cliente c) throws Exception
    {
        //parametros - se guarda en una variable tipo string
        //Definimos la consulta SQL que invoca al Stored Procedure:
        String sql =    "{call insertarCliente(?, ?, ?, ?, " + // Datos Personales
                                               "?, ?)}";  // Valores de Retorno
        
        //creando variables
        //Aquí guardaremos los ID's que se generarán:
        //variables fuera de rango
        int idPersonaGenerado = -1;
        int idClienteGenerado = -1;
        
        //Creamos objeto con el que nos vamos a conectar a la Base de Datos:
        ConexionBD connMySQL = new ConexionBD();
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();
        
        //Con este objeto invocaremos al StoredProcedure:
        CallableStatement cstmt = conn.prepareCall(sql);// Ejecutar instrucciones no SQL, ejemplo procedimentos almanecados
        
        //Establecemos los valores de los parámetros de los datos personales en el orden
        //en el ordren en el que los pide el procedimiento almacenado, comenzando en 1:
        cstmt.setString(1, c.getPersona().getNombre());
        cstmt.setString(2, c.getPersona().getPrimerApellido());
        cstmt.setString(3, c.getPersona().getTelCelular());
        cstmt.setString(4, c.getPersona().getCorreo());
        
        //Registramos los parámetros de salida:
        cstmt.registerOutParameter(5, Types.INTEGER);
        cstmt.registerOutParameter(6, Types.INTEGER);
        
        //Ejecutamos el Stored Procedure:
        cstmt.executeUpdate(); // Ejecutar instrucciones Delete, Update, insert 
        //Para este punto ya se guardo en la base de datos
        //Recuperamos los ID's generados:
        idPersonaGenerado = cstmt.getInt(5);
        idClienteGenerado = cstmt.getInt(6);

        
        c.setIdCliente(idClienteGenerado);
        c.getPersona().setIdPersona(idPersonaGenerado);
        
        cstmt.close();
        connMySQL.close();
        
        //Devolvemos el ID de Cliente generado:
        return idClienteGenerado;
    }
    
    public void update(Cliente c) throws Exception
    {
        //Definimos la consulta SQL que invoca al Stored Procedure:
         String sql =    "{call actualizarCliente(?, ?, ?, ?, " + // Datos Personales
                                               "?)}"; 
        
        
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionBD connMySQL = new ConexionBD();
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();
        
        //Con este objeto invocaremos al StoredProcedure:
        CallableStatement cstmt = conn.prepareCall(sql); // Ejecutar instrucciones no SQL, ejemplo procedimentos almanecados
        
        //Establecemos los parámetros de los datos personales en el orden
        //en que los pide el procedimiento almacenado, comenzando en 1:
        cstmt.setString(1, c.getPersona().getNombre());
        cstmt.setString(2, c.getPersona().getPrimerApellido());
        cstmt.setString(3, c.getPersona().getTelCelular());
        cstmt.setString(4, c.getPersona().getCorreo());
        
        cstmt.setInt(15, c.getPersona().getIdPersona());
        
        //Ejecutamos el Stored Procedure:
        cstmt.executeUpdate(); // Instruccion tipo DELETE, Update , insert
        
        cstmt.close();
        connMySQL.close();        
    }
    
    public void delete(int idCliente) throws Exception
    {
        
        String sql ="UPDATE cliente SET estatus = 0 WHERE idCliente='"+idCliente+"'";
        //Con este objeto nos vamos a conectar a la Base de Datos
        ConexionBD connMySQL=new ConexionBD();
        
        //Abrimos conexion con la base Base de Datos
        Connection conn=connMySQL.open();
        
        //Con este objeto preparamos la eliminacion
        PreparedStatement pstm=conn.prepareStatement(sql);
        
        //Ejecutamos la eliminación
        pstm.executeUpdate(); // DELETE, UPDATE, INSERT
        
        //Cerramos la conexion a la base 
        pstm.close();
        connMySQL.close();
        
    }
    
    public void activate(int idCliente) throws Exception
    {
        
        String sql ="UPDATE cliente SET estatus = 1 WHERE idCliente='"+idCliente+"'";
        //Con este objeto nos vamos a conectar a la Base de Datos
        ConexionBD connMySQL=new ConexionBD();
        
        //Abrimos conexion con la base Base de Datos
        Connection conn=connMySQL.open();
        
        //Con este objeto preparamos la eliminacion
        PreparedStatement pstm=conn.prepareStatement(sql);
        
        //Ejecutamos la eliminación
        pstm.executeUpdate(); // DELETE, UPDATE, INSERT
        
        //Cerramos la conexion a la base 
        pstm.close();
        connMySQL.close();
        
    }
    
    public List<Cliente> getAll(String filtro) throws Exception
    {
        //La consulta SQL a ejecutar:
        String sql = "SELECT * FROM v_clientes";
        
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionBD connMySQL = new ConexionBD(); // crea un objeto tipo conexionMySQL
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open(); //abre la conexion
        
        //Con este objeto ejecutaremos la consulta:
        PreparedStatement pstmt = conn.prepareStatement(sql); //ejecuta consultas SQL
        
        //Aquí guardaremos los resultados de la consulta:
        ResultSet rs = pstmt.executeQuery(); // envia una consulta tipo Select y devuelve un objeto tipo  resultSet -> conjunto de datos que son
                                                                                   // el resultado de la consulta
        List<Cliente> clientes = new ArrayList<>(); //Crea una lista tipo Empleado
        
        while (rs.next())
            clientes.add(fill(rs));
        
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        return clientes;
    }
    
    private Cliente fill(ResultSet rs) throws Exception
    {
        Cliente c = new Cliente();
        Persona p = new Persona();
        
        p.setIdPersona(rs.getInt("idPersona"));
        p.setNombre(rs.getString("nombre"));
        p.setPrimerApellido(rs.getString("primerApellido"));
        p.setTelCelular(rs.getString("telCelular"));
        p.setCorreo(rs.getString("correo"));
        c.setPersona(p);
        c.setIdCliente(rs.getInt("idCliente"));
        
        
        
        return c;
    }
    
    
}
