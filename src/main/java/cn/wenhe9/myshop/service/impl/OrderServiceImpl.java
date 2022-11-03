package cn.wenhe9.myshop.service.impl;

import cn.wenhe9.myshop.dao.OrderDao;
import cn.wenhe9.myshop.dao.impl.OrderDaoImpl;
import cn.wenhe9.myshop.domain.entity.Orders;
import cn.wenhe9.myshop.service.OrderService;

import java.sql.SQLException;
import java.util.List;

/**
 * @description: 订单业务接口实现类
 * @author: DuJinliang
 * @create: 2022/11/2
 */
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;

    public OrderServiceImpl() {
        this.orderDao = new OrderDaoImpl();
    }

    @Override
    public int createOrder(Orders order) throws SQLException {
        return orderDao.createOrder(order);
    }

    @Override
    public List<Orders> findAllOrder(int uId) throws SQLException {
        return orderDao.findAllOrder(uId);
    }
}
