package floreriabd;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundButton extends JButton {

    public RoundButton(String label) {
        super(label);
        setOpaque(false);  
        setFocusPainted(false); 
        setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Shape forma = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 40, 40);

        g2.setColor(getBackground());
        g2.fill(forma);

        g2.setClip(forma);
        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getForeground());
        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 40, 40);
        g2.dispose();
    }

    @Override
    public boolean contains(int x, int y) {
        Shape forma = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 40, 40);
        return forma.contains(x, y);
    }
}
