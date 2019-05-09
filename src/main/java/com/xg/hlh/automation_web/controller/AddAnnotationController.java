package com.xg.hlh.automation_web.controller;

import com.xg.hlh.automation_web.entity.VariableDomain;
import com.xg.hlh.automation_web.exception.Result;
import com.xg.hlh.automation_web.exception.ResultUtil;
import com.xg.hlh.automation_web.service.AnnotationDomainService;
import com.xg.hlh.automation_web.utils.FileUtil;
import com.xg.hlh.automation_web.utils.JavaParserService;
import com.xg.hlh.automation_web.utils.StaticVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AddAnnotationController {
    @Autowired
    JavaParserService javaParserService;
    @Autowired
    AnnotationDomainService annotationDomainService;


    /*find all 所有实体类名*/
    @RequestMapping(value = "/getDomainNames", method = RequestMethod.GET)
    public List<String> findAllDomainNames() {
        List<String> stringList =  FileUtil.findAllFileName(StaticVariable.operateDomainPath);
        return stringList;
    }

    /*find by pageble 某个类的所有字段*/
    @RequestMapping(value = "/getVariablesByDomain", method = RequestMethod.GET)
    public Map<String, Object> displayAllVariables(@RequestParam(value = "page") Integer page,
                                                   @RequestParam(value = "rows") Integer size,
                                                   @RequestParam(value = "fileName") String fileName)throws Exception {
        //要添加注释的文件路径
        String filePath = StaticVariable.operateDomainPath+fileName;
        //自定义的接收属性名称和类型的Map集合
        HashMap<String, String> propertyMaps = javaParserService.getVariableAndTypeMap(filePath);
        List<VariableDomain> variableDomains = new ArrayList<>();
        VariableDomain variableDomain = null;
        for (Map.Entry<String, String> entry : propertyMaps.entrySet()) {
            variableDomain = new VariableDomain(entry.getKey(),entry.getValue());
            variableDomains.add(variableDomain);
        }

        Map<String, Object> map = new HashMap<String, Object>();
        //查到的总用户数
        map.put("total", variableDomains.size());

        //总页数
        int pageTimes;
        if (variableDomains.size() % size == 0) {
            pageTimes = variableDomains.size() / size;
        } else {
            pageTimes = variableDomains.size() / size + 1;
        }
        map.put("pageTimes", pageTimes);

        List<VariableDomain> newApplicationStructureTypes = new ArrayList<>();
        //每页开始的第几条记录
        if(pageTimes==page) {
            newApplicationStructureTypes.addAll(variableDomains.subList((page-1)*size,variableDomains.size()));
        }else {
            newApplicationStructureTypes.addAll(variableDomains.subList((page-1)*size,(page-1)*size+size));
        }
        map.put("variableDomains", newApplicationStructureTypes);
        return map;
    }

    /**
     * 为某个类添加类注解
     * @param className 例如Student.java
     * @param classAnnotations 例如["注解a","注解b"]
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/addAnnotationForClass", method = RequestMethod.POST)
    public Result addAnnotationForClass(@RequestParam(value = "className") String className,
                                        @RequestParam(value = "classAnnotations") String classAnnotations[]) throws IOException {
        List<String> stringList = new ArrayList<>();
        for(int i=0;i<classAnnotations.length;i++){
            stringList.add(this.annotationDomainService.findBySimpleAnnotation(classAnnotations[i]).getAnnotation());
        }
        javaParserService.addAnnotationsForClass(StaticVariable.operateDomainPath+className,stringList);
        return ResultUtil.success();
    }


    @RequestMapping(value = "/addAnnotationForVariable", method = RequestMethod.POST)
    public Result addAnnotationForVariable(@RequestParam(value = "className") String className,
                                           @RequestParam(value = "variableName") String variableName,
                                        @RequestParam(value = "variableAnnotations") String variableAnnotations[]) throws IOException {
        List<String> stringList = new ArrayList<>();

        return ResultUtil.success();
    }

}
