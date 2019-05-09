package com.xg.hlh.automation_web.entity;

public class ClassDomain {
    private String key;
    private String title;
    private String direction;


    public ClassDomain(String key,String title,String direction) {
        this.key = key;
        this.title = title;
        this.direction = direction;
    }
    public ClassDomain(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
