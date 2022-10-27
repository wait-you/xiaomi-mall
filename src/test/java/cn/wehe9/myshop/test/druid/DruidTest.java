package cn.wehe9.myshop.test.druid;

import cn.wenhe9.myshop.domain.constant.DatabaseConsts;
import cn.wenhe9.myshop.domain.entity.Product;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

/**
 * @description: 测试druid的使用
 * @author: DuJinliang
 * @create: 2022/10/27
 */
public class DruidTest {
    /**
     * 测试druid基本使用
     */
    @Test
    public void test01() {
        String sql = "select * from product";

        Connection conn = null;

        PreparedStatement pstmt = null;

        try {
            Properties pro = new Properties();

            pro.load(DruidTest.class.getClassLoader().getResourceAsStream("druid.properties"));

            DataSource ds = DruidDataSourceFactory.createDataSource(pro);

            conn = ds.getConnection();

            pstmt = conn.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Product product = Product.builder()
                        .pId(rs.getInt(DatabaseConsts.ProductTable.COLUMN_P_ID))
                        .tId(rs.getInt(DatabaseConsts.ProductTable.COLUMN_T_ID))
                        .name(rs.getString(DatabaseConsts.ProductTable.COLUMN_P_NAME))
                        .image(rs.getString(DatabaseConsts.ProductTable.COLUMN_P_IMAGE))
                        .time(rs.getDate(DatabaseConsts.ProductTable.COLUMN_P_TIME))
                        .state(rs.getInt(DatabaseConsts.ProductTable.COLUMN_P_STATE))
                        .info(rs.getString(DatabaseConsts.ProductTable.COLUMN_P_INFO))
                        .price(rs.getBigDecimal(DatabaseConsts.ProductTable.COLUMN_P_PRICE))
                        .build();

                System.out.println(product);
            }

//            ArrayList<Object> arrayList = switchStringToObject(rs, Product.class);
//
//            for (Object o : arrayList) {
//                Product product = (Product) o;
//                System.out.println(product);
//            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 虽然可以将查询到的数据转换成对象，但是一旦添加新的属性和修改属性的名字都会出现异常
     * @param rs
     * @param clz
     * @return
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    @Deprecated
    protected ArrayList<Object> switchStringToObject(ResultSet rs, Class clz) throws SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Field[] declaredFields = clz.getDeclaredFields();

        ArrayList<Object> arrayList = new ArrayList<>();

        while (rs.next()) {
            Object instance = clz.newInstance();

            for (Field field : declaredFields) {
                String fieldName = field.getName();
                fieldName = fieldName.substring(0, 1) + "_" + fieldName.substring(1);
                Object object = rs.getObject(fieldName);
                Method method = clz.getMethod("set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1), field.getType());

                method.invoke(instance, object);

                arrayList.add(instance);
            }
        }

        return arrayList;
    }
}
