package cn.wenhe9.myshop.controller;

import cn.wenhe9.myshop.dao.impl.AddressDaoImpl;
import cn.wenhe9.myshop.dao.impl.CartDaoImpl;
import cn.wenhe9.myshop.dao.impl.ProductDaoImpl;
import cn.wenhe9.myshop.domain.entity.*;
import cn.wenhe9.myshop.service.AddressService;
import cn.wenhe9.myshop.service.CartService;
import cn.wenhe9.myshop.service.OrderService;
import cn.wenhe9.myshop.service.ProductService;
import cn.wenhe9.myshop.service.impl.AddressServiceImpl;
import cn.wenhe9.myshop.service.impl.CartServiceImpl;
import cn.wenhe9.myshop.service.impl.OrderServiceImpl;
import cn.wenhe9.myshop.service.impl.ProductServiceImpl;
import cn.wenhe9.myshop.utils.MD5Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @description: 订单控制器类
 * @author: DuJinliang
 * @create: 2022/11/2
 */
@WebServlet("/order")
public class OrderController extends BaseServlet{

    private CartService cartService;

    private AddressService addressService;

    private OrderService orderService;

    public OrderController() {
        this.cartService = new CartServiceImpl(new CartDaoImpl(), new ProductDaoImpl());
        this.addressService = new AddressServiceImpl(new AddressDaoImpl());
        this.orderService = new OrderServiceImpl();
    }

    public void preView(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, InvocationTargetException, IllegalAccessException, ServletException, IOException {
        User user = (User) request.getSession().getAttribute("loginUser");

        if (user == null) {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }

        List<Cart> cartList = cartService.findAll(user.getUid());

        List<Address> addresses = addressService.findAddressByUid(user.getUid());

        request.setAttribute("addressList", addresses);
        request.setAttribute("cartList", cartList);

        request.getRequestDispatcher("/order.jsp").forward(request, response);
    }

    public void show(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession();
        Object loginUser = session.getAttribute("loginUser");
        if (Objects.isNull(loginUser)) {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        User user = (User) loginUser;
        int userId = user.getUid();
        List<Orders> listOrder = orderService.findAllOrder(userId);
        request.setAttribute("ordersList", listOrder);
        request.getRequestDispatcher("orderList.jsp").forward(request, response);
    }

    public void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String uid = request.getParameter("uid");
        String aid = request.getParameter("aid");
        String sum = request.getParameter("sum");

        Orders orders = Orders.builder().build();
        String oid = MD5Utils.md5(String.valueOf(System.currentTimeMillis()));
        orders.setOid(oid);
        orders.setUid(Integer.parseInt(uid));
        orders.setAid(Integer.parseInt(aid));
        orders.setState(0);
        orders.setCount(BigDecimal.valueOf(Double.parseDouble(sum)));
        orders.setTime(new Date());

        orderService.createOrder(orders);

        request.setAttribute("id", oid);
        request.setAttribute("money", "￥" + sum);

        request.getRequestDispatcher("orderSuccess.jsp").forward(request, response);
    }
}
