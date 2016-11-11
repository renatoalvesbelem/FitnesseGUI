package components.table;

import javax.swing.table.DefaultTableModel;

/**
 * Created by jose.renato on 07/11/2016.
 */
public class DefaultTableModelFixture extends DefaultTableModel {
    public DefaultTableModelFixture() {
        super();
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

}



