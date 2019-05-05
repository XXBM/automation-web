package com.xg.hlh.automation_web.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class GenerateFileService {
    @Autowired
    JavaParserService javaParserService;


    /**
     * TODO
     * 生成domain
     * @param filePath 文件目录
     * @param file 要上传的文件
     */
    public void generateDomain(String filePath, MultipartFile file) throws IOException {
        File dest = new File(filePath + file.getName());
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        file.transferTo(dest);
        javaParserService.importPackage(filePath+file.getName(),StaticVariable.importDomainPackage);
    }

    /**
     * 生成dao类文件
     * @param entityName 实体类名 例如：Student
     * @throws Exception
     */
    public void generateDaoFile(String entityName) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("MyRepositoryPackageName", StaticVariable.importJpaRepositoryPackage);
        params.put("EntityPackageName", StaticVariable.importDomainPackage);
        params.put("EntityName", entityName);
        FreeMarkerTplLan.generateFile(StaticVariable.operateDaoPath,entityName+"Repository.java","dao.ftl", params);
        javaParserService.importPackage(StaticVariable.operateDaoPath+entityName+"Repository.java",StaticVariable.importDaoPackage);
    }

    /**
     * 生成service类文件
     * @param entityName 实体类名 例如：Student
     * @throws Exception
     */
    public void generateServiceFile(String entityName) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("BasicServicePackageName", StaticVariable.importBasicServicePackage);
        params.put("EntityPackageName", StaticVariable.importDomainPackage);
        params.put("EntityName", entityName);
        FreeMarkerTplLan.generateFile(StaticVariable.operateServicePath,entityName+"Service.java","service.ftl", params);
        javaParserService.importPackage(StaticVariable.operateServicePath+entityName+"Service.java",StaticVariable.importServicePackage);
    }

    /**
     * 生成controller类
     * @param entityName 实体类名 例如：Student
     * @throws Exception
     */
    public void generateControllerFile(String entityName) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("EntityPackageName", StaticVariable.importDomainPackage);
        params.put("EntityServicePackageName", StaticVariable.importServicePackage);
        params.put("EntityName",entityName);
        params.put("EntityNameLower", StringUtils.uncapitalize(entityName));
        FreeMarkerTplLan.generateFile(StaticVariable.operateControllerPath,entityName+"Controller.java","controller.ftl", params);
        javaParserService.importPackage(StaticVariable.operateControllerPath+entityName+"Controller.java",StaticVariable.importControllerPackage);
    }

    /**
     * 对指定目录下的所有文件导包
     * @param dirPath 目录
     * @param packageName 包名
     * @throws IOException
     */
    public void importPackageFromDirPath(String dirPath,String packageName) throws IOException {
        File file = new File(dirPath);
        File[] tempList = file.listFiles();
        for(int i=0;i<tempList.length;i++){
            javaParserService.importPackage(dirPath+tempList[i].getName(),packageName);
        }

    }

    /**
     * 对基础文件导包
     * @throws IOException
     */
    public void importBasicPackage() throws IOException {
        this.importPackageFromDirPath(StaticVariable.operateBasicServicePath,StaticVariable.importBasicServicePackage);
        this.importPackageFromDirPath(StaticVariable.operateJpaRepositoryPath,StaticVariable.importJpaRepositoryPackage);
    }
}
