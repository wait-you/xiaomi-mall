package cn.wenhe9.myshop.service.impl;

import cn.wenhe9.myshop.dao.AddressDao;
import cn.wenhe9.myshop.dao.impl.AddressDaoImpl;
import cn.wenhe9.myshop.domain.entity.Address;
import cn.wenhe9.myshop.service.AddressService;

import java.sql.SQLException;
import java.util.List;

/**
 * @description: 地址业务接口实现类
 * @author: DuJinliang
 * @create: 2022/11/1
 */
public class AddressServiceImpl implements AddressService {

    private AddressDao addressDao;

    public AddressServiceImpl(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    @Override
    public List<Address> findAddressByUid(int uid) throws SQLException {
        return addressDao.selectAddressByUid(uid);
    }

    @Override
    public void saveAddress(Address address) throws SQLException {
        addressDao.insertAddress(address);
    }
}
