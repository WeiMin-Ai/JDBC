package com.java2.preparedstatement.crud;

import com.java.util.JDBCUtils;
import com.java3.bean.Customers;
import com.java3.bean.Order;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * 使用PreparedStatement实现针对不同表的通用查询操作
 */

public class PreparedStatementQueryTest {
    @Test
    public void testGetInstance() {
        String sql1 = "select * from customers where name = ?";
        Customers customers = getInstance(Customers.class, sql1, "weimin@1234");
        System.out.println(customers);

        String sql = "select ord_id orId, ord_name ordName from `order` where ord_id = ?";
        Order order = getInstance(Order.class, sql, "weimin");
        System.out.println(order);
    }

    public <T> T getInstance(Class<T> clazz, String sql, Object... args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // 1. 获取连接
            connection = JDBCUtils.getConnection();

            // 2. 预编译SQL语句
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }

            // 3. 执行SQL，并返回结果集
            resultSet = preparedStatement.executeQuery();

            // 4. 获取结果集的元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            // 5. 处理结果集
            // 判断结果集的下一条是否有数据，如果有数据则返回true，并指针下移。如果返回flase指针不会下移。
            if (resultSet.next()) {
                T t = clazz.newInstance();
                // 6. 获取当前数据各个字段的值
                for (int i = 0; i < columnCount; i++) {
                    Object value = resultSet.getObject(i + 1);

                    // 获取每个列的列名
                    String columnName = metaData.getColumnName(i + 1);

                    // 给customers对象指定的columnName，赋值为columnValue，通过反射。
                    Field declaredField = clazz.getDeclaredField(columnName);
                    declaredField.setAccessible(true);
                    declaredField.set(t, value);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 7. 关闭资源
            JDBCUtils.closeResource(connection, preparedStatement, resultSet);
        }

        return null;
    }
}
