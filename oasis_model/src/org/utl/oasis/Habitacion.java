
package org.utl.oasis;

/**
 *
 * @author pasit
 */
public class Habitacion {
    int idHabitacion;
    String codigoUnico;
    String nombre;
    int estatus;
    String descripcion;
    String tematica;
    double precioRenta;
    String fotografia;

    public Habitacion() {
    }

    public Habitacion(int idHabitacion, String codigoUnico, String nombre, int estatus, String descripcion, String tematica, double precioRenta, String fotografia) {
        this.idHabitacion = idHabitacion;
        this.codigoUnico = codigoUnico;
        this.nombre = nombre;
        this.estatus = estatus;
        this.descripcion = descripcion;
        this.tematica = tematica;
        this.precioRenta = precioRenta;
        this.fotografia = fotografia;
    }

    public int getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(int idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public String getCodigoUnico() {
        return codigoUnico;
    }

    public void setCodigoUnico(String codigoUnico) {
        this.codigoUnico = codigoUnico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTematica() {
        return tematica;
    }

    public void setTematica(String tematica) {
        this.tematica = tematica;
    }

    public double getPrecioRenta() {
        return precioRenta;
    }

    public void setPrecioRenta(double precioRenta) {
        this.precioRenta = precioRenta;
    }

    public String getFotografia() {
        return fotografia;
    }

    public void setFotografia(String fotografia) {
        this.fotografia = fotografia;
    }
    

    @Override
    public String toString() {
        return "Habitacion{" + "idHabitacion=" + idHabitacion + ", codigoUnico=" + codigoUnico + ", nombre=" + nombre + ", estatus=" + estatus + ", descripcion=" + descripcion + ", tematica=" + tematica + ", precioRenta=" + precioRenta + '}';
    }
    
}
