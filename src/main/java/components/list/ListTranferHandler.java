package components.list;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

/**
 * Created by jose.renato on 01/11/2016.
 */
public class ListTranferHandler extends TransferHandler {
    private JList list;

    public ListTranferHandler(JList list) {
        this.list = list;
    }

    public boolean canImport(TransferHandler.TransferSupport info) {
        // we only import Strings
        if (!info.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return false;
        }

        JList.DropLocation dl = (JList.DropLocation) info.getDropLocation();
        if (dl.getIndex() == -1) {
            return false;
        }
        return true;
    }

    public boolean importData(TransferHandler.TransferSupport info) {
        if (!info.isDrop()) {
            return false;
        }

        // Check for String flavor
        if (!info.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return false;
        }

        JList.DropLocation dl = (JList.DropLocation) info.getDropLocation();
        DefaultListModel listModel = (DefaultListModel) list.getModel();
        int index = dl.getIndex();
        boolean insert = dl.isInsert();
        // Get the current string under the drop.
        String value = (String) listModel.getElementAt(index);

        // Get the string that is being dropped.
        Transferable t = info.getTransferable();
        String data;
        try {
            data = (String) t.getTransferData(DataFlavor.stringFlavor);
        } catch (Exception e) {
            return false;
        }

        // Display a dialog with the drop information.
        String dropValue = "\"" + data + "\" dropped ";
        if (dl.isInsert()) {
            if (dl.getIndex() == 0) {
            } else if (dl.getIndex() >= list.getModel().getSize()) {
            } else {
                String value1 = (String) list.getModel().getElementAt(dl.getIndex() - 1);
                String value2 = (String) list.getModel().getElementAt(dl.getIndex());
            }
        } else {

        }

        /**  This is commented out for the basicdemo.html tutorial page.
         **  If you add this code snippet back and delete the
         **  "return false;" line, the list will accept drops
         **  of type string.
         // Perform the actual import.
         if (insert) {
         listModel.add(index, data);
         } else {
         listModel.set(index, data);
         }
         return true;
         */
        return false;
    }

    public int getSourceActions(JComponent c) {
        return COPY;
    }

    protected Transferable createTransferable(JComponent c) {
        JList list = (JList) c;
        Object[] values = list.getSelectedValues();

        StringBuffer buff = new StringBuffer();

        for (int i = 0; i < values.length; i++) {
            Object val = values[i];
            buff.append(val == null ? "" : val.toString());
            if (i != values.length - 1) {
                buff.append("\n");
            }
        }
        return new StringSelection(buff.toString());
    }
}



