package com.univ.mybatisplus.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.univ.mybatisplus.entity.User;
import com.univ.mybatisplus.mapper.UserMapper;
import com.univ.mybatisplus.service.UserService;

/**
 * 由此可知，使用mybatis-plus的IService功能其实比较繁琐
 *      1. 需要继承mybatis-plus的ServiceImpl类，这样业务上就没法继承其它类了；
 *      2. 这里其实还引入了UserMapper；
 *
 * @author univ
 * 2022/7/5 4:37 下午
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
