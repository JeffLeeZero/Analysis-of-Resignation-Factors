import java.util.ArrayList;

/**
 * 决策树的节点类
 * @status 完成
 * @author 李沛昊
 * @time 2019.6.23
 */
public class TreeNode {
    private String name;//节点名（分裂属性的名称）
    private ArrayList<TreeNode> children;//子节点集合
    private ArrayList<String> rules;//节点的分裂规则 二分属性
    private ArrayList<ArrayList<String>> datas;//划分到该节点的训练元组
    private ArrayList<Attr> candAttrs;//划分到该节点的候选属性

    public TreeNode(){
        name = "";
        children = new ArrayList<TreeNode>();
        rules = new ArrayList<String>();
        datas = null;
        candAttrs = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<TreeNode> children) {
        this.children = children;
    }

    public ArrayList<String> getRules() {
        return rules;
    }

    public void setRules(ArrayList<String> rules) {
        this.rules = rules;
    }

    public ArrayList<ArrayList<String>> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<ArrayList<String>> datas) {
        this.datas = datas;
    }

    public ArrayList<Attr> getCandAttrs() {
        return candAttrs;
    }

    public void setCandAttrs(ArrayList<Attr> candAttrs) {
        this.candAttrs = candAttrs;
    }
}
