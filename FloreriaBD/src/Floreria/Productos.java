package Floreria;

import java.math.BigDecimal;

public class Productos {
    private int idProducto;
    private String nombreProd;
    private String descripProd;
    private BigDecimal precioProd;
    private int stockDispo;
    private String estadoProd;
    private int idCategoria;

    public Productos(int idProducto, String nombreProd, String descripProd, BigDecimal precioProd, int stockDispo, String estadoProd, int idCategoria) {
        this.idProducto = idProducto;
        this.nombreProd = nombreProd;
        this.descripProd = descripProd;
        this.precioProd = precioProd;
        this.stockDispo = stockDispo;
        this.estadoProd = estadoProd;
        this.idCategoria = idCategoria;
    }

    public Productos() {
    }
    
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProd() {
        return nombreProd;
    }

    public void setNombreProd(String nombreProd) {
        this.nombreProd = nombreProd;
    }

    public String getDescripProd() {
        return descripProd;
    }

    public void setDescripProd(String descripProd) {
        this.descripProd = descripProd;
    }

    public BigDecimal getPrecioProd() {
        return precioProd;
    }

    public void setPrecioProd(BigDecimal precioProd) {
        this.precioProd = precioProd;
    }

    public int getStockDispo() {
        return stockDispo;
    }

    public void setStockDispo(int stockDispo) {
        this.stockDispo = stockDispo;
    }

    public String getEstadoProd() {
        return estadoProd;
    }

    public void setEstadoProd(String estadoProd) {
        this.estadoProd = estadoProd;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
    
    
}
