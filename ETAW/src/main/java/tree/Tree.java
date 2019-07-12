package tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 决策树基类
 * @author 李沛昊
 */
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

    /**
     * 获取属性集中目标属性的值域和计数
     * @param datas
     * @return
     * @author 李沛昊
     */
    protected Map<String,Integer> classOfDatas(ArrayList<ArrayList<String>> datas){
        Map<String, Integer> classes = new HashMap<>();
        String value;
        for (ArrayList<String> tuple:
                datas) {
            //获取目标属性值
            value = tuple.get(tuple.size()-1);
            if(classes.containsKey(value)){
                classes.put(value,classes.get(value)+1);
            }else{
                classes.put(value,1);
            }
        }
        return classes;
    }

    /**
     * 获取目标属性中具有最大计数的属性值
     * @param classes
     * @return
     * @author 李沛昊
     */
    protected String getMaxClass(Map<String,Integer> classes){
        String val,maxV = "";
        int count,max = 0;
        for (Map.Entry<String,Integer> entry:
                classes.entrySet()) {
            val = entry.getKey();
            count = entry.getValue();
            if(count>max){
                max = count;
                maxV = val;
            }
        }
        return maxV;
    }
}
