package tree;

import java.util.*;

public class DecisionTree {
    private TreeNode treeNode;
    private ArrayList<Attr> attrList = null;
    private double ratio = 0.98;
    private double accuracy;

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public TreeNode getTree() {
        return treeNode;
    }

    public void setTree(TreeNode treeNode) {
        this.treeNode = treeNode;
    }

    public ArrayList<Attr> getAttrList() {
        return attrList;
    }

    public void buildArrayList(ArrayList<ArrayList<String>> datas,ArrayList<Attr> attrList){
        this.attrList = new ArrayList<>(attrList);
        Gain gain = new Gain(datas,attrList);
        double d;
        ArrayList<String> values;
        ArrayList<ArrayList<String>> data;
        Map<String,Integer> map;
        for(int i = 0;i<attrList.size();i++){
            d  = (double)((gain.infoD(datas) - gain.infoAttr(i)))/gain.splitInfo(i);
            this.attrList.get(i).setD(d);
            values = gain.getValues(datas,i);
            int n,sum;
            for (String value:
                    values) {
                data = gain.datasOfValue(i,value);
                if(data.size()==0){
                    continue;
                }
                map = gain.valueCounts(data,attrList.size()-1);
                if(map.size()==1){
                    if(map.containsKey("0")){
                        n = sum = map.get("0");
                    }else{
                        n = sum = map.get("1");
                    }
                }else{
                    try{
                        n = map.get("1");
                        sum = n + map.get("0");
                    }catch (Exception e){
                        e.printStackTrace();
                        return;
                    }
                }
                this.attrList.get(i).addProbability(value,(double)n/sum);
            }
        }
    }


    /**
     * 递归构造决策树
     * @param datas 数据集
     * @param attrList 待选属性集
     * @return 根节点
     * @author 李沛昊
     */
    public TreeNode buildTree(ArrayList<ArrayList<String>> datas, ArrayList<Attr> attrList){
        //TODO:退出条件的改进，防止过拟合
        //TODO:剪枝操作
        TreeNode node;
        node = new TreeNode();

        Map<String,Integer> classes = classOfDatas(datas);

        String maxc = getMaxClass(classes);

        //退出递归条件：1、所有的元组目标属性都一样；2、已将所有待划分节点划分完毕
        if(isAlmost(classes,maxc) || attrList.size() == 1){
            node.setName(maxc);
            return node;
        }

        Gain gain = new Gain(datas,attrList);
        int bestAttrIndex = gain.bestGainAttrIndex();
        ArrayList<String> rules = gain.getValues(datas,bestAttrIndex);

        node.setName(attrList.get(bestAttrIndex).getName());
        attrList = new ArrayList<>(attrList);
        attrList.remove(bestAttrIndex);

        ArrayList<ArrayList<String>> group;
        ArrayList<Attr> newAttr;
        //元组划分
        for (String rule:
                rules) {
            group = gain.datasOfValue(bestAttrIndex,rule);
            ArrayList<ArrayList<String>> newGroup = new ArrayList<>();
            for (ArrayList<String> tuple:
                    group) {
                tuple = new ArrayList<>(tuple);
                //tuple.remove(bestAttrIndex);
                newGroup.add(tuple);
            }
            group = newGroup;
            if(group.size()==0||attrList.size()==0){
                TreeNode leafNode = new TreeNode();
                leafNode.setName(maxc);
                leafNode.setValue(rule);
                node.getChildren().add(leafNode);
            }else{
                newAttr = new ArrayList<>(attrList);
                TreeNode newNode = buildTree(group,newAttr);
                newNode.setValue(rule);
                node.getChildren().add(newNode);
            }
        }
        return node;
    }

    public String doPrediction(ArrayList<String> data,ArrayList<Attr> attrlist){
        return treeNode.doPrediction(data,attrlist);
    }

    public List<String> getFinalAttr(ArrayList<String> data,ArrayList<Attr> attrlist){
        return getFinalAttr(treeNode,data,attrlist);
    }

    private List<String> getFinalAttr(TreeNode node,ArrayList<String> data,ArrayList<Attr> attrlist){
        if(node.getChildren().size()==0){
            return new ArrayList<>();
        }
        int i;
        Attr attr=null;
        for (i = 0;i < attrlist.size();i++){
            attr = attrlist.get(i);
            if(node.getName().equals(attr.getName())){
                break;
            }
        }
        for (TreeNode child:
                node.getChildren()) {
            boolean equal;
            if(attr.isSeperated()){
                equal = child.getValue().equals(data.get(i));
            }else{
                equal = child.getValue().equals(attr.getValue(data.get(i)));
            }
            if(equal){
                List<String> map =  getFinalAttr(child,data,attrlist);
                if(map!=null&&map.size()==0){
                    String name = node.getName();
                    map.add(name);
                    for (TreeNode c:
                         node.getChildren()) {
                        if(c.getName().equals("0")){
                            map.add(c.getValue());
                        }
                    }
                    return map;
                }else if(map!=null){
                    return map;
                }
            }
        }
        return null;
    }


    /**
     * 获取属性集中目标属性的值域和计数
     * @param datas
     * @return
     * @author 李沛昊
     */
    private Map<String,Integer> classOfDatas(ArrayList<ArrayList<String>> datas){
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
    private String getMaxClass(Map<String,Integer> classes){
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

    private boolean isAlmost(Map<String,Integer> map,String maxV){
        int sum = 0;
        for (Map.Entry<String,Integer> entry:
             map.entrySet()) {
            sum += entry.getValue();
        }
        if((double)map.get(maxV)/sum >= ratio){
            return true;
        }else{
            return false;
        }
    }

}
