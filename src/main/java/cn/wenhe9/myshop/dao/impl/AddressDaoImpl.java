package cn.wenhe9.myshop.dao.impl;

import cn.wenhe9.myshop.dao.AddressDao;
import cn.wenhe9.myshop.domain.constant.DatabaseConsts;
import cn.wenhe9.myshop.domain.entity.Address;
import cn.wenhe9.myshop.utils.DruidUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 地址数据访问接口实现类
 * @author: DuJinliang
 * @create: 2022/11/1
 */
public class AddressDaoImpl implements AddressDao {
    @Override
    public List<Address> selectAddressByUid(int uid) throws SQLException {
        Connection conn = DruidUtils.getConnection();

        String sql="select a_id ,u_id ,a_name ," +
                "a_phone ,a_detail , a_state from address a where u_id=?;";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, uid);

        ResultSet resultSet = pstmt.executeQuery();
        Address address = null;
        List<Address> list = new ArrayList<>();
        while (resultSet.next()) {
            address = Address.builder()
                    .aid(resultSet.getInt(DatabaseConsts.AddressTable.COLUMN_A_ID))
                    .uid(resultSet.getInt(DatabaseConsts.AddressTable.COLUMN_U_ID))
                    .name(resultSet.getString(DatabaseConsts.AddressTable.COLUMN_A_NAME))
                    .phone(resultSet.getString(DatabaseConsts.AddressTable.COLUMN_A_PHONE))
                    .detail(resultSet.getString(DatabaseConsts.AddressTable.COLUMN_A_DETAIL))
                    .state(resultSet.getInt(DatabaseConsts.AddressTable.COLUMN_A_STATE))
                    .build();
            list.add(address);
        }

        DruidUtils.release(resultSet, pstmt, conn);

        return list;
    }

    @Override
    public void insertAddress(Address address) throws SQLException {
        Connection conn = DruidUtils.getConnection();

        String sql="insert into address (u_id ,a_name,a_phone,a_detail,a_state) value(?,?,?,?,?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, address.getUid());
        pstmt.setString(2, address.getName());
        pstmt.setString(3, address.getPhone());
        pstmt.setString(4, address.getDetail());
        pstmt.setInt(5, address.getState());

        pstmt.executeUpdate();

        DruidUtils.release(null, pstmt, conn);
    }


    @Override
    public int updateAddress(Address address) throws SQLException {
        Connection conn = DruidUtils.getConnection();

        String sql="update address set a_name = ?, a_phone = ?, a_detail = ? where a_id = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, address.getName());
        pstmt.setString(2, address.getPhone());
        pstmt.setString(3, address.getDetail());
        pstmt.setInt(4, address.getAid());

        int row = pstmt.executeUpdate();

        DruidUtils.release(null, pstmt, conn);

        return row;
    }

    @Override
    public int setDefault(String aId) throws SQLException {
        Connection conn = DruidUtils.getConnection();

        String sql = "update address set a_state=1 where a_id=?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, aId);

        int row = pstmt.executeUpdate();

        DruidUtils.release(null, pstmt, conn);

        return row;
    }

    @Override
    public int removeAddress(String aId) throws SQLException {
        Connection conn = DruidUtils.getConnection();

        String sql = "delete from address where a_id=?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, aId);

        int row = pstmt.executeUpdate();

        DruidUtils.release(null, pstmt, conn);

        return row;
    }
}
