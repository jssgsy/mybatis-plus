package com.univ.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.univ.mybatisplus.entity.User;
import com.univ.mybatisplus.mapper.UserMapper;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * QueryWrapper更原始(使用字符串)，但能应对一些复杂查询场景。
 *
 * @author univ 2023/2/15 11:16
 */
@SpringBootTest
public class QueryWrapperTest {

	@Resource
	private UserMapper userMapper;

	/**
	 * group by语句
	 * 只能用在QueryWrapper中；
	 */
	@Test
	public void groupBy() {
		// 此时没法使用LambdaQueryWrapper了，因为没法在lambda中设置聚合函数
		QueryWrapper<User> query = Wrappers.query();
		query.select("age, count(*) as cnt");
		query.groupBy("age");

		// 此时可转成lambda query继续添加条件
		LambdaQueryWrapper<User> lambda = query.lambda();
		lambda.ge(User::getAge, 10);
		lambda.last("limit 1, 10");
		// selectList形式
		List<User> users = userMapper.selectList(lambda);
		System.out.println(users);

		// selectMaps形式
		List<Map<String, Object>> cntMap = userMapper.selectMaps(lambda);
		System.out.println(cntMap);
	}
}
