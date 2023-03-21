/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utl.oasis;

/**
 *
 * @author pasit
 */
public class Reservar {
    int idReservar;
    String codigoBarrasRervacion;
    String fechaCreacionRervacion;
    String fechaARervar;
    Producto producto;
    Habitacion habitacion;
    Cliente cliente;

    public Reservar() {
    }

    public Reservar(int idReservar, String codigoBarrasRervacion, String fechaCreacionRervacion, String fechaARervar, Producto producto, Habitacion habitacion, Cliente cliente) {
        this.idReservar = idReservar;
        this.codigoBarrasRervacion = codigoBarrasRervacion;
        this.fechaCreacionRervacion = fechaCreacionRervacion;
        this.fechaARervar = fechaARervar;
        this.producto = producto;
        this.habitacion = habitacion;
        this.cliente = cliente;
    }

    public int getIdReservar() {
        return idReservar;
    }

    public void setIdReservar(int idReservar) {
        this.idReservar = idReservar;
    }

    public String getCodigoBarrasRervacion() {
        return codigoBarrasRervacion;
    }

    public void setCodigoBarrasRervacion(String codigoBarrasRervacion) {
        this.codigoBarrasRervacion = codigoBarrasRervacion;
    }

    public String getFechaCreacionRervacion() {
        return fechaCreacionRervacion;
    }

    public void setFechaCreacionRervacion(String fechaCreacionRervacion) {
        this.fechaCreacionRervacion = fechaCreacionRervacion;
    }

    public String getFechaARervar() {
        return fechaARervar;
    }

    public void setFechaARervar(String fechaARervar) {
        this.fechaARervar = fechaARervar;
    }


    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "Reservar{" + "idReservar=" + idReservar + ", codigoBarrasRervacion=" + codigoBarrasRervacion + ", fechaCreacionRervacion=" + fechaCreacionRervacion + ", fechaARervar=" + fechaARervar + ", producto=" + producto + ", habitacion=" + habitacion + ", cliente=" + cliente + '}';
    }
    
    
}
