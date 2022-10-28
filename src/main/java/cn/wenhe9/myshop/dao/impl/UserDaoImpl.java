package cn.wenhe9.myshop.dao.impl;

import cn.wenhe9.myshop.dao.UserDao;
import cn.wenhe9.myshop.domain.constant.DatabaseConsts;
import cn.wenhe9.myshop.domain.constant.SystemConstants;
import cn.wenhe9.myshop.domain.entity.User;
import cn.wenhe9.myshop.utils.DruidUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description: 用户数据访问接口实现类
 * @author: DuJinliang
 * @create: 2022/10/28
 */
public class UserDaoImpl implements UserDao {
    @Override
    public User selectUserByUname(String username) throws SQLException {
        // 获取数据库连接
        Connection conn = DruidUtils.getConnection();

        // 定义查询sql
        String sql = "select u_id, u_name, u_password," +
                "u_sex, u_status, u_code, u_email," +
                "u_role from user where u_name=?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);

        ResultSet resultSet = pstmt.executeQuery();

        if (!resultSet.next()) {
            return null;
        }

        User user = User.builder()
                .uId(resultSet.getInt(DatabaseConsts.UserTable.COLUMN_U_ID))
                .username(resultSet.getString(DatabaseConsts.UserTable.COLUMN_U_NAME))
                .password(resultSet.getString(DatabaseConsts.UserTable.COLUMN_U_PASSWORD))
                .sex(resultSet.getString(DatabaseConsts.UserTable.COLUMN_U_SEX))
                .status(resultSet.getString(DatabaseConsts.UserTable.COLUMN_U_STATUS))
                .code(resultSet.getString(DatabaseConsts.UserTable.COLUMN_U_CODE))
                .email(resultSet.getString(DatabaseConsts.UserTable.COLUMN_U_EMAIL))
                .role(resultSet.getInt(DatabaseConsts.UserTable.COLUMN_U_ROLE))
                .build();

        DruidUtils.release(resultSet, pstmt, conn);
        return user;
    }

    @Override
    public int insertUser(User user) throws SQLException {

        Connection conn = DruidUtils.getConnection();

        String sql="insert into user(u_name,u_password,u_sex,u_status,u_code,u_email,u_role )" +
                "value(?,?,?,?,?,?,?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, user.getUsername());
        pstmt.setString(2, user.getPassword());
        pstmt.setString(3, user.getSex());
        pstmt.setString(4, user.getStatus());
        pstmt.setString(5, user.getCode());
        pstmt.setString(6, user.getEmail());
        pstmt.setInt(7, user.getRole());

        int row = pstmt.executeUpdate();

        DruidUtils.release(null, pstmt, conn);

        return row;
    }

    @Override
    public User selectUserByCode(String code) throws SQLException {
        Connection conn = DruidUtils.getConnection();

        String sql="select u_id, u_name, u_password" +
                ", u_sex, u_status , u_code, u_email" +
                ", u_role from user where u_code =?";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, code);

        ResultSet resultSet = pstmt.executeQuery();

        if (!resultSet.next()) {
            return null;
        }

        User user = User.builder()
                .uId(resultSet.getInt(DatabaseConsts.UserTable.COLUMN_U_ID))
                .username(resultSet.getString(DatabaseConsts.UserTable.COLUMN_U_NAME))
                .password(resultSet.getString(DatabaseConsts.UserTable.COLUMN_U_PASSWORD))
                .sex(resultSet.getString(DatabaseConsts.UserTable.COLUMN_U_SEX))
                .status(resultSet.getString(DatabaseConsts.UserTable.COLUMN_U_STATUS))
                .code(resultSet.getString(DatabaseConsts.UserTable.COLUMN_U_CODE))
                .email(resultSet.getString(DatabaseConsts.UserTable.COLUMN_U_EMAIL))
                .role(resultSet.getInt(DatabaseConsts.UserTable.COLUMN_U_ROLE))
                .build();

        DruidUtils.release(resultSet, pstmt, conn);

        return user;
    }

    @Override
    public int updateStatusByUid(int uId) throws SQLException {
        Connection conn = DruidUtils.getConnection();

        String sql="update user set u_status = ? where u_id= ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, SystemConstants.USER_ACTIVE);
        pstmt.setInt(2, uId);

        int row = pstmt.executeUpdate();

        DruidUtils.release(null, pstmt, conn);

        return row;
    }
}
