package com.java5.batch;

import com.java.util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * 使用preparedStatement实现批量数据的操作。
 */

public class InsertTest {
    @Test
    public void testInsert1() throws Exception {
        long starTime = System.currentTimeMillis();
        Connection connection = JDBCUtils.getConnection();
        String sql = "insert into goods(name) values(?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        for (int i = 1; i <= 20000; i++) {
            statement.setObject(1, "name_" + i);
            statement.execute();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("花费的时间：" + (endTime - starTime) + "ms");
        JDBCUtils.closeResource(connection, statement);
    }

    @Test
    public void testInsert2() throws Exception {
        long starTime = System.currentTimeMillis();
        Connection connection = JDBCUtils.getConnection();
        String sql = "insert into goods(name) values(?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        for (int i = 1; i <= 20000; i++) {
            statement.setObject(1, "name_" + i);
            // 1.攒SQL
            statement.addBatch();
            if (i % 500 == 0) {
                // 2. 执行Batch
                statement.executeBatch();
                // 3. 清空Batch
                statement.clearBatch();
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("花费的时间：" + (endTime - starTime) + "ms");
        JDBCUtils.closeResource(connection, statement);
    }
}
