package components.mouse;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class OptionsTableScenario extends MouseAdapter {
    JTable table;
    int row;

    public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger()) {
            doPop(e);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger()) {
            doPop(e);
        }
    }

    private void doPop(MouseEvent e) {
        table = ((JTable) e.getSource());
        row = table.rowAtPoint(e.getPoint());
       PopUpMenu menu = new PopUpMenu(table, row);
        menu.show(e.getComponent(), e.getX(), e.getY());
    }

    class PopUpMenu extends JPopupMenu {
        JMenuItem anItem;

        public PopUpMenu(JTable table, int row) {
            if (table.getSelectedRowCount() > 1) {
                anItem = new JMenuItem("Delete selected lines?");
            } else {
                anItem = new JMenuItem("Delete");
            }

            add(anItem);
            anItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JMenuItem buttonRemove = (JMenuItem) e.getSource();
                    if ((buttonRemove.getText().equals("Delete selected lines?")) || (buttonRemove.getText().equals("Delete") && table.getSelectedRowCount() > 1)) {
                        deleteRow(table.getSelectedRows());
                    } else {
                        deleteRow(new int[]{row});
                    }
                }

                private void deleteRow(int[] rows) {
                    DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
                    rows[0] = rows[0] + 1;
                    for (int row : rows) {
                        tableModel.removeRow(table.convertRowIndexToModel(row - 1));
                    }
                }
            });
        }
    }
}