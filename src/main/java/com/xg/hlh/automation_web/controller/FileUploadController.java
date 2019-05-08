package com.xg.hlh.automation_web.controller;


import com.xg.hlh.automation_web.entity.FileDomain;
import com.xg.hlh.automation_web.exception.Result;
import com.xg.hlh.automation_web.exception.ResultUtil;
import com.xg.hlh.automation_web.utils.FileUtil;
import com.xg.hlh.automation_web.utils.StaticVariable;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
public class FileUploadController {


    //单个文件上传
    @RequestMapping("/fileUpload")
    public String fileUpload( MultipartFile file) throws IOException {
        // 获取上传文件路径
        String uploadPath = file.getOriginalFilename();
        // 获取上传文件的后缀
        String fileSuffix = uploadPath.substring(uploadPath.lastIndexOf(".") + 1, uploadPath.length());
        //存储路径
        String filePath = StaticVariable.operateDomainPath;
        // 上传文件名
        String fileName = uploadPath.substring(uploadPath.lastIndexOf("\\")+1,uploadPath.lastIndexOf("."));
        //上传
        File savefile = new File(filePath + fileName+"."+fileSuffix);
        file.transferTo(savefile);
        return fileName;
    }

    // 批量上传
    @PostMapping("/moreFileUpload")
    public List<String> bacthFileUpload(MultipartFile[] file) throws IOException {
        List fileNames = new ArrayList<>();
        for (MultipartFile multipartFile : file) {
            String str = fileUpload(multipartFile);
           fileNames.add(str);
        }
        return fileNames;
    }

    // 返回所有上传成功的实体类
    @GetMapping("/getDomainFiles")
    public List<FileDomain> getDomainFiles() throws IOException {
         List<String> stringList =  FileUtil.findAllFileName(StaticVariable.operateDomainPath);
         List<FileDomain> fileDomains = new ArrayList<>();
        FileDomain fileDomain = null;
         for(int i=0;i<stringList.size();i++){
             fileDomain = new FileDomain(stringList.get(i),"done");
             fileDomains.add(fileDomain);
         }
         return fileDomains;
    }

    // 删除单个文件
    @RequestMapping(value = "/deleteFile", method = RequestMethod.DELETE)
    public boolean deleteFile(@RequestParam(value = "fileName") String fileName) {
        FileUtil.deleteFile(StaticVariable.operateDomainPath, fileName);
        return true;
    }

    // 批量删除
    @PostMapping("/clear")
    public Result clearFiles() {
        FileUtil.delAllFile(StaticVariable.operateDomainPath);
        FileUtil.delAllFile(StaticVariable.operateDaoPath);
        FileUtil.delAllFile(StaticVariable.operateServicePath);
        FileUtil.delAllFile(StaticVariable.operateControllerPath);
        return ResultUtil.success();
    }
}





