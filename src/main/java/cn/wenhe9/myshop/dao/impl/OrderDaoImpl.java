package cn.wenhe9.myshop.dao.impl;

import cn.wenhe9.myshop.dao.OrderDao;
import cn.wenhe9.myshop.domain.constant.DatabaseConsts;
import cn.wenhe9.myshop.domain.entity.Address;
import cn.wenhe9.myshop.domain.entity.Orders;
import cn.wenhe9.myshop.utils.DruidUtils;
import cn.wenhe9.myshop.utils.MD5Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 订单数据访问实现类
 * @author: DuJinliang
 * @create: 2022/11/2
 */
public class OrderDaoImpl implements OrderDao {
    @Override
    public int createOrder(Orders order) throws SQLException {
        Connection conn = DruidUtils.getConnection();

        String sql="insert into orders (o_id, u_id ,a_id,o_count,o_time,o_state) value(?,?,?,?,?,?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, order.getOid());
        pstmt.setInt(2, order.getUid());
        pstmt.setInt(3, order.getAid());
        pstmt.setBigDecimal(4, order.getCount());
        pstmt.setDate(5, new Date(System.currentTimeMillis()));
        pstmt.setInt(6, order.getState());

        int row = pstmt.executeUpdate();

        DruidUtils.release(null, pstmt, conn);

        return row;
    }

    @Override
    public List<Orders> findAllOrder(int uId) throws SQLException {
        Connection conn = DruidUtils.getConnection();


        String sql="select a.a_id, a.u_id, a.a_name," +
                "a.a_phone, a.a_detail, a.a_state," +
                "o.o_id, o.u_id, o.a_id, o.o_count," +
                "o.o_time, o.o_state  from orders o join address a on o.a_id = a.a_id where o.u_id =?;";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, uId);

        ResultSet resultSet = pstmt.executeQuery();
        List<Orders> list = new ArrayList<>();
        while (resultSet.next()) {
            Orders orders = Orders.builder()
                    .oid(resultSet.getString(DatabaseConsts.OrderTable.COLUMN_O_ID))
                    .aid(resultSet.getInt(DatabaseConsts.OrderTable.COLUMN_A_ID))
                    .uid(resultSet.getInt(DatabaseConsts.OrderTable.COLUMN_U_ID))
                    .count(resultSet.getBigDecimal(DatabaseConsts.OrderTable.COLUMN_O_COUNT))
                    .time(resultSet.getDate(DatabaseConsts.OrderTable.COLUMN_O_TIME))
                    .state(resultSet.getInt(DatabaseConsts.OrderTable.COLUMN_O_STATE))
                    .build();

            Address address = address = Address.builder()
                    .aid(resultSet.getInt(DatabaseConsts.AddressTable.COLUMN_A_ID))
                    .uid(resultSet.getInt(DatabaseConsts.AddressTable.COLUMN_U_ID))
                    .name(resultSet.getString(DatabaseConsts.AddressTable.COLUMN_A_NAME))
                    .phone(resultSet.getString(DatabaseConsts.AddressTable.COLUMN_A_PHONE))
                    .detail(resultSet.getString(DatabaseConsts.AddressTable.COLUMN_A_DETAIL))
                    .state(resultSet.getInt(DatabaseConsts.AddressTable.COLUMN_A_STATE))
                    .build();

            orders.setAddress(address);
            list.add(orders);
        }

        DruidUtils.release(resultSet, pstmt, conn);

        return list;
    }
}
