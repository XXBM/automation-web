package com.xg.hlh.automation_web.controller;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.google.gson.Gson;
import com.xg.hlh.automation_web.entity.VariableDomain;
import com.xg.hlh.automation_web.exception.Result;
import com.xg.hlh.automation_web.exception.ResultUtil;
import com.xg.hlh.automation_web.service.AnnotationDomainService;
import com.xg.hlh.automation_web.utils.FileUtil;
import com.xg.hlh.automation_web.utils.JavaParserService;
import com.xg.hlh.automation_web.utils.StaticVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
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
    @RequestMapping(value = "/addAnnotationsForClass", method = RequestMethod.POST)
    public Result addAnnotationsForClass(@RequestParam(value = "className") String className,
                                        @RequestParam(value = "classAnnotations") String classAnnotations[]) throws IOException {
        List<String> stringList = new ArrayList<>();
        for(int i=0;i<classAnnotations.length;i++){
            stringList.add(this.annotationDomainService.findBySimpleAnnotation(classAnnotations[i]).getAnnotation());
        }
        javaParserService.addAnnotationsForClass(StaticVariable.operateDomainPath+className,stringList);
        return ResultUtil.success();
    }


    /**
     * 为实体类添加注解（包括类注解和变量注解）
     * @param className 类名 例如："Student.java"
     * @param classAnnotations 类上要添加的注解们
     * @param variableAnnotations Map<变量名，变量要添加的注解们>
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/addAnnotations", method = RequestMethod.POST)
    public Result addAnnotations(@RequestParam(value = "className") String className,
                                 @RequestParam(value = "classAnnotations") List<String> classAnnotations,
                                 @RequestParam(value = "variableAnnotations") String variableAnnotations) throws IOException {
        /*将第三个参数从String转换为Map*/
        Gson gson = new Gson();
        Map<String, List<String>> variableAnnotationMap = new HashMap<String, List<String>>();
        variableAnnotationMap = gson.fromJson(variableAnnotations, variableAnnotationMap.getClass());
        List<String> classList = new ArrayList<>();
        for(int i=0;i<classAnnotations.size();i++){
            classList.add(this.annotationDomainService.findBySimpleAnnotation(classAnnotations.get(i)).getAnnotation());
        }

        Map<String,List<String>> variableMap = new HashMap();
        List<String> annotationStrs;
        for (Map.Entry<String,List<String>> entry : variableAnnotationMap.entrySet()) {
            List<String> variableList = new ArrayList<>();
            annotationStrs = entry.getValue();
            for(int i=0; i<annotationStrs.size(); i++){
                variableList.add(this.annotationDomainService.findBySimpleAnnotation(annotationStrs.get(i)).getAnnotation());
            }
            variableMap.put(entry.getKey(),variableList);
        }

        javaParserService.addAnnotations(StaticVariable.operateDomainPath+className,classList,variableMap);
        return ResultUtil.success();
    }


    @RequestMapping(value = "/addAnnotationsForVariable", method = RequestMethod.POST)
    public Result addAnnotationsForVariable(@RequestParam(value = "className") String className,
                                           @RequestParam(value = "variableName") String variableName,
                                           @RequestParam(value = "variableAnnotations") String variableAnnotations[]) throws IOException {
        List<String> stringList = new ArrayList<>();
        for(int i=0;i<variableAnnotations.length;i++){
            stringList.add(this.annotationDomainService.findBySimpleAnnotation(variableAnnotations[i]).getAnnotation());
        }
        javaParserService.addAnnotationsForVariable(StaticVariable.operateDomainPath+className,variableName,stringList);
        return ResultUtil.success();
    }

}
