package com.xg.hlh.automation_web.JpaRepository;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * Created by dell on 2016/10/17.
 */

/**
 * 定义接口的实现类
 * SimpleJpaRepository继承了JpaRepository，保证所有的Repository都具有基本的CRUD等一些方法
 * 和继承了JpaSpecificationExecutor，保证实现一些复杂的查询或者多条件的分页
 *
 */
@SuppressWarnings("SpringJavaConstructorAutowiringInspection")
public class MyCustomerRepository<T,ID extends Serializable>
        extends SimpleJpaRepository<T,ID>
        implements MyRepository<T,ID> {

    private final EntityManager em;

    public MyCustomerRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.em = entityManager;
    }

    public MyCustomerRepository(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.em = em;
    }

}
