package com.xg.hlh.automation_web.utils;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.NullCacheStorage;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.Map;

public class FreeMarkerTplLan {

    private static Configuration configuration = new Configuration(Configuration.VERSION_2_3_20);

    static{
        //这里比较重要，用来指定加载模板所在的路径
        configuration.setTemplateLoader(new ClassTemplateLoader(FreeMarkerTemplateUtils.class, "/templates"));
        configuration.setDefaultEncoding("UTF-8"); //设置默认编码为UTF-8
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setCacheStorage(NullCacheStorage.INSTANCE);
    }

    /**
     * 根据模板生成文件
     * @param fileDir 输出文件的目录
     * @param fileName 文件名
     * @param tempFileName 模板文件名
     * @param params 对应的占位符Map
     * @throws Exception
     */
    public static void generateFile(String fileDir,String fileName, String tempFileName, Map<String, Object> params) throws Exception {
        Template template = configuration.getTemplate(tempFileName);
        StringWriter writer = new StringWriter();
        template.process(params, writer);
        FileUtils.writeStringToFile(new File(fileDir,fileName), writer.toString(), Charset.forName("utf-8").toString());
    }
}
