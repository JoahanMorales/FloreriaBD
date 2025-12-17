package Floreria;

import java.io.Serializable;

public class Empleados implements Serializable{
    private Integer empId;
    private String empNombre;
    private String empApellidos;
    private Integer empEdad;
    private String empPuesto;
    private String empTelefono;
    private String empCorreo;
    private String empContra;
    private int empActivo;

    public Empleados() {
    }

    public Empleados(Integer empId, String empNombre, String empApellidos, Integer empEdad, String empPuesto, String empTelefono, String empCorreo, String empContra, int empActivo) {
        this.empId = empId;
        this.empNombre = empNombre;
        this.empApellidos = empApellidos;
        this.empEdad = empEdad;
        this.empPuesto = empPuesto;
        this.empTelefono = empTelefono;
        this.empCorreo = empCorreo;
        this.empContra = empContra;
        this.empActivo = empActivo;
    }
    
    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpNombre() {
        return empNombre;
    }

    public void setEmpNombre(String empNombre) {
        this.empNombre = empNombre;
    }

    public String getEmpApellidos() {
        return empApellidos;
    }

    public void setEmpApellidos(String empApellidos) {
        this.empApellidos = empApellidos;
    }

    public Integer getEmpEdad() {
        return empEdad;
    }

    public void setEmpEdad(Integer empEdad) {
        this.empEdad = empEdad;
    }

    public String getEmpPuesto() {
        return empPuesto;
    }

    public void setEmpPuesto(String empPuesto) {
        this.empPuesto = empPuesto;
    }

    public String getEmpTelefono() {
        return empTelefono;
    }

    public void setEmpTelefono(String empTelefono) {
        this.empTelefono = empTelefono;
    }

    public String getEmpCorreo() {
        return empCorreo;
    }

    public void setEmpCorreo(String empCorreo) {
        this.empCorreo = empCorreo;
    }

    public String getEmpContra() {
        return empContra;
    }

    public void setEmpContra(String empContra) {
        this.empContra = empContra;
    }

    public int getEmpActiv() {
        return empActivo;
    }
    
    public void setEmpActivo(int empActivo) {
        this.empActivo = empActivo;
    }
    
}
