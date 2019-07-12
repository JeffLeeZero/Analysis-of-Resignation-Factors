package analysis;

import analysis.DBUtil.DBUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import oracle.sql.CLOB;
import tree.Attr;
import tree.DecisionTree;
import tree.TreeNode;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TreeModel {
    private String aid;
    private DecisionTree tree;

    public TreeModel(String aid){
        this.aid = aid;
    }

    public void trainTree(ArrayList<ArrayList<String>> trainSet, ArrayList<ArrayList<String>> testSet, ArrayList<Attr> attrs){
        tree = new DecisionTree();

        ArrayList<ArrayList<String>> list = new ArrayList<>();
        list.addAll(trainSet);
        list.addAll(testSet);
        //TODO:使用决策树的特征重要性还是随机森林的
        tree.buildArrayList(list,attrs);
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

    public void save(){
        Type type = new TypeToken<TreeNode>(){}.getType();
        Gson gson = new Gson();
        String content = gson.toJson(tree.getTree(),type);
        Connection conn = DBUtil.getConnection();
        try{
            String sql = "insert into tree values(?,empty_clob(),?)";
            //锁住该列，防止并发写入时候该字段同时被多次写入造成错误
            String sqlClob = "select tree from tree where aid=? for update";
            PreparedStatement pst =null;
            ResultSet rs = null;
            Writer writer = null;
            conn.setAutoCommit(false);//设置不自动提交，开启事务
            pst = conn.prepareStatement(sql);
            pst.setString(1,aid);
            pst.setDouble(2,tree.getAccuracy());
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

    /**
     * 从数据库中恢复树模型
     */
    public void rebuildTree(){
        if(tree!=null){
            return;
        }
        Connection conn = DBUtil.getConnection();
        try{
            PreparedStatement state = conn.prepareStatement("select tree,accuracy from tree where aid = ?");
            state.setString(1,aid);
            ResultSet set = state.executeQuery();
            if(set.next()){
                Clob clob = set.getClob("tree");//java.sql.Clob
                String detailinfo = "";
                if(clob != null){
                    detailinfo = clob.getSubString((long)1,(int)clob.length());
                    Gson gson = new Gson();
                    Type type = new TypeToken<TreeNode>(){}.getType();
                    tree = new DecisionTree();
                    tree.setTree(gson.fromJson(detailinfo,type));
                }
                tree.setAccuracy(set.getDouble("accuracy"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.closeConn(conn);
        }
    }

    public List<String> improveMeasure(ArrayList<String> data,ArrayList<Attr> attrlist){
        return tree.getFinalAttr(data,attrlist);
    }

    public ArrayList<Double> getProbability(ArrayList<String> data,ArrayList<Attr> attrList){
        ArrayList<Double> result = new ArrayList<>();
        try{
            result.add(Double.valueOf(tree.doPrediction(data,attrList)));
        }catch (NumberFormatException e){
            result.add(-1.0);
        }
        result.add(tree.getAccuracy());
        return result;
    }
}
