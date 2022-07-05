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
}
