package Utils;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

public class IntegerDocumentFilter extends DocumentFilter {

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        StringBuilder sb = new StringBuilder();
        sb.append(fb.getDocument().getText(0, fb.getDocument().getLength()));
        sb.insert(offset, string);

        if (isInteger(sb.toString()) && Integer.parseInt(sb.toString()) > 0) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        StringBuilder sb = new StringBuilder();
        sb.append(fb.getDocument().getText(0, fb.getDocument().getLength()));
        sb.replace(offset, offset + length, text);

        if (isInteger(sb.toString()) && Integer.parseInt(sb.toString()) > 0) {
            super.replace(fb, offset, length, text, attrs);
        }
    }

    private boolean isInteger(String text) {
        try {
            int value = Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void clearTextField(JTextField textField) {
        Document doc = textField.getDocument();
        try {
            doc.remove(0, doc.getLength());
        } catch (BadLocationException e) {
            System.out.println(e);
        }
    }
}
