package com.java2.preparedstatement.crud;

import com.java.util.JDBCUtils;
import com.java3.bean.Customers;
import com.java3.bean.Order;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;

public class OrderForQuery {
    public Order OrderForQuery(String sql, Object... args) {
        Order order = new Order();
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JDBCUtils.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }

            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            if (resultSet.next()) {
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    Object value = resultSet.getObject(i + 1);

                    String columnLabel = metaData.getColumnLabel(i + 1);

                    Field declaredField = Order.class.getDeclaredField(columnLabel);
                    declaredField.setAccessible(true);
                    declaredField.set(order, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, preparedStatement, resultSet);
        }

        return order;
    }

    @Test
    public void testOrderForQuery1() {
        String sql = "select ord_id orId, ord_name ordName from `order` where ord_id = ?";
//        String sql = "select ord_id, ord_name from `order` where ord_id = ?";
        Order order = this.OrderForQuery(sql, "weimin");
        System.out.println(order);
    }
}
