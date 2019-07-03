import java.util.ArrayList;

public abstract class Tree {
    private TreeNode treeNode;

    abstract public TreeNode buildTree(ArrayList<ArrayList<String>> datas, ArrayList<Attr> attrList);

    abstract String doPrediction(ArrayList<String> data,ArrayList<Attr> attrList);

    public TreeNode getTreeNode() {
        return treeNode;
    }

    public void setTreeNode(TreeNode treeNode) {
        this.treeNode = treeNode;
    }
}
