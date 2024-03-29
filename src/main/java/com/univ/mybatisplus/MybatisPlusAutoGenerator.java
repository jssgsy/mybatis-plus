package com.univ.mybatisplus;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import java.util.Collections;

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

    static String url = "jdbc:mysql://localhost:3306/mybatis?useAffectedRows=true";
    static String username = "test";
    static String password = "123";
    public static void main(String[] args) {


        // 工程目录
        String projectPath = System.getProperty("user.dir");
        // 注：这里还有一个重载的方法，入参为DataSourceConfig.Builder，这里能设置的参数更多，如schema，这在诸如金仓等数据库中表名前需要加public schema非常有用
        FastAutoGenerator.create(url, username, password)
                // 1. globalConfig
                .globalConfig(builder -> {
                    builder.author("univ") // 设置作者
                            .disableOpenDir()
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            // 指定输出目录, 即要生成的各种文件放到哪个『目录』下，一般填写至src/main/java工程全路径
                            .outputDir(projectPath + "/src/main/java");
                })
                // 2. packageConfig
                .packageConfig(builder -> {
                    // 重要：parent + module 是包的全路径名，(parent一定要填，因为有默认值com.baomidou)
                    // 毕竟，这里就是包配置(packageConfig)
                    builder.parent("com.univ.mybatisplus") // 要生成的各种文件放到哪个『包』下
                            // .moduleName("moudule") // 要生成的各种文件放到哪个『包』下
                            // .entity("") // 即生成的entity文件放在哪个包下，默认就是entity,如entity.xxx，注意是包名
                            .service("") // 即生成的service文件放在哪个包下，默认就是service，其它的还有controller等，查看源码。

                            // 设置各种文件的组件，注，pathInfo不受上述的parent作用，即parent的上级是outputDir(在非全路径情况下？)
                            // xml资源文件还是放到resources目录下，而不是和java文件混在一起
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, projectPath + "/src/main/resources/mapper"));

                })
                // 3. strategyConfig
                .strategyConfig(builder -> {
                    // 设置需要生成的表名，不填会为库中所有表生成相应的代码
                    // 注：是可变长类型，可填多个表名
                    builder.addInclude("my_table_name", "");
                    // .addTablePrefix("t_", "c_"); // 设置过滤表前缀,如表为t_xxx，则此时生成的实例类为Xxx，而不是TXxx
                    // 默认生成的service接口前会加I，这样设置就不会加了，即可以在这里自定义生成的service、entity、mapper的类名
                    // .serviceBuilder().formatServiceFileName("%sService");
                    // builder.entityBuilder().enableLombok().formatFileName("Xxx%s");
                    // builder.serviceBuilder().formatServiceFileName("Xxx%sService");
                    // builder.serviceBuilder().formatServiceFileName("Xxx%sService").formatServiceImplFileName("Xxx%sServiceImpl");
                    // builder.mapperBuilder().formatMapperFileName("Xxx%sMapper").formatXmlFileName("Xxx%s");

                    // 此时会在对应属性上生成@TableLogic注解,经验证，如下任一设置均可
//                    builder.entityBuilder().logicDeletePropertyName("age");
//                    builder.entityBuilder().logicDeleteColumnName("age");
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

    /**
     * 演示表名前需要加schema时的生成代码，以金仓数据库为例
     */
    private void schemaSample() {

        // 不再直接以url, username, password为入参，而是DataSourceConfig.Builder
        FastAutoGenerator.create(new DataSourceConfig.Builder(url, username, password)
                // 这句是重点
                .schema("public"))
                .globalConfig(builder -> {
                    // 参考main方法
                })
                .packageConfig(builder -> {
                    // 参考main方法
                })
                .strategyConfig(builder -> {
                    // 注：这里就不要写成public.demo了，上面已经设置了schema为public
                    builder.addInclude("demo");// 设置需要生成的表名
                    // 参考main方法
                })
                //
                .templateEngine(new VelocityTemplateEngine())
                .execute();
    }


}
