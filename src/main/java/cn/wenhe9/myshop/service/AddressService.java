package cn.wenhe9.myshop.service;

import cn.wenhe9.myshop.domain.entity.Address;

import java.sql.SQLException;
import java.util.List;

/**
 * @description: 地址业务接口
 * @author: DuJinliang
 * @create: 2022/11/1
 */
public interface AddressService {

    /**
     * 根据uid查询地址
     */
    List<Address> findAddressByUid(int uid) throws SQLException;

    /**
     * 保存新的地址
     */
    void saveAddress(Address address) throws SQLException;
}
