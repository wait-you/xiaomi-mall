package cn.wenhe9.myshop.domain.constant;

/**
 * @description: 数据库表字段常量类
 * @author: DuJinliang
 * @create: 2022/10/27
 */
public class DatabaseConsts {

    /**
     * 地址表
     */
    public static class AddressTable {
        /**
         * 地址实体的唯一主键列
         */
        public static final String COLUMN_A_ID = "a_id";
        /**
         * 用户实体的主键属性
         */
        public static final String COLUMN_U_ID = "u_id";
        /**
         * 地址的收件人
         */
        public static final String COLUMN_A_NAME = "a_name";
        /**
         * 收件人电话
         */
        public static final String COLUMN_A_PHONE = "a_phone";
        /**
         * 收货人详细地址
         */
        public static final String COLUMN_A_DETAIL = "a_detail";
        /**
         * 是否是默认地址
         */
        public static final String COLUMN_A_STATE = "a_state";
    }

    /**
     * 购物车表
     */
    public static class CartTable {
        /**
         * 购物车的唯一标识
         */
        public static final String COLUMN_C_ID = "c_id";
        /**
         * 用户实体的主键属性
         */
        public static final String COLUMN_U_ID = "u_id";
        /**
         * 商品的唯一主键
         */
        public static final String COLUMN_P_ID = "p_id";
        /**
         * 购物车小计
         */
        public static final String COLUMN_C_COUNT = "c_count";
        /**
         * 购物车商品数量
         */
        public static final String COLUMN_C_NUM = "c_num";
    }

    /**
     * 订单内部的具体商品展示表
     */
    public static class ItemTable {
        /**
         * 订单项的唯一标识
         */
        public static final String COLUMN_I_ID = "i_id";
        /**
         * 订单编号是字符串类型但是也是唯一标识
         */
        public static final String COLUMN_O_ID = "o_id";
        /**
         * 商品的唯一主键
         */
        public static final String COLUMN_P_ID = "p_id";
        /**
         * 订单项的小计
         */
        public static final String COLUMN_C_COUNT = "i_count";
        /**
         * 订单项的数量
         */
        public static final String COLUMN_C_NUM = "i_num";
    }

    /**
     * 订单表
     */
    public static class OrderTable {
        /**
         * 订单编号是字符串类型但是也是唯一标识
         */
        public static final String COLUMN_O_ID = "o_id";
        /**
         * 用户实体的主键属性
         */
        public static final String COLUMN_U_ID = "u_id";
        /**
         * 地址实体的唯一主键列
         */
        public static final String COLUMN_A_ID = "a_id";
        /**
         * 订单的总金额
         */
        public static final String COLUMN_O_COUNT = "o_count";
        /**
         * 订单的详细时间
         */
        public static final String COLUMN_O_TIME = "o_time";
        /**
         * 订单状态
         */
        public static final String COLUMN_O_STATE = "o_state";
    }

    /**
     * 商品表
     */
    public static class ProductTable {
        /**
         * 商品的唯一主键
         */
        public static final String COLUMN_P_ID = "p_id";
        /**
         * 类别的主键id
         */
        public static final String COLUMN_T_ID = "t_id";
        /**
         * 商品的名称
         */
        public static final String COLUMN_P_NAME = "p_name";
        /**
         * 商品的上市时间
         */
        public static final String COLUMN_P_TIME = "p_time";
        /**
         * 商品图片的路径
         */
        public static final String COLUMN_P_IMAGE = "p_image";
        /**
         * 商品的热门指数
         */
        public static final String COLUMN_P_STATE = "p_state";
        /**
         * 商品的描述
         */
        public static final String COLUMN_P_INFO = "p_info";
        /**
         * 商品的价格
         */
        public static final String COLUMN_P_PRICE = "p_price";
    }

    /**
     * 类别表
     */
    public static class TypeTable {
        /**
         * 类别的主键id
         */
        public static final String COLUMN_T_ID = "t_id";
        /**
         * 类别的名称
         */
        public static final String COLUMN_T_NAME = "t_name";
        /**
         * 类别的描述
         */
        public static final String COLUMN_T_INFO = "t_info";
    }

    /**
     * 用户表
     */
    public static class UserTable {
        /**
         * 用户实体的主键属性
         */
        public static final String COLUMN_U_ID = "u_id";
        /**
         * 用户账号
         */
        public static final String COLUMN_U_NAME = "u_name";
        /**
         * 用户密码
         */
        public static final String COLUMN_U_PASSWORD = "u_password";
        /**
         * 用户的邮箱
         */
        public static final String COLUMN_U_EMAIL = "u_email";
        /**
         * 用户性别
         */
        public static final String COLUMN_U_SEX = "u_sex";
        /**
         * 用户的激活状态
         */
        public static final String COLUMN_U_STATUS = "u_status";
        /**
         * 邮件激活码
         */
        public static final String COLUMN_U_CODE = "u_code";
        /**
         * 用户角色
         */
        public static final String COLUMN_U_ROLE = "u_role";
    }

}
