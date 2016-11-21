package components.mouse;

import teste.CadastroFixture;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class OptionsTableFixtures extends MouseAdapter {
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
        JMenuItem newItem, deleteItem, editItem;

        public PopUpMenu(JTable table, int row) {

            if (table.getValueAt(row, 1) == null) {
                newItem = new JMenuItem("New");
                add(newItem);
                newItem.addActionListener(new ActionListenerTable(table, row));
            } else {
                newItem = new JMenuItem("New");
                deleteItem = new JMenuItem("Delete");
                editItem = new JMenuItem("Edit");

                add(newItem);
                add(deleteItem);
                add(editItem);

                newItem.addActionListener(new ActionListenerTable(table, row));
                deleteItem.addActionListener(new ActionListenerTable(table, row));
                editItem.addActionListener(new ActionListenerTable(table, row));
            }
        }
    }
}

class ActionListenerTable implements ActionListener {
    JTable table;
    int row;

    public ActionListenerTable(JTable table, int row) {
        this.table = table;
        this.row = row;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem itemSelected = (JMenuItem) e.getSource();
        if (itemSelected.getText().equals("Delete")) {
            deleteRow(new int[]{row});
        }

        else if (itemSelected.getText().equals("Edit")){
            new CadastroFixture(table,row,"Edit");
        }
        else if (itemSelected.getText().equals("New")){
            new CadastroFixture(table,row, "New");
        }
    }

    private void deleteRow(int[] rows) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        rows[0] = rows[0] + 1;
        for (int row : rows) {
            tableModel.removeRow(table.convertRowIndexToModel(row - 1));
        }
    }
}


