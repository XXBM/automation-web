package com.xg.hlh.automation_web.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    /**
     * 获取指定目录下的所有文件名（例如："Student.java"）
     * @param dirPath
     * @return
     */
    public static List<String> findAllFileName(String dirPath){
        List<String> list = new ArrayList<>();
        File file = new File(dirPath);
        File[] tempList = file.listFiles();
        for(int i=0;i<tempList.length;i++){
            list.add(tempList[i].getName());
        }
        return list;
    }

    /**
     * 获取指定目录下的所有文件
     * @param dirPath
     * @return
     */
    public static List<File> findAllFile(String dirPath){
        List<File> list = new ArrayList<>();
        File file = new File(dirPath);
        File[] tempList = file.listFiles();
        for(int i=0;i<tempList.length;i++){
            list.add(tempList[i]);
        }
        return list;
    }
}
