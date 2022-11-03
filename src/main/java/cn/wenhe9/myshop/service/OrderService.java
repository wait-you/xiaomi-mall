package cn.wenhe9.myshop.service;

import cn.wenhe9.myshop.domain.entity.Orders;

import java.sql.SQLException;
import java.util.List;

/**
 * @description: 订单业务接口
 * @author: DuJinliang
 * @create: 2022/11/2
 */
public interface OrderService {

    /**
     * 创建订单
     */
    int createOrder(Orders order) throws SQLException;

    /**
     * 查询所有订单
     */
    List<Orders> findAllOrder(int uId) throws SQLException;
}
