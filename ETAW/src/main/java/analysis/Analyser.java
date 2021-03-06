package analysis;

import analysis.DBUtil.DBUtil;

import com.sun.jndi.toolkit.url.Uri;
import jdk.nashorn.internal.runtime.ECMAException;
import tree.Attr;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 机器学习和后台的接口类
 * @author 李沛昊、张鼎、万宇
 */
public class Analyser implements ResignationAnalyser {
    private String account ;
    private String url;
    private String name;
    private String aid;
    private ForestModel forest = null;
    private TreeModel tree = null;
    private PythonModel py = null;
    private ArrayList<Attr> attrs;
    private ArrayList<ArrayList<String>> trainSet = null;
    private ArrayList<ArrayList<String>> testSet = null;
    private ArrayList<String> data;
    private double ratio = 0.8;//训练集占总数据的比例


    public Analyser(String account,String name){
        this.account = account;
        this.name = name;
    }

    public Analyser(String account){
        this(account,"分析方案");
    }

    /**
     * 训练C4.5决策树、逻辑回归、随机森林、支撑向量机，
     * 并将模型存入数据库。
     * @param url
     */
    @Override
    public void trainModel(String url) {
        //TODO:训练速度上的优化
        System.out.println(System.getProperty("user.dir"));
        this.url = url;

        buildPreparement(importCsv(new File(url)));
        //获取模型aid
        String aid = saveInfo();
        System.out.println("saveinfor");
        //训练随机森林并保存
        forest = new ForestModel(aid);
        forest.trainForest(trainSet,testSet,attrs);
        System.out.println("forestbuild");
        forest.save();
        System.out.println("forestsave");
        //训练决策树并保存
        tree = new TreeModel(aid);
        tree.trainTree(trainSet,testSet,attrs);
        System.out.println("treebuild");
        tree.save();
        System.out.println("treesave");

        saveAttr(aid);
        System.out.println("attrsave");

        //训练逻辑回归模型并保存
        py = new PythonModel(aid);
        py.trainModel(url);
        System.out.println("pybuild");
    }

    /**
     * 获取训练模型准确率
     * @return
     *
     */
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

    /**
     * 预测离职结果
     * @param data
     * @return
     * [预测结果，准确率]
     * 判断结果取值范围{"离职","不离职"}
     */
    @Override
    public ArrayList<String> getProbability(ArrayList<String> data) {
        this.data = data;
        String department = data.get(data.size()-2);
        getAttrAndInfo();
        if(tree==null){
            tree = new TreeModel(aid);
            tree.rebuildTree();
        }
        if(forest==null){
            forest = new ForestModel(aid);
            forest.rebuildModel();
        }
        if(py == null){
            py = new PythonModel(aid);
        }
        ArrayList<ArrayList<Double>> results = py.getProbability(data,department);
        results.add(tree.getProbability(data,attrs));
        results.add(forest.getProbability(data,attrs));
        return getAverageResult(results);
    }

    /**
     * 批量预测
     * @param csvURL 预测数据文件url
     * @return
     * [
     *      [判断结果，准确率],
     *      [判断结果，准确率],
     *      ……
     * ]
     * 判断结果取值范围{"离职","不离职"}
     */
    @Override
    public ArrayList<ArrayList<String>> getProbabilityFromCSV(String csvURL){
        getAttrAndInfo();
        ArrayList<ArrayList<String>> datas = importCsv(new File(csvURL));
        datas.remove(0);
        if(tree==null){
            tree = new TreeModel(aid);
            tree.rebuildTree();
        }
        if(forest==null){
            forest = new ForestModel(aid);
            forest.rebuildModel();
        }
        if(py == null){
            py = new PythonModel(aid);
        }
        ArrayList<ArrayList<ArrayList<Double>>> results = py.getProbabilityFromCSV(csvURL);
        ArrayList<ArrayList<String>> answers = new ArrayList<>();
        for (int i = 0;i<results.size();i++){
            datas.get(i).remove(9);
            results.get(i).add(tree.getProbability(datas.get(i),attrs));
            results.get(i).add(forest.getProbability(datas.get(i),attrs));
            answers.add(getAverageResult(results.get(i)));
        }
        return answers;
    }

    @Override
    public ArrayList<String> getNumberFromCSV(String csvURL){
        getAttrAndInfo();
        ArrayList<ArrayList<String>> datas = importCsv(new File(csvURL));
        ArrayList<String> number = new ArrayList<>();
        for(int i = 1;i<datas.size();i++){
            number.add(datas.get(i).get(9));
        }
        return number;
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

    /**
     * 获取改进（挽留）员工措施
     * 该方法需要在调用doPrediction(),获得为‘1’的判断结果后调用，
     * 否则无法获取结果
     * @return [0]关键因素名称，[1,2,3...]如何改变可以挽回
     */
    @Override
    public List<String> improveMeasure() {
        return tree.improveMeasure(data,attrs);
    }


    @Override
    public Map<String, Double> getAttrRatio(String attrName) {

//        try{
//            File file = new File("./myfile.txt");
//            if(file.createNewFile())
//                System.out.println("文件创建成功！");
//            else
//                System.out.println("出错了，该文件已经存在。");
//        }
//        catch(IOException ioe) {
//            ioe.printStackTrace();
//        }

        Connection conn = DBUtil.getConnection();
        Map<String,Double> map = new HashMap<>();
        try{
            PreparedStatement state = conn.prepareStatement("select value,ratio,seperated from attrvalue natural join analysis natural join attribute where account = ? and name = ? and attrname = ?");
            state.setString(1,account);
            state.setString(2,name);
            state.setString(3,attrName);
            ResultSet set = state.executeQuery();
            boolean isSeperated;
            String value;
            double ratio;
            int temp;
            while(set.next()){
                isSeperated = set.getBoolean("seperated");
                value = set.getString(1);
                ratio = set.getDouble(2);
                if(attrName.equals("left")){
                    map.put(value,ratio);
                    continue;
                }
                temp = (int)(ratio*100);
                ratio = (double)temp/100;
                if(!isSeperated){
                    temp = (int)(Double.valueOf(value)*10);
                    value = String.valueOf((double) temp/10.0);
                }
                map.put(value,ratio);
                map.put("isSeperated",isSeperated?1.0:0.0);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.closeConn(conn);
            return map;
        }
    }


    private String saveInfo(){
        Connection conn = DBUtil.getConnection();
        String aid = null;
        try{
            conn.setAutoCommit(false);
            int count = 0;
            PreparedStatement state = conn.prepareStatement("select aid from analysis where account = ? and name = ?");
            state.setString(1,account);
            state.setString(2,name);
            ResultSet set = state.executeQuery();
            if (set.next()){
                aid = set.getString("aid");
                System.out.println(aid);
                state = conn.prepareStatement("delete from attribute where aid = ?");
                state.setString(1,aid);
                state.executeUpdate();
                System.out.println(aid);
                state = conn.prepareStatement("delete from attrvalue where aid = ?");
                state.setString(1,aid);
                state.executeUpdate();
                System.out.println(aid);
                state = conn.prepareStatement("delete from tree where aid = ?");
                state.setString(1,aid);
                state.executeUpdate();
                System.out.println(aid);
                state = conn.prepareStatement("delete from regression where aid = ?");
                state.setString(1,aid);
                state.executeUpdate();
                System.out.println(aid);
                state = conn.prepareStatement("delete from svm where aid = ?");
                state.setString(1,aid);
                state.executeUpdate();
                System.out.println(aid);
                state = conn.prepareStatement("delete from forest where aid = ?");
                state.setString(1,aid);
                state.executeUpdate();
                System.out.println(aid);
                conn.commit();
                return aid;
            }
            state = conn.prepareStatement("select count(*) from analysis where account = ?");
            state.setString(1,account);
            set = state.executeQuery();
            if(set.next()){
                count = set.getInt(1);
            }
            aid = String.valueOf(count);
            aid = account+aid;
            set.close();
            state.close();
            state = conn.prepareStatement("insert into analysis values(?,?,?,?)");
            state.setString(1,account);
            state.setString(2,name);
            state.setString(3,String.valueOf(aid));
            state.setDouble(4,0);
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

    private void saveAttr(String aid){
        Connection conn = DBUtil.getConnection();
        try{
            conn.setAutoCommit(false);
            PreparedStatement state;
            PreparedStatement state2;
            for (Attr attr:
                 attrs) {
                state = conn.prepareStatement("insert into attribute values (?,?,?,?,?,?,?,?)");
                state.setString(1,attr.getName());
                state.setString(2,aid);
                state.setDouble(3,attr.getD());
                state.setBoolean(4,attr.isSeperated());
                state.setDouble(5,attr.getMin());
                state.setDouble(6,attr.getLen());
                state.setInt(7,attr.getM());
                state.setInt(8,attr.getIndex());
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

    private void buildPreparement(ArrayList<ArrayList<String>> datas){
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
            if(i<3){
                attrs.add(new Attr(attr,false));
                attrs.get(i).divide(datas,i);
            }else{
                attrs.add(new Attr(attr));
            }
            attrs.get(i).setIndex(i);
            i++;
        }
    }

    private void getAttrAndInfo(){
        Connection conn = DBUtil.getConnection();
        if (attrs==null){attrs = new ArrayList<Attr>();}
        try{
            PreparedStatement state = conn.prepareStatement("select aid from analysis where account = ? and name = ?");
            state.setString(1,account);
            state.setString(2,name);
            ResultSet set = state.executeQuery();
            if(set.next()){
                aid = set.getString("aid");
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
                attr.setIndex(set.getInt("in_dex"));
                attr.setD(D);
                attrs.add(attr);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.closeConn(conn);
        }
    }

    private ArrayList<String> getAverageResult(ArrayList<ArrayList<Double>>  results){
        double sum = 0.0;
        double OpAccuracy = 0,IpAccuracy = 0;
        int OpInt = 0, IpInt = 0;
        for (ArrayList<Double> result:
                results) {
            if(result.get(0)>0){
                sum += result.get(1);
                OpAccuracy += result.get(1);
                OpInt++;
            }else if(result.get(0)==0.0||result.get(0)>-1){
                sum -= result.get(1);
                IpAccuracy += result.get(1);
                IpInt++;
            }else{

            }
        }
        ArrayList<String> result = new ArrayList<>();
        if(sum>0){
            result.add("离职");
            result.add(String.valueOf(OpAccuracy/OpInt));
        }else {
            result.add("不离职");
            result.add(String.valueOf(IpAccuracy/IpInt));
        }
        return result;
    }

    public static void main(String[] args){
        Analyser analyser = new Analyser("123");
        analyser.getProbabilityFromCSV("import_test.csv");
    }
}
