import java.util.ArrayList;
import java.util.Vector;

public class Attr {
    private String name;
    private boolean seperated;
    private int M = 10;
    private ArrayList<String> division;

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
        this.division = new ArrayList<>();
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
        double n = (max-min)/M;
        for(int m = 0; m < M; m++){
            division.add(String.valueOf(min+n*m));
        }
    }

    public String getValue(String v){
        double num = Double.valueOf(v);
        String result=String.valueOf(division.get(0));
        for (String value:
             division) {
            if(num>Double.valueOf(value)){
                result = value;
            }else{
                break;
            }
        }
        return result;
    }

    public ArrayList<String> getValues(){
        return division;
    }
}
