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
import org.utl.oasis.Persona;
import org.utl.oasis.Producto;

/**
 *
 * @author pasit
 */
public class ControllerProducto {
    
    public int insert(Producto p) throws Exception
    {
        //parametros - se guarda en una variable tipo string
        //Definimos la consulta SQL que invoca al Stored Procedure:
        String sql =    "{call insertarProducto(?, ?, ?, ?, ?, ?, ?, " + // Datos Personales
                                               "?)}";  // Valores de Retorno
        
        //creando variables
        //Aquí guardaremos los ID's que se generarán:
        //variables fuera de rango
        int idProductoGenerado = -1;
        
        //Creamos objeto con el que nos vamos a conectar a la Base de Datos:
        ConexionBD connMySQL = new ConexionBD();
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();
        
        //Con este objeto invocaremos al StoredProcedure:
        CallableStatement cstmt = conn.prepareCall(sql);// Ejecutar instrucciones no SQL, ejemplo procedimentos almanecados
        
        //Establecemos los valores de los parámetros de los datos personales en el orden
        //en el ordren en el que los pide el procedimiento almacenado, comenzando en 1:
        cstmt.setString(1, p.getNombreDelProducto());
        cstmt.setString(2, p.getCodigoDeBarras());
        cstmt.setString(3, p.getMarca());
        cstmt.setString(4, p.getCategoria());
        cstmt.setInt(5, p.getExistencias());
        cstmt.setDouble(6, p.getPrecioCompra());
        cstmt.setDouble(7, p.getPrecioVenta());
        
        //Registramos los parámetros de salida:
        cstmt.registerOutParameter(8, Types.INTEGER);
        
        //Ejecutamos el Stored Procedure:
        cstmt.executeUpdate(); // Ejecutar instrucciones Delete, Update, insert 
        //Para este punto ya se guardo en la base de datos
        //Recuperamos los ID's generados:
        idProductoGenerado = cstmt.getInt(8);

        p.setIdProducto(idProductoGenerado);
        
        cstmt.close();
        connMySQL.close();
        
        //Devolvemos el ID de Cliente generado:
        return idProductoGenerado;
    }
    
    public void update(Producto p) throws Exception
    {
        //Definimos la consulta SQL que invoca al Stored Procedure:
         String sql =    "{call actualizarProducto(?, ?, ?, ?, ?, ?, ?, " + // Datos Personales
                                               "?)}";  // Valores de Retorno
        
        
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionBD connMySQL = new ConexionBD();
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();
        
        //Con este objeto invocaremos al StoredProcedure:
        CallableStatement cstmt = conn.prepareCall(sql); // Ejecutar instrucciones no SQL, ejemplo procedimentos almanecados
        
        //Establecemos los parámetros de los datos personales en el orden
        //en que los pide el procedimiento almacenado, comenzando en 1:
        cstmt.setString(1, p.getNombreDelProducto());
        cstmt.setString(2, p.getCodigoDeBarras());
        cstmt.setString(3, p.getMarca());
        cstmt.setString(4, p.getCategoria());
        cstmt.setInt(5, p.getExistencias());
        cstmt.setDouble(6, p.getPrecioCompra());
        cstmt.setDouble(7, p.getPrecioVenta());
        
        cstmt.setInt(8, p.getIdProducto());
        
        //Ejecutamos el Stored Procedure:
        cstmt.executeUpdate(); // Instruccion tipo DELETE, Update , insert
        
        cstmt.close();
        connMySQL.close();        
    }
    
    public void delete(int idProducto) throws Exception
    {
        
        String sql ="UPDATE producto SET estatus = 0 WHERE idProducto='"+idProducto+"'";
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
    
    public void activate(int idProducto) throws Exception
    {
        
        String sql ="UPDATE producto SET estatus = 1 WHERE idProducto='"+idProducto+"'";
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
    
    public List<Producto> getAll(String filtro) throws Exception
    {
        //La consulta SQL a ejecutar:
        String sql = "SELECT * FROM producto";
        
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionBD connMySQL = new ConexionBD(); // crea un objeto tipo conexionMySQL
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open(); //abre la conexion
        
        //Con este objeto ejecutaremos la consulta:
        PreparedStatement pstmt = conn.prepareStatement(sql); //ejecuta consultas SQL
        
        //Aquí guardaremos los resultados de la consulta:
        ResultSet rs = pstmt.executeQuery(); // envia una consulta tipo Select y devuelve un objeto tipo  resultSet -> conjunto de datos que son
                                                                                   // el resultado de la consulta
        List<Producto> productos = new ArrayList<>(); //Crea una lista tipo Empleado
        
        while (rs.next())
            productos.add(fill(rs));
        
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        return productos;
    }
    
    private Producto fill(ResultSet rs) throws Exception
    {
        Producto p = new Producto();
        
        p.setIdProducto(rs.getInt("idProducto"));
        p.setNombreDelProducto(rs.getString("nombreDelProducto"));
        p.setCodigoDeBarras(rs.getString("codigoDeBarras"));
        p.setMarca(rs.getString("marca"));
        p.setEstatus(rs.getInt("estatus"));
        p.setCategoria(rs.getString("categoria"));
        p.setExistencias(rs.getInt("existencias"));
        p.setPrecioCompra(rs.getDouble("precioCompra"));
        p.setPrecioVenta(rs.getDouble("precioVenta"));

        return p;
    }
    
}
