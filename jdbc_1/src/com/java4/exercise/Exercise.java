package com.java4.exercise;

import com.java.util.InputDataUtils;
import com.java.util.JDBCUtils;
import com.java2.preparedstatement.crud.PreparedStatementQueryTest;
import com.java3.bean.Examstudent;
import com.java3.bean.InputData;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


public class Exercise {


    /**
     * 练习题：从控制台向数据表Customers中插入一条数据
     */
    @Test
    public void exercise1() throws Exception {
        InputData inputData1 = InputDataUtils.getInputData();
        InputData inputData = new InputData();
        inputData.setName("WeiMin");
        inputData.setEmail("177242941@qq.com");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = dateFormat.parse("1990-12-12");
        inputData.setBirth(parse);

        String sql = "insert into customers(cust_name,email,birth) values(?,?,?)";

        Connection connection = JDBCUtils.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1, inputData.getName());
        preparedStatement.setObject(2, inputData.getEmail());
        preparedStatement.setObject(3, inputData.getBirth());

        int res = preparedStatement.executeUpdate();
        if (res > 0) {
            System.out.println("插入成功！");
        }

        JDBCUtils.closeResource(connection, preparedStatement);
    }

    @Test
    public void exercise2() throws Exception {
        // 创建Scanner对象
        Scanner scanner = new Scanner(System.in);
        // 类型
        System.out.print("Type: ");
        String type = scanner.nextLine();
        // 身份证号码
        System.out.print("IDCard: ");
        String iDCard = scanner.nextLine();
        // 准考证号
        System.out.print("ExamCard: ");
        String examCard = scanner.nextLine();
        // 学生姓名
        System.out.print("StudentName: ");
        String studentName = scanner.nextLine();
        // 地区
        System.out.print("Location: ");
        String location = scanner.nextLine();
        // 成绩
        System.out.print("Grade: ");
        String grade = scanner.nextLine();

        // 关闭Scanner对象
        scanner.close();

        String sql = "insert into examstudent(Type, IDCard, ExamCard, StudentName, Location, Grade) value (?,?,?,?,?,?)";
        Connection connection = JDBCUtils.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, type);
        preparedStatement.setString(2, iDCard);
        preparedStatement.setString(3, examCard);
        preparedStatement.setString(4, studentName);
        preparedStatement.setString(5, location);
        preparedStatement.setString(6, grade);

        int res = preparedStatement.executeUpdate();
        if (res > 0) {
            System.out.println("插入成功!");
        }
    }

    /**
     * 输入身份证号或者准考证号可以查询到学生的基本信息
     *
     * @throws Exception
     */
    @Test
    public void exercise3() throws Exception {
        // 创建Scanner对象
        Scanner scanner = new Scanner(System.in);

        System.out.print("请选择您要输入的类型: \na:准考证号\nb:身份证号\n");
        String type = scanner.nextLine();
        switch (type) {
            case "a":
                System.out.print("请输入准考证号：");
                String examCard = scanner.nextLine();
                String sql = "select FlowId flowId,Type type, IDCard idCard, ExamCard examCard, StudentName studentName, Location location, Grade grade from examstudent where ExamCard = ?";
                Examstudent instance = PreparedStatementQueryTest.getInstance(Examstudent.class, sql, examCard);
                System.out.println(instance);
                break;
            case "b":
                System.out.println("");
                break;
            default:
                System.out.println("您的输入有误！请重新进入程序。");
                break;
        }
    }
}
