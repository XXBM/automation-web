package com.xg.hlh.automation_web.utils;

public class StaticVariable {
    public static String rootPath = System.getProperty("user.dir");

    public static String importDomainPackage = "com.xg.hlh.bysj.domain";
    public static String importDaoPackage = "com.xg.hlh.bysj.dao";
    public static String importServicePackage = "com.xg.hlh.bysj.service";
    public static String importControllerPackage = "com.xg.hlh.bysj.controller";
    public static String importBasicServicePackage = "com.xg.hlh.bysj.BasicService";
    public static String importJpaRepositoryPackage = "com.xg.hlh.bysj.JpaRepository";

    public static String operateDomainPath = rootPath+"/src/main/resources/com/xg/hlh/bysj/domain/";
    public static String operateDaoPath = rootPath+"/src/main/resources/com/xg/hlh/bysj/dao/";
    public static String operateServicePath = rootPath+"/src/main/resources/com/xg/hlh/bysj/service/";
    public static String operateControllerPath = rootPath+"/src/main/resources/com/xg/hlh/bysj/controller/";
    public static String operateBasicServicePath = rootPath+"/src/main/resources/com/xg/hlh/bysj/BasicService/";
    public static String operateJpaRepositoryPath = rootPath+"/src/main/resources/com/xg/hlh/bysj/JpaRepository/";

}
