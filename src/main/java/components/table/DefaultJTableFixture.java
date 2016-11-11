package components.table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;

public class DefaultJTableFixture extends JTable {
    public DefaultJTableFixture(DefaultTableModel defaultTableModelFixture) {
        super(defaultTableModelFixture);
    }

    public String getToolTipText(MouseEvent e) {
        StringBuilder stringBuilder = new StringBuilder();
        Point p = e.getPoint();
        int rowIndex = rowAtPoint(p);
        int colIndex = columnAtPoint(p) + 2;

        try {
            String[] descFixture = getValueAt(rowIndex, colIndex).toString().split("\n");
            stringBuilder.append("<html>");
            for (String desc : descFixture) {
                stringBuilder.append("<p>" + desc + "</p>");
            }
            stringBuilder.append("</html>");
        } catch (RuntimeException e1) {
        }
        return stringBuilder.toString();
    }
}