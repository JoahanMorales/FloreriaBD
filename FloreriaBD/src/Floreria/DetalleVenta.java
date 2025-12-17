package Floreria;

import java.math.BigDecimal;

public class DetalleVenta {
    private int idDtllVen;
    private int cantidadVen;
    private BigDecimal precUVen;
    private BigDecimal subtotalVen;
    private int idVenta;
    private int idProducto;
    
    // Para mostrar información del producto en el carrito
    private String nombreProducto;
    
    // Constructores
    public DetalleVenta() {}
    
    public DetalleVenta(int cantidadVen, BigDecimal precUVen, int idProducto) {
        this.cantidadVen = cantidadVen;
        this.precUVen = precUVen;
        this.idProducto = idProducto;
        this.subtotalVen = precUVen.multiply(new BigDecimal(cantidadVen));
    }
    
    // Getters y Setters
    public int getIdDtllVen() {
        return idDtllVen;
    }
    
    public void setIdDtllVen(int idDtllVen) {
        this.idDtllVen = idDtllVen;
    }
    
    public int getCantidadVen() {
        return cantidadVen;
    }
    
    public void setCantidadVen(int cantidadVen) {
        this.cantidadVen = cantidadVen;
        // Recalcular subtotal cuando cambia la cantidad
        if (this.precUVen != null) {
            this.subtotalVen = this.precUVen.multiply(new BigDecimal(cantidadVen));
        }
    }
    
    public BigDecimal getPrecUVen() {
        return precUVen;
    }
    
    public void setPrecUVen(BigDecimal precUVen) {
        this.precUVen = precUVen;
        // Recalcular subtotal cuando cambia el precio
        if (this.cantidadVen > 0) {
            this.subtotalVen = precUVen.multiply(new BigDecimal(this.cantidadVen));
        }
    }
    
    public BigDecimal getSubtotalVen() {
        return subtotalVen;
    }
    
    public void setSubtotalVen(BigDecimal subtotalVen) {
        this.subtotalVen = subtotalVen;
    }
    
    public int getIdVenta() {
        return idVenta;
    }
    
    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }
    
    public int getIdProducto() {
        return idProducto;
    }
    
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }
    
    public String getNombreProducto() {
        return nombreProducto;
    }
    
    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }
}