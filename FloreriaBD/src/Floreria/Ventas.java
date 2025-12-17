package Floreria;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Ventas {
    private int idVenta;
    private LocalDate fechaVen;
    private BigDecimal totalVen;
    private String metodoPago;
    private Integer empId;
    
    public Ventas() {}
    
    public Ventas(LocalDate fechaVen, BigDecimal totalVen, String metodoPago, Integer empId) {
        this.fechaVen = fechaVen;
        this.totalVen = totalVen;
        this.metodoPago = metodoPago;
        this.empId = empId;
    }
    
    public int getIdVenta() {
        return idVenta;
    }
    
    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }
    
    public LocalDate getFechaVen() {
        return fechaVen;
    }
    
    public void setFechaVen(LocalDate fechaVen) {
        this.fechaVen = fechaVen;
    }
    
    public BigDecimal getTotalVen() {
        return totalVen;
    }
    
    public void setTotalVen(BigDecimal totalVen) {
        this.totalVen = totalVen;
    }
    
    public String getMetodoPago() {
        return metodoPago;
    }
    
    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
    
    public Integer getEmpId() {
        return empId;
    }
    
    public void setEmpId(Integer empId) {
        this.empId = empId;
    }
}