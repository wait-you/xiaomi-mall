package cn.wenhe9.myshop.dao;

import cn.wenhe9.myshop.domain.entity.Cart;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * @description: 购物车数据访问接口
 * @author: DuJinliang
 * @create: 2022/11/1
 */
public interface CartDao {
    /**
     * 查询是否有该产品
     */
    Cart hasCart(int uid, String pid) throws SQLException, InvocationTargetException, IllegalAccessException;

    /**
     * 更新购物车
     */
    void updateCart(Cart cart) throws SQLException;

    /**
     * 新增购物车
     */
    void insertCart(Cart cart) throws SQLException;

    /**
     * 根据uid查询购物车
     */
    List<Cart> selectCartByUid(int uid) throws SQLException, InvocationTargetException, IllegalAccessException;

    /**
     * 根据cid删除
     */
    void deleteCartByCid(String cid) throws SQLException;

    /**
     * 根据cid更新
     */
    void updateByCid(BigDecimal count, BigDecimal cnumbig, String cid) throws SQLException;

    /**
     * 根据uid删除
     */
    void deleteCartByUid(String uid) throws SQLException;
}
