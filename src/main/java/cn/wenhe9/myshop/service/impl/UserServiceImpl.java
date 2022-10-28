package cn.wenhe9.myshop.service.impl;

import cn.wenhe9.myshop.dao.UserDao;
import cn.wenhe9.myshop.dao.impl.UserDaoImpl;
import cn.wenhe9.myshop.domain.constant.SystemConstants;
import cn.wenhe9.myshop.domain.entity.User;
import cn.wenhe9.myshop.service.UserService;
import cn.wenhe9.myshop.utils.EmailUtils;

import javax.jws.WebService;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @description: 用户业务接口实现类
 * @author: DuJinliang
 * @create: 2022/10/28
 */

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserServiceImpl() {
        userDao = new UserDaoImpl();
    }

    @Override
    public boolean checkUsernameExist(String username) throws SQLException {
        User user = userDao.selectUserByUname(username);

        if (Objects.isNull(user)) {
            return false;
        }

        return true;
    }

    @Override
    public int registerUser(User user) throws SQLException {
        int row = userDao.insertUser(user);

        Thread thread = new Thread(() -> EmailUtils.sendEmail(user));
        thread.start();

        return row;
    }

    @Override
    public User login(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public int activeUser(String code) throws SQLException {
        UserDao userDao =new UserDaoImpl();
        //1.根据激活码查找用户
        User user=userDao.selectUserByCode(code);

        if (user == null){
            //0激活失败
            return SystemConstants.ACTIVE_FAIL;
        }

        //2.判断用户是否激活
        if (user.getStatus().equals(SystemConstants.USER_ACTIVE)) {
            return SystemConstants.ACTIVE_ALREADY;
        }

        //3.进行激活操作
        int i = userDao.updateStatusByUid(user.getUId());

        if (i>0){
            return SystemConstants.ACTIVE_SUCCESS;
        }
        return SystemConstants.ACTIVE_FAIL;
    }
}
