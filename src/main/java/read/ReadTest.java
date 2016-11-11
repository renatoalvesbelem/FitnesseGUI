package read;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ReadTest {

    private FileWriter fw;
    private BufferedWriter bw;
    private PrintWriter log;

    public void getScriptTest(String fileName, DefaultTableModel defaultTableModel) {

        while (defaultTableModel.getRowCount() > 0) {
            defaultTableModel.removeRow(0);
        }
        FileInputStream fstream;
        try {
            fstream = new FileInputStream(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                defaultTableModel.addRow(new String[]{strLine});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, StringBuilder> getFixtureScenario(DefaultTableModel defaultTableModel, File rootPath, String testPath) {
        FileInputStream fstream;
        HashMap<String, StringBuilder> mapFixture = new HashMap<>();
        while (defaultTableModel.getRowCount() > 0) {
            defaultTableModel.removeRow(0);
        }
        List<String> listScenarios = getListScenarios(rootPath.getAbsolutePath(), testPath);
        Collections.reverse(listScenarios);
        for (String scenario : listScenarios) {
            try {
                fstream = new FileInputStream(scenario + "\\ScenarioLibrary\\content.txt");
                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
                String strLine;
                defaultTableModel.addRow(new String[]{scenario.substring(scenario.lastIndexOf("\\") + 1)});
                while ((strLine = br.readLine()) != null) {
                    if (strLine.contains("scenario")) {
                        String nameScenario = strLine.replace("|scenario", "").trim();
                        StringBuilder descFixture = new StringBuilder();
                        descFixture.append(strLine + "\n");

                        strLine = br.readLine();
                        while (strLine != null && !strLine.contains("scenario") && strLine.contains("|")) {
                            descFixture.append(strLine + "\n");
                            strLine = br.readLine();
                        }
                        defaultTableModel.addRow(new Object[]{nameScenario, scenario, descFixture});
                        mapFixture.put(nameScenario + scenario, descFixture);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mapFixture;
    }

/*
    public void getFixtureScenario(DefaultListModel defaultListModel, File rootPath, String testPath) {
        FileInputStream fstream = null;
        defaultListModel.clear();
        List<String> listScenarios = getListScenarios(rootPath.getAbsolutePath(), testPath);
        Collections.reverse(listScenarios);
        for (String scenario : listScenarios) {
            try {
                fstream = new FileInputStream(scenario + "\\ScenarioLibrary\\content.txt");
                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
                String strLine;
                defaultListModel.addElement(scenario.substring(scenario.lastIndexOf("\\") + 1));
                while ((strLine = br.readLine()) != null) {
                    if (strLine.contains("scenario")) {
                        defaultListModel.addElement(strLine);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/


    private List<String> getListScenarios(String absolutePath, String path) {
        List<String> listDirectoryScenarios = new ArrayList<>();
        while (!absolutePath.equals(path)) {
            File file = new File(path);
            file.getParentFile();
            String files[] = file.getParentFile().list();

            for (String fileScenario:files                 ) {
                if (fileScenario.contains("ScenarioLibrary")) {
                    listDirectoryScenarios.add(file.getParentFile().toString());
                }
            }

            int lastOccurrence = path.lastIndexOf("\\");
            path = path.substring(0, lastOccurrence);
        }
        return listDirectoryScenarios;
    }


    public void setScriptTest(String filePath, JTable table) {
        System.out.println(filePath);
        filePath = "C:\\Users\\jose.renato\\parametros.xml";
        openFile(new File(filePath));
        for (int i = 0; i < table.getRowCount(); i++) {
            writeFile(table.getValueAt(i, 0).toString());
        }
        closeFile();
    }

    private void openFile(File file) {
        try {
            fw = new FileWriter(file, false);
            bw = new BufferedWriter(fw);
            log = new PrintWriter(bw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeFile() {
        try {
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeFile(String info) {
        log.println(info);
    }
}