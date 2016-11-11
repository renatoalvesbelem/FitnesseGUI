package read;

import com.sun.prism.paint.*;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.Color;

public class CustonListRenderer extends DefaultListCellRenderer {

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        String valueString = (String) value;
        if (valueString.contains("!include") | valueString.contains("!*") | valueString.contains("*!")) {
            setForeground(Color.getHSBColor(0.26851854F, 0.6976744F, 0.5058824F));
        } else if (valueString.contains("!3")) {
            setForeground(Color.getHSBColor(0.9776536F, 0.9179487F, 0.7647059F));
            setFont(new Font("Dialog", Font.BOLD, 12));
        } else if (valueString.contains("!4")) {
            setForeground(Color.getHSBColor(0.9776536F, 0.9179487F, 0.7647059F));
        } else if (valueString.contains("!define")) {
            setForeground(Color.getHSBColor(0.04144144F, 0.8333333F, 0.87058824F));
        }
        return (this);
    }
}