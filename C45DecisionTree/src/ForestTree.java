import java.util.ArrayList;
import java.util.Map;

public class ForestTree extends Tree {

    private ArrayList<Attr> attrList;
    private int i = 0;
    public ForestTree( ArrayList<Attr> attrList){
        this.attrList = new ArrayList<>();
        Attr attr;
        for (Attr old:
             attrList) {
            attr = new Attr(old);
            this.attrList.add(attr);
        }
    }

    @Override
    public TreeNode buildTree(ArrayList<ArrayList<String>> datas, ArrayList<Attr> attrList) {
        i++;
        TreeNode node = new TreeNode();
        ArrayList<Attr> newAttrs = getRandomAttrs(this.attrList);
        Gain gain = new Gain(datas,newAttrs);
        Map<String,Integer> classes = classOfDatas(datas);
        String maxc;
        if(i>10||classes.size()==1){
            maxc = getMaxClass(classes);
            node.setName(maxc);
            i--;
            return node;
        }
        int bestAttrIndex = gain.bestGainAttrIndex("RECORD");
        ArrayList<String> rules = gain.getValues(datas,bestAttrIndex);

        node.setName(newAttrs.get(bestAttrIndex).getName());

        ArrayList<ArrayList<String>> group;
        for (String rule:
             rules) {
            group = gain.datasOfValue(bestAttrIndex,rule);
            TreeNode leaf;
            if(group.size()==0){
                leaf = new TreeNode();
                leaf.setName("null");
                leaf.setValue(rule);
            }else{
                leaf = buildTree(group,null);
            }
            node.getChildren().add(leaf);
            leaf.setValue(rule);
        }
        i--;
        return node;
    }

    @Override
    String doPrediction(ArrayList<String> data, ArrayList<Attr> attrList) {
        return getTreeNode().doPrediction(data,attrList);
    }

    private ArrayList<Attr> getRandomAttrs(ArrayList<Attr> attrList){
        int count = attrList.size()-1;
        int len = (int)Math.sqrt(count);
        int index;
        ArrayList<Attr> attrs = new ArrayList<>();
        for (int i = 0; i < len;i++){
            index = (int)(Math.random()*count);
            attrs.add(attrList.get(index));
        }
        attrs.add(attrList.get(attrList.size()-1));
        return attrs;
    }

    public ArrayList<Attr> getAttrList() {
        return attrList;
    }
}
