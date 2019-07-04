package analysis;

import analysis.DBUtil.DBUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import oracle.sql.CLOB;
import tree.Attr;
import tree.RandomForest;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;

public class ForestModel {
    private RandomForest forest;
    private String aid;
    private double accuracy;

    public ForestModel(String aid){
        this.aid = aid;
    }

    public void trainForest(ArrayList<ArrayList<String>> trainSet, ArrayList<ArrayList<String>> testSet, ArrayList<Attr> attrs){
        forest = new RandomForest();
        forest.buildForest(trainSet,attrs);
        int trueNum = 0;
        for (ArrayList<String> data:
                trainSet) {
            String answer = forest.doPrediction(data,attrs);
            if(answer.equals(data.get(9))){
                trueNum++;
            }
        }
        accuracy = (double)trueNum/trainSet.size();
    }

    public void save(){
        Type type = new TypeToken<RandomForest>(){}.getType();
        Gson gson = new Gson();
        String content = gson.toJson(forest,type);
        Connection conn = DBUtil.getConnection();
        try{
            String sql = "insert into forest values(?,empty_clob(),?)";
            //锁住该列，防止并发写入时候该字段同时被多次写入造成错误
            String sqlClob = "select forest from forest where aid=? for update";
            PreparedStatement pst =null;
            ResultSet rs = null;
            Writer writer = null;
            conn.setAutoCommit(false);//设置不自动提交，开启事务
            pst = conn.prepareStatement(sql);
            pst.setString(1,aid);
            pst.setDouble(2,accuracy);
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

    public void rebuildModel(){
        Connection conn = DBUtil.getConnection();
        String aid="";
        try{
            PreparedStatement state = conn.prepareStatement("select forest from forest where aid = ?");
            state.setString(1,aid);
            ResultSet set = state.executeQuery();
            if(set.next()){
                Clob clob = set.getClob("tree");//java.sql.Clob
                String detailinfo = "";
                if(clob != null){
                    detailinfo = clob.getSubString((long)1,(int)clob.length());
                    Gson gson = new Gson();
                    Type type = new TypeToken<RandomForest>(){}.getType();
                    forest = gson.fromJson(detailinfo,type);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.closeConn(conn);
        }
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public ArrayList<Double> getProbability(ArrayList<String> data,ArrayList<Attr> attrList){
        ArrayList<Double> result = new ArrayList<>();
        result.add(Double.valueOf(forest.doPrediction(data,attrList)));
        result.add(accuracy);
        return result;
    }
}
