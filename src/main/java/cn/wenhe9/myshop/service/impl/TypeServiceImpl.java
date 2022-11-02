package cn.wenhe9.myshop.service.impl;

import cn.wenhe9.myshop.dao.TypeDao;
import cn.wenhe9.myshop.service.TypeService;
import cn.wenhe9.myshop.domain.entity.Type;

import java.sql.SQLException;
import java.util.List;

/**
 * @description: 类别服务实现类
 * @author: DuJinliang
 * @create: 2022/11/1
 */
public class TypeServiceImpl implements TypeService {

    private TypeDao typeDao;

    public TypeServiceImpl(TypeDao typeDao) {
        this.typeDao = typeDao;
    }

    @Override
    public List<Type> findAll() throws SQLException {
        return typeDao.selectAll();
    }
}
