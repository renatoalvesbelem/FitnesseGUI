package Nodes;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;

/**
 * Created by jose.renato on 28/10/2016.
 */
public class CreateChildNodes implements Runnable {

    private DefaultMutableTreeNode root;

    private File fileRoot;

    public CreateChildNodes(File fileRoot, DefaultMutableTreeNode root) {
        this.fileRoot = fileRoot;
        this.root = root;
    }

    @Override
    public void run() {
        createChildren(fileRoot, root);
    }

    private void createChildren(File fileRoot, DefaultMutableTreeNode node) {
        File[] files = fileRoot.listFiles();
        if (files == null) return;

        for (File file : files) {
            String pathFile = file.getAbsoluteFile().toString();
            if (pathFile.contains("FitNesseRoot") && !pathFile.contains("files")&& !file.getName().matches("^\\..*") && !pathFile.contains("properties")) {
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new FileNode(file));
                node.add(childNode);
                if (file.isDirectory()) {
                    createChildren(file, childNode);
                }
            }
        }
    }
}
