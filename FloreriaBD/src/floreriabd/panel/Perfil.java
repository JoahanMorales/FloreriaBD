package floreriabd.panel;

import Floreria.Empleados;
import FloreriaService.EmpleadosService;
import floreriabd.Colors;
import floreriabd.FuentePersonalizada;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Perfil extends JPanel{
    
    JLabel fondo, nombre, edad, apellidos, puesto, telefono, correo;
    
    public Perfil() {
        paraLabels();
        rellenar();
    }
    
    private void paraLabels() {
        setLayout(null);
        setBackground(Color.WHITE);
        setBounds(0, 0, 1220, 570);

        fondo = new JLabel();
        fondo.setBounds(0, 0, 1220, 600);
        fondo.setLayout(null);

        ImageIcon icon2 = new ImageIcon(getClass().getResource("/Imagenes/Perfil.png"));
        Image img = icon2.getImage();
        Image newimg = img.getScaledInstance(1220, 600, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(newimg);
        fondo.setIcon(newIcon);
        add(fondo);
    }

    
    
    private JLabel crearEtiqueta(String texto, int x, int y, int tamFuente) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(FuentePersonalizada.cargarMontserrat((float) tamFuente));
        etiqueta.setForeground(Colors.VERDE_MEDIO);

        FontMetrics fm = etiqueta.getFontMetrics(etiqueta.getFont());
        int anchoTexto = fm.stringWidth(texto);
        int altoTexto = fm.getHeight();

        etiqueta.setBounds(x, y, anchoTexto, altoTexto + 10);

        fondo.add(etiqueta);

        return etiqueta;
    }

    
    
    
    private void rellenar(){
        Empleados empleado;
        EmpleadosService emp = new EmpleadosService();
        empleado = emp.obtenerEmpleadoActivo();
        nombre = crearEtiqueta(empleado.getEmpNombre(), 230, 182, 30);
        apellidos = crearEtiqueta(empleado.getEmpApellidos(), 230, 288, 30);
        edad = crearEtiqueta(empleado.getEmpEdad().toString(), 650, 182, 30);
        puesto = crearEtiqueta(empleado.getEmpPuesto(), 650, 288, 30);
        telefono = crearEtiqueta(empleado.getEmpTelefono(), 230, 396, 30);
        correo = crearEtiqueta(empleado.getEmpCorreo(), 650, 396, 30);
    }
}
