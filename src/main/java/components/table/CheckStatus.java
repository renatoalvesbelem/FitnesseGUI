package components.table;

import read.ReadTest;
import teste.BasicDnD;

/**
 * Created by jose.renato on 07/11/2016.
 */
public class CheckStatus implements Runnable {
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (BasicDnD.statusTableScript != BasicDnD.tableScenario.getRowCount()) {
                new ReadTest().setScriptTest(BasicDnD.pathArchive, BasicDnD.tableScenario);
                BasicDnD.statusTableScript = BasicDnD.tableScenario.getRowCount();
            }
        }
    }
}
