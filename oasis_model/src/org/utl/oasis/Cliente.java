package org.utl.oasis;

/**
 *
 * @author pasit
 */
public class Cliente {
    int idCliente;
    Persona persona;

    public Cliente() {
    }

    public Cliente(int idCliente, Persona persona) {
        this.idCliente = idCliente;
        this.persona = persona;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    @Override
    public String toString() {
        return "Cliente{" + "idCliente=" + idCliente + ", persona=" + persona + '}';
    }
    
}
