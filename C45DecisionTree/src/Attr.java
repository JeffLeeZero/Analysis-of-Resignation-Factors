import java.util.ArrayList;
import java.util.Vector;

public class Attr {
    private String name;
    private boolean seperated;
    private static int M = 10;
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

    public static void setM(int m){
        M = m;
    }

    public static int getM(){
        return M;
    }


}
