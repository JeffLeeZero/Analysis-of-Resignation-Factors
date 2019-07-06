package tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DecisionTree extends Tree{
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
        return getTreeNode();
    }

    public void setTree(TreeNode treeNode) {
        setTreeNode(treeNode);
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
        for(int i = 0;i<attrList.size()-1;i++){
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
                        n = 0;
                        sum = map.get("0");
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

        //离职率和总人数
        int sum = datas.size();
        double ratio = (double) gain.getValues(datas,attrList.size()-1).size()/sum;
        this.attrList.get(attrList.size()-1).addProbability("总人数",(double)sum);
        this.attrList.get(attrList.size()-1).addProbability("离职率",ratio);
    }


    /**
     * 递归构造决策树
     * @param datas 数据集
     * @param attrList 待选属性集
     * @return 根节点
     * @author 李沛昊
     */
    @Override
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

    @Override
    public String doPrediction(ArrayList<String> data,ArrayList<Attr> attrlist){
        return getTree().doPrediction(data,attrlist);
    }

    public List<String> getFinalAttr(ArrayList<String> data,ArrayList<Attr> attrlist){
        return getFinalAttr(getTree(),data,attrlist);
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
