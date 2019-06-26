import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DecisionTree {
    private TreeNode treeNode;
    private double ratio = 1;


    public TreeNode getTree() {
        return treeNode;
    }

    public void setTree(TreeNode treeNode) {
        this.treeNode = treeNode;
    }

    /**
     * 递归构造决策树
     * @param datas 数据集
     * @param attrList 待选属性集
     * @return 根节点
     * @author 李沛昊
     */
    public TreeNode buildTree(ArrayList<ArrayList<String>> datas,ArrayList<Attr> attrList){
        //TODO:退出条件的改进，防止过拟合
        //TODO:剪枝操作
        TreeNode node;
        node = new TreeNode();
        node.setDatas(datas);
        node.setCandAttrs(attrList);

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
            //TODO:任然存在拷贝问题
            group = gain.datasOfValue(bestAttrIndex,rule);
            ArrayList<ArrayList<String>> newGroup = new ArrayList<>();
            for (ArrayList<String> tuple:
                    group) {
                tuple = new ArrayList<>(tuple);
                tuple.remove(bestAttrIndex);
                newGroup.add(tuple);
            }
            group = newGroup;
            //事实上这个判断条件应该不会发生
            if(group.size()==0||attrList.size()==0){
                TreeNode leafNode = new TreeNode();
                leafNode.setName(maxc);
                leafNode.setDatas(group);
                leafNode.setValue(rule);
                leafNode.setCandAttrs(attrList);
                node.getChildren().add(leafNode);
            }else{
                newAttr = new ArrayList<>(attrList);
                TreeNode newNode = buildTree(group,newAttr);
                newNode.setValue(rule);
                node.getChildren().add(newNode);
            }
        }
        return node;
        //TODO:1、浅拷贝得到的众多List可否释放掉
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
