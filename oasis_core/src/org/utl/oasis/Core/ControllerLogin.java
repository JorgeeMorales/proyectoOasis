package org.utl.oasis.Core;

import org.utl.oasis.ConexionBD.ConexionBD;
import org.utl.oasis.Empleado;
import org.utl.oasis.Persona;
import org.utl.oasis.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.CallableStatement;

/**
 *
 * @author rocha
 */
public class ControllerLogin{
    /*
        Metodo que recibe un usuario y contraseña y usa los atributos en la consulta en la base de datos
        para ver si existe o no las credenciales
    */
    public Empleado login(String usuario, String contrasenia) throws Exception{
        //Preparamos la colnsulta a base de datos donde ? significa que ahi pondremos los atributos recibidos
        String sql="SELECT * FROM v_empleados VE WHERE VE.nombreDeUsuario=? AND VE.contrasenia=?";
        
        //Hacemos el objeto con el que abrimos la base de datos
        ConexionBD connMySQL=new ConexionBD();
        
        //Abrimos la conexion a la base de datos
        Connection conn=connMySQL.open();
        
        //Preparamos la consulta, es como poner el codigo en workbench
        PreparedStatement prepst=conn.prepareStatement(sql);
        
        //Preparamos la variable que va a recibir el reultado de la consulta
        ResultSet rs=null;
        
        //En el primer ? de la consulta pondremos el usuario
        prepst.setString(1, usuario);
        
        //En el segundo ? de la consulta pondremos la contraseña
        prepst.setString(2, contrasenia);
        
        //Ejecutamos la consulta y la guardamos en la variable que preparamos anteriormente
        rs=prepst.executeQuery();
        
        //Creamos una variable de tipo empleado
        Empleado emp=null;
        
        //Por cada reultado lo guardamos en empleado y llenamos un metodo llamado fill, Para que sirve en la linea 70 respondo
        if(rs.next())
            emp=(fill(rs));
        
        //Cerramos el Resultset
        rs.close();
        
        //Cerramos el prepared
        prepst.close();
        
        //Cerramos conexion a base de datos
        connMySQL.close();
        
        //Regresamos un empleado
        return emp;
    }
    
    /*
        A cada atributo del objeto empleado le pasa un dato dependiendo que columna busca
        ejem: hay 2 resultados del SELECT
        rs.next() si hay datos, al idPersona agregale(set) busca idPersona de esta primera fila
        rs.next() Esta rtambien tiene datos entonces asignaselo
        rs.next() ya no hay datos por que solo habia 2 filas en la consulta
    */
    private Empleado fill(ResultSet rs) throws Exception{
        //Creamos la variable de tipo empleado
        Empleado e = new Empleado();
        //Creamos la variable de tipo Persona
        Persona p=new Persona();
        
        //A cada atributo del empleado se busca su columna en la fila del resultado y se asigna el dato
        p.setIdPersona(rs.getInt("idPersona"));
        p.setNombre(rs.getString("nombre"));
        p.setPrimerApellido(rs.getString("primerApellido"));
        p.setTelCelular(rs.getString("telCelular"));
        p.setCorreo(rs.getString("correo"));
        
        e.setIdEmpleado(rs.getInt("idEmpleado"));
        e.setNumeroUnico(rs.getString("numeroUnico"));
        e.setSegundoApellido(rs.getString("segundoApellido"));
        e.setGenero(rs.getString("genero"));
        e.setFechaDeNacimiento(rs.getString("fechaNacimiento"));
        e.setCalle(rs.getString("calle"));
        e.setNumero(rs.getString("numero"));
        e.setColonia(rs.getString("colonia"));
        e.setCodigoPostal(rs.getString("codigoPostal"));
        e.setUsuario(new Usuario());
        e.getUsuario().setIdUsuario(rs.getInt("idUsuario"));
        e.getUsuario().setNombreDeUsuario(rs.getString("nombreDeUsuario"));
        e.getUsuario().setContrasenia(rs.getString("contrasenia"));
        e.getUsuario().setLastToken(rs.getString("lastToken"));
        e.getUsuario().setDateLastToken(rs.getString("dateLastToken"));
        e.setPersona(p);
        
        //Se regresa el empleado
        return e;
    }
    
    /*
        Metodo que le asigna el token en la base de datos al usuario que inicio sesion
    */
    public void generarToken(int idUsuario, String token) throws SQLException{
        //Preparamos el procedure
        String sql="CALL generarToken(?, ?);";
        
        //Llamamos el objeto con el que vamos a conectar a base de datos
        ConexionBD connMySQL=new ConexionBD();
        
        //Abrimos conexion a base de datos
        Connection conn=connMySQL.open();
        
        //Llamamos al procedure que escribimos en la variable sql
        CallableStatement callsmnt=conn.prepareCall(sql);
        
        //En el primer ? del procedure le asignamos el id del usuario
        callsmnt.setInt(1, idUsuario);
        //En el segundo? del procedure le asignamos el token que se genero en el metodo de la clase del modelo
        callsmnt.setString(2, token);
        //Ejecutamos el procedure
        callsmnt.executeUpdate();
        
        //Cerramos el call
        callsmnt.close();
        //Cerramos conexion
        connMySQL.close();
    }
    
    /*
        Metodo para consultar si existe el token y es igual al que esta manipulando la app
    */
    public boolean validarToken(String t) throws Exception{
        //Declaramos una variable que nos regresa un falso
        boolean r=false;
        //Preparamos la consulta y la t es donde ira el parametro que recibe el metodo para asi quede completa la consulta
        String sql="SELECT * FROM v_empleados WHERE lastToken='"+t+"';";
        
        //Traemos el objeto con el que vamos a conectar a la base de datos
        ConexionBD conexionMySQL=new ConexionBD();
        
        //Abrimos conexion a la base de datos
        Connection connection=conexionMySQL.open();
        
        //Creamos el objeto que modela la sentencia
        Statement stmnt=connection.createStatement();
        //Preparamos la variable que va a recibir el reultado de la consulta y ejecutamos la consulta
        ResultSet rs=stmnt.executeQuery(sql);
        //Si hay resultados la variable nos la pone en verdadero
        if(rs.next())
            r=true;
        
        //Cerramos el statment
        stmnt.close();
        //Cerramos la conexion
        connection.close();
        //Cerramos el objeto
        conexionMySQL.close();
        
        //Nos regresa verdad si llego hasta aqui
        return r;
    }
    
    /*
        Metodo que elimina el token de la base de datos cada que se cierra la sesion
    */
    public boolean eliminarToken(Empleado e) throws Exception{
        //Declaramos una variable en falso
        boolean r=false;
        //Prpeparamos la actualizacion
        String sql="UPDATE usuario SET lastToken='' WHERE idUsuario=?;";
        
        //Traemos el objeto con el que nos vamos a conectar
        ConexionBD connMySQL=new ConexionBD();
        
        //Abrimos conexion a la base de datos
        Connection connection=connMySQL.open();
        
        //Preparamos la actualizacion
        PreparedStatement prepst=connection.prepareCall(sql);
        //En el primer ? del update pondremos el id del usuario que se va a actualizar
        prepst.setInt(1, e.getUsuario().getIdUsuario());
        //Ejecutamos la actualizacion
        prepst.execute();
        //Si llego hasta aqui bien se cambia a verdadero
        r=true;
        //Cerramos el prepared
        prepst.close();
        //Cerramos la conexion
        connection.close();
        //Cerramos el objeto con el que nos conetcamos
        connMySQL.close();
        
        //Regresa verdadero
        return r;
    }
}
