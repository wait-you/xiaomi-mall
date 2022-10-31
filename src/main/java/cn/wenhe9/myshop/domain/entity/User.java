package cn.wenhe9.myshop.domain.entity;

import lombok.*;

import java.io.Serializable;

/**
 * @description: 用户类
 * @author: DuJinliang
 * @create: 2022/10/27
 */

@Getter
@Setter
@Builder
@ToString
public class User extends BaseEntity {

    /**
     * 用户实体的主键属性
     */
    private int uid;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户性别
     */
    private String sex;

    /**
     * 用户的激活状态 0 未激活 1 激活
     */
    private String status;

    /**
     * 邮件激活码
     */
    private String code;

    /**
     * 用户的邮箱 用于激活使用
     */
    private String email;

    /**
     * 用户 0 管理员 1
     */
    private int role;
}
