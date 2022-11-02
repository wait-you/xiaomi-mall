package cn.wenhe9.myshop.controller;

import cn.wenhe9.myshop.dao.impl.AddressDaoImpl;
import cn.wenhe9.myshop.domain.entity.Address;
import cn.wenhe9.myshop.domain.entity.User;
import cn.wenhe9.myshop.service.AddressService;
import cn.wenhe9.myshop.service.impl.AddressServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @description: 地址控制器类
 * @author: DuJinliang
 * @create: 2022/11/1
 */

@WebServlet("/address")
public class AddressController extends BaseServlet {

    private AddressService addressService;

    public AddressController() {
        this.addressService = new AddressServiceImpl(new AddressDaoImpl());
    }

    public void show(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {

        //接下来完成查询当前用户有哪些地址
        //判断是否登录
        HttpSession session=request.getSession();
        User user=(User) session.getAttribute("loginUser");
        if (user==null){
            session.setAttribute("msg","需要先登录");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }
        //获取当前用户的id
        int uid = user.getUid();

        //根据uid查询对应用户的地址中存储了哪些地址
        //调用业务逻辑
        List<Address> addresses = addressService.findAddressByUid(uid);
        //将我们查询到的地址信息转发到共享域中
        request.setAttribute("list",addresses);

        //首先，完成功能之后我们是将页面跳转到地址的显示页面
        request.getRequestDispatcher("/self_info.jsp").forward(request, response);
    }
    public void add(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException, SQLException, ServletException, IOException {

        //1.获取到我们输入的地址信息
        Map<String, String[]> map = request.getParameterMap();

        Address address = Address.builder().build();
        BeanUtils.populate(address,map);

        //调用业务逻辑进行地址的添加(保存)
        addressService.saveAddress(address);
        //转发到展示方法
        request.getRequestDispatcher("/address?method=show").forward(request, response);
    }
}