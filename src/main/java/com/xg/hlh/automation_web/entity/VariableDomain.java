package com.xg.hlh.automation_web.entity;

public class VariableDomain {
    private String name;
    private String type;

    public VariableDomain(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
