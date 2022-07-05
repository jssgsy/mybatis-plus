package com.univ.mybatisplus;

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.univ.mybatisplus.entity.User;
import com.univ.mybatisplus.mapper.UserMapper;

@SpringBootTest
class MybatisPlusApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }

    @Test
    public void testInsert() {
        User user = new User();
        user.setAge(30);
        user.setEmail("my email");
        user.setId(60L);
        user.setName("unicom");
        int result = userMapper.insert(user);
        System.out.println(result);
    }

    @Test
    public void testUpdate() {
        User user = new User();
        user.setName("new unicom");

        // 参数一：要被设置的值，参数二：where条件
        // 这里注意下为何eq返回的仍然是一个QueryWrapper
        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("age", 30);
        int result = userMapper.update(user, wrapper);
        System.out.println(result);
    }

    @Test
    public void testDelete() {
        int result = userMapper.delete(new QueryWrapper<User>().eq("id", 1));
        System.out.println(result);
    }

    @Test
    void contextLoads() {
    }

}
