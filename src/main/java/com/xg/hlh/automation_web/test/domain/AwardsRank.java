package com.xg.hlh.automation_web.test.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dell on 2017-05-10 .
 * 奖项级别
 */
@Entity
public class AwardsRank implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String description;
    private String name;
    public AwardsRank() {
    }

    public AwardsRank(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

