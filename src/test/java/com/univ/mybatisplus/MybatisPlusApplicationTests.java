package com.univ.mybatisplus;

import java.util.List;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.univ.mybatisplus.entity.User;
import com.univ.mybatisplus.mapper.UserMapper;
import com.univ.mybatisplus.service.UserService;

@SpringBootTest
class MybatisPlusApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserService userService;

    @Test
    public void testMapperSelect() {
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }

    /**
     * 分页查询
     *
     * 注：mybatis-plus的分页是通过插件形式完成的，因此需要在项目中配置分页插件。参见{@link com.baomidou.mybatisplus.core.MybatisConfiguration}
     *
     * @see <a href='https://baomidou.com/pages/2976a3/#spring'>mybatis-plus插件官网说明</a>
     */
    @Test
    public void testMapperSelectWithPage() {
        // mybatis-plus没有提供page的工具类，可以自己封装一个
        IPage<User> page = new Page<>();
        page.setCurrent(2);
        page.setSize(2);

        IPage<User> userIPage = userMapper.selectPage(page, null);
        // 数据也保存在IPage中
        List<User> userList = userIPage.getRecords();
        userList.forEach(System.out::println);
    }

    @Test
    public void testMapperInsert() {
        User user = new User();
        user.setAge(30);
        user.setEmail("my email");
        user.setId(60L);
        user.setName("unicom");
        int result = userMapper.insert(user);
        System.out.println(result);
    }

    @Test
    public void testMapperUpdate() {
        User user = new User();
        user.setName("new unicom");

        // 参数一：要被设置的值，参数二：where条件
        // 这里注意下为何eq返回的仍然是一个QueryWrapper
        // 另一种用法：使用Wrappers工具类
        // QueryWrapper<User> queryWrapper = Wrappers.<User>query();
        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("age", 30);
        int result = userMapper.update(user, wrapper);
        System.out.println(result);
    }

    /**
     * 效果与{@link #testMapperUpdate()}等价，用来演示lambda的用法
     *
     * 使用lambda的好处：不需要硬编码字段
     *
     */
    @Test
    public void testMapperUpdateUseLambda() {
        User user = new User();
        user.setName("new unicom v2");

        LambdaQueryWrapper<User> lambdaQueryWrapper = Wrappers.<User>lambdaQuery().eq(User::getAge, 30);
        int result = userMapper.update(user, lambdaQueryWrapper);
        System.out.println(result);
    }

    @Test
    public void testMapperDelete() {
        int result = userMapper.delete(new QueryWrapper<User>().eq("id", 1));
        System.out.println(result);
    }

    // 使用xml中的sql语句
    @Test
    public void testMapperSelectXml() {
        User user = userMapper.getByIdXml(2L);
        System.out.println(user);
    }

    @Test
    public void testMapperSelectAnnotation() {
        User user = userMapper.getByIdAnnotation(2L);
        System.out.println(user);
    }

    /**
     * 使用mybatis-plus中的IService会有一些限制(主要是java只允许单继承)，实际中一般不要使用
     */
    @Test
    public void testServiceSave() {
        User user = new User();
        user.setAge(40);
        user.setEmail("email40");
        user.setId(40L);
        user.setName("unicom40");
        boolean result = userService.save(user);
        System.out.println(result);
    }

    /**
     * 同时使用and与or，看注释
     */
    @Test
    public void combineAndOr() {
        LambdaQueryWrapper<User> query = Wrappers.lambdaQuery(User.class);
        // query的默认连接符是and
        // 1. 单纯的and：name = 'xxx' and age = 20 and email = 'yyy'
        query.eq(User::getName, "xxx").eq(User::getAge, 20).eq(User::getEmail, "yyy");

        // 2. 单纯的or：name = 'xxx' or age = 20 or email = 'yyy'
        query.eq(User::getName, "xxx")
                .or()
                .eq(User::getAge, 20)
                .or()   // 不能少，每一个or()就是在sql后加一个or连接
                .eq(User::getEmail, "yyy");

        // 3. and与or混用：错误的写法，
        // 此时等价于：name = 'xxx' and age = 20 or email = 'yyy' and stuId = 1
        // 注意，此时并没有加任何括号
        query.eq(User::getName, "xxx").eq(User::getAge, 20).
                or()
                .eq(User::getEmail, "yyy").eq(User::getStuId, 1);

        // 4. and与or混用：正确的写法，需要加括号时将整个条件语句放到重载的or或and方法中!
        // 4.1 a = x1 and (b = x2 or c = x3)
        query.eq(User::getName, "xxx").or(t -> t.eq(User::getEmail, "yyy").eq(User::getStuId, 1));

        // 4.2 (a = x1 and b = x2) or c = x3
        query.and(t -> t.eq(User::getName, "xxx").eq(User::getEmail, "yyy"))
                .or()
                .eq(User::getEmail, "yyy");
        // userMapper.selectList(query);
    }

}
