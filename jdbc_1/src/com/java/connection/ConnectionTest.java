package com.java.connection;

import org.junit.Test;
import com.mysql.jdbc.Driver;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionTest {

    /**
     * 方式一：基本连接
     * @throws SQLException
     */
    @Test
    public void testConnection1() throws SQLException {
        // 1. 获取Driver实现类对象
        Driver driver = new com.mysql.jdbc.Driver();

        // 2. 配置连接信息
        String url = "jdbc:mysql://localhost:3306/test";
        Properties info = new Properties();
        info.put("user", "root");
        info.put("password", "kh26hv2XAjrzg1/sWYUxlkJKiQj1ltzE");

        // 3. 使用配置信息建立连接
        Connection connect = driver.connect(url, info);
        System.out.println(connect);
    }

    /**
     * 方式二：对方式一的迭代
     * 为什么要迭代？代码最好不要出现任何第三方API，所以通过反射技术实现，让程序具有更好的可移植性。
     * @throws SQLException
     */
    @Test
    public void testConnection2() throws Exception {
        // 1. 获取Driver实现类对象，通过反射实现。
        Class aClass = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) aClass.newInstance();

        // 2. 配置连接信息
        String url = "jdbc:mysql://localhost:3306/test";
        Properties info = new Properties();
        info.put("user", "root");
        info.put("password", "kh26hv2XAjrzg1/sWYUxlkJKiQj1ltzE");

        // 3. 建立连接
        Connection connect = driver.connect(url, info);

        System.out.println(connect);
    }

    /**
     * 方式三：使用DriverManager替换Driver
     */
    @Test
    public void testConnection3() throws Exception {
        // 1. 获取Driver实现类对象
        Class aClass = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) aClass.newInstance();

        // 2. 配置连接信息
        String url = "jdbc:mysql://localhost:3306/test";
        Properties info = new Properties();
        info.put("user", "root");
        info.put("password", "kh26hv2XAjrzg1/sWYUxlkJKiQj1ltzE");

        // 2. 注册驱动
        DriverManager.registerDriver(driver);

        // 3. 建立连接
        Connection connection = DriverManager.getConnection(url, info);

        System.out.println(connection);
    }

    /**
     * 方式四：在方式三的基础上进行优化。
     */
    @Test
    public void testConnection4() throws Exception {
        // 1. 加载Driver实现类对象
        Class.forName("com.mysql.jdbc.Driver");

        // 2. 配置连接信息
        String url = "jdbc:mysql://localhost:3306/test";
        Properties info = new Properties();
        info.put("user", "root");
        info.put("password", "kh26hv2XAjrzg1/sWYUxlkJKiQj1ltzE");

        // 注册驱动，优化了这一个步骤，为什么可以省略？mysql驱动帮我们注册了。具体可以查看com.mysql.jdbc.Driver源码的静态方法。
        // DriverManager.registerDriver(driver);

        // 3. 建立连接
        Connection connection = DriverManager.getConnection(url, info);

        System.out.println(connection);
    }

    /**
     * 方式五：优化连接配置通过配置文件实现，通过读取配置文件方式获取连接。
     * 实现了数据与代码分离，实现解耦。
     */
    @Test
    public void testConnection5() throws Exception {
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
        Connection connection = DriverManager.getConnection(url, user, password);

        System.out.println(connection);
    }
}
