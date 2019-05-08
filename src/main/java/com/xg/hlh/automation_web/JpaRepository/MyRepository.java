package com.xg.hlh.automation_web.JpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;


/**
 *  先自定义一个MyRepository接口
 *  继承了PagingAndSortingRepository，保证所有的Repository都具有基本的CRUD等一些方法
 *  加上@NoRepositoryBean注解，SpringDataJpa在启动时就不会去实例化这个接口
 *  在里面定义全局共享的方法
 */
@NoRepositoryBean
@Transactional(readOnly = true)
public interface MyRepository<T,ID extends Serializable>
        extends JpaRepository<T,ID>, JpaSpecificationExecutor<T> {
}
