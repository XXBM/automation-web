package com.xg.hlh.automation_web.utils;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.printer.PrettyPrinter;
import com.github.javaparser.printer.PrettyPrinterConfiguration;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@Service
public class JavaParserService extends VoidVisitorAdapter<List<FieldDeclaration>> {

    /**
     * 访问java语法树中的变量
     * @param fieldDeclaration
     * @param fieldDeclarations
     */
    @Override
    public void visit(FieldDeclaration fieldDeclaration, List<FieldDeclaration> fieldDeclarations) {
        super.visit(fieldDeclaration, fieldDeclarations);
        fieldDeclarations.add(fieldDeclaration);
    }

    /**
     * 解析语法树并获取变量集合
     * @param filePath
     * @return
     * @throws FileNotFoundException
     */
    public List<FieldDeclaration> getFieldDeclaration(String filePath) throws FileNotFoundException {
        List<FieldDeclaration> fieldDeclarations = new ArrayList<>();
        File javaFile = new File(filePath);
        //javaparser解析java文件的对象
        CompilationUnit cu = JavaParser.parse(javaFile);
        //访问解析到的java语法树
        this.visit(cu,fieldDeclarations);
        return fieldDeclarations;
    }

    /**
     * 获取一个类的属性的集合
     * @param filePath 类路径
     * @return 返回的Map<变量名，变量类型>
     * @throws FileNotFoundException
     */
    public HashMap<String,String> getVariableAndTypeMap(String filePath) throws FileNotFoundException  {
        //自定义的接收属性名称和类型的Map集合
        HashMap<String,String> propertyMaps = new HashMap<String, String>();
        List<FieldDeclaration> fieldDeclarationList = this.getFieldDeclaration(filePath);
        for(FieldDeclaration fieldDeclaration : fieldDeclarationList){
            String propertyType = fieldDeclaration.getElementType().asString();
            String propertyName = fieldDeclaration.getVariable(0).getNameAsString();
            propertyMaps.put(propertyName,propertyType);
        }
        return propertyMaps;
    }

    /**
     * 获取一个类的属性的集合
     * @param filePath 类路径
     * @return 返回的Map<变量名，变量对象>
     * @throws FileNotFoundException
     */
    public HashMap<String,FieldDeclaration> getVariableAndFieldDeclarationMap(String filePath) throws FileNotFoundException  {
        //自定义的接收属性名称和类型的Map集合
        HashMap<String,FieldDeclaration> propertyMaps = new HashMap<String, FieldDeclaration>();
        List<FieldDeclaration> fieldDeclarationList = this.getFieldDeclaration(filePath);
        for(FieldDeclaration fieldDeclaration : fieldDeclarationList){
            String propertyName = fieldDeclaration.getVariable(0).getNameAsString();
            propertyMaps.put(propertyName,fieldDeclaration);
        }
        return propertyMaps;
    }

    /**
     * 根据变量名找到FieldDeclaration
     * @param filePath
     * @param name
     * @return
     * @throws FileNotFoundException
     */
    public FieldDeclaration findFieldDeclarationByVariableName(String filePath,String name) throws FileNotFoundException {
        return this.getVariableAndFieldDeclarationMap(filePath).get(name);
    }

    /**
     * 为一个类的类和字段添加注解信息
     * @param filePath
     * @param classAnnotationList 类上要添加的注解
     * @param variableMap <变量名，该变量要添加的注解集合>
     * @throws IOException
     */
    public void addAnnotations(String filePath,List<String> classAnnotationList,Map<String,List<String>> variableMap) throws IOException {
        List<FieldDeclaration> fieldDeclarations = new ArrayList<>();
        //javaparser解析java文件的对象
        File javaFile = new File(filePath);
        CompilationUnit cu = JavaParser.parse(javaFile);

        //添加类注解
        ClassOrInterfaceDeclaration classOrInterfaceDeclaration = (ClassOrInterfaceDeclaration) cu.getType(0);
        for(int i=0;i<classAnnotationList.size();i++){
            classOrInterfaceDeclaration.addAndGetAnnotation(classAnnotationList.get(i));
        }

        //访问解析到的java语法树，初始化字段集合
        this.visit(cu,fieldDeclarations);

        //将变量名-变量对象一一对应
        HashMap<String,FieldDeclaration> propertyMaps = new HashMap<String, FieldDeclaration>();
        for(FieldDeclaration fieldDeclaration : fieldDeclarations){
            String propertyName = fieldDeclaration.getVariable(0).getNameAsString();
            propertyMaps.put(propertyName,fieldDeclaration);
        }

        //添加变量注解
        FieldDeclaration fd;
        List<String> annotationStrs;
        for (Map.Entry<String,List<String>> entry : variableMap.entrySet()) {
            fd = propertyMaps.get(entry.getKey());
            annotationStrs = entry.getValue();
            for(int i=0;i<annotationStrs.size();i++){
                addAnnotationsByVariable(fd,annotationStrs.get(i));
            }
        }
        //将修改写入文件
        Files.write(javaFile.toPath(),cu.toString().getBytes());
    }

    /**
     *  为一个变量对象添加注解们
     * @param fd
     * @param annotationStr
     * "javax.persistence.OneToMany(mappedBy = \"employee\", fetch = javax.persistence.FetchType.LAZY, cascade = javax.persistence.CascadeType.ALL)"
     */
    public void addAnnotationsByVariable(FieldDeclaration fd,String annotationStr){
        annotationStr.trim();
        //注解名字javax.persistence.OneToMany
        String annootationName = annotationStr.substring(0,annotationStr.lastIndexOf("("));
        NormalAnnotationExpr normalAnnotationExpr = fd.addAndGetAnnotation(annootationName);
        //注解内容
        //mappedBy = \"employee\", fetch = javax.persistence.FetchType.LAZY, cascade = javax.persistence.CascadeType.ALL
        String value = annotationStr.substring(annotationStr.lastIndexOf("(")+1,annotationStr.lastIndexOf(")"));
        if(!value.isEmpty()){
            //fetch = javax.persistence.FetchType.LAZY
            String varibles[] = value.split(",");
            for(int i=0;i<varibles.length;i++){
                //fetch是var[0]
                //javax.persistence.FetchType.LAZY是var[1]
                String var[] = varibles[i].split("=");
                normalAnnotationExpr.addPair(var[0],var[1]);
            }
        }
    }


    /**
     * 为某个文件导包
     * @param filePath 文件路径
     * "src/main/resources/com.xg.hlh.com/BasicService/BasicService.java"
     * @param packageName 包名
     * "com.xg.hlh.automation_web.entity"
     */
    public void importPackage(String filePath,String packageName) throws IOException {
        //javaparser解析java文件的对象
        File javaFile = new File(filePath);
        CompilationUnit cu = JavaParser.parse(javaFile);
        cu.setPackageDeclaration(packageName);
        Files.write(javaFile.toPath(),cu.toString().getBytes());
    }

    /**
     * 新建dao类
     * @param fileDir 新建dao类的目录
     * @param className 实体类名
     * @param packageName 导包 包名
     * @param extendPackage 要继承的类的包名
     * @param classPackage 实体类包名
     * @throws IOException
     */
    public void createDao(String fileDir,String className,String packageName,String extendPackage,String classPackage) throws IOException {
        File javaFile = new File(fileDir,className+"Repository.java");
        //新建一个操作java文件的对象
        CompilationUnit cu = new CompilationUnit();
        cu.setPackageDeclaration(packageName);
        ClassOrInterfaceDeclaration classDeclaration  = cu.addInterface(className+"Repository").setPublic(true);
        classDeclaration.addAndGetAnnotation("org.springframework.stereotype.Repository");
        classDeclaration.addExtendedType("MyRepository<"+className+",Long>");
        cu.addImport(extendPackage);
        cu.addImport(classPackage);
        Files.write(javaFile.toPath(),this.prettyPrinter().print(cu).getBytes());
    }

    /**
     * 新建service类
     * @param fileDir 新建service的目录
     * @param className 实体类名
     * @param packageName 导包 包名
     * @param extendPackage 要继承的类的包名
     * @param classPackage 实体类包名
     * @throws IOException
     */
    public void createService(String fileDir,String className,String packageName,String extendPackage,String classPackage) throws IOException {
        File javaFile = new File(fileDir,className+"Service.java");
        //新建一个操作java文件的对象
        CompilationUnit cu = new CompilationUnit();
        cu.setPackageDeclaration(packageName);
        ClassOrInterfaceDeclaration classDeclaration  = cu.addClass(className+"Service").setPublic(true);
        classDeclaration.addAndGetAnnotation("org.springframework.stereotype.Service");
        classDeclaration.addExtendedType("BasicService<"+className+",Long>");
        cu.addImport(extendPackage);
        cu.addImport(classPackage);

        Files.write(javaFile.toPath(),this.prettyPrinter().print(cu).getBytes());
    }

    /**
     * 格式化输出
     * @return
     */
    public PrettyPrinter prettyPrinter(){
        //代码格式化输出
        PrettyPrinterConfiguration conf = new PrettyPrinterConfiguration();
        conf.setIndentType(PrettyPrinterConfiguration.IndentType.SPACES);
        conf.setPrintComments(false);
        PrettyPrinter prettyPrinter = new PrettyPrinter(conf);
        return  prettyPrinter;
    }

}
