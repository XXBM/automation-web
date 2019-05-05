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
    public AwardsRank(Long id) {
        this.id = id;
    }
}

