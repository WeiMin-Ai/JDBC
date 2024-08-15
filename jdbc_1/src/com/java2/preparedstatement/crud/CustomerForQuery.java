package com.java2.preparedstatement.crud;

import com.java.util.JDBCUtils;
import com.java3.bean.Customers;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;

/**
 * 针对于Customer表的查询
 */

public class CustomerForQuery {

    /**
     * 针对Customers表通用查询操作
     */
    public Customers queryForCustomers(String sql, Object... args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Customers customers = new Customers();

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
                // 6. 获取当前数据各个字段的值
                for (int i = 0; i < columnCount; i++) {
                    Object value = resultSet.getObject(i + 1);

                    // 获取每个列的列名
                    String columnName = metaData.getColumnName(i + 1);

                    // 给customers对象指定的columnName，赋值为columnValue，通过反射。
                    Field declaredField = Customers.class.getDeclaredField(columnName);
                    declaredField.setAccessible(true);
                    declaredField.set(customers, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 7. 关闭资源
            JDBCUtils.closeResource(connection, preparedStatement, resultSet);
        }

        return customers;
    }

    @Test
    public void testQuery1() throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // 1. 获取连接
            connection = JDBCUtils.getConnection();

            // 2. 预编译SQL语句
            String sql = "select * from customers where name = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "weimin@1234");

            // 3. 执行SQL，并返回结果集
            resultSet = preparedStatement.executeQuery();

            // 4. 处理结果集
            // 判断结果集的下一条是否有数据，如果有数据则返回true，并指针下移。如果返回false指针不会下移。
            if (resultSet.next()) {
                // 5. 获取当前数据各个字段的值
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                Date birth = resultSet.getDate("birth");

                // 获取数据方式一：直接打印
                System.out.println(name + " " + email + " " + birth);

                // 获取数据方式二：存储到数组中
                Object[] date = new Object[]{name, email, birth};

                // 获取数据方式三：将数据封装为一个对象（推荐）
                Customers customers = new Customers(name, email, birth);
                System.out.println(customers);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 6. 关闭资源
            JDBCUtils.closeResource(connection, preparedStatement, resultSet);
        }
    }

    @Test
    public void testQueryForCustomers() throws Exception {
        String sql = "select * from customers where name = ?";
        Customers customers = this.queryForCustomers(sql, "weimin@1234");
        System.out.println(customers);
    }
}
