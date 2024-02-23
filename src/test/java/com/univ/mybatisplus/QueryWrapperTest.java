package com.univ.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.univ.mybatisplus.entity.User;
import com.univ.mybatisplus.mapper.UserMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * QueryWrapper更原始，但能应对一些复杂查询场景。
 * 核心：使用字符串形式来填充sql语句的各部分，如select、group by、having、order by、limit等
 * 注意：
 * 	1. 这些部分模板大部分定义在父类AbstractWrapper中；
 * 	2. limit子句需用last方法来代替；
 * @author univ 2023/2/15 11:16
 */
@SpringBootTest
public class QueryWrapperTest {

	@Resource
	private UserMapper userMapper;

	private <T> QueryWrapper<T> instantiate() {
		QueryWrapper<T> query = Wrappers.query();
		return query;
		// 或者直接new
        /*QueryWrapper<T> q2 = new QueryWrapper<>();
		return q2;*/
	}

	private void commonMethod(){
		QueryWrapper<User> query = Wrappers.query();
		// 填充select部分
		// 简单字段
		query.select("name, age");
		// 聚合函数
		query.select("distinct name, age");
		// 聚合函数
		query.select("sum(age)");

		query.groupBy("date");
		query.groupBy(Arrays.asList("date", "depart"));

		query.having("sum(age) > 10");
		query.having("sum(age) > {0}", 11);

		query.orderByAsc("date");
		query.orderByAsc(Arrays.asList("date", "name"));
		query.orderByDesc("date");
		query.orderByDesc(Arrays.asList("date", "name"));

		// 只能调用一次,多次调用以最后一次为准 有sql注入的风险,请谨慎使用
		query.last("limit 1,, 10");

		// 转成LambdaQueryWrapper
		LambdaQueryWrapper<User> lambda = query.lambda();
	}

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
