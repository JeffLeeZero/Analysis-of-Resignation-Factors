package com.zpj.bean;

import tree.Attr;

import java.util.*;

public class AttrBean {
   private String name;
   private double D;
   private List<AttrValue> list = null;;
   private boolean isSeperated;
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


    public void sortList(){
        list.sort(new Comparator<AttrValue>() {
            @Override
            public int compare(AttrValue o1, AttrValue o2) {
                try{
                    if(Double.valueOf(o1.value)>Double.valueOf(o2.value)){
                        return 1;
                    }else if(Double.valueOf(o1.value)<Double.valueOf(o2.value)){
                        return -1;
                    }else{
                        return 0;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    return -1;
                }
            }
        });
    }

    public boolean isSeperated() {
        return isSeperated;
    }

    public void setSeperated(boolean seperated) {
        isSeperated = seperated;
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
