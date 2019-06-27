package tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Gain {
    private ArrayList<ArrayList<String>> D = null;//训练元组
    private ArrayList<Attr> attrList = null;//候选属性集，侯选属性集中最后一位表示最终的决策结果
    public Gain(ArrayList<ArrayList<String>> datas,ArrayList<Attr> attrList){
        this.D = datas;
        this.attrList = attrList;
    }

    /**
     * 获取最佳候选属性列上的值域
     * @param datas 指定的数据集
     * @param attrIndex 指定的属性列的索引
     * @return 值域集合
     * @author 李沛昊
     */
    public ArrayList<String> getValues(ArrayList<ArrayList<String>> datas,int attrIndex){
        if(!attrList.get(attrIndex).isSeperated()){
            return attrList.get(attrIndex).getValues();
        }
        ArrayList<String> values = new ArrayList<>();
        String value;
        for (ArrayList<String> list:
             datas) {
            value = list.get(attrIndex);
            if(!values.contains(value)){
                values.add(value);
            }
        }
        return values;
    }


    /**
     * 获取指定数据集中指定属性列的值域和每个取值的计数
     * @param datas 指定的数据集
     * @param attrIndex 指定的属性列索引
     * @return 值域和其计数的map
     * @author 李沛昊
     */
    public Map<String,Integer> valueCounts(ArrayList<ArrayList<String>> datas,int attrIndex){
        if(!attrList.get(attrIndex).isSeperated()){
            return valueCounts(datas,attrIndex,false);
        }
        Map<String,Integer> valueCount = new HashMap<>();
        String value;
        for (ArrayList<String> tuple:
                datas) {
            value = tuple.get(attrIndex);
            if(valueCount.containsKey(value)){
                valueCount.put(value,valueCount.get(value)+1);
            }else{
                valueCount.put(value,1);
            }
        }
        return valueCount;
    }

    private Map<String,Integer> valueCounts(ArrayList<ArrayList<String>> datas,int attrIndex, boolean seperated){
        Map<String,Integer> valueCount = new HashMap<>();
        String value;
        Attr attr = attrList.get(attrIndex);
        for (ArrayList<String> tuple:
                datas) {
            value = tuple.get(attrIndex);
            value = attr.getValue(value);
            if(valueCount.containsKey(value)){
                valueCount.put(value,valueCount.get(value)+1);
            }else{
                valueCount.put(value,1);
            }
        }
        return valueCount;
    }



    /**
     * 求各个参考属性在取各自的值对应目标属性的信息熵
     * @param datas 一般是某个属性特定属性值的元组集
     * @return 信息熵值
     * @author 李沛昊
     */
    public double infoD(ArrayList<ArrayList<String>> datas){

        double info = 0.0;
        int total = datas.size();

        //各个参考属性在取各自的值对应目标属性的分割
        Map<String,Integer> classes = valueCounts(datas,attrList.size()-1);

        //classes.size()==data.size();
        Integer[] counts = new Integer[classes.size()];
        Integer count;
        int i = 0;
        for (Map.Entry<String, Integer> entry
                : classes.entrySet()) {
            count = entry.getValue();
            counts[i] = count;
            ++i;
        }

//        Iterator<Map.Entry<String,Integer>> iter = classes.entrySet().iterator();
//        for(int i = 0;iter.hasNext();i++){
//            Map.Entry<String,Integer> entry = iter.next();
//            count = entry.getValue();
//            counts[i] = count;
//        }

        double base;
        for (Integer n:
             counts) {
            base = (double)n / total;
            info += (-1)*base*Math.log(base);
        }
        return info;
    }

    /**
     * 获取指定属性列上属性为指定值的元组
     * @param attrIndex 属性列索引
     * @param value 指定属性值
     * @return 目标元组
     * @author 李沛昊
     */
    public ArrayList<ArrayList<String>> datasOfValue(int attrIndex,String value){
        if(!attrList.get(attrIndex).isSeperated()){
            return datasOfValue(attrIndex,value,false);
        }
        ArrayList<ArrayList<String>> Di = new ArrayList<>();
        for (ArrayList<String> t:
             D) {
            if(t.get(attrIndex).equals(value)){
                Di.add(t);
            }
        }
        return Di;
    }


    private ArrayList<ArrayList<String>> datasOfValue(int attrIndex, String value, boolean seperated){
        ArrayList<ArrayList<String>> Di = new ArrayList<>();
        Attr attr = attrList.get(attrIndex);
        for (ArrayList<String> t:
                D) {
            if(attr.getValue(t.get(attrIndex)).equals(value)){
                Di.add(t);
            }
        }
        return Di;
    }

    /**
     * 获取指定参考属性的信息熵
     * @param attrIndex 属性索引
     * @return 信息熵
     * @author 李沛昊
     */
    public double infoAttr(int attrIndex){
        double info = 0.0;
        ArrayList<String> values = getValues(D,attrIndex);
        for (int i = 0; i < values.size(); i++){
            ArrayList<ArrayList<String>> dv = datasOfValue(attrIndex,values.get(i));
            //整个属性的信息熵应该是各个取值的信息熵的加权平均值
            info += infoD(dv)*dv.size()/D.size();
        }
        return info;
    }


    //todo:
    public double splitInfo(int attrIndex){
        double split = 0.0;
        int size = D.size();
        Map<String,Integer> map = valueCounts(D,attrIndex);
        double ratio = 0.0;
        for (Map.Entry<String,Integer> entry:
             map.entrySet()) {
            ratio = (double)(entry.getValue())/size;
            split += (-1)*ratio*Math.log(ratio);
        }
        return split;
    }

    /**
     * 获取最佳的分割属性
     * @return 最佳的分割属性索引
     * @author 李沛昊
     */
    public int bestGainAttrIndex(){
        int index = 0;
        double gain = 0.0;
        double temp = 0.0;
        for(int i = 0; i < attrList.size()-1; i++){
            temp = (infoD(D) - infoAttr(i))/splitInfo(i);
            if(temp > gain){
                gain = temp;
                index = i;
            }
        }
        return index;
    }
}
