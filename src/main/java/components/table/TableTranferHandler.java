package components.table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

public class TableTranferHandler extends TransferHandler {
    private JTable table;
    private DefaultTableModel listModel;

    public TableTranferHandler(JTable table, DefaultTableModel listModel) {
        this.table = table;
        this.listModel = listModel;
    }

    public boolean canImport(TransferHandler.TransferSupport info) {
        if (!info.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return false;
        }

        JTable.DropLocation dl = (JTable.DropLocation) info.getDropLocation();
        return dl.getRow() != -1;
    }

    public boolean importData(TransferHandler.TransferSupport info) {
        if (!info.isDrop()) {
            return false;
        }

        if (!info.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return false;
        }

        JTable.DropLocation dl = (JTable.DropLocation) info.getDropLocation();

        Transferable t = info.getTransferable();
        String dropValue;
        try {
            dropValue = (String) t.getTransferData(DataFlavor.stringFlavor);
        } catch (Exception e) {
            return false;
        }

        if (dl.isInsertRow()) {
            int rowDropped = dl.getRow();

            try {
                removeRowWhenMove(table);
            } catch (Exception e) {
                System.out.println("Tentou excluir da tabela");
            }

            if (dropValue.contains("\n") || dropValue.contains("BLANK")) {
                String[] rows = dropValue.split("\n");

                for (String row : rows) {
                    if (row.equals("BLANK")) {
                        listModel.insertRow(rowDropped, new String[]{""});
                    } else {
                        listModel.insertRow(rowDropped, new String[]{row});
                    }
                    rowDropped = rowDropped + 1;
                }

            } else {
                listModel.insertRow(rowDropped, new String[]{dropValue});
            }
        }
        return false;
    }

    public int getSourceActions(JComponent c) {
        return COPY;
    }

    protected Transferable createTransferable(JComponent c) {
        StringBuilder stringBuilder = new StringBuilder();
        JTable table = (JTable) c;
        int[] rows = table.getSelectedRows();
        for (int row : rows) {
            if (table.getValueAt(row, 0).equals("")) {
                stringBuilder.append("BLANK" + "\n");
            } else {
                stringBuilder.append(table.getValueAt(row, 0).toString() + "\n");
            }
        }
        return new StringSelection(stringBuilder.toString().substring(0, stringBuilder.length() - 1));
    }

    private void removeRowWhenMove(JTable table) {
        int rows[] = table.getSelectedRows();
        rows[0] = rows[0] + 1;
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        for (int row : rows) {
            tableModel.removeRow(table.convertRowIndexToModel(row - 1));
        }

    }
}