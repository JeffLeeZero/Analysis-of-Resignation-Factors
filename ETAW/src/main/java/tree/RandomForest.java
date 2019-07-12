package tree;

import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;

/**
 * 随机森林
 * @author 李沛昊
 */
public class RandomForest {
    private ArrayList<ForestTree> forest;
    private int TREECOUNT;//树的棵树
    private int threadCount = 20;//线程个数
    private CyclicBarrier barrier;//屏障
    public RandomForest(){
        this.forest = new ArrayList<>();
        this.TREECOUNT = 300;
    }

    /**
     * 构建森林
     * @param datas
     * @param attrList
     */
    public void buildForest(ArrayList<ArrayList<String>> datas,ArrayList<Attr> attrList){
        barrier = new CyclicBarrier(threadCount+1);
        int n = (int)Math.sqrt(attrList.size());
        int m = datas.size() * 2 / 3;
        DecisionTree tree;
        for(int i = 0;i<this.TREECOUNT;){
            for(int j = 0;j<threadCount;j++){
                ForestRunnble runnble = new ForestRunnble(i,datas,attrList);
                this.forest.add(runnble.getTree());
                Thread thread = new Thread(runnble);
                thread.start();
            }
            i+=threadCount;
            try{
                barrier.await();
                barrier.reset();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        barrier = null;
    }

    /**
     * 为每一颗树随机有放回地抽取训练集
     * @param datas
     * @return
     */
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



    public String doPrediction(ArrayList<String> data,ArrayList<Attr> attrList){
        String result;
        int count = 0;
        for (ForestTree tree:
             forest) {
            result = tree.doPrediction(data,attrList);
            if(result.equals("1")){
                count++;
            }else{
                count--;
            }
        }
        if(count>0){
            return "1";
        }else {
            return "0";
        }
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

    /**
     * 用于多线程构建随机森林
     */
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
            tree.setAttrList(null);
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

