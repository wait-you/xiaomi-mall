package cn.wenhe9.myshop.service;

import cn.wenhe9.myshop.domain.entity.User;

import java.sql.SQLException;

/**
 * @description: 用户业务接口
 * @author: DuJinliang
 * @create: 2022/10/28
 */
public interface UserService {
    /**
     * 检查用户名是否存在
     */
    boolean checkUsernameExist(String username) throws SQLException;

    /**
     * 注册的业务逻辑
     */
    int registerUser(User user) throws SQLException;

    /**
     * 登录的业务逻辑
     */
    User login(String username,String password) throws SQLException;

    /**
     * 激活用户
     */
    int activeUser(String code) throws SQLException;
}
