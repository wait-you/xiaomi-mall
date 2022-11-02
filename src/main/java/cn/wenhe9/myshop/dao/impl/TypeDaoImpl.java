package cn.wenhe9.myshop.dao.impl;

import cn.wenhe9.myshop.dao.TypeDao;
import cn.wenhe9.myshop.domain.constant.DatabaseConsts;
import cn.wenhe9.myshop.domain.constant.SystemConstants;
import cn.wenhe9.myshop.domain.entity.Type;
import cn.wenhe9.myshop.utils.DruidUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 类别数据访问层
 * @author: DuJinliang
 * @create: 2022/11/1
 */
public class TypeDaoImpl implements TypeDao {
    @Override
    public List<Type> selectAll() throws SQLException {

        Connection conn = DruidUtils.getConnection();

        String sql= "select t_id, t_name, t_info from type limit 5";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        ResultSet resultSet = pstmt.executeQuery();

        List<Type> typeList = new ArrayList<>();

        while (resultSet.next()) {
            typeList.add(
                    Type.builder()
                    .tId(resultSet.getInt(DatabaseConsts.TypeTable.COLUMN_T_ID))
                    .name(resultSet.getString(DatabaseConsts.TypeTable.COLUMN_T_NAME))
                    .info(resultSet.getString(DatabaseConsts.TypeTable.COLUMN_T_INFO))
                    .build()
            );
        }

        DruidUtils.release(resultSet, pstmt, conn);

        return typeList;
    }
}
