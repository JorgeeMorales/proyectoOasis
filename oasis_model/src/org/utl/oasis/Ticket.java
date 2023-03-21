package org.utl.oasis;

/**
 *
 * @author pasit
 */
public class Ticket {
    int idTicket;
    double total;
    int cantidad;
    Reservar reservar;

    public Ticket() {
    }

    public Ticket(int idTicket, double total, int cantidad, Reservar reservar) {
        this.idTicket = idTicket;
        this.total = total;
        this.cantidad = cantidad;
        this.reservar = reservar;
    }

    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Reservar getReservar() {
        return reservar;
    }

    public void setReservar(Reservar reservar) {
        this.reservar = reservar;
    }

    @Override
    public String toString() {
        return "Ticket{" + "idTicket=" + idTicket + ", total=" + total + ", cantidad=" + cantidad + ", reservar=" + reservar + '}';
    }
    
    
}
