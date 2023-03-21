package org.utl.oasis.Core;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.utl.oasis.ConexionBD.ConexionBD;
import org.utl.oasis.Habitacion;

/**
 *
 * @author jorgemorales
 */
public class ControllerHabitacion {
    
    public int insert(Habitacion h) throws Exception
    {
        
        //Definimos la consulta SQL que invoca al Stored Procedure:
        String sql =    "{call insertarHabitacion(?, ?, ?, ?, ?,  " + // Datos de la Habitacion
                                               "?, ?)}";  // Valores de Retorno
        
        //Aquí guardaremos los ID's que se generarán:
        int idHabitacionGenerada = -1;
        String CodicoUnicoGenerado = "";
        
        
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionBD connMySQL = new ConexionBD();
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();
        
        //Con este objeto invocaremos al StoredProcedure:
        CallableStatement cstmt = conn.prepareCall(sql);
        
        //Establecemos los  valores de los parámetros de los datos de producto en el orden
        //en que los pide el procedimiento almacenado, comenzando en 1:
        cstmt.setString(1, h.getNombre());
        cstmt.setString(2, h.getDescripcion());
        cstmt.setString(3,  h.getTematica());
        cstmt.setDouble(4,  h.getPrecioRenta());    
        cstmt.setString(5, h.getFotografia());
        
        //Registramos los parámetros de salida:
        cstmt.registerOutParameter(6, Types.INTEGER);
        cstmt.registerOutParameter(7, Types.VARCHAR);
      
        
        //Ejecutamos el Stored Procedure:
        cstmt.executeUpdate();
        
        //Recuperamos los ID's generados:
        idHabitacionGenerada = cstmt.getInt(6);
        CodicoUnicoGenerado= cstmt.getString(7);
       
        h.setIdHabitacion(idHabitacionGenerada);
        h.setCodigoUnico(CodicoUnicoGenerado);
        
        
        cstmt.close();
        connMySQL.close();
        
        //Devolvemos el ID de Accesorio generado:
        return idHabitacionGenerada;
        
    }
    
    public String update(Habitacion h)throws Exception
    {
        //Definimos la consulta SQL que invoca al Stored Procedure:
        String sql =    "{call actualizarHabitacion(?, ?, ?, ?, ?  " + // Datos de la Habitacion
                                               "?," //ID Habitacion
                                                + " ?)}";  // Valor de Retorno
        
        String codigoUnicoGenerado = "";
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionBD connMySQL = new ConexionBD();
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();
        
        //Con este objeto invocaremos al StoredProcedure:
        CallableStatement cstmt = conn.prepareCall(sql);
        
        //Establecemos los parámetros de los datos de producto en el orden
        //en que los pide el procedimiento almacenado, comenzando en 1:
        cstmt.setString(1, h.getNombre());
        cstmt.setString(2, h.getDescripcion());
        cstmt.setString(3,  h.getTematica());
        cstmt.setDouble(4,  h.getPrecioRenta()); 
        cstmt.setInt(5,  h.getIdHabitacion()); 
        cstmt.setString(6, h.getFotografia());
        
        cstmt.registerOutParameter(7, Types.VARCHAR);
        
        //Ejecutamos el Stored Procedure:
        cstmt.executeUpdate();
        
        codigoUnicoGenerado= cstmt.getString(7);
        
        h.setCodigoUnico(codigoUnicoGenerado);
        
        cstmt.close();
        connMySQL.close();
        return codigoUnicoGenerado;
    }
    //Con este metodo cambiamos de estatus 
    public void delete(int idHabitacion)throws Exception
    {
        //Definimos la consulta SQL que invoca al cambio de estatus:
          String sql =    "UPDATE habitacion SET estatus = 0 WHERE idHabitacion = '"+idHabitacion+"'"; // IDs
        
        
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionBD connMySQL = new ConexionBD();
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();
        
        //Con este objeto ejecutaremos la consulta:
        PreparedStatement pstm=conn.prepareStatement(sql);
        //ejecutamos la consulta
        pstm.executeUpdate();
        
        //cerramos la consulta
        pstm.close();   
        //cerramos la conexion a MySQL
        connMySQL.close();    
    }
    
    public List<Habitacion>getAll(String filtro) throws Exception
    {
        
        //La consulta SQL a ejecutar:
        String sql = "SELECT * FROM habitacion";
        
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionBD connMySQL = new ConexionBD();
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();
        
        //Con este objeto ejecutaremos la consulta:
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        //Aquí guardaremos los resultados de la consulta:
        ResultSet rs = pstmt.executeQuery();
        
        List<Habitacion> habitaciones = new ArrayList<>();
        
        while (rs.next())
            habitaciones.add(fill(rs));
        
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        return habitaciones;
        
    }
    //para llenar la tabla
    private Habitacion fill(ResultSet rs) throws Exception
    {
        //creamos objetos de tipo accesorio y producto
        Habitacion h = new Habitacion();
        
        h.setIdHabitacion(rs.getInt("idHabitacion"));
        h.setCodigoUnico(rs.getString("codigoUnico"));
        h.setNombre(rs.getString("nombre"));
        h.setEstatus(rs.getInt("estatus"));
        h.setDescripcion(rs.getString("descripcion"));
        h.setTematica(rs.getString("tematica"));
        h.setPrecioRenta(rs.getInt("precioRenta"));
        h.setFotografia(rs.getString("fotografia"));
        //regresamos accesorio
        return h;

        
    }
}
