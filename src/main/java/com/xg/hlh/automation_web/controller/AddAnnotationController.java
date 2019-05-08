package com.xg.hlh.automation_web.controller;

import com.xg.hlh.automation_web.entity.VariableDomain;
import com.xg.hlh.automation_web.utils.FileUtil;
import com.xg.hlh.automation_web.utils.JavaParserService;
import com.xg.hlh.automation_web.utils.StaticVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AddAnnotationController {
    @Autowired
    JavaParserService javaParserService;

    /*find all*/
    @RequestMapping(value = "/getDomainNames", method = RequestMethod.GET)
    public List<String> findAllDomainNames() {
        List<String> stringList =  FileUtil.findAllFileName(StaticVariable.operateDomainPath);
        return stringList;
    }

    /*find by pageble*/
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

}
