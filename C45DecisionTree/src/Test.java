
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class Test {

    private static ArrayList<ArrayList<String>> trainSet = new ArrayList<>();

    private static ArrayList<ArrayList<String>> testSet = new ArrayList<>();

    private static ArrayList<Attr> attrs = null;

    private static double ratio = 0.8;

    private static int N = 100;
    public static void main(String[] args){
//        double sum = 0.0;
//        int trueNum;
//        for(int i = 0;i<N;i++){
//            trueNum = 0;
//            TreeNode node = buildTree();
//            for (ArrayList<String> data:
//                    testSet) {
//                String pre = node.doPrediction(data,attrs);
//                if(pre.equals(data.get(9))){
//                    trueNum++;
//                }
//            }
//            sum += (double)trueNum/testSet.size();
//        }
//        double average = sum/N;
//        System.out.println("average accuracy:"+average);
//        TreeNode node = buildTree();
//        ArrayList<String> data = testSet.get(0);
//        DecisionTree tree = new DecisionTree();
//        tree.setTree(node);
//        String predict = tree.doPrediction(data,attrs);
//        List<String> map = tree.getFinalAttr(data,attrs);
        long startTime =  System.currentTimeMillis();/** 程序运行 processRun();*/ /** 获取当前的系统时间，与初始时间相减就是程序运行的毫秒数，除以1000就是秒数*/
        RandomForest forest = buildForest();
        long endTime =  System.currentTimeMillis();
        long usedTime = (endTime-startTime)/1000;
        System.out.println(usedTime);
    }

    public static RandomForest buildForest(){
        ArrayList<ArrayList<String>> datas = importCsv(new File("test.csv"));
        ArrayList<String> attrList = datas.get(0);
        datas.remove(0);
        attrs = new ArrayList<>();
        int i = 0;
        for (String attr:
                attrList) {
            if(i<5){
                attrs.add(new Attr(attr,false));
                attrs.get(i).divide(datas,i);
            }else{
                attrs.add(new Attr(attr));
            }
            attrs.get(i).setIndex(i);
            i++;
        }
        RandomForest forest = new RandomForest();
        forest.buildForest(datas,attrs);
        return forest;
    }



    public static TreeNode buildTree(){

        ArrayList<ArrayList<String>> datas = importCsv(new File("test.csv"));
        ArrayList<String> attrList = datas.get(0);

        trainSet = new ArrayList<>();
        testSet = new ArrayList<>();
        datas.remove(0);
        int sum = datas.size();
        int n = (int)(sum*ratio);
        for (ArrayList<String> data:
                datas) {
            if(n>0 && Math.random()<ratio){
                n--;
                trainSet.add(data);
            }else {
                testSet.add(data);
            }
        }
        attrs = new ArrayList<>();
        int i = 0;
        for (String attr:
                attrList) {
            if(i<5){
                attrs.add(new Attr(attr,false));
                attrs.get(i).divide(datas,i);
            }else{
                attrs.add(new Attr(attr));
            }
            attrs.get(i).setIndex(i);
            i++;
        }
        DecisionTree tree = new DecisionTree();
        tree.buildArrayList(trainSet,attrs);
        return tree.buildTree(trainSet,attrs);
    }


    public static ArrayList<ArrayList<String>> importCsv(File file){
        ArrayList<ArrayList<String>> dataList=new ArrayList<>();
        ArrayList<String> cells;
        BufferedReader br=null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = "";
            String[] items;
            while ((line = br.readLine()) != null) {
                items = line.split(",");
                cells = new ArrayList<>();
                for (String item:
                     items) {
                    cells.add(item);
                }
                if(cells.size()==9){
                    System.out.println("empty");
                }
                dataList.add(cells);
            }
        }catch (Exception e) {
        }finally{
            if(br!=null){
                try {
                    br.close();
                    br=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return dataList;
    }


}
