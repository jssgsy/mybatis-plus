package com.univ.mybatisplus.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    /**
     * mybatis-plus中也可以有对应的xml文件。
     * 准确讲，mapper使用对应的xml文件是mybatis的功能，而mybatis-plus是基于mybatis的，自然支持这种方式。
     *
     * @param id
     * @return
     */
    User getByIdXml(@Param("id") Long id);

    /**
     * 直接使用注解形式
     *
     * @param id
     * @return
     */
    @Select("select * from user where id = #{id}")
    User getByIdAnnotation(@Param("id") Long id);

}
