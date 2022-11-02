package cn.wenhe9.myshop.controller;

import cn.wenhe9.myshop.dao.impl.UserDaoImpl;
import cn.wenhe9.myshop.domain.constant.SystemConstants;
import cn.wenhe9.myshop.domain.entity.User;
import cn.wenhe9.myshop.service.UserService;
import cn.wenhe9.myshop.service.impl.UserServiceImpl;
import cn.wenhe9.myshop.utils.Base64Utils;
import cn.wenhe9.myshop.utils.MD5Utils;
import cn.wenhe9.myshop.utils.RandomUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @description: 用户控制类
 * @author: DuJinliang
 * @create: 2022/10/28
 */
@WebServlet("/user")
public class UserController extends BaseServlet{

    private UserService userService;

    public UserController() {
        userService = new UserServiceImpl(new UserDaoImpl());
    }

    public void check(HttpServletRequest request , HttpServletResponse response) throws SQLException, IOException {

        //1.获取用户名
        String username=request.getParameter("username");
        if (StringUtils.isBlank(username)){
            writeValue("1", response);
        }

        //2.调用业务逻辑判断用户名是否存在
        boolean ifExist = userService.checkUsernameExist(username);

        //3.响应
        if (ifExist){
            //用户名存在
            writeValue("1", response);
        }

        writeValue("0", response);
    }

    public void register(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        //1.获取用户信息
        Map<String,String[]> parameterMap = request.getParameterMap();

        String password = parameterMap.get("password")[0];
        String confirmPassword = parameterMap.get("confirmPassword")[0];

        if (StringUtils.isAnyBlank(password, confirmPassword) || !password.equals(confirmPassword)) {
            request.setAttribute("registerMsg", "两次提交的密码不匹配");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        if (password.length() < 6) {
            request.setAttribute("registerMsg", "密码的长度不符合要求");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        String pattern = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        String email = parameterMap.get("email")[0];

        boolean isMatch = Pattern.matches(pattern, email);
        if (!isMatch) {
            request.setAttribute("registerMsg", "邮箱格式不符合要求");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        //首先先去定义一个user
        User user = User.builder().build();
        try {
            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            response.sendRedirect(SystemConstants.DEFAULT_ERROR_PAGE);
            return;
        }

        //2.完善用户
        user.setStatus("0");
        user.setRole(0);

        //激活码
        user.setCode(RandomUtils.createActive());

        //需要对密码进行一个处理
        user.setPassword(MD5Utils.md5(user.getPassword()));

        try {
            userService.registerUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("registerMag","注册失败！");
            //发生异常，返回到我们注册
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        //4.响应
        request.getRequestDispatcher("/registerSuccess.jsp").forward(request, response);
    }

    /**
     * 激活方法
     */
    public void active(HttpServletRequest request,HttpServletResponse response) throws SQLException, ServletException, IOException {
        //1.获取激活码
        //已经转成base64
        String c = request.getParameter("c");
        //base64翻转,decode反转
        String code= Base64Utils.decode(c);
        //2.调用激活的业务逻辑
        int i = userService.activeUser(code);
        //3.响应（激活失败（code没有找到） 已经激活  激活成功）
        if (i==SystemConstants.ACTIVE_FAIL){
            request.setAttribute("msg","未激活成功！");
        }else if(i==SystemConstants.ACTIVE_SUCCESS) {
            request.setAttribute("msg","激活成功，请登录！");
        }else {
            request.setAttribute("msg","已经激活");
        }
        request.getRequestDispatcher("/message.jsp").forward(request, response);
    }

    public void login(HttpServletRequest request,HttpServletResponse response) throws SQLException, ServletException, IOException {
        //1.获取请求参数
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String auto=request.getParameter("auto");
        String code=request.getParameter("code");

        //正确的验证码
        HttpSession session=request.getSession();
        String codeStr= (String) session.getAttribute(SystemConstants.CHECK_CODE);

        //2.判断验证码是否正确
        if(code ==null || !code.equalsIgnoreCase(codeStr)){
            //给一个提示
            request.setAttribute("msg" ,"验证码错误");
            //将页面返回到我们的登录页面
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }
        //3.调用业务逻辑判断账号和密码
        User user = userService.login(username,password);

        //4.判断用户是否存在
        if (Objects.isNull(user)){
            //提示
            request.setAttribute("msg" ,"账号或者密码错误");
            //返回我们的登录页面
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        //5.判断用户是否激活
        if (SystemConstants.USER_NOT_ACTIVE.equals(user.getStatus())) {
            //提示
            request.setAttribute("msg" ,"该账号尚未激活，请先激活后登录");
            //返回我们的登录页面
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        session.setAttribute("loginUser",user);

        /**
         * 自动登录
         */
        //1.判断是否勾选自动登录
        if (Objects.isNull(auto)){
            //没有勾选！
            //将本地浏览器的cookie'清空'
            //创建一个cookie
            //定义一个常量autoUser,value里面没有值主要是一个覆盖功能
            Cookie cookie = new Cookie(SystemConstants.AUTO_NAME,"");
            //表示当前项目下所有的访问资源servlet都可以访问这个cookie
            cookie.setPath("/");
            cookie.setMaxAge(0);
            //写回cookie
            response.addCookie(cookie);

        }else {
            //自动登录数据存储 2周
            //将cookie存入到本地
            String content= username+":"+password;
            //用Base64转一下码,就无法直接看出来了
            content =Base64Utils.encode(content);
            //传入我们的账号密码
            Cookie cookie=new Cookie(SystemConstants.AUTO_NAME, content);
            //表示当前项目下所有的访问资源servlet都可以访问这个cookie
            cookie.setPath("/");
            //两周
            cookie.setMaxAge(14*24*60*60);
            //写回cookie
            response.addCookie(cookie);
        }

        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
    public void logOut(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        //1.清空session中的用户数据
        HttpSession session = request.getSession();
        session.removeAttribute("loginUser");

        //2.转发到登录页面
        request.setAttribute("msg","注销登录成功");

        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}
