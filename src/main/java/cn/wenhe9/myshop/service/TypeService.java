package cn.wenhe9.myshop.service;

import cn.wenhe9.myshop.domain.entity.Type;

import java.sql.SQLException;
import java.util.List;

/**
 * @description: 类别业务接口
 * @author: DuJinliang
 * @create: 2022/11/1
 */
public interface TypeService {

    /**
     * 查询所有的类别
     */
    List<Type> findAll() throws SQLException;
}
