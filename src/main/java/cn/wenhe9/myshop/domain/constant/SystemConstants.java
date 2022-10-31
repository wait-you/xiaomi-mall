package cn.wenhe9.myshop.domain.constant;

/**
 * @description: 系统常量类
 * @author: DuJinliang
 * @create: 2022/10/28
 */
public class SystemConstants {
    /**
     * 声明method标识
     */
    public static final String TAG = "method";

    public static final String FORWARD = "forward:";

    public static final String REDIRECT="redirect:";

    public static final String FLAG=":";

    public static final String INDEX="index";

    /**
     * 验证码存放常量
     */
    public static final String CHECK_CODE = "CHECK_CODE";

    /**
     * 定义用户模块涉及的常量
     */
    public static final String HAS_USER="1";

    public  static String NOT_HAS_USER = "0";

    /**
     * 用户的状态
     */
    public static final String USER_ACTIVE ="1";

    public static final String USER_NOT_ACTIVE = "0";

    /**
     * 用户
     */
    public static final int ROLE_CUSTOMER = 0;

    /**
     * 管理员
     */
    public static final int ROLE_ADMIN= 1;

    /**
     * 用户模块激活状态
     */
    public static final int  ACTIVE_FAIL= 0;
    public static final int  ACTIVE_SUCCESS= 1;
    public static final int  ACTIVE_ALREADY= 2;

    /**
     * 自动登录cookie名
     */
    public static  final String AUTO_NAME="autoUser";


    public static final String SYSTEM_PATH = "/myshop";

    /**
     * 404异常页面
     */
    public static final String NOT_FOUND_ERROR_PAGE = SYSTEM_PATH + "/error/404.html";

    /**
     * 服务器出错异常页面
     */
    public static final String SYSTEM_ERROR_PAGE = SYSTEM_PATH + "/error/500.jsp";

    /**
     * 默认错误页面
     */
    public static final String DEFAULT_ERROR_PAGE = SYSTEM_PATH + "/error/error.jsp";
}
