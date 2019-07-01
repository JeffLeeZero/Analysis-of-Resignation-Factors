package analysis;

import analysis.DBUtil.DBUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.ibatis.session.SqlSessionException;
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
        Process proc;
        try{
            String[] fileData = new String[]{"python", "src\\main\\java\\logisticregression\\logistic_regression2.py",  aid, url};
            proc = Runtime.getRuntime().exec(fileData);
            BufferedReader in =  new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while ((line = in.readLine()) != null){
                System.out.println(line);
            }
            in.close();
            proc.waitFor();
        }
        catch (Exception e){
            e.printStackTrace();
        }


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
    public ArrayList<String> getProbability(ArrayList<String> data, String aid, String department) {
        //TODO:
        Process proc;
        ArrayList<String> dataset = new ArrayList<>();
        try{
            String testDatas = String.join(",", data);
            String[] fileData = new String[]{"python", "src\\main\\java\\logisticregression\\analyze.py", testDatas, aid, department};
            proc = Runtime.getRuntime().exec(fileData);
            BufferedReader in =  new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while ((line = in.readLine()) != null){
                dataset.add(line);
            }
            in.close();
            proc.waitFor();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return dataset;
    }

    @Override
    public ArrayList<Float> getResult(ArrayList<String> result, int index){
        ArrayList<Float> which = new ArrayList<>();
        for(int i = 0;i<result.size();i++){
            if(i%4 == index){
                which.add(Float.parseFloat(result.get(i)));
            }
        }
        System.out.println(which);
        return which;
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
        tree.getTree().doPrediction(data,attrs);
        Connection conn = DBUtil.getConnection();
        String aid="";
        try{
            PreparedStatement state = conn.prepareStatement("select tree,aid from tree natural join analysis where account = ? and name = ?");
            state.setString(1,account);
            state.setString(2,name);
            ResultSet set = state.executeQuery();
            if(set.next()){
                Clob clob = set.getClob("tree");//java.sql.Clob
                aid = set.getString("aid");
                String detailinfo = "";
                if(clob != null){
                    detailinfo = clob.getSubString((long)1,(int)clob.length());
                    Gson gson = new Gson();
                    Type type = new TypeToken<TreeNode>(){}.getType();
                    tree = new DecisionTree();
                    tree.setTree(gson.fromJson(detailinfo,type));
                }
            }
            set.close();
            state.close();
            state = conn.prepareStatement("select * from attribute where aid = ?");
            state.setString(1,aid);
            set = state.executeQuery();
            int M,seperated;
            double len,min,D;
            String name;
            Attr attr;
            while(set.next()){
                name = set.getString("attrname");
                len = set.getDouble("len");
                min = set.getDouble("min");
                D = set.getDouble("D");
                M = set.getInt("M");
                seperated = set.getInt("seperated");
                attr = new Attr(name,seperated>0,M,min,len);
                attrs.add(attr);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.closeConn(conn);
        }
        return tree.doPrediction(data,attrs);
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
            state = conn.prepareStatement("insert into analysis values(?,?,?,?,?)");
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
        String content = gson.toJson(tree,type);
        Connection conn = DBUtil.getConnection();
        try{
            PreparedStatement state = conn.prepareStatement("insert into tree values (?,?)");
            Reader reader = new StringReader(content);
            state.setString(1,aid);
            state.setCharacterStream(2,reader,content.length());
            int i = state.executeUpdate();
            if(i<1){
                System.err.println("clob error!");
            }
        }catch (SQLException e){
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
        analyser.trainModel("test.csv");
        //analyser.doPrediction(null);
        //测试数据,这部分需要前端传入
        ArrayList<String> data = new ArrayList<>();
        //'0.38 0.53 157 3 0 0 0'
        data.add("0.38");
        data.add("0.53");
        data.add("157");
        data.add("3");
        data.add("0");
        data.add("0");
        data.add("0");
        //获取训练数据集的URL
        analyser.trainModel("E:\\LR\\Analysis-of-Resignation-Factors-master\\ETAW\\test.csv");
        ArrayList<String> results = analyser.getProbability(data, "1", "IT");
        //是否离职 0不离职，1离职
        ArrayList<Float> leftResult = analyser.getResult(results,0);
        //上一个结果的准确度（可能是）
        ArrayList<Float> successResult = analyser.getResult(results,1);
        //离职与否的不准确度（可能是）
        ArrayList<Float> failureResult = analyser.getResult(results,2);
        //该模型的拟合度
        ArrayList<Float> scoreResult = analyser.getResult(results,3);

    }
}
