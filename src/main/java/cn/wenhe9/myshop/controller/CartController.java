package cn.wenhe9.myshop.controller;

import cn.wenhe9.myshop.dao.impl.CartDaoImpl;
import cn.wenhe9.myshop.dao.impl.ProductDaoImpl;
import cn.wenhe9.myshop.domain.entity.Cart;
import cn.wenhe9.myshop.domain.entity.User;
import cn.wenhe9.myshop.service.CartService;
import cn.wenhe9.myshop.service.impl.CartServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @description: 购物车控制类
 * @author: DuJinliang
 * @create: 2022/11/1
 */

@WebServlet("/cart")
public class CartController extends BaseServlet {

    private CartService cartService;

    public CartController() {
        this.cartService = new CartServiceImpl(new CartDaoImpl(), new ProductDaoImpl());
    }

    public void create(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, SQLException, InvocationTargetException, ServletException, IOException {

        HttpSession session = request.getSession();
        //我们登录成功之后是将我们的信息存入到loginUser
        User user= (User) session.getAttribute("loginUser");
        if (user ==null){
            session.setAttribute("msg","添加购物车必须先登录！");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        //1.获取到对应商品id和用户id
        int uid = user.getUid();
        //商品id通过参数传递过来的
        String pid=request.getParameter("pid");

        //3.调用对应业务逻辑

        cartService.createCart(uid,pid);

        //我们完成后需要跳转到添加成功页面
        request.getRequestDispatcher("/cartSuccess.jsp").forward(request, response);
    }
    public void show(HttpServletRequest request,HttpServletResponse response) throws IllegalAccessException, SQLException, InvocationTargetException, ServletException, IOException {

        HttpSession session = request.getSession();
        //我们登录成功之后是将我们的信息存入到loginUser
        User user= (User) session.getAttribute("loginUser");
        if (user ==null){
            session.setAttribute("msg","添加购物车必须先登录！");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        //1.获取请求参数
        int uid=user.getUid();

        //2.调用对应的业务逻辑进行数据查询（查询当前用户有哪些购物车信息）
        //定义一个查询当前用户购物车内的所有信息
        List<Cart> list=cartService.findAll(uid);

        //将我们获取到的信息转发到共享域中
        request.setAttribute("list",list);
        request.getRequestDispatcher("/cart.jsp").forward(request, response);
    }

    public void delete(HttpServletRequest request,HttpServletResponse response) throws SQLException, ServletException, IOException {
        HttpSession session = request.getSession();
        //我们登录成功之后是将我们的信息存入到loginUser
        User user= (User) session.getAttribute("loginUser");
        if (user ==null){
            session.setAttribute("msg","添加购物车必须先登录！");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        //1.获取请求参数cid
        String cid=request.getParameter("cid");

        //调用对应的业务逻辑
        cartService.deleteCartByCid(cid);

        //3.转发到展示的处理方法当中
        request.getRequestDispatcher("/cart?method=show").forward(request, response);
    }
    public void update(HttpServletRequest request,HttpServletResponse response) throws SQLException, ServletException, IOException {
        HttpSession session = request.getSession();
        //我们登录成功之后是将我们的信息存入到loginUser
        User user= (User) session.getAttribute("loginUser");
        if (user ==null){
            session.setAttribute("msg","添加购物车必须先登录！");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        //1.获取请求参数
        String cid=request.getParameter("cid");
        //商品的单价
        String price=request.getParameter("price");
        //修改后的数量
        String cnum=request.getParameter("cnum");

        //2.调用业务逻辑进行修改
        cartService.updateCartByCid(cid,price,cnum);

        //3.转发到展示的方法中
        request.getRequestDispatcher("/cart?method=show").forward(request, response);
    }
    public void clear(HttpServletRequest request,HttpServletResponse response) throws SQLException, ServletException, IOException {
        HttpSession session = request.getSession();
        //我们登录成功之后是将我们的信息存入到loginUser
        User user= (User) session.getAttribute("loginUser");
        if (user ==null){
            session.setAttribute("msg","添加购物车必须先登录！");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        //1.获取请求参数
        String uid=request.getParameter("uid");
        //2.调用业务逻辑进行清空购物车
        cartService.clearCart(uid);
        //3.转发到展示的方法中
        request.getRequestDispatcher("/cart?method=show").forward(request, response);
    }

}
