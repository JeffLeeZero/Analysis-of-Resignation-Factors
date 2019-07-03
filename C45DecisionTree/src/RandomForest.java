

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CyclicBarrier;

public class RandomForest {
    private ArrayList<ForestTree> forest;
    private int TREECOUNT;
    private final CyclicBarrier barrier = new CyclicBarrier(11);
    public RandomForest(){
        this.forest = new ArrayList<>();
        this.TREECOUNT = 100;
    }

    public void buildForest(ArrayList<ArrayList<String>> datas,ArrayList<Attr> attrList){

        int n = (int)Math.sqrt(attrList.size());
        int m = datas.size() * 2 / 3;
        DecisionTree tree;
        for(int i = 0;i<this.TREECOUNT;){
            for(int j = 0;j<10;j++){
                ForestRunnble runnble = new ForestRunnble(i,datas,attrList);
                this.forest.add(runnble.getTree());
                Thread thread = new Thread(runnble);
                thread.start();
            }
            i+=10;
            try{
                barrier.await();
                barrier.reset();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        computeAttrImportance(attrList);
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

    private void computeAttrImportance(ArrayList<Attr> attrList){
        ArrayList<Attr> attrs;
        for (ForestTree tree:
             forest) {
            attrs = tree.getAttrList();
            for(int i = 0;i<attrList.size()-1;i++){
                attrList.get(i).setD(attrList.get(i).getD()+attrs.get(i).getD());
            }
        }
        double sum = 0.0;
        for(int i = 0;i<attrList.size()-1;i++){
            sum += attrList.get(i).getD();
        }
        for(int i = 0;i<attrList.size()-1;i++){
            attrList.get(i).setD(attrList.get(i).getD()/sum);
        }
    }


    class ForestRunnble implements Runnable{

        public ForestTree tree;
        private ArrayList<ArrayList<String>> datas;
        public ForestRunnble(int i,ArrayList<ArrayList<String>> datas,ArrayList<Attr> attrList){
            tree = new ForestTree(attrList);
            this.datas = datas;
        }

        @Override
        public void run() {
            ArrayList<ArrayList<String>> train = RandomForest.getRandomData(datas);
            tree.setTreeNode(tree.buildTree(train,null));
            try{
                barrier.await();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        public ForestTree getTree() {
            return tree;
        }

    }
}

