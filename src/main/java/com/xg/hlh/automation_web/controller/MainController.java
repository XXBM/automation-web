package com.xg.hlh.automation_web.controller;

import com.xg.hlh.automation_web.exception.Result;
import com.xg.hlh.automation_web.exception.ResultUtil;
import com.xg.hlh.automation_web.utils.FileUtil;
import com.xg.hlh.automation_web.utils.GenerateFileService;
import com.xg.hlh.automation_web.utils.JavaParserService;
import com.xg.hlh.automation_web.utils.StaticVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
public class MainController {

    @Autowired
    JavaParserService javaParserService;
    @Autowired
    GenerateFileService generateFileService;

    /**
     * 上传现有实体类（单文件上传）
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Result javaClassUpload(@RequestParam("file") MultipartFile file) throws IOException {
        generateFileService.generateDomain(StaticVariable.operateDomainPath,file);
        return ResultUtil.success();
    }

    /**
     * 找到所有实体类文件名
     * @return
     */
    @RequestMapping(value = "/findAllDomainNames", method = RequestMethod.GET)
    public List<String> findAllDomainFileName(){
        return FileUtil.findAllFileName(StaticVariable.operateDomainPath);
    }

    /**
     * 根据实体类名找到该类的<变量名，变量类型>Map
     * @param entityName
     * @return
     * @throws FileNotFoundException
     */
    @RequestMapping(value = "/findVariables", method = RequestMethod.GET)
    public HashMap<String, String> findVariables(@RequestParam("entityName") String entityName) throws FileNotFoundException {
        return javaParserService.getVariableAndTypeMap(StaticVariable.operateDomainPath+entityName+".java");
    }



}
