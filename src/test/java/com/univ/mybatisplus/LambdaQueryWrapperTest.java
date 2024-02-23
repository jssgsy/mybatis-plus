package com.univ.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.univ.mybatisplus.entity.User;
import com.univ.mybatisplus.mapper.UserMapper;
import java.util.List;
import javax.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * LambdaQueryWrapper以面向对象的方式使用sql，只能用在简单场景下。
 *
 * 这里演示使用mapper层的方法<br>
 *
 * @author univ 2023/2/15 11:14
 */
@SpringBootTest
public class LambdaQueryWrapperTest {

	@Resource
	private UserMapper userMapper;

	@Test
	public void testMapperSelect() {
		List<User> userList = userMapper.selectList(null);
		userList.forEach(System.out::println);
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
//		userMapper.selectList(query);

		// 2. 单纯的or：name = 'xxx' or age = 20 or email = 'yyy'
		query.eq(User::getName, "xxx")
				.or()
				.eq(User::getAge, 20)
				.or()   // 不能少，每一个or()就是在sql后加一个or连接
				.eq(User::getEmail, "yyy");
//		userMapper.selectList(query);

		// 3. and与or混用：错误的写法，
		// 此时等价于：name = 'xxx' and age = 20 or email = 'yyy' and stuId = 1
		// 注意，此时并没有加任何括号
		query.eq(User::getName, "xxx").eq(User::getAge, 20).
			or()
			.eq(User::getEmail, "yyy").eq(User::getStuId, 1);
//		userMapper.selectList(query);

		// 4. and与or混用：正确的写法，需要加括号时将整个条件语句放到重载的or或and方法中!
		// 4.1 a = x1 or (b = x2 and c = x3)
		query.eq(User::getName, "xxx").
				or(
						t -> t.eq(User::getEmail, "yyy").eq(User::getStuId, 1)
				);

		// 4.2 (a = x1 and b = x2) or c = x3
		query.and(
				t -> t.eq(User::getName, "xxx").eq(User::getEmail, "yyy")
				)
			.or()
			.eq(User::getEmail, "yyy");
		// userMapper.selectList(query);
	}

	/**
	 * 只取部分字段：放在QueryWrapper中<br>
	 * 不论是LambdaQueryWrapper还是QueryWrapper都有几个重载的select方法，可用来只获取部分字段<br>
	 *
	 * 注：因为QueryWrapper是字符串形式，因此可发挥空间更大，不仅限于用来获取部分字段，还能用来填充聚合函数等。参见{@link QueryWrapperTest#groupBy()}
	 * 但select方法在LambdaQueryWrapper中切切实实只能用来获取部分字段
	 */
	@Test
	public void select() {
		LambdaQueryWrapper<User> query = Wrappers.lambdaQuery(User.class);
		// 是可变参数，
		query.select(User::getEmail, User::getAge);
		List<User> users = userMapper.selectList(query);// SELECT email,age FROM user
		System.out.println(users);
	}

	/**
	 * limit子句在LambdaQueryWrapper中对应的方法是last。
	 */
	@Test
	public void limit() {
		com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery(User.class);
		wrapper.last("limit 1");
		List<User> users = userMapper.selectList(wrapper);
		System.out.println(users);
	}

	@Test
	public void orderBy() {
		com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery(User.class);
		wrapper.orderByDesc(User::getAge);
		List<User> users = userMapper.selectList(wrapper);
		System.out.println(users);
	}

	/**
	 * LambdaQueryWrapper本身不支持distinct查询
	 *
	 * 但可由QueryWrapper使用distinct语句后转成LambdaQueryWrapper
	 */
	@Test
	public void distinct() {
		LambdaQueryWrapper<User> lambda = new QueryWrapper<User>().select("distinct age").lambda();
		userMapper.selectCount(lambda);
	}
}
