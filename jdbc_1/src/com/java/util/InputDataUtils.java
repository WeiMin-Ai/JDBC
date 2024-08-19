package com.java.util;

import com.java3.bean.InputData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class InputDataUtils {
    public static InputData getInputData() {
        // 创建Scanner对象
        Scanner scanner = new Scanner(System.in);
        InputData inputData = new InputData();

        System.out.print("请输入你的名字: ");
        String name = scanner.nextLine();

        System.out.print("请输入你的邮箱: ");
        String emain = scanner.nextLine();

        System.out.print("请输入你的生日: ");
        String birth = scanner.nextLine();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parse = dateFormat.parse(birth);
            inputData.setName(name);
            inputData.setEmail(emain);
            inputData.setBirth(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 关闭Scanner对象
        scanner.close();

        return inputData;
    }
}
