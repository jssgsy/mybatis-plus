# [mybatis-plus官网]()

# 关于IService
参见UserService及实现类UserServiceImpl。可知，其实IService的功能比较鸡肋，限制比较多，实际中避免使用。

# 关于Wrapper
类似于mybatis-generator中的Criteria，用来组装sql语句的。
最核心的子类应该就是QueryWrapper了。
