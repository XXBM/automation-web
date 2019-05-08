package com.xg.hlh.automation_web.controller;

import com.xg.hlh.automation_web.entity.ClassDomain;
import com.xg.hlh.automation_web.utils.FileUtil;
import com.xg.hlh.automation_web.utils.StaticVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GenerateFileController {

    /*find all*/
    @RequestMapping(value = "/getDomainNamesWithTitle", method = RequestMethod.GET)
    public List<ClassDomain> findAllDomainNames() {
        List<String> stringList =  FileUtil.findAllFileName(StaticVariable.operateDomainPath);
        List<ClassDomain> classDomains = new ArrayList<>();
        ClassDomain classDomain = null;
        for(int i=0;i<stringList.size();i++){
            classDomain = new ClassDomain(stringList.get(i));
            classDomains.add(classDomain);
        }
        return classDomains;
    }

}
