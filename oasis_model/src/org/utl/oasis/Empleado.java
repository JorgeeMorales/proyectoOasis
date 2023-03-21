/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utl.oasis;

/**
 *
 * @author pasit
 */
public class Empleado {
    int idEmpleado;
    String numeroUnico;
    String segundoApellido;
    String genero;
    String fechaDeNacimiento;
    String estatus;
    String calle;
    String numero;
    String colonia;
    String codigoPostal;
    Usuario usuario;
    Persona persona;

    public Empleado() {
    }

    public Empleado(int idEmpleado, String numeroUnico, String segundoApellido, String genero, String fechaDeNacimiento, String status, String calle, String numero, String colonia, String codigoPostal, Usuario usuario, Persona persona) {
        this.idEmpleado = idEmpleado;
        this.numeroUnico = numeroUnico;
        this.segundoApellido = segundoApellido;
        this.genero = genero;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.estatus = status;
        this.calle = calle;
        this.numero = numero;
        this.colonia = colonia;
        this.codigoPostal = codigoPostal;
        this.usuario = usuario;
        this.persona = persona;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNumeroUnico() {
        return numeroUnico;
    }

    public void setNumeroUnico(String numeroUnico) {
        this.numeroUnico = numeroUnico;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(String fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public String getStatus() {
        return estatus;
    }

    public void setStatus(String status) {
        this.estatus = status;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    
    
}
