package cn.wenhe9.myshop.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DruidUtils {
	/**
	 * 获取数据源 Druid配置文件信息
	 */
	private static volatile DataSource ds  = null;

	/**
	 * 对外提供一个get方法 可以通过外部访问到DataSource
	 * 双重检查懒汉式单利模式
	 */
	public static DataSource getDataSource(){
		if (ds == null) {
			synchronized (DruidUtils.class) {
				if (ds == null) {
					Properties pro = new Properties();

					try {
						pro.load(DruidUtils.class.getClassLoader().getResourceAsStream("druid.properties"));

						DataSource ds = DruidDataSourceFactory.createDataSource(pro);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}

				}
			}
		}
		return ds;
	}
	//获取连接
	public static Connection getConnection(){
		try {
			if (ds == null) {
				ds = getDataSource();
			}
			return ds.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException("服务器繁忙....");
		}
	}
	//释放资源
	public static void release(ResultSet rs,Statement stmt,Connection conn){
		try {
			if(rs!=null){
				rs.close();
			}
			if(stmt!=null){
				stmt.close();
			}
			if(conn!=null){
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
