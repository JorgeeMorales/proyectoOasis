package org.utl.oasis.Core;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.utl.oasis.ConexionBD.ConexionBD;
import org.utl.oasis.Empleado;
import org.utl.oasis.Persona;
import org.utl.oasis.Usuario;

/**
 *
 * @author pasit
 */
public class ControllerEmpleado {
    public int insert(Empleado e) throws Exception
    {
        //parametros - se guarda en una variable tipo string
        //Definimos la consulta SQL que invoca al Stored Procedure:
        String sql =    "{call insertarEmpleado(?, ?," + //Datos de Seguridad
                                               "?, ?, ?, ?, " + // Datos Personales
                                               "?, ?, ?, ?, ?, ?, ?, ?, ?, " + // Datos de Seguridad
                                               "?, ?, ?, ?, ?)}";  // Valores de Retorno
        //creando variables
        //Aquí guardaremos los ID's que se generarán:
        //variables fuera de rango
        String lastToeknGenerado = "";
        int idPersonaGenerado = -1;
        int idEmpleadoGenerado = -1;
        int idUsuarioGenerado = -1;
        String numeroUnicoGenerado = "";
        
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionBD connMySQL = new ConexionBD();
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();
        
        //Con este objeto invocaremos al StoredProcedure:
        CallableStatement cstmt = conn.prepareCall(sql);
        
        //Establecemos los valores de los parámetros de los datos personales en el orden
        //en el ordren en el que los pide el procedimiento almacenado, comenzando en 1:
        
        // Registramos parámetros de datos de seguridad:
        cstmt.setString(1, e.getUsuario().getNombreDeUsuario());
        cstmt.setString(2, e.getUsuario().getContrasenia());
        
        // Registramos parámetros de datos Personales:
        cstmt.setString(3, e.getPersona().getNombre());
        cstmt.setString(4, e.getPersona().getPrimerApellido());
        cstmt.setString(5, e.getPersona().getTelCelular());
        cstmt.setString(6, e.getPersona().getCorreo());
        
        // Registramos parámetros de datos de Empleado:
        cstmt.setString(7, e.getSegundoApellido());
        cstmt.setString(8, e.getGenero());
        cstmt.setString(9, e.getFechaDeNacimiento());
        cstmt.setString(10, e.getCalle());
        cstmt.setString(11, e.getNumero());
        cstmt.setString(12, e.getColonia());
        cstmt.setString(13, e.getCodigoPostal());
   
        //Registramos los parámetros de salida:
        cstmt.registerOutParameter(16, Types.INTEGER);
        cstmt.registerOutParameter(17, Types.INTEGER);
        cstmt.registerOutParameter(18, Types.INTEGER);
        cstmt.registerOutParameter(19, Types.VARCHAR);
        cstmt.registerOutParameter(20, Types.VARCHAR);
        
        //Ejecutamos el Stored Procedure:
        cstmt.executeUpdate();
        //Para este punto ya se guardo en la base de datos
        //Recuperamos los ID's generados:
        idPersonaGenerado = cstmt.getInt(16);
        idUsuarioGenerado = cstmt.getInt(17);
        idEmpleadoGenerado = cstmt.getInt(18);        
        numeroUnicoGenerado = cstmt.getString(19);
        lastToeknGenerado = cstmt.getString(20);
        
        
        e.setIdEmpleado(idEmpleadoGenerado);
        e.getPersona().setIdPersona(idPersonaGenerado);
        e.getUsuario().setIdUsuario(idUsuarioGenerado);
        e.setNumeroUnico(numeroUnicoGenerado);
        e.getUsuario().setLastToken(lastToeknGenerado);
        
        cstmt.close();
        connMySQL.close();
        
        //Devolvemos el ID de Empleado generado:
        return idEmpleadoGenerado;
    }
    
    public void update(Empleado e) throws Exception
    {
        //Definimos la consulta SQL que invoca al Stored Procedure:
        String sql =    "{call actualizarEmpleado(?, ?," + //Datos de Seguridad
                                               "?, ?, ?, ?, " + // Datos Personales
                                               "?, ?, ?, ?, ?, ?, ?, ?, ?, " + // Datos de Seguridad
                                               "?, ?, ?)}";  // Valores de Retorno
        
        
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionBD connMySQL = new ConexionBD();
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();
        
        //Con este objeto invocaremos al StoredProcedure:
        CallableStatement cstmt = conn.prepareCall(sql); // Ejecutar instrucciones no SQL, ejemplo procedimentos almanecados
        
        //Establecemos los parámetros de los datos personales en el orden
        //en que los pide el procedimiento almacenado, comenzando en 1:
        // Registramos parámetros de datos de seguridad:
        cstmt.setString(1, e.getUsuario().getNombreDeUsuario());
        cstmt.setString(2, e.getUsuario().getContrasenia());
        
        // Registramos parámetros de datos Personales:
        cstmt.setString(3, e.getPersona().getNombre());
        cstmt.setString(4, e.getPersona().getPrimerApellido());
        cstmt.setString(5, e.getPersona().getTelCelular());
        cstmt.setString(6, e.getPersona().getCorreo());
        
        // Registramos parámetros de datos de Empleado:
        cstmt.setString(7, e.getSegundoApellido());
        cstmt.setString(8, e.getGenero());
        cstmt.setString(9, e.getFechaDeNacimiento());
        cstmt.setString(10, e.getCalle());
        cstmt.setString(11, e.getNumero());
        cstmt.setString(12, e.getColonia());
        cstmt.setString(13, e.getCodigoPostal());
        
        cstmt.setInt(15, e.getUsuario().getIdUsuario());
        cstmt.setInt(15, e.getPersona().getIdPersona());
        cstmt.setInt(15, e.getIdEmpleado());

        
        //Ejecutamos el Stored Procedure:
        cstmt.executeUpdate(); // Instruccion tipo DELETE, Update , insert
        
        cstmt.close();
        connMySQL.close();        
    }
    
    public void delete(int idEmpleado) throws Exception
    {
        
        String sql ="UPDATE empleado SET estatus =0 WHERE idEmpleado='"+idEmpleado+"'";
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
    
    public void activate(int idEmpleado) throws Exception
    {
        
        String sql ="UPDATE empleado SET estatus ='"+1+"'WHERE idEmpleado='"+idEmpleado+"'";
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
    
    public List<Empleado> getAll(String filtro) throws Exception
    {
        //La consulta SQL a ejecutar:
        String sql = "SELECT * FROM v_empleados";
        
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionBD connMySQL = new ConexionBD(); // crea un objeto tipo conexionMySQL
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open(); //abre la conexion
        
        //Con este objeto ejecutaremos la consulta:
        PreparedStatement pstmt = conn.prepareStatement(sql); //ejecuta consultas SQL
        
        //Aquí guardaremos los resultados de la consulta:
        ResultSet rs = pstmt.executeQuery(); // envia una consulta tipo Select y devuelve un objeto tipo  resultSet -> conjunto de datos que son
                                                                                   // el resultado de la consulta
        List<Empleado> empleados = new ArrayList<>(); //Crea una lista tipo Empleado
        
        while (rs.next())
            empleados.add(fill(rs));
        
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        return empleados;
    }
    
    private Empleado fill(ResultSet rs) throws Exception
    {
        Empleado e = new Empleado();
        Persona p = new Persona();
        Usuario u = new Usuario();
        
        p.setIdPersona(rs.getInt("idPersona"));
        p.setNombre(rs.getString("nombre"));
        p.setPrimerApellido(rs.getString("primerApellido"));
        p.setTelCelular(rs.getString("telCelular"));
        p.setCorreo(rs.getString("correo"));
        
        u.setIdUsuario(rs.getInt("idUsuario"));
        u.setNombreDeUsuario(rs.getString("nombreDeUsuario"));
        u.setContrasenia(rs.getString("contrasenia"));
        u.setLastToken(rs.getString("lastToken"));
        u.setDateLastToken(rs.getString("dateLastToken"));
        
        e.setIdEmpleado(rs.getInt("idEmpleado"));
        e.setNumeroUnico(rs.getString("numeroUnico"));
        e.setSegundoApellido(rs.getString("segundoApellido"));
        e.setGenero(rs.getString("genero"));
        e.setFechaDeNacimiento(rs.getString("fechaNacimiento"));
        e.setStatus(rs.getString("estatus"));
        e.setCalle(rs.getString("calle"));
        e.setNumero(rs.getString("numero"));
        e.setColonia(rs.getString("colonia"));
        e.setCodigoPostal(rs.getString("codigoPostal"));
        
        
        e.setPersona(p);
        e.setUsuario(u);
        
        return e;
    }
}
