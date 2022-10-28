package cn.wehe9.myshop.test.code;

import cn.wenhe9.myshop.utils.ValidateCodeUtils;
import org.junit.Test;

/**
 * @description: 随机生成验证码测试类
 * @author: DuJinliang
 * @create: 2022/10/28
 */
public class ValidateCodeTest {
    /**
     * 测试随机生成验证码工具类的可行性
     */
    @Test
    public void test01() {
        String code = ValidateCodeUtils.generateValidateCode4String(4);
        System.out.println(code);
    }
}
