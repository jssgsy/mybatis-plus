package com.univ.mybatisplus;

import org.junit.Test;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

/**
 * MybatisPlus代码自动生成
 * 几个修改比较频繁的配置是：
 *  outputDir
 *  parent
 *  addInclude
 *
 * @author univ
 * 2022/8/9 3:42 下午
 */
public class MybatisPlusAutoGenerator {

    String url = "jdbc:postgresql://127.0.0.1:5432/univ";
    String username = "test";
    String password = "1234";

    @Test
    public void test() {
        FastAutoGenerator.create(url, username, password)
                // 1. globalConfig
                .globalConfig(builder -> {
                    builder.author("univ") // 设置作者
                            .disableOpenDir()
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            // 指定输出目录, 即要生成的各种文件放到哪，一般填写工程全路径(可理解成一级目录)
                            .outputDir("/Users/univ/gitRepos/dplus-customer");
                })
                // 2. packageConfig
                .packageConfig(builder -> {
                    builder.parent("com.unicom"); // 要生成的各种文件放到哪(可理解成二级目录)
                    // .moduleName("moudule") // 要生成的各种文件放到哪(可理解成三级目录)
                    // .entity("") // 即生成的entity文件放在哪个包下，默认就是entity
                    // .service("") // 即生成的service文件放在哪个包下，默认就是service，其它的还有controller等，查看源码
                    // .pathInfo(pathInfo); // 设置各种文件的组件，注，pathInfo不受上述的parent作用，即parent的上级是outputDir
                })
                // 3. strategyConfig
                .strategyConfig(builder -> {
                    builder.addInclude("demo");// 设置需要生成的表名
                    // .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                    // .serviceBuilder().formatServiceFileName("%sService");// 默认生成的service接口前会加I，这样设置就不会加了
                })
                // 4. templateEngine
                .templateEngine(new VelocityTemplateEngine())   // 使用Velocity引擎模板，默认的是Velocity引擎模板、不同的引擎要引入不同的包
                // 5. templateConfig
                // .templateConfig(builder -> builder.controller("")) // 为空表示不生成controller,其它的如service，mapper等一样
                // .templateConfig(builder -> builder.service(""))
                .execute();
    }


}
