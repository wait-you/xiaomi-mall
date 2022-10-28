package cn.wenhe9.myshop.controller;

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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @description: 用户控制器
 * @author: DuJinliang
 * @create: 2022/10/28
 */
@WebServlet("/user")
public class UserController extends BaseServlet{

    private UserService userService;

    public UserController() {
        userService = new UserServiceImpl();
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

//    public String login(HttpServletRequest request,HttpServletResponse response) throws SQLException {
//        //1.获取请求参数
//        String username=request.getParameter("username");
//        String password=request.getParameter("password");
//        String code=request.getParameter("code");
//
//        //正确的验证码
//        HttpSession session=request.getSession();
//        String codestr=(String) session.getAttribute("code");
//
//        //2.判断验证码是否正确
//        if(code ==null || !code.equalsIgnoreCase(codestr)){
//            //给一个提示
//            request.setAttribute("msg" ,"验证码错误");
//            //将页面返回到我们的登录页面
//            return Constants.FORWARD+"/login.jsp";
//        }
//        //3.调用业务逻辑判断账号和密码
//        UserService userService=new UserServiceImpl();
//        User user=userService.login(username,password);
//
//        //4.响应
//        if (user ==null){
//            //提示
//            request.setAttribute("msg" ,"账号或者密码错误");
//            //返回我们的登录页面
//            return Constants.FORWARD+"/login.jsp";
//        }
//        session.setAttribute("loginUser",user);
//
//        return Constants.FORWARD+"/index.jsp";
//    }
//    public String logOut(HttpServletRequest request,HttpServletResponse response){
//        //1.清空session中的用户数据
//        HttpSession session=request.getSession();
//        session.removeAttribute("loginUser");
//
//        //2.转发到登录页面
//        request.setAttribute("msg","注销登录成功");
//
//        return Constants.FORWARD+"/login.jsp";
//    }
}
