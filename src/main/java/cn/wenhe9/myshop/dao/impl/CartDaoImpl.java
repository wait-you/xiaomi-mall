package cn.wenhe9.myshop.dao.impl;

import cn.wenhe9.myshop.dao.CartDao;
import cn.wenhe9.myshop.domain.constant.DatabaseConsts;
import cn.wenhe9.myshop.domain.entity.Cart;
import cn.wenhe9.myshop.domain.entity.Product;
import cn.wenhe9.myshop.utils.DruidUtils;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 购物车数据访问实现类
 * @author: DuJinliang
 * @create: 2022/11/1
 */
public class CartDaoImpl implements CartDao {
    @Override
    public Cart hasCart(int uid, String pid) throws SQLException, InvocationTargetException, IllegalAccessException {

        Connection conn = DruidUtils.getConnection();

        String sql="select p.p_name, p.p_id, p.t_id," +
                "p.p_time, p.p_image, p.p_state," +
                "p.p_info, p.p_price, c.c_id, c.u_id, c.c_count," +
                "c.c_num from product p join cart c on p.p_id =c.p_id where c.u_id =? and c.p_id=?;";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, uid);
        pstmt.setString(2, pid);

        ResultSet resultSet = pstmt.executeQuery();

        Cart cart = null;
        Product product = null;

        if (resultSet.next()) {
            cart = Cart.builder()
                    .cid(resultSet.getInt(DatabaseConsts.CartTable.COLUMN_C_ID))
                    .uid(resultSet.getInt(DatabaseConsts.CartTable.COLUMN_U_ID))
                    .pid(resultSet.getInt(DatabaseConsts.CartTable.COLUMN_P_ID))
                    .num(resultSet.getInt(DatabaseConsts.CartTable.COLUMN_C_NUM))
                    .count(resultSet.getBigDecimal(DatabaseConsts.CartTable.COLUMN_C_COUNT))
                    .build();
            product = Product.builder()
                    .pid(resultSet.getInt(DatabaseConsts.ProductTable.COLUMN_P_ID))
                    .tid(resultSet.getInt(DatabaseConsts.ProductTable.COLUMN_T_ID))
                    .name(resultSet.getString(DatabaseConsts.ProductTable.COLUMN_P_NAME))
                    .time(resultSet.getDate(DatabaseConsts.ProductTable.COLUMN_P_TIME))
                    .image(resultSet.getString(DatabaseConsts.ProductTable.COLUMN_P_IMAGE).replace(" ", ""))
                    .state(resultSet.getInt(DatabaseConsts.ProductTable.COLUMN_P_STATE))
                    .info(resultSet.getString(DatabaseConsts.ProductTable.COLUMN_P_INFO))
                    .price(resultSet.getBigDecimal(DatabaseConsts.ProductTable.COLUMN_P_PRICE))
                    .build();
            cart.setProduct(product);
        }

        DruidUtils.release(resultSet, pstmt, conn);

        return cart;
    }

    @Override
    public void updateCart(Cart cart) throws SQLException {

        Connection conn = DruidUtils.getConnection();

        String sql="update cart set c_num=?,c_count=? where c_id=?;";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, cart.getNum());
        pstmt.setBigDecimal(2, cart.getCount());
        pstmt.setInt(3, cart.getCid());

        pstmt.executeUpdate();

        DruidUtils.release(null, pstmt, conn);
    }

    @Override
    public void insertCart(Cart cart) throws SQLException {
        Connection conn = DruidUtils.getConnection();

        String sql="insert into cart (u_id,p_id,c_num,c_count) value(?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, cart.getUid());
        pstmt.setInt(2, cart.getPid());
        pstmt.setInt(3, cart.getNum());
        pstmt.setBigDecimal(4, cart.getCount());

        pstmt.executeUpdate();

        DruidUtils.release(null, pstmt, conn);
    }

    @Override
    public List<Cart> selectCartByUid(int uid) throws SQLException, InvocationTargetException, IllegalAccessException {
        Connection conn = DruidUtils.getConnection();


        String sql="select p.p_name, p.p_id, p.t_id," +
                "p.p_time, p.p_image, p.p_state," +
                "p.p_info, p.p_price, c.c_id, c.u_id, c.c_count," +
                "c.c_num from product p join cart c on p.p_id =c.p_id where c.u_id =?;";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, uid);

        ResultSet resultSet = pstmt.executeQuery();

        List<Cart> cartList = new ArrayList<>();
        Cart cart = null;
        Product product = null;
        while (resultSet.next()) {
            cart = Cart.builder()
                    .cid(resultSet.getInt(DatabaseConsts.CartTable.COLUMN_C_ID))
                    .uid(resultSet.getInt(DatabaseConsts.CartTable.COLUMN_U_ID))
                    .pid(resultSet.getInt(DatabaseConsts.CartTable.COLUMN_P_ID))
                    .num(resultSet.getInt(DatabaseConsts.CartTable.COLUMN_C_NUM))
                    .count(resultSet.getBigDecimal(DatabaseConsts.CartTable.COLUMN_C_COUNT))
                    .build();
            product = Product.builder()
                    .pid(resultSet.getInt(DatabaseConsts.ProductTable.COLUMN_P_ID))
                    .tid(resultSet.getInt(DatabaseConsts.ProductTable.COLUMN_T_ID))
                    .name(resultSet.getString(DatabaseConsts.ProductTable.COLUMN_P_NAME))
                    .time(resultSet.getDate(DatabaseConsts.ProductTable.COLUMN_P_TIME))
                    .image(resultSet.getString(DatabaseConsts.ProductTable.COLUMN_P_IMAGE).replace(" ", ""))
                    .state(resultSet.getInt(DatabaseConsts.ProductTable.COLUMN_P_STATE))
                    .info(resultSet.getString(DatabaseConsts.ProductTable.COLUMN_P_INFO))
                    .price(resultSet.getBigDecimal(DatabaseConsts.ProductTable.COLUMN_P_PRICE))
                    .build();
            cart.setProduct(product);
            cartList.add(cart);
        }

        DruidUtils.release(resultSet, pstmt, conn);
        return cartList;
    }

    @Override
    public void deleteCartByCid(String cid) throws SQLException {
        Connection conn = DruidUtils.getConnection();

        String sql="delete from cart where c_id=?;";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, cid);

        pstmt.executeUpdate();

        DruidUtils.release(null, pstmt, conn);
    }

    @Override
    public void updateByCid(BigDecimal count, BigDecimal cnumbig, String cid) throws SQLException {
        Connection conn = DruidUtils.getConnection();

        String sql="update cart set c_count=?,c_num=?  where c_id=?;";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setBigDecimal(1, count);
        pstmt.setBigDecimal(2, cnumbig);
        pstmt.setString(3, cid);

        pstmt.executeUpdate();

        DruidUtils.release(null, pstmt, conn);
    }

    @Override
    public void deleteCartByUid(String uid) throws SQLException {
        Connection conn = DruidUtils.getConnection();

        String sql="delete from cart where u_id=?;";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, uid);

        pstmt.executeUpdate();

        DruidUtils.release(null, pstmt, conn);
    }

}
