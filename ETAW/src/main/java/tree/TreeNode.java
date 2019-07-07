package tree;

import java.util.ArrayList;

/**
 * 决策树的节点类
 * @status 完成
 * @author 李沛昊
 * @time 2019.6.23
 */
public class TreeNode {
    private String name;//节点名（节点的分裂属性）
    private ArrayList<TreeNode> children;//子节点集合
    private String value;//对应的属性值
    private ArrayList<ArrayList<String>> datas;//划分到该节点的训练元组
    private ArrayList<Attr> candAttrs;//划分到该节点的候选属性
    private static int tab = 0;
    public TreeNode(){
        name = "";
        children = new ArrayList<TreeNode>();
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


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public void show(){
        tab++;
        for(int i = 0;i<tab;i++){
            System.out.print("\t");
        }
        System.out.println("<"+name+":"+value+">");
        for (TreeNode node:
             children) {
            node.show();
        }
        for(int i = 0;i<tab;i++){
            System.out.print("\t");
        }
        System.out.println("</"+name+":"+value+">");
        tab--;
    }

    public String doPrediction(ArrayList<String> data,ArrayList<Attr> attrlist) {

        if (this.getChildren().size() == 0) {
            return getName();
        }
        int i;
        Attr attr = null;
        for (i = 0; i < attrlist.size(); i++) {
            attr = attrlist.get(i);
            if (name.equals(attr.getName())) {
                break;
            }
        }
        if (attr.isSeperated()) {
            for (TreeNode node :
                    children) {
                if (node.getValue().equals(data.get(attr.getIndex()))) {
                    return node.doPrediction(data, attrlist);
                }
            }
            return "数据不足，无法预测。";
        } else {
            for (TreeNode node :
                    children) {
                if (node.getValue().equals(attr.getValue(data.get(attr.getIndex())))) {
                    return node.doPrediction(data, attrlist);
                }

            }
            return "数据不足，无法预测。";
        }
    }
}
