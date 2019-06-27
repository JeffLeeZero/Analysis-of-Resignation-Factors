package tree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class Test {

    private static ArrayList<ArrayList<String>> trainSet = new ArrayList<>();

    private static ArrayList<ArrayList<String>> testSet = new ArrayList<>();

    private static ArrayList<Attr> attrs = null;

    private static int trueNum = 0;

    private static double ratio = 0.8;

    public static void main(String[] args){
        TreeNode node = buildTree();
        System.out.println("safa");
        for (ArrayList<String> data:
             testSet) {
            String pre = node.doPrediction(data,attrs);
            if(pre.equals(data.get(9))){
                trueNum++;
            }
        }
        System.out.println((double)trueNum/testSet.size());
    }



    public static TreeNode buildTree(){
        ArrayList<ArrayList<String>> datas = importCsv(new File("test.csv"));
        ArrayList<String> attrList = datas.get(0);

        datas.remove(0);
        int sum = datas.size();
        int n = (int)(sum*ratio);
        for(int i= 0;i<n;i++){
            trainSet.add(datas.get(i));
        }
        for(int i= n;i<datas.size();i++){
            testSet.add(datas.get(i));
        }
//        for(int i= 0;i<datas.size()-n;i++){
//            testSet.add(datas.get(i));
//        }
//        for(int i= datas.size()-n;i<datas.size();i++){
//            trainSet.add(datas.get(i));
//        }
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
            i++;
        }
        DecisionTree tree = new DecisionTree();
        //tree.buildArrayList(trainSet,attrs);
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
