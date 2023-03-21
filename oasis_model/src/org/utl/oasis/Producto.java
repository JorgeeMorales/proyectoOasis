package org.utl.oasis;

/**
 *
 * @author pasit
 */
public class Producto {
    int idProducto;
    String nombreDelProducto;
    String codigoDeBarras;
    String marca;
    int estatus;
    String categoria;
    int existencias;
    double precioCompra;
    double precioVenta;

    public Producto() {
    }

    public Producto(int idProducto, String nombreDelProducto, String codigoDeBarras, String marca, int estatus, String categoria, int existencias, double precioCompra, double precioVenta) {
        this.idProducto = idProducto;
        this.nombreDelProducto = nombreDelProducto;
        this.codigoDeBarras = codigoDeBarras;
        this.marca = marca;
        this.estatus = estatus;
        this.categoria = categoria;
        this.existencias = existencias;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreDelProducto() {
        return nombreDelProducto;
    }

    public void setNombreDelProducto(String nombreDelProducto) {
        this.nombreDelProducto = nombreDelProducto;
    }

    public String getCodigoDeBarras() {
        return codigoDeBarras;
    }

    public void setCodigoDeBarras(String codigoDeBarras) {
        this.codigoDeBarras = codigoDeBarras;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getExistencias() {
        return existencias;
    }

    public void setExistencias(int existencias) {
        this.existencias = existencias;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    @Override
    public String toString() {
        return "Producto{" + "idProducto=" + idProducto + ", nombreDelProducto=" + nombreDelProducto + ", codigoDeBarras=" + codigoDeBarras + ", marca=" + marca + ", estatus=" + estatus + ", categoria=" + categoria + ", existencias=" + existencias + ", precioCompra=" + precioCompra + ", precioVenta=" + precioVenta + '}';
    }
    
    
}
