package com.univ.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.univ.mybatisplus.entity.User;

/**
 * 1. 被扫描后(@MapperScan)后就自动生成代理对象了，拿来即用。
 * 2. 这里的泛型变量是User，表明要操作的是User类对应的表；
 *      即BaseMapper的泛型即是 实体类；
 *
 * @author univ
 * 2022/7/5 3:46 下午
 *
 */
public interface UserMapper extends BaseMapper<User> {

}
