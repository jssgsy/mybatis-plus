package com.univ.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.univ.mybatisplus.entity.User;
import com.univ.mybatisplus.mapper.UserMapper;
import com.univ.mybatisplus.service.UserService;
import java.util.List;
import javax.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DMLTest {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserService userService;

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

    // 原生使用mybatis的xml
    @Test
    public void testMapperSelectXml() {
        User user = userMapper.getByIdXml(2L);
        System.out.println(user);
    }

    /**
     * 原生使用mybatis的注解
     */
    @Test
    public void testMapperSelectAnnotation() {
        User user = userMapper.getByIdAnnotation(2L);
        System.out.println(user);
    }

}
