package com.xg.hlh.automation_web.dao;
import com.xg.hlh.automation_web.entity.AnnotationDomain;
import com.xg.hlh.automation_web.test.JpaRepository.MyRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnotationDomainRepository extends MyRepository<AnnotationDomain,Integer> {
    AnnotationDomain findBySimpleAnnotation(String simpleAnnotation);
    List<AnnotationDomain> findByType(Integer type);
}