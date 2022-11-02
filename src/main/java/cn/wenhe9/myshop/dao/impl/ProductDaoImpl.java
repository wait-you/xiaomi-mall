package cn.wenhe9.myshop.dao.impl;

import cn.wenhe9.myshop.dao.ProductDao;
import cn.wenhe9.myshop.domain.constant.DatabaseConsts;
import cn.wenhe9.myshop.domain.entity.Product;
import cn.wenhe9.myshop.utils.DruidUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 产品数据访问接口实现类
 * @author: DuJinliang
 * @create: 2022/11/1
 */
public class ProductDaoImpl implements ProductDao {
    @Override
    public long selectCountByTid(String tid) throws SQLException {
        Connection conn = DruidUtils.getConnection();

        String sql = "select count(*) as total from product where t_id=?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, tid);

        ResultSet resultSet = pstmt.executeQuery();

        int count = 0;

        if (resultSet.next()) {
            count = resultSet.getInt("total");
        }

        DruidUtils.release(resultSet, pstmt, conn);
        return count;
    }

    @Override
    public List<Product> selectProductByPage(int currentPage, int pageSize, String tid) throws SQLException {
        Connection conn = DruidUtils.getConnection();

        String sql="select p_id, t_id, p_name, p_time," +
                "p_image, p_state, p_info, p_price" +
                " from product where t_id=? limit ?,?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, tid);
        pstmt.setInt(2, currentPage);
        pstmt.setInt(3, pageSize);

        ResultSet resultSet = pstmt.executeQuery();

        List<Product> productList = new ArrayList<>();
        while (resultSet.next()) {
            productList.add(
                    Product.builder()
                            .pid(resultSet.getInt(DatabaseConsts.ProductTable.COLUMN_P_ID))
                            .tid(resultSet.getInt(DatabaseConsts.ProductTable.COLUMN_T_ID))
                            .name(resultSet.getString(DatabaseConsts.ProductTable.COLUMN_P_NAME))
                            .time(resultSet.getDate(DatabaseConsts.ProductTable.COLUMN_P_TIME))
                            .image(resultSet.getString(DatabaseConsts.ProductTable.COLUMN_P_IMAGE).replace(" ", ""))
                            .state(resultSet.getInt(DatabaseConsts.ProductTable.COLUMN_P_STATE))
                            .info(resultSet.getString(DatabaseConsts.ProductTable.COLUMN_P_INFO))
                            .price(resultSet.getBigDecimal(DatabaseConsts.ProductTable.COLUMN_P_PRICE))
                            .build()
            );
        }

        DruidUtils.release(resultSet, pstmt, conn);

        return productList;
    }

    @Override
    public Product selectProductByPid(String pid) throws SQLException {
        Connection conn = DruidUtils.getConnection();

        String sql="select p_id, t_id, p_name, p_time," +
                "p_image, p_state, p_info, p_price" +
                " from product where p_id=?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, pid);

        ResultSet resultSet = pstmt.executeQuery();

        Product product = null;
        if (resultSet.next()) {
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
        }

        return product;
    }
}
