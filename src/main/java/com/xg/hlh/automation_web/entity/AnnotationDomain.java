package com.xg.hlh.automation_web.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class AnnotationDomain implements Serializable {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
    private Integer id;
    private String simpleAnnotation;
    private String annotation;
    private Integer type;

    public AnnotationDomain() {
    }

    public AnnotationDomain(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSimpleAnnotation() {
        return simpleAnnotation;
    }

    public void setSimpleAnnotation(String simpleAnnotation) {
        this.simpleAnnotation = simpleAnnotation;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
