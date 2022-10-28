package cn.wenhe9.myshop.dao;

import cn.wenhe9.myshop.domain.entity.User;

import java.sql.SQLException;

/**
 * @description: 用户数据访问接口
 * @author: DuJinliang
 * @create: 2022/10/28
 */
public interface UserDao {

    /**
     * 根据用户名查询用户信息
     */
    User selectUserByUname(String username) throws SQLException;

    /**
     * 新增新的用户信息
     */
    int insertUser(User user) throws SQLException;


    /**
     * 根据激活码查找用户
     */
    User selectUserByCode(String code) throws SQLException;

    /**
     * 根据uid更新用户状态
     */
    int updateStatusByUid(int uId) throws SQLException;
}
