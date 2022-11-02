package cn.wenhe9.myshop.service.impl;

import cn.wenhe9.myshop.dao.ProductDao;
import cn.wenhe9.myshop.dao.impl.ProductDaoImpl;
import cn.wenhe9.myshop.domain.entity.PageBean;
import cn.wenhe9.myshop.domain.entity.Product;
import cn.wenhe9.myshop.service.ProductService;

import java.sql.SQLException;
import java.util.List;

/**
 * @description: 产品服务接口实现类
 * @author: DuJinliang
 * @create: 2022/11/1
 */
public class ProductServiceImpl implements ProductService {

    private ProductDao productDao;

    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public PageBean<Product> findByPage(String tid, int currentPage, int pageSize) throws SQLException {
        //查询匹配tid的数据总量
        long count= productDao.selectCountByTid(tid);

        //分页查询匹配tid的数据
        List<Product> list = productDao.selectProductByPage(currentPage,pageSize,tid);

        //返回PageBean
        return new PageBean<Product>(list, currentPage, pageSize, count);
    }

    @Override
    public Product findProductByPid(String pid) throws SQLException {
        return productDao.selectProductByPid(pid);
    }
}
