package teste;

import components.cadastrofixture.table.DefaultTableModelParameters;
import components.table.TableTranferHandlerParameter;
import xml.Fixture;
import xml.Fixtures;
import xml.XMLFixtures;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class CadastroFixture {

    public static void main(String[] args) {

        Runnable r = new Runnable() {
            JPanel gui;
            JFrame frame;
            DefaultTableModel defaultTableModel = new DefaultTableModelParameters();
            JTable tableLabels = new JTable(defaultTableModel);

            public void run() {
                JTextField nameFixtureTextField = new JTextField();
                JComboBox fixturesNamesComboBox = new JComboBox<String>();
                JComboBox seletoresComboBox = new JComboBox();
                JTextField valueSeletorFixtureTextField = new JTextField();
                JButton adicionarFixtureButton = new JButton("+");
                JButton salvarFecharButton = new JButton("Salvar e Fechar");
                JTextArea area = new JTextArea();

                frame = new JFrame("");
                frame.setResizable(false);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gui = new JPanel(new BorderLayout(5, 5));
                JPanel dataFixturePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 3));
                JPanel nameFixtureJpanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 3));
                JPanel valueSelectorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 3));

                gui.setBorder(new TitledBorder(""));

                Fixtures fixtures = new XMLFixtures().getFixtures();
                nameFixtureTextField.setPreferredSize(new Dimension(300, 25));
                JPanel allDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 3));

                JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 3, 3));

                nameFixtureJpanel.add(new JLabel("Nome Fixture: "));
                nameFixtureJpanel.add(nameFixtureTextField);

                buttonsPanel.add(salvarFecharButton);
                allDatePanel.setLayout(new BoxLayout(allDatePanel, BoxLayout.X_AXIS));
                allDatePanel.add(nameFixtureJpanel,BorderLayout.WEST);
                allDatePanel.add(buttonsPanel,BorderLayout.EAST);

                valueSeletorFixtureTextField.setPreferredSize(new Dimension(250, 25));
                nameFixtureJpanel.add(valueSeletorFixtureTextField);
                fixturesNamesComboBox.addItem("Selecionar");

                valueSeletorFixtureTextField.setEditable(false);

                for (Fixture fixture : fixtures.getFixtures()) {
                    fixturesNamesComboBox.addItem(fixture.getNomeFixture());
                }

                fixturesNamesComboBox.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        seletoresComboBox.removeAllItems();
                        for (Fixture fixture : fixtures.getFixtures()) {
                            if (((JComboBox) event.getSource()).getSelectedItem().equals(fixture.getNomeFixture())) {
                                String usage = fixture.getUsage();
                                valueSeletorFixtureTextField.setText(usage);
                                return;
                            }
                        }
                    }
                });

                adicionarFixtureButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        if (!fixturesNamesComboBox.getSelectedItem().equals("Selecionar")) {
                            area.append(valueSeletorFixtureTextField.getText() + "\n");
                        }
                    }
                });

                salvarFecharButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent event) {

                        StringBuilder fixtureFinal = new StringBuilder();
                        fixtureFinal.append("|scenario|" + nameFixtureTextField.getText() + "|");
                        for (int i = 0; i < tableLabels.getRowCount(); i++) {
                            if (i == 0) {
                                fixtureFinal.append(tableLabels.getValueAt(i, 0) + "|");
                            } else {
                                fixtureFinal.append(tableLabels.getValueAt(i, 1) + "|" + tableLabels.getValueAt(i, 0) + "|");
                            }

                        }
                        fixtureFinal.append("\n");

                        fixtureFinal.append(area.getText());
                        System.out.println(fixtureFinal.toString().replace("null", ""));
                    }
                });

                valueSelectorPanel.add(fixturesNamesComboBox);
                valueSelectorPanel.add(valueSeletorFixtureTextField);
                valueSelectorPanel.add(adicionarFixtureButton);

                dataFixturePanel.setLayout(new BoxLayout(dataFixturePanel, BoxLayout.Y_AXIS));
                dataFixturePanel.add(allDatePanel, BorderLayout.NORTH);
                dataFixturePanel.add(valueSelectorPanel, BorderLayout.SOUTH);


                gui.add(dataFixturePanel, BorderLayout.NORTH);

                setPanelParametros();

                area.setLineWrap(true);
                area.setPreferredSize(new Dimension(500, 500));
                JScrollPane tableScroll = new JScrollPane(area);
                Dimension tablePreferred = tableScroll.getPreferredSize();
                tableScroll.setPreferredSize(new Dimension(tablePreferred.width, tablePreferred.height / 3));

                JPanel imagePanel = new JPanel(new GridBagLayout());
                imagePanel.setBorder(new TitledBorder("Visualização"));

                gui.add(tableScroll, BorderLayout.EAST);
                frame.setContentPane(gui);
                frame.pack();
                frame.setLocationRelativeTo(null);
                try {
                    frame.setLocationByPlatform(true);
                    frame.setMinimumSize(frame.getSize());
                } catch (Throwable ignoreAndContinue) {
                    ignoreAndContinue.printStackTrace();
                }
                frame.setVisible(true);
            }
            private void setPanelParametros() {
                JPanel dynamicLabels = new JPanel(new BorderLayout(4, 4));
                dynamicLabels.setLayout(new BoxLayout(dynamicLabels, BoxLayout.Y_AXIS));
                dynamicLabels.setBorder(new TitledBorder(""));

                JPanel dadosParamerosPanel = new JPanel();
                dadosParamerosPanel.setLayout(new BoxLayout(dadosParamerosPanel, BoxLayout.X_AXIS));
                JTextField nomeParametros = new JTextField();
                dadosParamerosPanel.add(nomeParametros);

                nomeParametros.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        char keyChar = e.getKeyChar();
                        if (Character.isUpperCase(keyChar)) {
                            e.setKeyChar(Character.toLowerCase(keyChar));
                        } else if (!((keyChar < '0') || (keyChar > '9')) || Character.isSpaceChar(keyChar)) {
                            e.consume();
                        }
                    }
                });

                JButton adicionarParametroButton = new JButton("+");
                dadosParamerosPanel.add(adicionarParametroButton);

                defaultTableModel.addColumn("Parâmetro");
                defaultTableModel.addColumn("Indicador Fitnesse");
                tableLabels.setSize(100, 20);
                dynamicLabels.add(dadosParamerosPanel, BorderLayout.NORTH);
                dynamicLabels.add(tableLabels, BorderLayout.SOUTH);
                tableLabels.setDragEnabled(true);
                tableLabels.setDropMode(DropMode.ON_OR_INSERT);
                tableLabels.setTransferHandler(new TableTranferHandlerParameter(tableLabels, defaultTableModel));
                tableLabels.getTableHeader().setReorderingAllowed(false);
                gui.add(dynamicLabels, BorderLayout.WEST);

                adicionarParametroButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        if (!nomeParametros.equals("")) {
                            List<String> tempParametros = new ArrayList<>();
                            for (Object vector : defaultTableModel.getDataVector()) {
                                tempParametros.add((String) ((Vector) vector).get(0));
                            }
                            if (!tempParametros.contains(nomeParametros.getText())) {
                                defaultTableModel.addRow(new String[]{nomeParametros.getText()});
                                nomeParametros.setText("");
                            }
                        }
                    }
                });
                dynamicLabels.add(new JScrollPane(tableLabels), BorderLayout.SOUTH);
            }
        };
        SwingUtilities.invokeLater(r);
    }
}