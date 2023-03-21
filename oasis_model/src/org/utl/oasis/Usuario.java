
package org.utl.oasis;

import java.util.Date;
//import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author pasit
 */
public class Usuario {
    int idUsuario;
    String nombreDeUsuario;
    String contrasenia;
    String lastToken;
    String dateLastToken;

    public Usuario() {
    }

    public Usuario(int idUsuario, String nombreDeUsuario, String contrasenia, String lastToken, String dateLastToken) {
        this.idUsuario = idUsuario;
        this.nombreDeUsuario = nombreDeUsuario;
        this.contrasenia = contrasenia;
        this.lastToken = lastToken;
        this.dateLastToken = dateLastToken;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreDeUsuario() {
        return nombreDeUsuario;
    }

    public void setNombreDeUsuario(String nombreDeUsuario) {
        this.nombreDeUsuario = nombreDeUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getLastToken()
    {
        return lastToken;
    }

    public void setLastToken(String lastToken)
    {
        this.lastToken = lastToken;
    }

    public String getDateLastToken()
    {
        return dateLastToken;
    }

    public void setDateLastToken(String dateLastToken)
    {
        this.dateLastToken = dateLastToken;
    }
   public void setLastToken() {
        //String u = this.getNombre();
        String p = this.getContrasenia();
        String k = new Date().toString();
        //String x = (DigestUtils.sha256Hex(u + ";" + p + ";" + k));
        //this.lastToken = x;

    }
    public void setDateLastToken(){
        String fecha=new Date().toString();
        this.dateLastToken=fecha;
    }

    @Override
    public String toString() {
        return "Usuario{" + "idUsuario=" + idUsuario + ", nombreDeUsuario=" + nombreDeUsuario + ", contrasenia=" + contrasenia + ", lastToken=" + lastToken + ", dateLastToken=" + dateLastToken + '}';
    }
    
    
}
