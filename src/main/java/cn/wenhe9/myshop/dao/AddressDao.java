package cn.wenhe9.myshop.dao;

import cn.wenhe9.myshop.domain.entity.Address;
import cn.wenhe9.myshop.utils.DruidUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @description: 地址数据访问接口
 * @author: DuJinliang
 * @create: 2022/11/1
 */
public interface AddressDao {
    /**
     * 根据uid查询地址
     */
    List<Address> selectAddressByUid(int uid) throws SQLException;

    /**
     * 保存新的地址
     */
    void insertAddress(Address address) throws SQLException;

    /**
     * 删除地址
     */
    int removeAddress(String aId) throws SQLException;


    /**
     * 更新地址
     */
    int updateAddress(Address address) throws SQLException;

    /**
     * 设置成默认地址
     */
    int setDefault(String aId) throws SQLException;
}
