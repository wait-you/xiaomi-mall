package cn.wenhe9.myshop.service;

import cn.wenhe9.myshop.domain.entity.PageBean;
import cn.wenhe9.myshop.domain.entity.Product;

import java.sql.SQLException;

/**
 * @description: 产品业务接口
 * @author: DuJinliang
 * @create: 2022/11/1
 */
public interface ProductService {

    /**
     * 分页查询
     */
    PageBean<Product> findByPage(String tid, int page, int pageSize) throws SQLException;

    /**
     * 根据pid查询产品
     */
    Product findProductByPid(String pid) throws SQLException;
}
