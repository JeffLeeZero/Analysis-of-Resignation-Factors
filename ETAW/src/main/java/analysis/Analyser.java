package analysis;

import analysis.DBUtil.DBUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleResultSet;
import oracle.sql.CLOB;
import tree.Attr;
import tree.DecisionTree;
import tree.TreeNode;

import java.io.*;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Analyser implements ResignationAnalyser {
    private String account;
    private String url;
    private String name;

    private DecisionTree tree;
    private ArrayList<Attr> attrs;
    private double ratio = 0.8;//训练集占总数据的比例


    public Analyser(String account,String name){
        this.account = account;
        this.name = name;
    }

    public Analyser(String account){
        this(account,"分析方案");
    }



    @Override
    public void trainModel(String url) {
        this.url = url;
        trainTree();
        String aid = saveInfo();
        saveAttr(aid);
        saveNode(aid);
        //TODO:逻辑回归模型训练和保存
    }

    @Override
    public double getAccuracy() {
        Connection conn = DBUtil.getConnection();
        double accuracy = 0;
        try{
            PreparedStatement state = conn.prepareStatement("select treeaccuracy from analysis where account = ? and name = ?");
            state.setString(1,account);
            state.setString(2,name);
            ResultSet set = state.executeQuery();
            if(set.next()){
                accuracy = set.getDouble(1);
            }
            return accuracy;
        }catch (SQLException e){
            e.printStackTrace();
            return accuracy;
        }finally {
            DBUtil.closeConn(conn);
        }
    }

    @Override
    public double getProbability(ArrayList<String> data) {
        return 0;
    }

    @Override
    public Map<String, Double> getAttrRatio() {
        Connection conn = DBUtil.getConnection();
        Map<String,Double> map = new HashMap<>();
        try{
            PreparedStatement state = conn.prepareStatement("select attrname,d from attribute natural join analysis where account = ? and name = ?");
            state.setString(1,account);
            state.setString(2,name);
            ResultSet set = state.executeQuery();
            while(set.next()){
                map.put(set.getString(1),set.getDouble(2));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.closeConn(conn);
            return map;
        }
    }

    @Override
    public Map<String, String> improveMeasure() {
        return null;
    }

    @Override
    public Map<String, Double> getAttrRatio(String attrName) {
        Connection conn = DBUtil.getConnection();
        Map<String,Double> map = new HashMap<>();
        try{
            PreparedStatement state = conn.prepareStatement("select value,ratio from attrvalue natural join analysis where account = ? and name = ? and attrname = ?");
            state.setString(1,account);
            state.setString(2,name);
            state.setString(3,attrName);
            ResultSet set = state.executeQuery();
            while(set.next()){
                map.put(set.getString(1),set.getDouble(2));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.closeConn(conn);
            return map;
        }
    }

    @Override
    public String doPrediction(ArrayList<String> data) {
        Connection conn = null;
        String tree=null;
        String aid;
        Gson gson = new Gson();
        try{
            conn = DBUtil.getConnection();

            PreparedStatement state = conn.prepareStatement("select aid from analysis where account = ? and name = ?");//conn.prepareStatement("select tree from tree natural join where account = ? and  name = ?");
            state.setString(1,account);
            state.setString(2,name);
            ResultSet set = state.executeQuery();
            if(set.next()){
                aid = set.getString(1);
            }else{
                return "数据库未知错误。";
            }
            set.close();
            state.close();
            state = conn.prepareStatement("select tree from tree where aid = ?");
            state.setString(1,aid);
            set = state.executeQuery();
            if(set.next()){
                Clob clob = set.getClob(1);
                if(clob!=null){
                    tree = clob.getSubString(1,(int)clob.length());
                    Type type = new TypeToken<TreeNode>(){}.getType();
                    TreeNode node = gson.fromJson(tree,type);
                    this.tree = new DecisionTree();
                    this.tree.setTree(node);
                }
            }
            set.close();
            state.close();
            state = conn.prepareStatement("select * from attribute where aid = ?");
            state.setString(1,aid);
            set = state.executeQuery();
            double min,len,d;
            int M,seperated;
            String name;
            attrs = new ArrayList<>();
            while(set.next()){
                name = set.getString("attrname");
                d = set.getDouble("D");
                min = set.getDouble("min");
                len = set.getDouble("len");
                seperated = set.getInt("seperated");
                M = set.getInt("m");
                Attr a;
                if(seperated>0){
                    a = new Attr(name,true,M,min,len);
                    a.setD(d);
                }else{
                    a = new Attr(name,false,M,min,len);
                    a.setD(d);
                    a.divide();
                }
                attrs.add(a);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.closeConn(conn);
        }
        return this.tree.getTree().doPrediction(data,attrs);
    }

    private void trainTree(){
        ArrayList<ArrayList<String>> datas = importCsv(new File(url));
        ArrayList<String> attrList = datas.get(0);
        ArrayList<ArrayList<String>> trainSet = new ArrayList<>();
        ArrayList<ArrayList<String>> testSet = new ArrayList<>();
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
            i++;
        }
        tree = new DecisionTree();
        tree.buildArrayList(trainSet,attrs);
        tree.setTree(tree.buildTree(trainSet,attrs));
        int trueNum = 0;
        for (ArrayList<String> data:
                testSet) {
            String pre = tree.getTree().doPrediction(data,attrs);
            if(pre.equals(data.get(9))){
                trueNum++;
            }
        }
        tree.setAccuracy((double)trueNum/testSet.size());
    }

    private String saveInfo(){
        Connection conn = DBUtil.getConnection();
        String aid = null;
        try{
            conn.setAutoCommit(false);
            int count = 0;
            PreparedStatement state = conn.prepareStatement("select count(*) from analysis");
            ResultSet set = state.executeQuery();
            if(set.next()){
                count = set.getInt(1);
            }
            aid = String.valueOf(count);
            set.close();
            state.close();
            state = conn.prepareStatement("insert into analysis values(?,?,?,?)");
            state.setString(1,account);
            state.setString(2,name);
            state.setString(3,String.valueOf(aid));
            state.setDouble(4,tree.getAccuracy());
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
        Type type = new TypeToken<TreeNode>(){}.getType();
        Gson gson = new Gson();
        String content = gson.toJson(tree.getTree(),type);
        Connection conn = DBUtil.getConnection();
        try{

            //content = "123222222222222222222222222222222222222222222222222";
            //Clob clob = new Clob();

            String sql = "insert into tree values(?,empty_clob())";
            //锁住该列，防止并发写入时候该字段同时被多次写入造成错误
            String sqlClob = "select tree from tree where aid=? for update";
            PreparedStatement pst =null;
            ResultSet rs = null;
            Writer writer = null;
            conn.setAutoCommit(false);//设置不自动提交，开启事务
            pst = conn.prepareStatement(sql);
            pst.setString(1,aid);
            pst.executeUpdate();

            pst= conn.prepareStatement(sqlClob);
            pst.setString(1, aid);

            rs = pst.executeQuery();
            CLOB clob = null;
            if(rs.next()){

                clob = (CLOB) rs.getClob(1);
                writer = clob.getCharacterOutputStream(); //拿到clob的字符输入流
                writer.write(content);
                writer.flush();
                writer.close();
            }
            conn.commit();




        }catch (SQLException e){
            e.printStackTrace();
            DBUtil.rollback(conn);
        }catch (IOException e){
            e.printStackTrace();
            DBUtil.rollback(conn);
        }finally {
            DBUtil.closeConn(conn);
        }
    }

    private void saveAttr(String aid){
        Connection conn = DBUtil.getConnection();
        try{
            conn.setAutoCommit(false);
            PreparedStatement state;
            PreparedStatement state2;
            for (Attr attr:
                 attrs) {
                state = conn.prepareStatement("insert into attribute values (?,?,?,?,?,?,?)");
                state.setString(1,attr.getName());
                state.setString(2,aid);
                state.setDouble(3,attr.getD());
                state.setBoolean(4,attr.isSeperated());
                state.setDouble(5,attr.getMin());
                state.setDouble(6,attr.getLen());
                state.setInt(7,attr.getM());
                state.executeUpdate();
                state.close();
                for (Map.Entry<String,Double> entry:
                     attr.getProbability().entrySet()) {
                     state2 = conn.prepareStatement("insert into attrvalue values (?,?,?,?)");
                    state2.setString(1,attr.getName());
                    state2.setString(2,aid);
                    state2.setString(3,entry.getKey());
                    state2.setDouble(4,entry.getValue());
                    state2.executeUpdate();
                    state2.close();
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

    public static void main(String[] args){
        ResignationAnalyser analyser = new Analyser("jeff11");
        analyser.doPrediction(null);
    }
}
