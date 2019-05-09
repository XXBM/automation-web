package com.xg.hlh.automation_web.service;

import com.xg.hlh.automation_web.dao.AnnotationDomainRepository;
import com.xg.hlh.automation_web.entity.AnnotationDomain;
import com.xg.hlh.automation_web.test.BasicService.BasicService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AnnotationDomainService extends BasicService<AnnotationDomain,Integer> {
    @Autowired
    AnnotationDomainRepository annotationDomainRepository;
    public AnnotationDomain findBySimpleAnnotation(String simpleAnnotation){
        return this.annotationDomainRepository.findBySimpleAnnotation(simpleAnnotation);
    }

    public List<AnnotationDomain> findByType(Integer type) {
        return this.annotationDomainRepository.findByType(type);
    }
}