package components.cadastrofixture.table;

import javax.swing.table.DefaultTableModel;

public class DefaultTableModelParameters extends DefaultTableModel {

    @Override
    public boolean isCellEditable(int row, int column) {
        return !(row == 0 && column == 1);
    }
}