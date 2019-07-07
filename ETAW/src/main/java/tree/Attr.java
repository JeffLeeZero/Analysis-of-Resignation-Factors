package tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Attr {
    private String name;
    private boolean seperated;
    private int M = 10;
    private double len;
    private double min;
    private ArrayList<String> division;
    private double D;
    private Map<String,Double> probability = new HashMap<>();
    private int index=-1;

    /**
     * 从数据库中还原属性
     * @param name
     * @param seperated
     * @param M
     * @param min
     * @param len
     */
    public Attr(String name,boolean seperated,int M,double min,double len){
        this.name = name;
        this.seperated = seperated;
        this.division = new ArrayList<>();
        this.M = M;
        this.min = min;
        this.len = len;
        divide();
    }

    public Attr(String name){
        this(name,true);
    }

    /**
     * @param name 属性名称
     * @param seperated 是否是离散的
     */
    public Attr(String name,boolean seperated){
        this.name = name;
        this.seperated = seperated;
        if(!isSeperated()){
            this.division = new ArrayList<>();
        }
    }

    public Attr(Attr old){
        this.name = old.getName();
        setSeperated(old.isSeperated());
        setD(0.0);
        setIndex(old.getIndex());
        setM(old.getM());
        setMin(old.getMin());
        setLen(old.getLen());
        setProbability(old.getProbability());
        if(!seperated){
            divide();
        }
    }

    public Map<String,Double> getProbability() {
        return probability;
    }

    public void setProbability(Map<String,Double> probability) {
        this.probability = probability;
    }

    public void addProbability(String value,Double pro){
        probability.put(value,pro);
    }

    public double getD() {
        return D;
    }

    public void setD(double d) {
        D = d;
    }

    public String getName() {
        return name;
    }

    public boolean isSeperated() {
        return seperated;
    }

    public void setSeperated(boolean seperated) {
        this.seperated = seperated;
    }

    public void setM(int m){
        M = m;
    }

    public int getM(){
        return M;
    }

    private void divide(){
        division = new ArrayList<>();
        for(int i = 0; i < M;i++){
            division.add(String.valueOf(min+i*len));
        }
    }

    public void divide(ArrayList<ArrayList<String>> datas,int attrIndex){
        double i, min = Double.POSITIVE_INFINITY, max = Double.NEGATIVE_INFINITY;
        for (ArrayList<String> tuple:
             datas) {
            i = Double.valueOf(tuple.get(attrIndex));
            if(i<min){
                min = i;
            }
            if(i>max){
                max = i;
            }
        }
        this.len = (max-min)/M;
        this.min = min;
        for (int m = 0;m < M;m++){
            division.add(String.valueOf(min+len*m));
        }
    }

    public String getValue(String v){
        double num = Double.valueOf(v);
        int n = (int)((num-min)/len);
        try{
            return division.get((n>=M)?M-1:((n<0)?0:n));
        }catch (Exception e){
            System.out.println(name+";value:"+v+";min:"+min+";len:"+len);
            e.printStackTrace();
            throw e;
        }
    }

    public ArrayList<String> getValues(){
        return division;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double getLen() {
        return len;
    }

    public void setLen(double len) {
        this.len = len;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }
}
