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
                            // 指定输出目录, 即要生成的各种文件放到哪个『目录』下，一般填写至src/main/java工程全路径
                            .outputDir("/Users/univ/gitRepos/mybatis-plus/src/main/java");
                })
                // 2. packageConfig
                .packageConfig(builder -> {
                    // 重要：parent + module 是包的全路径名，(parent一定要填，因为有默认值com.baomidou)
                    // 毕竟，这里就是包配置(packageConfig)
                    builder.parent("com.univ.mybatisplus"); // 要生成的各种文件放到哪个『包』下
                    // .moduleName("moudule") // 要生成的各种文件放到哪个『包』下
                    // .entity("") // 即生成的entity文件放在哪个包下，默认就是entity
                    // .service("") // 即生成的service文件放在哪个包下，默认就是service，其它的还有controller等，查看源码
                    // .pathInfo(pathInfo); // 设置各种文件的组件，注，pathInfo不受上述的parent作用，即parent的上级是outputDir
                })
                // 3. strategyConfig
                .strategyConfig(builder -> {
                    // 设置需要生成的表名，不填会为库中所有表生成相应的代码
                    // 注：是可变长类型，可填多个表名
                    builder.addInclude("my_table_name", "");
                    // .addTablePrefix("t_", "c_"); // 设置过滤表前缀,如表为t_xxx，则此时生成的实例类为Xxx，而还是TXxx
                    // 默认生成的service接口前会加I，这样设置就不会加了
                    // .serviceBuilder().formatServiceFileName("%sService");
                })
                // 4. templateEngine
                // 使用Velocity引擎模板，默认的是Velocity引擎模板、不同的引擎要引入不同的包
                .templateEngine(new VelocityTemplateEngine())
                // 5. templateConfig
                // 为空表示不生成controller,其它的如service，mapper等一样
                .templateConfig(builder -> builder.controller(""))
                .templateConfig(builder -> builder.service(""))
                .templateConfig(builder -> builder.serviceImpl(""))
                .execute();
    }


}
