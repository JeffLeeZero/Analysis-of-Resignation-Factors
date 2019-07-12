package com.zpj.pojo;

/**
 * 文章类
 * @author 毕修平
 */
public class Answer {
    private String title;
    private String author;
    private Object content;

    public String getTitle() {
        return title;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public Object getContent(){
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
