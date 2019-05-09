package com.xg.hlh.automation_web.controller;

import com.xg.hlh.automation_web.exception.Result;
import com.xg.hlh.automation_web.exception.ResultUtil;
import com.xg.hlh.automation_web.utils.FileUtil;
import com.xg.hlh.automation_web.utils.StaticVariable;
import com.xg.hlh.automation_web.utils.ZipCompress;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class FileDownloadController {

    //文件下载相关代码
    @GetMapping("/download")
    public Result downloadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String sourceFilePath = StaticVariable.operateRootDirPath;
        String zipFilePath = StaticVariable.operateZipDirPath;
        String zipName = "bysj.zip";
        ZipCompress.zip(zipFilePath+zipName,sourceFilePath);
        FileUtil.downloadFile(request,response,StaticVariable.operateZipDirPath,zipName);
        return ResultUtil.success();
    }


}
