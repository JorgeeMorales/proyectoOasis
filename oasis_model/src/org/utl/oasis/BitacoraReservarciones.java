/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utl.oasis;

/**
 *
 * @author pasit
 */
public class BitacoraReservarciones {
    int idBitacoraReservacion;
    String estatus;
    Ticket ticket;

    public BitacoraReservarciones() {
    }

    public BitacoraReservarciones(int idBitacoraReservacion, String estatus, Ticket ticket) {
        this.idBitacoraReservacion = idBitacoraReservacion;
        this.estatus = estatus;
        this.ticket = ticket;
    }

    public int getIdBitacoraReservacion() {
        return idBitacoraReservacion;
    }

    public void setIdBitacoraReservacion(int idBitacoraReservacion) {
        this.idBitacoraReservacion = idBitacoraReservacion;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public String toString() {
        return "BitacoraReservarciones{" + "idBitacoraReservacion=" + idBitacoraReservacion + ", estatus=" + estatus + ", ticket=" + ticket + '}';
    }
    
    
}
