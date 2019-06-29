package com.zpj.bean;

import java.util.ArrayList;
import java.util.List;

public class AttrBean {
   private String name;
   private double D;
   private List<AttrValue> list = null;;

    public AttrBean(String name) {
        this.name = name;
    }

    public AttrBean(String name,double D) {
        this.name = name;
        this.D = D;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getD() {
        return D;
    }

    public void setD(double d) {
        D = d;
    }

    public void addList(AttrValue value){
        if(list == null){
            list =  new ArrayList<>();
        }
        list.add(value);
    }

    public void addList(String values, double ratio)
    {
        AttrValue value = new AttrValue(values,ratio);
        addList(value);
    }


    class AttrValue{
       private String value;
       private double ratio;

       public AttrValue(String value, double ratio) {
           this.value = value;
           this.ratio = ratio;
       }
   }
}
