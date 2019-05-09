package com.xg.hlh.automation_web.controller;

import com.xg.hlh.automation_web.entity.ClassDomain;
import com.xg.hlh.automation_web.exception.Result;
import com.xg.hlh.automation_web.exception.ResultUtil;
import com.xg.hlh.automation_web.utils.FileUtil;
import com.xg.hlh.automation_web.utils.GenerateFileService;
import com.xg.hlh.automation_web.utils.StaticVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GenerateFileController {
    @Autowired
    GenerateFileService generateFileService;

    /*find all domain*/
    @RequestMapping(value = "/getDomainNamesWithTitle", method = RequestMethod.GET)
    public List<ClassDomain> findAllDomainNames() {
        List<String> stringList =  FileUtil.findAllFileName(StaticVariable.operateDomainPath);
        List<ClassDomain> classDomains = new ArrayList<>();
        ClassDomain classDomain = null;
        for(int i=0;i<stringList.size();i++){
            classDomain = new ClassDomain(""+i,stringList.get(i),"left");
            classDomains.add(classDomain);
        }
        return classDomains;
    }

    /*find all dao*/
    @RequestMapping(value = "/getDaoNamesWithTitle", method = RequestMethod.GET)
    public List<ClassDomain> findAllDaoNames() {
        List<String> stringList =  FileUtil.findAllFileName(StaticVariable.operateDaoPath);
        List<ClassDomain> classDomains = new ArrayList<>();
        ClassDomain classDomain = null;
        for(int i=0;i<stringList.size();i++){
            classDomain = new ClassDomain(""+i,stringList.get(i),"left");
            classDomains.add(classDomain);
        }
        return classDomains;
    }

    /*find all service*/
    @RequestMapping(value = "/getServiceNamesWithTitle", method = RequestMethod.GET)
    public List<ClassDomain> findAllServiceNames() {
        List<String> stringList =  FileUtil.findAllFileName(StaticVariable.operateServicePath);
        List<ClassDomain> classDomains = new ArrayList<>();
        ClassDomain classDomain = null;
        for(int i=0;i<stringList.size();i++){
            classDomain = new ClassDomain(""+i,stringList.get(i),"left");
            classDomains.add(classDomain);
        }
        return classDomains;
    }



    /*生成dao文件*/
    @RequestMapping(value = "/generateDaoFile", method = RequestMethod.GET)
    public Result generateDaoFile(@RequestParam(value = "daoNames") String fileNames[]) throws Exception {
        String fileName;
       for(int i=0;i<fileNames.length;i++){
           fileName = fileNames[i];
           generateFileService.generateDaoFile(fileName.substring(0,fileName.lastIndexOf(".")));
       }
       return ResultUtil.success();
    }

    /*生成service文件*/
    @RequestMapping(value = "/generateServiceFile", method = RequestMethod.GET)
    public Result generateServiceFile(@RequestParam(value = "serviceNames") String fileNames[]) throws Exception {
        String fileName;
        for(int i=0;i<fileNames.length;i++){
            fileName = fileNames[i];
            generateFileService.generateServiceFile(fileName.substring(0,fileName.lastIndexOf("R")));
        }
        return ResultUtil.success();
    }

    /*生成controller文件*/
    @RequestMapping(value = "/generateControllerFile", method = RequestMethod.GET)
    public Result generateControllerFile(@RequestParam(value = "controllerNames") String fileNames[]) throws Exception {
        String fileName;
        for(int i=0;i<fileNames.length;i++){
            fileName = fileNames[i];
            generateFileService.generateControllerFile(fileName.substring(0,fileName.lastIndexOf("S")));
        }
        return ResultUtil.success();
    }

}
