package floreriabd;

import Floreria.Empleados;
import FloreriaService.EmpleadosService;
import floreriabd.panel.Inicio;
import floreriabd.panel.Perfil;
import floreriabd.panel.ProductosPanel;
import floreriabd.panel.VentasPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Index extends JFrame {
    private JPanel panelContenido;
    private JPanel barraPestanas;

    public Index() {
        configurarVentana();
        crearBarraDePestanas();
        mostrarPanel(new Inicio());
    }

    private void configurarVentana() {
        setTitle("Florería - Página Principal");
        setSize(1220, 630);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        panelContenido = new JPanel();
        panelContenido.setBounds(0, 60, 1220, 570);
        panelContenido.setLayout(null);
        panelContenido.setBackground(Color.WHITE);
        add(panelContenido);
    }

    private void crearBarraDePestanas() {
        barraPestanas = new JPanel();
        barraPestanas.setLayout(null);
        barraPestanas.setBounds(0, 0, 1220, 60);
        barraPestanas.setBackground(Colors.BLANCO);
        add(barraPestanas);
        
        ImageIcon icono = new ImageIcon(getClass().getResource("/Imagenes/Index1.png"));
        Image imagen = icono.getImage().getScaledInstance(60, 30, Image.SCALE_SMOOTH);
        JLabel labelImagen = new JLabel(new ImageIcon(imagen));
        labelImagen.setBounds(1140, 5, 50, 30);
        barraPestanas.add(labelImagen);

        agregarBotonPestana("Inicio", 50, 0);
        agregarBotonPestana("Perfil", 200, 0);
        agregarBotonPestana("Productos", 350, 0);
        agregarBotonPestana("Ventas", 500, 0);
        agregarBotonPestana("Cerrar Sesión", 910, 1);
        agregarBotonPestana("Vete a la verga", 700, 0);
    }

    private void agregarBotonPestana(String nombre, int x, int color) {
        JButton boton = new JButton(nombre);
        boton.setBackground(Colors.BLANCO);
        Color colorUso = Colors.VERDE_FUERTE;
        if (color == 1) {
            colorUso = Colors.NEGRO;
        }
        boton.setForeground(colorUso);   
        boton.setFocusPainted(false);
        boton.setBorderPainted(true);
        boton.setContentAreaFilled(false);
        boton.setOpaque(true);

        boton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
        
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Color colorUso = Colors.VERDE_FUERTE;
                if (color == 1) {
                    colorUso = Colors.NEGRO;
                }
                boton.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, colorUso));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            }
        });
        
        FontMetrics fm = boton.getFontMetrics(boton.getFont());
        int anchoTexto = fm.stringWidth(boton.getText());
        int altoTexto = fm.getHeight();
        boton.setBounds(x, 10, anchoTexto + 55, altoTexto + 10);
        boton.setFont(FuentePersonalizada.cargarMontserrat(16f));

        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (nombre) {
                    case "Inicio":
                        mostrarPanel(new Inicio());
                        break;
                    case "Perfil":
                        mostrarPanel(new Perfil());
                        break;
                    case "Productos":
                        mostrarPanel(new ProductosPanel());
                        break;
                    case "Ventas":
                        mostrarPanel(new VentasPanel());
                        break;
                    case "Cerrar Sesión":
                        EmpleadosService emp = new EmpleadosService();
                        Empleados empleado;
                        EstilosUI.aplicarEstiloDialogo();
                        empleado = emp.obtenerEmpleadoActivo();
                        if (emp.desActivarEmpleado(empleado.getEmpId())) {
                            JOptionPane.showMessageDialog(Index.this, "Sesión cerrada correctamente");
                            Login login = new Login();
                            login.setVisible(true);
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(Index.this, "Hubo un error al cerrar la sesión");
                        }
                        break;
                }
            }
        });
        barraPestanas.add(boton);
    }


    private void mostrarPanel(JPanel nuevoPanel) {
        panelContenido.removeAll();
        panelContenido.add(nuevoPanel);
        panelContenido.revalidate();
        panelContenido.repaint();
    }
}
