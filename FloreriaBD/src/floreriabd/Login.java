package floreriabd;

import Floreria.Empleados;
import FloreriaService.EmpleadosService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame implements ActionListener {

    private JTextField campoUsuario;
    private JPasswordField campoContrasena;
    private RoundButton botonIngresar, botonSalir;
    private JLabel fondo;
    private JToggleButton toggleMostrar;

    public Login() {
        configurarVentana();
        configurarCamposDeTexto();
        configurarBotones();
        configurarToggleMostrar();
        paraLabels();
    }

    private void paraLabels() {
        fondo = new JLabel();
        fondo.setBounds(0, 0, 1220, 600);
        fondo.requestFocus();

        add(fondo);
        ImageIcon icon2 = new ImageIcon(getClass().getResource("/Imagenes/Login.png"));
        Image img = icon2.getImage();
        Image newimg = img.getScaledInstance(1220, 600, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(newimg);
        fondo.setIcon(newIcon);
    }

    private void configurarVentana() {
        setTitle("Login");
        setBounds(100, 100, 1220, 630);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void configurarCamposDeTexto() {
        campoUsuario = new JTextField();
        campoUsuario.setBounds(80, 365, 430, 53);
        campoUsuario.setFont(FuentePersonalizada.cargarMontserrat(25f));
        campoUsuario.setForeground(Colors.VERDE_FUERTE);
        campoUsuario.setOpaque(false);
        campoUsuario.setBorder(null);
        campoUsuario.setDocument(new LimitadorDeTexto(10));
        add(campoUsuario);

        campoContrasena = new JPasswordField();
        campoContrasena.setBounds(715, 365, 325, 53);
        campoContrasena.setFont(FuentePersonalizada.cargarMontserrat(25f));
        campoContrasena.setForeground(Colors.VERDE_FUERTE);
        campoContrasena.setOpaque(false);
        campoContrasena.setBorder(null);
        campoContrasena.setDocument(new LimitadorDeTexto(10));
        add(campoContrasena);
    }

    private void configurarToggleMostrar() {
        toggleMostrar = new JToggleButton();
        toggleMostrar.setBounds(1050, 355, 70, 70);
        toggleMostrar.setOpaque(false);
        toggleMostrar.setContentAreaFilled(false);
        toggleMostrar.setBorderPainted(false);

        ImageIcon iconOjoAbierto = new ImageIcon(getClass().getResource("/Imagenes/eye.png"));
        ImageIcon iconOjoCerrado = new ImageIcon(getClass().getResource("/Imagenes/eye-closed.png"));

        toggleMostrar.setIcon(iconOjoCerrado);

        char echoCharOriginal = campoContrasena.getEchoChar();

        toggleMostrar.addActionListener(e -> {
            if (toggleMostrar.isSelected()) {
                campoContrasena.setEchoChar((char) 0);
                toggleMostrar.setIcon(iconOjoAbierto);
            } else {
                campoContrasena.setEchoChar(echoCharOriginal);
                toggleMostrar.setIcon(iconOjoCerrado);
            }
        });

        add(toggleMostrar);
    }

    private void configurarBotones() {
        botonIngresar = new RoundButton ("Ingresar");
        botonIngresar.setBounds(705, 485, 50, 50);
        botonIngresar.setBackground(Colors.VERDE_FUERTE);
        botonIngresar.setForeground(Colors.VERDE_FUERTE);
        botonIngresar.setBorderPainted(false);
        botonIngresar.setFocusPainted(false);
        botonIngresar.setContentAreaFilled(false);
        botonIngresar.addActionListener(this);
        add(botonIngresar);
        
        
        botonSalir = new RoundButton("Salir");
        botonSalir.setBounds(465, 485, 50, 50);
        botonSalir.setBackground(Colors.VERDE_MEDIO);
        botonSalir.setForeground(Colors.VERDE_MEDIO);
        botonSalir.setBorderPainted(false);
        botonSalir.setFocusPainted(false);
        botonSalir.setContentAreaFilled(false);
        botonSalir.addActionListener(this);
        add(botonSalir);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        EstilosUI.aplicarEstiloDialogo();

        if (comando.equals("Ingresar")) {
            Integer usuario = Integer.parseInt(campoUsuario.getText().toString());
            String contrasena = new String(campoContrasena.getPassword());
            EmpleadosService emp = new EmpleadosService();
            
            if (emp.validarCredenciales(usuario, contrasena)) {
                emp.activarEmpleado(usuario);
                JOptionPane.showMessageDialog(this, "Acceso permitido");
                Index index = new Index();
                index.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos");
            }
        }
        if (comando.equals("Salir")) {
            dispose();
        }
    }
}
