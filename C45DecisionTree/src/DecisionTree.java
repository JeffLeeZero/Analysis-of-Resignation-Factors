import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DecisionTree {
    private TreeNode treeNode;

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
        System.out.println("候选属性列表：");
        for (Attr attr:
                attrList) {
            System.out.print(attr.getName()+"\t");
        }
        System.out.println();

        TreeNode node;
        node = new TreeNode();
        node.setDatas(datas);
        node.setCandAttrs(attrList);

        Map<String,Integer> classes = classOfDatas(datas);

        String maxc = getMaxClass(classes);
        System.out.println("maxc"+maxc);

        //退出递归条件：1、所有的元组目标属性都一样；2、已将所有待划分节点划分完毕
        if(classes.size()==1 || attrList.size() == 1){
            node.setName(maxc);
            return node;
        }

        Gain gain = new Gain(datas,attrList);
        int bestAttrIndex = gain.bestGainAttrIndex();
        ArrayList<String> rules = gain.getValues(datas,bestAttrIndex);

        node.setRules(rules);
        node.setName(attrList.get(bestAttrIndex).getName());

        attrList.remove(bestAttrIndex);

        ArrayList<ArrayList<ArrayList<String>>> allDatas = new ArrayList<>();
        ArrayList<ArrayList<String>> di;

        //元组划分
        for (String rule:
                rules) {
            di = gain.datasOfValue(bestAttrIndex,rule);
            allDatas.add(di);
        }

        for (ArrayList<ArrayList<String>> group:
                allDatas) {
            for (ArrayList<String> tuple:
                    group) {
                tuple.remove(bestAttrIndex);
            }
            //事实上这个判断条件应该不会发生
            if(group.size()==0||attrList.size()==0){
                System.err.println("something wrong happened!!!!!");
                TreeNode leafNode = new TreeNode();
                leafNode.setName(maxc);
                leafNode.setDatas(group);
                leafNode.setCandAttrs(attrList);
                node.getChildren().add(leafNode);
            }else{
                TreeNode newNode = buildTree(group,attrList);
                node.getChildren().add(newNode);
            }
        }
        return node;
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
     * 获取具有最大计数的属性值
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



}
