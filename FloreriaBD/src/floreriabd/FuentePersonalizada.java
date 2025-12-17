package floreriabd;

import java.awt.*;
import java.io.InputStream;
import java.io.IOException;

public class FuentePersonalizada {
    public static Font cargarMontserrat(float tamaño) {
        try {
            InputStream is = FuentePersonalizada.class.getResourceAsStream("/Recursos/Fuentes/Montserrat-SemiBold.ttf");
            return Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(tamaño);
        } catch (Exception e) {
            e.printStackTrace();
            return new Font("SansSerif", Font.PLAIN, (int) tamaño);
        }
    }
}
