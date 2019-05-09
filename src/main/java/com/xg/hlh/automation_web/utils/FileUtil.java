package com.xg.hlh.automation_web.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    /**
     * 获取指定目录下的所有文件名（例如："Student.java"）
     *
     * @param dirPath
     * @return
     */
    public static List<String> findAllFileName(String dirPath) {
        List<String> list = new ArrayList<>();
        File file = new File(dirPath);
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            list.add(tempList[i].getName());
        }
        return list;
    }

    /**
     * 获取指定目录下的所有文件
     *
     * @param dirPath
     * @return
     */
    public static List<File> findAllFile(String dirPath) {
        List<File> list = new ArrayList<>();
        File file = new File(dirPath);
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            list.add(tempList[i]);
        }
        return list;
    }


    /**
     * 删除指定文件夹下所有文件
     *
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

    /**
     * 搜索指定路径下指定文件名的文件
     *
     * @param baseDirName
     * @param targetFileName
     * @return
     */
    public static boolean deleteFile(String baseDirName, String targetFileName) {

        File baseDir = new File(baseDirName);        // 创建一个File对象
        if (!baseDir.exists() || !baseDir.isDirectory()) {    // 判断目录是否存在
            System.out.println("文件查找失败：" + baseDirName + "不是一个目录！");
        }
        String tempName = null;
        //判断目录是否存在
        File tempFile;
        File[] files = baseDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            tempFile = files[i];
            if (tempFile.isDirectory()) {
                deleteFile(tempFile.getAbsolutePath(), targetFileName);
            } else if (tempFile.isFile()) {
                tempName = tempFile.getName();
                if (tempName.equals(targetFileName)) {
                    return tempFile.delete();
                }
            }
        }
        return false;
    }


    /**文件下载*/
    public static String downloadFile(HttpServletRequest request, HttpServletResponse response, String filePath,String fileName) {
        //设置文件路径
        File file = new File(filePath+fileName);
        System.out.println(file.exists());
        //File file = new File(realPath , fileName);
        if (file.exists()) {
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                return "下载成功";
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return "下载失败";
    }
}
