package floreriabd;

import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.UIManager;

public class EstilosUI {
    public static void aplicarEstiloDialogo() {
        Font fuenteMensaje = FuentePersonalizada.cargarMontserrat(16f);
        Font fuenteBoton = FuentePersonalizada.cargarMontserrat(14f);

        UIManager.put("OptionPane.background", Colors.BLANCO);
        UIManager.put("Panel.background", Colors.BLANCO);
        UIManager.put("OptionPane.messageForeground", Colors.VERDE_FUERTE);
        UIManager.put("OptionPane.messageFont", fuenteMensaje);
        UIManager.put("Button.background", Colors.VERDE_MEDIO);
        UIManager.put("Button.foreground", Colors.VERDE_FUERTE);
        UIManager.put("Button.font", fuenteBoton);
        ImageIcon iconOriginal = new ImageIcon(EstilosUI.class.getResource("/imagenes/planta.png"));
        Image img = iconOriginal.getImage();
        Image imgEscalada = img.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon iconPequeno = new ImageIcon(imgEscalada);

        UIManager.put("OptionPane.informationIcon", iconPequeno);

    }
}
