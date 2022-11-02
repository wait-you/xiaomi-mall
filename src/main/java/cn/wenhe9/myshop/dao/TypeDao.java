package cn.wenhe9.myshop.dao;

import cn.wenhe9.myshop.domain.entity.Type;

import java.sql.SQLException;
import java.util.List;

/**
 * @description: 类型数据访问层
 * @author: DuJinliang
 * @create: 2022/11/1
 */
public interface TypeDao {
    /**
     * 查询所有类别
     */
    List<Type> selectAll() throws SQLException;
}
