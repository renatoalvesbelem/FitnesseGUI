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
        JMenuItem espacoBrancoAntes;
        JMenuItem espacoBrancoDepois;

        public PopUpMenu(JTable table, int row) {
            if (table.getSelectedRowCount() > 1) {
                anItem = new JMenuItem("Delete selected lines?");
            } else {
                anItem = new JMenuItem("Delete");
                espacoBrancoAntes = new JMenuItem("Linha em Branco Antes");
                espacoBrancoDepois = new JMenuItem("Linha em Branco Depois");
            }

            add(anItem);
            try {
                add(espacoBrancoAntes);
                add(espacoBrancoDepois);
                espacoBrancoAntes.addActionListener(new ActionListenerCuston());
                espacoBrancoDepois.addActionListener(new ActionListenerCuston());

            } catch (Exception ex) {

            }
            anItem.addActionListener(new ActionListenerCuston());
        }
    }

    class ActionListenerCuston implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JMenuItem buttonRemove = (JMenuItem) e.getSource();
            if ((buttonRemove.getText().equals("Delete selected lines?")) || (buttonRemove.getText().equals("Delete") && table.getSelectedRowCount() >= 1)) {
                deleteRow(table.getSelectedRows());
            } else if (buttonRemove.getText().equals("Linha em Branco Antes")) {
                ((DefaultTableModel) table.getModel()).insertRow(table.getSelectedRow(), new String[]{""});
            } else if (buttonRemove.getText().equals("Linha em Branco Depois")) {
                ((DefaultTableModel) table.getModel()).insertRow(table.getSelectedRow() + 1, new String[]{""});
            }
        }

        private void deleteRow(int[] rows) {
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            for (int i = 0; i < rows.length; i++) {
                tableModel.removeRow(table.convertRowIndexToModel(rows[0]));
            }
        }
    }
}