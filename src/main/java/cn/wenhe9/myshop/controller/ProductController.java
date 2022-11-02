package cn.wenhe9.myshop.controller;

import cn.wenhe9.myshop.dao.impl.ProductDaoImpl;
import cn.wenhe9.myshop.domain.constant.SystemConstants;
import cn.wenhe9.myshop.domain.entity.PageBean;
import cn.wenhe9.myshop.domain.entity.Product;
import cn.wenhe9.myshop.service.ProductService;
import cn.wenhe9.myshop.service.impl.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @description: 产品控制类
 * @author: DuJinliang
 * @create: 2022/11/1
 */

@WebServlet("/product")
public class ProductController extends BaseServlet{

    private ProductService productService;

    public ProductController() {
        this.productService = new ProductServiceImpl(new ProductDaoImpl());
    }

    public void show(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        //1.接受请求参数 tid 类别id
        String tid = request.getParameter("tid");
        //重前端获取当前页数和页容量
        //页容量前端没有值，我们给一个默认值6
        int pageSize = SystemConstants.PAGE_SIZE;

        //当前页数
        String currentPage = request.getParameter("currentPage");

        //设置当前页数
        int page = SystemConstants.CURRENT_PAGE;

        if (currentPage !=null){
            //当有当前页数传入时，当前页数就为currentPage，没有就默认为1。
            page = Integer.parseInt(currentPage);
        }

        PageBean<Product> pageBean = productService.findByPage(tid, page, pageSize);

        //3.响应即可(把pageBean放到共享域中，转发到商品对应的列表页面)
        request.setAttribute("pageBean",pageBean);

        request.getRequestDispatcher("/goodsList.jsp").forward(request, response);
    }

    public void detail(HttpServletRequest request,HttpServletResponse response) throws SQLException, ServletException, IOException {
        String pid=request.getParameter("pid");

        Product product = productService.findProductByPid(pid);

        if (Objects.isNull(product)) {
            response.sendRedirect(SystemConstants.NOT_FOUND_ERROR_PAGE);
            return;
        }

        request.setAttribute("product",product);

        request.getRequestDispatcher("/goodsDetail.jsp").forward(request, response);
    }

}