package cn.wenhe9.myshop.controller;

import cn.wenhe9.myshop.domain.constant.SystemConstants;
import cn.wenhe9.myshop.utils.ValidateCodeUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @description: 验证码控制类 使用awt生成验证码图形
 * @author: DuJinliang
 * @create: 2022/10/29
 */
@WebServlet("/code")
public class CodeController extends BaseServlet{
    /**
     * 使用awt创建验证码并存放到session中
     */
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");
        response.setHeader("expires", "0");
        int width = 80;
        int height = 30;
        BufferedImage image = new BufferedImage(width, height, 1);
        Graphics g = image.getGraphics();
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, width, height);
        String checkCode = this.getCheckCode();
        request.getSession().setAttribute(SystemConstants.CHECK_CODE, checkCode);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("黑体", 1, 24));
        g.drawString(checkCode, 15, 25);
        ImageIO.write(image, "PNG", response.getOutputStream());
    }

    /**
     * 生成随机字符串
     */
    private String getCheckCode() {
        String base = "0123456789ABCDEFGabcdefg";
        int size = base.length();
        Random r = new Random();
        StringBuffer sb = new StringBuffer();

        for(int i = 1; i <= 4; ++i) {
            int index = r.nextInt(size);
            char c = base.charAt(index);
            sb.append(c);
        }

        return sb.toString();
    }
}
