package cn.wenhe9.myshop.controller;

import cn.wenhe9.myshop.dao.impl.TypeDaoImpl;
import cn.wenhe9.myshop.domain.entity.Type;
import cn.wenhe9.myshop.service.TypeService;
import cn.wenhe9.myshop.service.impl.TypeServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @description: 类别接口
 * @author: DuJinliang
 * @create: 2022/11/1
 */
@WebServlet("/type")
public class TypeController extends BaseServlet {

    private TypeService typeService;

    public TypeController() {
        this.typeService = new TypeServiceImpl(new TypeDaoImpl());
    }

    public void findAll(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        List<Type> typeList = typeService.findAll();
        //将集合转化成对应的gson数据
         writeValue(typeList, response);
    }

}