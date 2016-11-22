package components.mouse;

import labels.cadastrofixture.mouse.ConstantsLabel;

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

    class PopUpMenu extends JPopupMenu implements ConstantsLabel {
        JMenuItem anItem;
        JMenuItem espacoBrancoAntes;
        JMenuItem espacoBrancoDepois;

        public PopUpMenu(JTable table, int row) {
            if (table.getSelectedRowCount() > 1) {
                anItem = new JMenuItem(DELETAR_LINHAS_SELECIONADAS);
            } else {
                anItem = new JMenuItem(DELETAR);
                espacoBrancoAntes = new JMenuItem(INSERIR_LINHA_ANTES);
                espacoBrancoDepois = new JMenuItem(INSERIR_LINHA_DEPOIS);
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

    class ActionListenerCuston implements ActionListener, ConstantsLabel {

        @Override
        public void actionPerformed(ActionEvent e) {
            JMenuItem buttonRemove = (JMenuItem) e.getSource();
            if ((buttonRemove.getText().equals(DELETAR_LINHAS_SELECIONADAS)) || (buttonRemove.getText().equals(DELETAR) && table.getSelectedRowCount() >= 1)) {
                deleteRow(table.getSelectedRows());
            } else if (buttonRemove.getText().equals(INSERIR_LINHA_ANTES)) {
                ((DefaultTableModel) table.getModel()).insertRow(table.getSelectedRow(), new String[]{""});
            } else if (buttonRemove.getText().equals(INSERIR_LINHA_DEPOIS)) {
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