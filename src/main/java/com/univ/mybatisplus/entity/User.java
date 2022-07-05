package com.univ.mybatisplus.entity;

import lombok.Data;

/**
 * @author univ
 * 2022/7/5 3:46 下午
 */
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
