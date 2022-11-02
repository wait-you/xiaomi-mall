package cn.wenhe9.myshop.dao;

import cn.wenhe9.myshop.domain.entity.Product;

import java.sql.SQLException;
import java.util.List;

/**
 * @description: 产品数据访问层
 * @author: DuJinliang
 * @create: 2022/11/1
 */
public interface ProductDao {
    /**
     * 根据tid查询总量
     */
    long selectCountByTid(String tid) throws SQLException;

    /**
     * 根据tid分页查询产品
     */
    List<Product> selectProductByPage(int currentPage, int pageSize, String tid) throws SQLException;

    /**
     * 根据pid查询产品
     */
    Product selectProductByPid(String pid) throws SQLException;
}
