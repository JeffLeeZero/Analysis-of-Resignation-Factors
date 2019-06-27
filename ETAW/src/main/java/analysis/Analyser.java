package analysis;

import analysis.DBUtil.DBUtil;
import tree.Attr;
import tree.DecisionTree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class Analyser implements ResignationAnalyser {
    private String account;
    private String url;
    private String name;
    private DecisionTree tree;
    private ArrayList<Attr> attrs;
    private double ratio = 0.8;//训练集占总数据的比例


    public Analyser(String account,String url,String name){
        this.account = account;
        this.url = url;
        this.name = name;
    }

    public Analyser(String account,String url){
        this(account,url,"分析方案");
    }

    @Override
    public void trainModel() {
        trainTree();
        String aid = saveInfo();
        saveAttr(aid);
        saveNode(aid);
    }

    private void trainTree(){
        ArrayList<ArrayList<String>> datas = importCsv(new File(url));
        ArrayList<String> attrList = datas.get(0);
        ArrayList<ArrayList<String>> trainSet = new ArrayList<>();
        ArrayList<ArrayList<String>> testSet = new ArrayList<>();
        datas.remove(0);
        int sum = datas.size();
        int n = (int)(sum*ratio);
        for(int i= 0;i<n;i++){
            trainSet.add(datas.get(i));
        }
        for(int i= n;i<datas.size();i++){
            testSet.add(datas.get(i));
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
            i++;
        }
        tree = new DecisionTree();
        tree.buildArrayList(trainSet,attrs);
        tree.setTree(tree.buildTree(trainSet,attrs));
    }

    private String saveInfo(){
        Connection conn = DBUtil.getConnection();
        String aid = null;
        try{
            int count = 0;
            PreparedStatement state = conn.prepareStatement("select count(*) from analysis");
            ResultSet set = state.executeQuery();
            if(set.next()){
                count = set.getInt(1);
            }
            aid = String.valueOf(count);
            set.close();
            state.close();
            state = conn.prepareStatement("insert into analysis values(?,?,?,?,?)");
            state.setString(1,account);
            state.setString(2,name);
            state.setString(3,String.valueOf(aid));
            state.setDouble(4,tree.getAccuracy());
            state.setString(5,aid+"0");
            state.executeUpdate();
            conn.commit();
        }catch (SQLException e){
            DBUtil.rollback(conn);
            e.printStackTrace();
        }finally {
            DBUtil.closeConn(conn);
            return aid;
        }
    }

    private void saveNode(String aid){

    }

    private void saveAttr(String aid){
        Connection conn = DBUtil.getConnection();
        try{
            PreparedStatement state = conn.prepareStatement("insert into attribute values (?,?,?,?,?,?,?)");
            PreparedStatement state2 = conn.prepareStatement("insert into attrvalue values (?,?,?,?)");
            for (Attr attr:
                 attrs) {
                state.setString(1,attr.getName());
                state.setString(2,aid);
                state.setDouble(3,attr.getD());
                state.setBoolean(4,attr.isSeperated());
                state.setDouble(5,attr.getMin());
                state.setDouble(6,attr.getLen());
                state.setInt(7,attr.getM());
                state.executeUpdate();
                for (Map.Entry<String,Double> entry:
                     attr.getProbability().entrySet()) {
                    state2.setString(1,attr.getName());
                    state2.setString(2,aid);
                    state2.setString(3,entry.getKey());
                    state2.setDouble(4,entry.getValue());
                    state2.executeUpdate();
                }
            }
            conn.commit();
        }catch (SQLException e){
            e.printStackTrace();
            DBUtil.rollback(conn);
        }finally {
            DBUtil.closeConn(conn);
        }
    }

    private ArrayList<ArrayList<String>> importCsv(File file){
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
                if(cells.size()<10){
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
