package tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RandomForest {
    private List<DecisionTree> forest;
    private int TREECOUNT;

    public RandomForest(){
        this.forest = new ArrayList<>();
        this.TREECOUNT = 100;
    }

    public void buildForest(List<List<String>> datas,List<Attr> attrList){
        int n = (int)Math.sqrt(attrList.size());
        int m = datas.size() * 2 / 3;
        DecisionTree tree;
        for(int i = 0;i<this.TREECOUNT;){
            for(int j = 0;j<10;j++){
                tree = new DecisionTree();

                this.forest.add(tree);
            }

        }
    }


}

class ForestRunnble implements Runnable{

    public DecisionTree tree;
    //public Map<> map;
    private int index,m,n;
    private List<List<String>> datas;
    List<Attr> attrList;
    public ForestRunnble(int i,int m, int n,List<List<String>> datas,List<Attr> attrList){
        index = i;
        this.m = m;
        this.n = n;
        this.datas = datas;
        this.attrList = attrList;
    }

    @Override
    public void run() {
        List<List<String>> train = new ArrayList<>();
        List<Attr> attrs = new ArrayList<>();
        int i = 0;
        int len = attrList.size();
        for(i = 0;i<len-1;i++){
            if(Math.random()<n/len){
                attrs.add(attrList.get(i));
            }
        }
        attrs.add(attrList.get(len-1));
        len = datas.size();
        for (i = 0;i<len;i++){
            if(Math.random()<m/len){
                train.add(datas.get(i));
            }
        }
    }
}