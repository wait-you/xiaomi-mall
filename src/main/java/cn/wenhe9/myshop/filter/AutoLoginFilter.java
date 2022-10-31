package cn.wenhe9.myshop.filter;

import cn.wenhe9.myshop.domain.constant.SystemConstants;
import cn.wenhe9.myshop.domain.entity.User;
import cn.wenhe9.myshop.service.UserService;
import cn.wenhe9.myshop.service.impl.UserServiceImpl;
import cn.wenhe9.myshop.utils.Base64Utils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @description: 自动登录过滤器
 * @author: DuJinliang
 * @create: 2022/10/31
 */
@WebFilter("/login.jsp")
public class AutoLoginFilter implements Filter {

    private UserService userService;

    public AutoLoginFilter() {
        this.userService = new UserServiceImpl();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        //处理自动登录
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        //获取Cookie
        Cookie[] cookies = request.getCookies();

        if (Objects.nonNull(cookies)){
            //本地存储了cookie，但不一定是存储我们账号密码的cookie
            //为了接存储账号密码的数据
            String content = null;
            //遍历一下
            for (Cookie cookie : cookies) {
                //如果找到存储自动登录cookie的名字就读取存储的账号和密码
                if (SystemConstants.AUTO_NAME.equals(cookie.getName())) {
                    content = cookie.getValue();
                }
            }
            if (Objects.nonNull(content)){
                //读取到了存储用户和密码的cookie
                //同样还是要判断用户和密码是否正确（因为账号密码可能你在其他电脑上进行了修改）

                //这里不要忘了再用Base64转回来
                content = Base64Utils.decode(content);

                //到我们的UserController中将我们的"："用常量代替
                //获取账号和密码(通过；进行切割)切割成一个数据数组
                String[] split = content.split(SystemConstants.FLAG);

                //账号
                String username = split[0];
                //密码
                String password = split[1];

                //调用我们登录的业务逻辑(传入我们的账号和密码)
                try {
                    User user = userService.login(username, password);
                    if (Objects.nonNull(user)){
                        //可以自动登录
                        //然后就是将账号和密码存储到session中
                        HttpSession session = request.getSession();
                        session.setAttribute("loginUser",user);
                        //最后跳转到首页
                        HttpServletResponse response = (HttpServletResponse) servletResponse;
                        //做一个重定向即可(request.getContextPath()获取项目的根路径)
                        response.sendRedirect(request.getContextPath()+"/index.jsp");
                    }else {
                        //就进行放行（到我们的手动登录）
                        //放行（放行连filterChain）
                        filterChain.doFilter(servletRequest,servletResponse);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }


            }else {
                //没有读取到，同样是放行
                //放行（放行连filterChain）
                filterChain.doFilter(servletRequest,servletResponse);
            }


        }else {
            //本地没有cookie，放行即可
            //放行（放行连filterChain）
            filterChain.doFilter(servletRequest,servletResponse);
        }

    }

    @Override
    public void destroy() {

    }
}
