package com.univ.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @author univ
 * 2022/7/5 3:46 下午
 */
@Data
@TableName("user")
public class User {

    @TableId
    private Long id;

    @TableField("name")
    private String name;

    private Integer age;
    private String email;

    /**
     * 实体类字段与表名字映射
     *
     * 实践证明，表中字段为stu_id，实体类字段为stuId，两者能自动映射过来
     * 原因参见 {@link TableField#value()}注解
     */
    private Long stuId;

}
