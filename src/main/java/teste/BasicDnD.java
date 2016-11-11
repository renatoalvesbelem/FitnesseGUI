package teste;

import Nodes.CreateChildNodes;
import Nodes.FileNode;
import components.mouse.OptionsTableFixtures;
import components.mouse.OptionsTableScenario;
import components.table.CheckStatus;
import components.table.DefaultJTableFixture;
import components.table.DefaultTableModelFixture;
import components.table.TableTranferHandler;
import org.apache.commons.collections.map.HashedMap;
import read.ReadTest;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class BasicDnD extends JPanel implements ActionListener {
    private static JFrame frame;
    public static JTable tableFixtures;
    public static JTable tableScenario;
    private JTree tree;
    private JCheckBox toggleDnD;
    static File fileRoot = new File("C:\\Users\\jose.renato\\Documents\\Projetos\\esaj-test");
    public static int statusTableScript = 0;
    public static String pathArchive;
    HashMap<String,StringBuilder> mapFixture;


    public BasicDnD() {
        super(new BorderLayout());
        JPanel panelFixture = createVerticalBoxPanel();
        JPanel panelTree = createVerticalBoxPanel();
        panelTree.setPreferredSize(new Dimension(300, 50));
        JPanel panelScrip = createVerticalBoxPanel();
        panelScrip.setPreferredSize(new Dimension(300, 550));

        DefaultTableModel tableModelScenario = new DefaultTableModel();
        tableModelScenario.addColumn("Test Script");
        tableScenario = new JTable(tableModelScenario);
        tableScenario.setDropMode(DropMode.ON_OR_INSERT);
        tableScenario.setTableHeader(null);
        tableScenario.setTransferHandler(new TableTranferHandler(tableScenario, tableModelScenario));
        tableScenario.addMouseListener(new OptionsTableScenario());
        JScrollPane tableView = new JScrollPane(tableScenario);
        panelScrip.add(createPanelForComponent(tableView, "Script Fitnesse"));


        DefaultTableModel tableModelFixtures = new DefaultTableModelFixture();
        tableModelFixtures.addColumn("Fixture");
        tableModelFixtures.addColumn("path");
        tableModelFixtures.addColumn("Descrição");

        tableFixtures = new DefaultJTableFixture(tableModelFixtures);
        tableFixtures.getColumn("path").setWidth(0);
        tableFixtures.getColumn("path").setMinWidth(0);
        tableFixtures.getColumn("path").setMaxWidth(0);

        tableFixtures.getColumn("Descrição").setWidth(0);
        tableFixtures.getColumn("Descrição").setMinWidth(0);
        tableFixtures.getColumn("Descrição").setMaxWidth(0);



        tableFixtures.setDropMode(DropMode.ON_OR_INSERT);
        tableFixtures.addMouseListener(new OptionsTableFixtures());
        tableFixtures.setTableHeader(null);
        tableFixtures.setTransferHandler(new TableTranferHandler(tableFixtures, tableModelFixtures));
        JScrollPane listView = new JScrollPane(tableFixtures);
        listView.setPreferredSize(new Dimension(300, 100));
        panelFixture.add(createPanelForComponent(listView, "Fixtures"));

        //Create a tree.
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(new FileNode(fileRoot));
        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        tree = new JTree(treeModel);
        tree.setShowsRootHandles(true);
        JScrollPane scrollPane = new JScrollPane(tree);
        frame.add(scrollPane);

        frame.setLocationByPlatform(true);
        frame.setSize(640, 480);
        frame.setVisible(true);

        CreateChildNodes ccn = new CreateChildNodes(fileRoot, root);
        new Thread(ccn).start();
        JScrollPane treeView = new JScrollPane(tree);
        panelTree.add(createPanelForComponent(treeView, "Fitnesse"));

        //Create the toggle button.
        toggleDnD = new JCheckBox("Turn on Drag and Drop");
        toggleDnD.setActionCommand("toggleDnD");
        toggleDnD.addActionListener(this);
        toggleDnD.setVisible(false);

        JSplitPane rightPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelScrip, panelFixture);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelTree, rightPane);
        rightPane.setOneTouchExpandable(true);
        splitPane.setOneTouchExpandable(true);

        add(splitPane, BorderLayout.CENTER);
        add(toggleDnD, BorderLayout.PAGE_END);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        MouseListener ml = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int selRow = tree.getRowForLocation(e.getX(), e.getY());
                if (selRow != -1) {
                    if (tree.getAnchorSelectionPath() != null && tree.getAnchorSelectionPath().toString().contains(".txt")) {
                        JTree jTree = (JTree) e.getSource();
                        TreePath treePath = jTree.getAnchorSelectionPath();
                        if (e.getClickCount() == 2) {
                            pathArchive = fileRoot.getAbsolutePath() + (treePath.getParentPath().toString().replace(treePath.getPathComponent(0).toString(), "").replace(", ", "\\").replace("[", "").replace("]", "")) + "\\" + (treePath.getLastPathComponent().toString());
                            new ReadTest().getScriptTest(pathArchive, tableModelScenario);
                            mapFixture= new ReadTest().getFixtureScenario(tableModelFixtures, fileRoot, pathArchive);
                            statusTableScript = tableModelScenario.getRowCount();
                        }
                    }
                }
            }
        };
        tree.addMouseListener(ml);
        tableFixtures.setDragEnabled(true);
        tableScenario.setDragEnabled(true);
        tree.setDragEnabled(true);


    }

    protected JPanel createVerticalBoxPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
        p.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return p;
    }


    public JPanel createPanelForComponent(JComponent comp, String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(comp, BorderLayout.CENTER);
        if (title != null) {
            panel.setBorder(BorderFactory.createTitledBorder(title));
        }
        return panel;
    }

    private void displayDropLocation(final String string) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JOptionPane.showMessageDialog(null, string);
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        if ("toggleDnD".equals(e.getActionCommand())) {
            boolean toggle = toggleDnD.isSelected();
            tableFixtures.setDragEnabled(toggle);
            tableScenario.setDragEnabled(toggle);
            tree.setDragEnabled(toggle);
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        frame = new JFrame("BasicDnD");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new BasicDnD();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                createAndShowGUI();
                new Thread(new CheckStatus()).start();
            }
        });
    }
}