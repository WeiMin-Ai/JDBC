package com.java2.preparedstatement.crud;

import com.java1.connection.ConnectionTest;
import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * 使用PreparedStatement来替换Statement，实现对数据表的crud操作。
 * cud：稍微简单。
 * r：较为复杂。
 */

public class PreparedStatementUpdateTest {
    @Test
    public void testInsert() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // 1. 读取配置文件中的基本连接信息
            InputStream resourceAsStream = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
            Properties properties = new Properties();
            properties.load(resourceAsStream);
            String user = properties.getProperty("user");
            String password = properties.getProperty("password");
            String url = properties.getProperty("url");
            String driverClass = properties.getProperty("driverClass");

            // 2. 加载驱动
            Class.forName(driverClass);

            // 3. 获取连接
            connection = DriverManager.getConnection(url, user, password);

            // 4. 预编译SQL语句，返回PreparedStatement的实例。
            String sql = "insert into customers(name,email,birth) values(?,?,?)";
            preparedStatement = connection.prepareStatement(sql);

            // 5. 填充占位符
            preparedStatement.setString(1, "scott");
            preparedStatement.setString(2, "scott@gmail.com");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = simpleDateFormat.parse("1900-01-01");
            preparedStatement.setDate(3, new java.sql.Date(date.getTime()));

            // 6. 执行操作
            boolean execute = preparedStatement.execute();
            if (!execute) {
                System.out.println("插入成功！");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 7. 资源关闭
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }


    }
}
