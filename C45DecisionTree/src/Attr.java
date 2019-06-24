public class Attr {
    private String name;
    private boolean seperated;

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
}
