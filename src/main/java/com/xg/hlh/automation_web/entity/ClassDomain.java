package com.xg.hlh.automation_web.entity;

public class ClassDomain {
    private String key;
    private String title;


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
}
