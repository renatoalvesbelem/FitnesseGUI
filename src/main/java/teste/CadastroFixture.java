package teste;

import components.table.TableTranferHandler;
import components.table.TableTranferHandlerParameter;
import xml.Fixture;
import xml.Fixtures;
import xml.SelectoresFixture;
import xml.XMLFixtures;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.function.ObjDoubleConsumer;

public class CadastroFixture {

    public static void main(String[] args) {

        Runnable r = new Runnable() {
            JPanel gui;
            JFrame frame;

            public void run() {
                JTextField nameFixtureTextField = new JTextField();
                JComboBox fixturesNamesComboBox = new JComboBox();
                JComboBox seletoresComboBox = new JComboBox();
                JTextField valueSeletorFixtureTextField = new JTextField();
                JButton adicionarFixture = new JButton("+");
                JTextArea area = new JTextArea();

                frame = new JFrame("");
                frame.setResizable(false);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gui = new JPanel(new BorderLayout(5, 5));
                JPanel panelAll = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 3));
                JPanel nameFixtureJpanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 3));
                JPanel valueSelectorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 3));

                gui.setBorder(new TitledBorder(""));
                nameFixtureJpanel.setBorder(new TitledBorder("Fixture"));

                Fixtures fixtures = new XMLFixtures().getFixtures();
                nameFixtureTextField.setPreferredSize(new Dimension(300, 25));
                valueSelectorPanel.add(new JLabel("Nome Fixture: "));
                valueSelectorPanel.add(nameFixtureTextField);

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

                adicionarFixture.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        if (!fixturesNamesComboBox.getSelectedItem().equals("Selecionar")) {
                            area.append(valueSeletorFixtureTextField.getText());
                            area.append("\n");
                        }
                    }
                });

                nameFixtureJpanel.add(fixturesNamesComboBox);
                nameFixtureJpanel.add(valueSeletorFixtureTextField);
                nameFixtureJpanel.add(adicionarFixture);


                panelAll.setLayout(new BoxLayout(panelAll, BoxLayout.Y_AXIS));
                panelAll.add(valueSelectorPanel, BorderLayout.NORTH);
                panelAll.add(nameFixtureJpanel, BorderLayout.SOUTH);

                gui.add(panelAll, BorderLayout.NORTH);

                setPanelParametros();


                area.setLineWrap(true);
                area.setSize(600, 500);
                JScrollPane tableScroll = new JScrollPane(area);
                Dimension tablePreferred = tableScroll.getPreferredSize();
                tableScroll.setPreferredSize(new Dimension(tablePreferred.width, tablePreferred.height / 3));

                JPanel imagePanel = new JPanel(new GridBagLayout());
                imagePanel.setBorder(new TitledBorder("Visualização"));

                BufferedImage bi = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = bi.createGraphics();
                GradientPaint gp = new GradientPaint(20f, 20f, Color.red, 180f, 180f, Color.yellow);
                g.setPaint(gp);
                g.fillRect(0, 0, 200, 200);
                ImageIcon ii = new ImageIcon(bi);
                JLabel imageLabel = new JLabel(ii);
                imagePanel.add(imageLabel, null);

                //   JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tableScroll, new JScrollPane(imagePanel));
                gui.add(tableScroll, BorderLayout.EAST);

                frame.setContentPane(gui);

                frame.pack();

                frame.setLocationRelativeTo(null);
                try {
                    frame.setLocationByPlatform(true);
                    frame.setMinimumSize(frame.getSize());
                } catch (Throwable ignoreAndContinue) {
                }
                frame.setVisible(true);
            }

            public void setPanelParametros() {
                JPanel dynamicLabels = new JPanel(new BorderLayout(4, 4));
                dynamicLabels.setLayout(new BoxLayout(dynamicLabels, BoxLayout.Y_AXIS));
                dynamicLabels.setBorder(new TitledBorder(""));

                JPanel dadosParamerosPanel = new JPanel();
                dadosParamerosPanel.setLayout(new BoxLayout(dadosParamerosPanel, BoxLayout.X_AXIS));
                JTextField nomeParametros = new JTextField();
                dadosParamerosPanel.add(nomeParametros);

                JButton adicionarParametroButton = new JButton("+");
                dadosParamerosPanel.add(adicionarParametroButton);

                DefaultTableModel defaultTableModel = new DefaultTableModel();
                JTable tableLabels = new JTable(defaultTableModel);
                defaultTableModel.addColumn("Parâmetro");
                defaultTableModel.addColumn("Indicador Fitnesse");
                tableLabels.setSize(100, 20);
                dynamicLabels.add(dadosParamerosPanel, BorderLayout.NORTH);
                dynamicLabels.add(tableLabels, BorderLayout.SOUTH);
                tableLabels.setDragEnabled(true);
                tableLabels.setDropMode(DropMode.ON_OR_INSERT);
                tableLabels.setTransferHandler(new TableTranferHandlerParameter(tableLabels, defaultTableModel));


                gui.add(dynamicLabels, BorderLayout.WEST);

                adicionarParametroButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        if (!nomeParametros.getText().equals("")) {
                            List<String> tempParametros = new ArrayList<>();
                            for (Object vector : defaultTableModel.getDataVector()) {
                                tempParametros.add(((Vector<String>) vector).get(0));
                            }
                            if (!tempParametros.contains(nomeParametros.getText())) {
                                defaultTableModel.addRow(new String[]{nomeParametros.getText()});
                            }
                            frame.validate();
                        }
                    }
                });
                dynamicLabels.add(new JScrollPane(tableLabels), BorderLayout.SOUTH);
            }
        };
        SwingUtilities.invokeLater(r);
    }
}