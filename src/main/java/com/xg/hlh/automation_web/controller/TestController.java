package com.xg.hlh.automation_web.controller;

import com.xg.hlh.automation_web.utils.GenerateFileService;
import com.xg.hlh.automation_web.utils.JavaParserService;
import com.xg.hlh.automation_web.utils.FreeMarkerTplLan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    JavaParserService javaParserService;
    @Autowired
    GenerateFileService generateFileService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public List<String> test0(){
        List<String> stringList = new ArrayList<>();
        stringList.add("黄丽华");
        stringList.add("黄昌伶");
        stringList.add("黄宝");
        return stringList;
    }

    /**
     * OK
     * 测试返回一个类的变量集合<变量名，变量类型>
     * @return
     * @throws FileNotFoundException
     */
    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    public HashMap<String, String> test1() throws FileNotFoundException {
        //要添加注释的文件路径
        String filePath = "src/main/java/com/xg/hlh/automation_web/entity/Student.java";
        System.out.println(javaParserService);
        //自定义的接收属性名称和类型的Map集合
        HashMap<String, String> propertyMaps = javaParserService.getVariableAndTypeMap(filePath);
        //将获取的类型打印出来
        for (Map.Entry<String, String> entry : propertyMaps.entrySet()) {
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
        }
        return propertyMaps;
    }

    /**
     * OK
     * 测试为一个类添加注解
     * @throws IOException
     */
    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    public void test2() throws IOException {
        //要添加注释的文件路径
        String filePath = "src/main/java/com/xg/hlh/automation_web/entity/Student.java";
        Map<String, List<String>> map = new HashMap<>();
        List<String> classList = new ArrayList<>();
        classList.add("javax.persistence.Entity");
        List<String> list = new ArrayList<>();
        //list.add("javax.persistence.OneToMany(mappedBy = \"employee\", fetch = javax.persistence.FetchType.LAZY, cascade = javax.persistence.CascadeType.ALL)");
        list.add("javax.persistence.OneToMany()");
        map.put("id",list);
        if(!map.isEmpty()){
            javaParserService.addAnnotations(filePath,classList,map);
        }
    }

    /**
     * OK
     * 测试导包
     * @throws IOException
     */
    @RequestMapping(value = "/test3", method = RequestMethod.GET)
    public void test3() throws IOException {
        //要添加注释的文件路径
        String filePath = "src/main/resources/com.xg.hlh.com/BasicService/BasicService.java";
        javaParserService.importPackage(filePath,"com.xg.hlh.automation_web.entity");
    }

    /**
     * OK
     * 测试创建dao文件
     * @throws IOException
     */
    @RequestMapping(value = "/test4", method = RequestMethod.GET)
    public void test4() throws IOException {
        //要添加注释的文件路径
        String filePath = "src/main/java/com/xg/hlh/automation_web/test/dao/";
        String className = "Student";
        String packageName = "src.main.resources.com.xg.hlh.com.dao";
        String extendPackage = "com.absmis.JpaRepository.MyRepository";
        String classPackage = "com.absmis.domain.authority.Role";
        javaParserService.createDao(filePath,className,packageName,extendPackage,classPackage);
    }
    /**
     * OK
     * 测试创建service文件
     * @throws IOException
     */
    @RequestMapping(value = "/test5", method = RequestMethod.GET)
    public void test5() throws IOException {
        String filePath = "src/main/java/com/xg/hlh/automation_web/test/service/";
        String className = "Student";
        String packageName = "src.main.resources.com.xg.hlh.com.service";
        String extendPackage = "com.absmis.JpaRepository.MyRepository";
        String classPackage = "com.absmis.domain.authority.Role";
        javaParserService.createService(filePath,className,packageName,extendPackage,classPackage);
    }

    /**
     * OK
     * 测试生成controller
     * @throws Exception
     */
    @RequestMapping(value = "/test6", method = RequestMethod.GET)
    public void test6() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("EntityPackageName", "com.xg.hlh.automation_web.test.domain");
        params.put("EntityServicePackageName", "com.xg.hlh.automation_web.test.service");
        params.put("EntityName", "AwardsRank");
        params.put("EntityNameLower","awardsRank");
        //baseController生成
        FreeMarkerTplLan.generateFile("src/main/java/com/xg/hlh/automation_web/test/controller/","AwardsRankController.java","controller.ftl", params);

    }

    /**
     * OK
     * 测试生成dao
     * @throws Exception
     */
    @RequestMapping(value = "/test7", method = RequestMethod.GET)
    public void test7() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("MyRepositoryPackageName", "com.xg.hlh.automation_web.test.JpaRepository");
        params.put("EntityPackageName", "com.xg.hlh.automation_web.test.domain");
        params.put("EntityName", "AwardsRank");
        //baseController生成
        FreeMarkerTplLan.generateFile("src/main/java/com/xg/hlh/automation_web/test/dao/","AwardsRankRepository.java","dao.ftl", params);

    }

    /**
     * OK
     * 测试生成service
     * @throws Exception
     */
    @RequestMapping(value = "/test8", method = RequestMethod.GET)
    public void test8() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("BasicServicePackageName", "com.xg.hlh.automation_web.test.BasicService");
        params.put("EntityPackageName", "com.xg.hlh.automation_web.test.domain");
        params.put("EntityName", "AwardsRank");
        //baseController生成
        FreeMarkerTplLan.generateFile("src/main/java/com/xg/hlh/automation_web/test/service/","AwardsRankService.java","service.ftl", params);

    }

    /**
     * 测试对BasicService和JpaRepository文件夹下的所有文件导包
     * @throws Exception
     */
    @RequestMapping(value = "/test9", method = RequestMethod.GET)
    public void test9() throws Exception {
        generateFileService.importBasicPackage();
    }


    /**
     * 生成dao service controller
     * 前台需要传给我String[],例如{'Student','Teacher'}
     */

    /**
     * 获取指定目录下的所有文件名，返回一个List<String>
     */

    /**
     *
     * 根据指定目录下的文件名，获取该文件的<变量名,变量类型>Map
     */


}
