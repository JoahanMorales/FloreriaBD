package floreriabd;

import javax.swing.text.*;

public class LimitadorDeTexto extends PlainDocument {
    private int max;

    public LimitadorDeTexto(int max) {
        this.max = max;
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (str == null) return;

        if ((getLength() + str.length()) <= max) {
            super.insertString(offs, str, a);
        }
    }
}
