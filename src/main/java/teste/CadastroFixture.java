package teste;

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
import java.util.List;

public class CadastroFixture {

    public static void main(String[] args) {

        Runnable r = new Runnable() {

            public void run() {
                JTextField nameFixtureTextField = new JTextField();
                JComboBox fixturesNamesComboBox = new JComboBox();
                JComboBox seletoresComboBox = new JComboBox();
                JTextField valueSeletorFixtureTextField = new JTextField();
                JFrame frame = new JFrame("");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                JPanel gui = new JPanel(new BorderLayout(5, 5));
                JPanel panelAll = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 3));
                JPanel nameFixtureJpanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 3));
                JPanel valueSelectorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 3));

                gui.setBorder(new TitledBorder(""));
                nameFixtureJpanel.setBorder(new TitledBorder("Fixture"));
                nameFixtureJpanel.setBorder(new TitledBorder("Seletor"));

                Fixtures fixtures = new XMLFixtures().getFixtures();

                nameFixtureTextField.setPreferredSize(new Dimension(300, 25));
                valueSelectorPanel.add(new JLabel("Nome Fixture: "));
                valueSelectorPanel.add(nameFixtureTextField);

                valueSeletorFixtureTextField.setPreferredSize(new Dimension(250, 25));
                nameFixtureJpanel.add(valueSeletorFixtureTextField);
                fixturesNamesComboBox.addItem("Selecionar");
                seletoresComboBox.setVisible(false);
                valueSeletorFixtureTextField.setVisible(false);
                for (Fixture fixture : fixtures.getFixtures()) {
                    fixturesNamesComboBox.addItem(fixture.getNomeFixture());
                }

                fixturesNamesComboBox.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        seletoresComboBox.removeAllItems();
                        for (Fixture fixture : fixtures.getFixtures()) {
                            if (fixture.getSelectoresFixtures() != null && ((JComboBox) event.getSource()).getSelectedItem().equals(fixture.getNomeFixture())) {
                                List<SelectoresFixture> seletores = fixture.getSelectoresFixtures();
                                seletoresComboBox.setVisible(true);
                                valueSeletorFixtureTextField.setVisible(true);
                                frame.validate();
                                for (SelectoresFixture seletor : seletores) {
                                    seletoresComboBox.addItem(seletor.getSeletor());
                                }
                                return;
                            } else {
                                seletoresComboBox.setVisible(false);
                                valueSeletorFixtureTextField.setVisible(false);
                                frame.validate();
                            }
                        }
                    }
                });
                nameFixtureJpanel.add(fixturesNamesComboBox) ;
                nameFixtureJpanel.add(seletoresComboBox);
                nameFixtureJpanel.add(valueSeletorFixtureTextField);

                panelAll.setLayout(new BoxLayout(panelAll, BoxLayout.Y_AXIS));
                panelAll.add(valueSelectorPanel, BorderLayout.NORTH);
                panelAll.add(nameFixtureJpanel, BorderLayout.SOUTH);
                gui.add(panelAll, BorderLayout.NORTH);

                JPanel dynamicLabels = new JPanel(new BorderLayout(4, 4));
                dynamicLabels.setBorder(new TitledBorder(""));
                gui.add(dynamicLabels, BorderLayout.WEST);

                JPanel labels = new JPanel(new GridLayout(0, 2, 3, 3));
                labels.setBorder(new TitledBorder("Parâmetro"));

                JButton addNew = new JButton("Adicionar Parâmetro");
                dynamicLabels.add(addNew, BorderLayout.CENTER);
                JTextField nomeParametros = new JTextField();
                dynamicLabels.add(nomeParametros, BorderLayout.NORTH);
                addNew.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent ae) {
                        labels.add(new JLabel(nomeParametros.getText()));
                        frame.validate();
                    }
                });
                dynamicLabels.add(new JScrollPane(labels), BorderLayout.SOUTH);

                String[] header = {"Name", "Value"};
                String[] a = new String[0];
                String[] names = System.getProperties().
                        stringPropertyNames().toArray(a);
                String[][] data = new String[names.length][2];
                for (int ii = 0; ii < names.length; ii++) {
                    data[ii][0] = names[ii];
                    data[ii][1] = System.getProperty(names[ii]);
                }
                DefaultTableModel model = new DefaultTableModel(data, header);
                JTable table = new JTable(model);
                try {
                    // 1.6+
                    table.setAutoCreateRowSorter(true);
                } catch (Exception continuewithNoSort) {
                }
                JScrollPane tableScroll = new JScrollPane(table);
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

                JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tableScroll, new JScrollPane(imagePanel));
                gui.add(splitPane, BorderLayout.CENTER);

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
        };
        SwingUtilities.invokeLater(r);
    }
}