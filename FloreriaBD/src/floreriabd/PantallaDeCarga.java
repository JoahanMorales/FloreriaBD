package floreriabd; // Puedes cambiarlo según tu estructura

import FloreriaService.EmpleadosService;
import javax.swing.*;
import java.awt.*;

public class PantallaDeCarga extends JFrame {

    private JProgressBar barraCarga;
    private JLabel fondo;
    private JPanel panel;

    public PantallaDeCarga() {
        configurarVentana();
        configurarComponentes();
        iniciarCarga();
    }

    private void configurarVentana() {
        setLayout(null);
        setBounds(0, 0, 1220, 630);
        setLocationRelativeTo(null);
        setTitle("Inicio");
        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void configurarComponentes() {
        panel = new JPanel(null);
        panel.setBounds(0, 0, 1220, 630);
        add(panel);

        barraCarga = new JProgressBar();
        barraCarga.setBounds(485, 580, 250, 20);
        barraCarga.setStringPainted(true);
        Color verdePastel = Color.decode("#c1dbaf");
        barraCarga.setForeground(verdePastel);
        barraCarga.setBackground(Color.WHITE);
        barraCarga.setBorderPainted(false); 
        barraCarga.setFont(FuentePersonalizada.cargarMontserrat(14f));
        barraCarga.setStringPainted(true);
        
        barraCarga.setUI(new javax.swing.plaf.basic.BasicProgressBarUI() {
            @Override
            protected Color getSelectionBackground() {
                return Color.BLACK; 
            }

            @Override
            protected Color getSelectionForeground() {
                return Color.WHITE;
            }
        });

        panel.add(barraCarga);

        fondo = new JLabel();
        fondo.setBounds(0, 0, 1220, 630);
        panel.add(fondo);
        ImageIcon icono = new ImageIcon(getClass().getResource("/Imagenes/Inicio.png"));
        Image imagen = icono.getImage().getScaledInstance(1220, 630, Image.SCALE_SMOOTH);
        fondo.setIcon(new ImageIcon(imagen));
    }



    private void iniciarCarga() {
        EmpleadosService emp = new EmpleadosService();
        new Thread(() -> {
            for (int i = 0; i <= 100; i++) {
                final int progreso = i;
                SwingUtilities.invokeLater(() -> barraCarga.setValue(progreso));
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (!emp.hayEmpleadoActivo()) {
                SwingUtilities.invokeLater(() -> {
                setVisible(false);
                Login login = new Login();
                dispose();
            });
            } else {
                SwingUtilities.invokeLater(() -> {
                setVisible(false);
                Index index = new Index();
                index.setVisible(true);
                dispose();
            });
            }
        }).start();
    }
}
