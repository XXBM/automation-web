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


    /**
     * 删除指定文件夹下所有文件
     * @param path 文件夹完整绝对路径 ,"Z:/xuyun/save"
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                flag = true;
            }
        }
        return flag;
    }
}
