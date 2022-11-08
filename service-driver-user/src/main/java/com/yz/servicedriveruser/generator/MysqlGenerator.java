package com.yz.servicedriveruser.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * @Author: yangzhen
 * @Date 2022/11/4-17:46
 * @Description: 自动生成代码工具类
 * @version: 1.0
 */
public class MysqlGenerator {

    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/service-driver-user?characterEncoding=utf-8&serverTimezone=GMT%2B8",
                "root","root")
                .globalConfig(builder ->{
                    builder.author("yz").fileOverride().outputDir("E:\\simpleProject\\online-taxi-public\\service-driver-user\\src\\main\\java");
                })
                .packageConfig(builder -> {
                    builder.parent("com.yz.servicedriveruser").pathInfo(Collections.singletonMap(OutputFile.mapperXml,
                            "E:\\simpleProject\\online-taxi-public\\service-driver-user\\src\\main\\java\\com\\yz\\servicedriveruser\\mapper"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude("driver_user_work_status");
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
