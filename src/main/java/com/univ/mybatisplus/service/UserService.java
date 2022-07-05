package com.univ.mybatisplus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.univ.mybatisplus.entity.User;

/**
 * 和Mapper不同的是，IService的实现类不用被扫描，继承了就能用。
 *      当然，前提是实现类要成为一个Bean(@Service)
 * 缺点：java是单继承，如果继承了mybatis-plus的IService，则业务上没法继承其它接口了
 *
 * @author univ
 * 2022/7/5 4:32 下午
 */
public interface UserService extends IService<User> {
}
