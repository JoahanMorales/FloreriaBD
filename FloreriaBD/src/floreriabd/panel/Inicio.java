package floreriabd.panel;

import floreriabd.Colors;
import floreriabd.FuentePersonalizada;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Inicio extends JPanel{
    
    private JLabel fondo;
    
    public Inicio() {
        paraLabels();
    }
    
    private void paraLabels() {
        fondo = new JLabel();
        fondo.setBounds(0, 0, 1220, 600);
        fondo.requestFocus();

        add(fondo);
        ImageIcon icon2 = new ImageIcon(getClass().getResource("/Imagenes/index.png"));
        Image img = icon2.getImage();
        Image newimg = img.getScaledInstance(1220, 600, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(newimg);
        fondo.setIcon(newIcon);
        
        setLayout(null);
        setBackground(Color.WHITE);
        setBounds(0, 0, 1220, 570);

        JLabel label = new JLabel("Bienvenido al Inicio");
        label.setFont(FuentePersonalizada.cargarMontserrat(100f));
        label.setForeground(Colors.VERDE_CLARO);

        FontMetrics fm = label.getFontMetrics(label.getFont());
        int anchoTexto = fm.stringWidth(label.getText());
        int altoTexto = fm.getHeight();

        label.setBounds(50, 300, anchoTexto, altoTexto);

        add(label);
    }
}
