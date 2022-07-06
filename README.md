# [mybatis-plus官网]()

# 关于IService
参见UserService及实现类UserServiceImpl。可知，其实IService的功能比较鸡肋，限制比较多，实际中避免使用。

# 关于Wrapper
类似于mybatis-generator中的Criteria，用来组装sql语句的。

## 工具类-Wrappers
主要用来生成某个类型的具体Wrapper

## 主要的Wrapper类型
可从上述Wrappers类中查看。Wrapper主要有：
* QueryWrapper；
* UpdateWrapper；
* LambdaUpdateWrapper；
* LambdaQueryWrapper；

还有几个对应的用来支持链式查询的Wrapper，如下：
* QueryChainWrapper
* LambdaQueryChainWrapper
* UpdateChainWrapper
* LambdaUpdateChainWrapper

# 关于mapper xml
mybatis-plus的一个特点是常用场景下可以不使用mapper xml文件。但在有需要时也可以有(准确讲，mapper使用对应的xml文件是mybatis的功能，而mybatis-plus是基于mybatis的，自然支持这种方式。)
方法如下：
1. 建一个对应的xml文件，其中的namespace指向要绑定的Mapper接口；
    * 参见UserMapper.xml文件；
2. 在配置文件中指定xml文件的扫描路径；
    * 参见application.yml中的mybatis-plus.mapper-locations配项；
    
# 关于分页
参考：MybatisPlusApplicationTests.testMapperSelectWithPage
核心：需要配置分页插件，参考：MybatisPlusConfig