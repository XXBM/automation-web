package com.xg.hlh.automation_web.dao;
import com.xg.hlh.automation_web.entity.AnnotationDomain;
import com.xg.hlh.automation_web.test.JpaRepository.MyRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnotationDomainRepository extends MyRepository<AnnotationDomain,Integer> {

}