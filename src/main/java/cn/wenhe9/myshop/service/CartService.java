package cn.wenhe9.myshop.service;

import cn.wenhe9.myshop.domain.entity.Cart;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @description: 购物车业务接口
 * @author: DuJinliang
 * @create: 2022/11/1
 */
public interface CartService {
    /**
     * 创建新的购物车
     */
    void createCart(int uid, String pid) throws SQLException, InvocationTargetException, IllegalAccessException;

    /**
     * 查询所有
     */
    List<Cart> findAll(int uid) throws IllegalAccessException, SQLException, InvocationTargetException;

    /**
     * 根据cid删除
     */
    void deleteCartByCid(String cid) throws SQLException;

    /**
     * 根据cid更新
     */
    void updateCartByCid(String cid, String price, String cnum) throws SQLException;

    /**
     * 清空购物车
     */
    void clearCart(String uid) throws SQLException;
}
