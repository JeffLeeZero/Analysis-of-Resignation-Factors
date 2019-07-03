

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RandomForest {
    private ArrayList<DecisionTree> forest;
    private int TREECOUNT;

    public RandomForest(){
        this.forest = new ArrayList<>();
        this.TREECOUNT = 100;
    }

    public void buildForest(ArrayList<ArrayList<String>> datas,ArrayList<Attr> attrList){
        int n = (int)Math.sqrt(attrList.size());
        int m = datas.size() * 2 / 3;
        DecisionTree tree;
        for(int i = 0;i<this.TREECOUNT;i++){
            ForestRunnble runnble = new ForestRunnble(i,datas,attrList);
            this.forest.add(runnble.getTree());
            Thread thread = new Thread(runnble);
            thread.run();
        }
    }

    public static ArrayList<Attr> getRandomAttrs(ArrayList<Attr> attrList){
        int count = attrList.size()-1;
        int len = (int)Math.sqrt(count);
        double ratio = (double)len/count;
        ArrayList<Attr> attrs = new ArrayList<>();
        for (int i = 0; i < count-1;i++){
            if(Math.random()<ratio){
                attrs.add(attrList.get(i));
            }
        }
        attrs.add(attrList.get(attrList.size()-1));
        return attrs;
    }

    public static ArrayList<ArrayList<String>> getRandomData(ArrayList<ArrayList<String>> datas){
        int count = datas.size();
        int len = count * 2 /3;
        ArrayList<ArrayList<String>> sub= new ArrayList<>();
        for (int i = 0; i < len;i++){
            int m = (int)(Math.random()*count);
            sub.add(datas.get(m));
        }
        return sub;
    }

}

class ForestRunnble implements Runnable{

    public DecisionTree tree;
    //public Map<> map;
    private int index,m,n;
    private ArrayList<ArrayList<String>> datas;
    ArrayList<Attr> attrList;
    public ForestRunnble(int i,ArrayList<ArrayList<String>> datas,ArrayList<Attr> attrList){
        index = i;
        tree = new DecisionTree();
        this.datas = datas;
        this.attrList = attrList;
    }

    @Override
    public void run() {
        ArrayList<ArrayList<String>> train = RandomForest.getRandomData(datas);
        ArrayList<Attr> attrs = RandomForest.getRandomAttrs(attrList);
        tree.setTree(tree.buildTree(train,attrs));
    }

    public DecisionTree getTree() {
        return tree;
    }

    public void setTree(DecisionTree tree) {
        this.tree = tree;
    }
}