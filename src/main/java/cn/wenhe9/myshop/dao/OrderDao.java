package cn.wenhe9.myshop.dao;

import cn.wenhe9.myshop.domain.entity.Orders;

import java.sql.SQLException;
import java.util.List;

/**
 * @description: 订单数据访问层
 * @author: DuJinliang
 * @create: 2022/11/2
 */
public interface OrderDao {
    /**
     * 创建订单
     */
    int createOrder(Orders order) throws SQLException;

    /**
     * 查询所有订单
     */
    List<Orders> findAllOrder(int uId) throws SQLException;
}
