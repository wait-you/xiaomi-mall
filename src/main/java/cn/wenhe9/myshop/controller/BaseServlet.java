package cn.wenhe9.myshop.controller;

import cn.wenhe9.myshop.domain.constant.SystemConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @description: servlet通用父类，定义
 * @author: DuJinliang
 * @create: 2022/10/28
 */

public class BaseServlet extends HttpServlet {
    /**
     * 这个方法在get和post之前执行，所以可以在这里根据参数使用反射来获取对应的servlet来执行逻辑
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String methodName = req.getParameter(SystemConstants.TAG);

        try {
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, req, resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            resp.sendRedirect(SystemConstants.NOT_FOUND_ERROR_PAGE);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            resp.sendRedirect(SystemConstants.DEFAULT_ERROR_PAGE);
        }

    }

    public void writeValue(Object obj, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), obj);
    }

    public String writeValueAsString(Object obj, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
}
