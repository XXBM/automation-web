package com.xg.hlh.automation_web.test.BasicService;

import com.xg.hlh.automation_web.test.JpaRepository.MyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;


@Service
public abstract class BasicService<T extends Serializable,ID extends Serializable> {

    @Autowired
    MyRepository<T,ID> basicDao;

    //得到一条信息
    public Optional<T> findOne(Example<T> t){
        return basicDao.findOne(t);
    }
    //根据查询条件得到一条信息
    public Optional<T> findOneByX(Specification<T> spec){
        return basicDao.findOne(spec);
    }

    //得到某实体类所有信息
    public List<T> findAllT() {
        return basicDao.findAll();
    }

    //得到可以分页的所有实体类信息
    public Page<T> findAllPagebleT(Pageable pt) {
        return basicDao.findAll(pt);
    }

    //多个动态条件查找，查询到满足条件的多个实体类信息
    public List<T> findTsByXX(Specification<T> specification) {
        return basicDao.findAll(specification);
    }

    //多个动态条件查找，查询到满足条件的多个实体类信息---分页显示
    public Page<T> findPageTsByXX(Specification<T> specification, Pageable pageable) {
        return basicDao.findAll(specification, pageable);
    }

    //单个增加
    public void add(T t) {
        this.basicDao.save(t);
    }

    //批量增加
    public void addList(Iterable<T> ts) {
        this.basicDao.saveAll(ts);
    }

    //单个更新
    public void update(T t) {
        this.basicDao.saveAndFlush(t);
    }

    //单个删除，传的是实体类
    public void delete(T t) {
        this.basicDao.delete(t);
    }

    //单个删除,传的ID
    public void deleteById(ID id){
        this.basicDao.deleteById(id);
    }

    //批量删除,后台是生成一条SQL语句[之前那个是一条条删除]，效率更高些
    public void deleteList(Iterable<T> ts) {
        this.basicDao.deleteInBatch(ts);
    }

}